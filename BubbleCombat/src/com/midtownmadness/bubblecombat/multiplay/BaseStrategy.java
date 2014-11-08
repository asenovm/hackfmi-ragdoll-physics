package com.midtownmadness.bubblecombat.multiplay;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.util.HashMap;
import java.util.Map;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;

public abstract class BaseStrategy implements MultiplayStrategy {

	/**
	 * {@value}
	 */
	private static final String TAG = BaseStrategy.class.getSimpleName();

	private Map<Integer, BluetoothSocket> connectPlayers = new HashMap<Integer, BluetoothSocket>();

	private LooperThread looper;

	public BaseStrategy() {
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
}
