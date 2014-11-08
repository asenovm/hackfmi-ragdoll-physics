package com.midtownmadness.bubblecombat.multiplay;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

public abstract class BaseStrategy implements MultiplayStrategy {

	private static final String TAG = BaseStrategy.class.getSimpleName();
	private Map<Integer, BluetoothSocket> connectPlayers = new HashMap<Integer, BluetoothSocket>();
	private LooperThread looper;
	protected Context context;
	private Handler uiHandler = new Handler(Looper.getMainLooper());
	protected MultiplayManager manager;

	private Queue<MultiplayEvent> queue = new ConcurrentLinkedQueue<MultiplayEvent>();

	protected BluetoothSocket otherPlayer;
	protected boolean stop;

	public BaseStrategy(Context context, MultiplayManager manager) {
		this.context = context;
		this.manager = manager;
		this.looper = new LooperThread();
		this.looper.setHandlerReadyListener(new Callback<Void>() {

			@Override
			public void call(Void argument) {
				BaseStrategy.this.onLooperReady();
			}
		});
	}

	public void onPlayerConnected(final int id, final BluetoothSocket socket) {
		connectPlayers.put(id, socket);
	}

	protected void sendMessage(Object payload, MessageType type,
			BluetoothSocket... players) {
		BluetoothMessage message = new BluetoothMessage(type, payload);
		toast("Sending message" + message.toString());
		for (BluetoothSocket playerSocket : players) {
			if (players == null) {
				Log.d(TAG, "ERROR!: ATTEMPTED TO FIND PLAYER WITH id "
						+ playerSocket
						+ " => NOT PRESENT IN MULTIPLAY MANAGER!");
				continue;
			}

			try {
				playerSocket.getOutputStream().write(message.toBytes());
				playerSocket.getOutputStream().flush();
				toast("Written to " + playerSocket.toString() + " and flushed");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	protected void sendEmptyMessage(MessageType type,
			BluetoothSocket... sockets) {
		sendMessage(new String(), type, sockets);
	}

	@Override
	public void start() {
		looper.start();
	}

	public Handler getHandler() {
		return looper.getHandler();
	}

	public LooperThread getThread() {
		return looper;
	}

	public Map<Integer, BluetoothSocket> getConnectedPlayers() {
		return connectPlayers;
	}

	public abstract void onLooperReady();

	protected BluetoothMessage obtainMessage(BluetoothSocket otherPlayer2) {
		BluetoothMessage message = new BluetoothMessage(MessageType.ERROR, null);
		try {
			ObjectInputStream inputStream = new ObjectInputStream(
					otherPlayer2.getInputStream());
			int messageTypeOrdinal = inputStream.readInt();
			MessageType type = MessageType.getByOrdinal(messageTypeOrdinal);
			Object payload = inputStream.readObject();
			message = new BluetoothMessage(type, payload);
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			return message;
		}

	}

	public void toast(final String text) {
		uiHandler.post(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(context, text, Toast.LENGTH_SHORT).show();

			}
		});
	}

	@Override
	public void action() {
		getHandler().post(new Runnable() {

			@Override
			public void run() {
				while (!stop) {
					// write one
					if (!queue.isEmpty()) {
						sendMessage(queue.poll(), MessageType.EVENT,
								otherPlayer);
					}

					// read one
					try {
						if (otherPlayer.getInputStream().available() > 0) {
							BluetoothMessage message = obtainMessage(otherPlayer);
							toast("I've received "
									+ (message.payload != null ? message.payload
											: "null"));
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	@Override
	public void add(MultiplayEvent event) {
		queue.offer(event);
	}
}
