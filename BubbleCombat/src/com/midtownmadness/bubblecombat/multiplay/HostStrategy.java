package com.midtownmadness.bubblecombat.multiplay;

import java.io.IOException;

import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;

import com.midtownmadness.bubblecombat.Settings;

public class HostStrategy extends BaseStrategy {

	protected static final String TAG = HostStrategy.class.getSimpleName();
	private BluetoothServerSocket serverSocket;
	private MultiplayManager manager;
	private BluetoothSocket otherPlayer;
	private Object monitor = new Object();
	private boolean goCommandGiven;

	public HostStrategy(Context context, BluetoothServerSocket serverSocket,
			MultiplayManager manager) {
		super(context);
		this.serverSocket = serverSocket;
		this.manager = manager;
	}

	@Override
	public void handshakeAndLoad() {
		getHandler().post(new Runnable() {

			@Override
			public void run() {
				try {
					otherPlayer = serverSocket.accept(Settings.HOST_TIMEOUT);
					toast("Accepted client socket " + otherPlayer.toString());

					// please don't do ugly stuff with my 'otherPlayer' field
					manager.onPlayerConnected(otherPlayer);

					while (true) {
						BluetoothMessage message = obtainMessage(otherPlayer);
						toast("message received" + message.toString());
					}

				} catch (IOException e) {
					// timeout
					e.printStackTrace();
					Log.e(TAG, "Timeout when hosting!");
				}

			}
		});
	}

	@Override
	public void onLooperReady() {
		handshakeAndLoad();
	}

	@Override
	public void close() {
		toast("close");
		try {
			if (otherPlayer != null) {
				otherPlayer.getInputStream().close();
				otherPlayer.getOutputStream().close();
				otherPlayer.close();
			}
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void commenceGame(final MultiplayerGame game) {
		BluetoothMessage message = obtainMessage(otherPlayer);
		toast("Player " + otherPlayer.getRemoteDevice().getName()
				+ "has joined!");

		// await GO order
		try {
			while (goCommandGiven == false) {
				monitor.wait();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		sendGoMessage(game);
	}

	private void sendGoMessage(MultiplayerGame game) {
		MessageType type = MessageType.GO;
		sendEmptyMessage(type);
	}

}
