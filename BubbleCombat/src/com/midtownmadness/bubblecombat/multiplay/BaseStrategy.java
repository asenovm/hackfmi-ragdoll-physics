package com.midtownmadness.bubblecombat.multiplay;

import java.io.IOException;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;
import android.util.SparseArray;

public abstract class BaseStrategy implements MultiplayStrategy {

	/**
	 * {@value}
	 */
	private static final String TAG = BaseStrategy.class.getSimpleName();

	private SparseArray<BluetoothSocket> connectPlayers = new SparseArray<BluetoothSocket>();

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
			int... playerIds) {
		byte[] message = BluetoothMessage.from(type, payload);

		for (int playerId : playerIds) {
			BluetoothSocket connectedPlayer = connectPlayers.get(playerId);
			if (connectedPlayer == null) {
				Log.d(TAG, "ERROR!: ATTEMPTED TO FIND PLAYER WITH id "
						+ playerId + " => NOT PRESENT IN MULTIPLAY MANAGER!");
				continue;
			}

			try {
				connectedPlayer.getOutputStream().write(message);
				connectedPlayer.getOutputStream().flush();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	protected void sendEmptyMessage(MessageType type, int... playerIds) {
		sendMessage(new String(), type, playerIds);
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

	public abstract void onLooperReady();
}
