package com.midtownmadness.bubblecombat.multiplay;

import java.io.IOException;

import android.bluetooth.BluetoothServerSocket;
import android.content.Context;
import android.graphics.Paint.Join;
import android.os.SystemClock;
import android.util.Log;

import com.midtownmadness.bubblecombat.Settings;
import com.midtownmadness.bubblecombat.multiplay.commobjects.GoMessageObject;

public class HostStrategy extends BaseStrategy {

	protected static final String TAG = HostStrategy.class.getSimpleName();
	private BluetoothServerSocket serverSocket;
	private Object monitor = new Object();
	private boolean goCommandGiven;

	public HostStrategy(Context context, BluetoothServerSocket serverSocket,
			MultiplayManager manager) {
		super(context, manager);
		this.serverSocket = serverSocket;
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

					BluetoothMessage message = obtainMessage(otherPlayer);

					if (message.messageType == MessageType.JOIN_GAME) {
						commenceGame();
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
	public void commenceGame() {
		getHandler().post(new Runnable() {

			@Override
			public void run() {
				toast("Player " + otherPlayer.getRemoteDevice().getName()
						+ "has joined!");

				final long syncStamp = sendGoMessage();
				manager.onGameCommenced(syncStamp);
			}
		});
	}

	private long sendGoMessage() {
		MessageType type = MessageType.GO;
		long baseTimestamp = SystemClock.uptimeMillis();
		GoMessageObject payload = new GoMessageObject(baseTimestamp);
		sendMessage(payload, type, otherPlayer);
		return baseTimestamp;
	}

}
