package com.midtownmadness.bubblecombat.multiplay;

import java.io.IOException;

import com.midtownmadness.bubblecombat.Settings;

import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

public class HostStrategy extends BaseStrategy {
	protected static final String TAG = HostStrategy.class.getSimpleName();
	private BluetoothServerSocket serverSocket;
	private MultiplayManager manager;
	private BluetoothSocket otherPlayer;

	public HostStrategy(BluetoothServerSocket serverSocket,
			MultiplayManager manager) {
		super();
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

					// please don't do ugly stuff with my 'otherPlayer' field
					manager.onPlayerConnected(otherPlayer);

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

}
