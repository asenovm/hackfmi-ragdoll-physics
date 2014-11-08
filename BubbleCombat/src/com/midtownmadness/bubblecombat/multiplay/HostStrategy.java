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

	public HostStrategy(BluetoothServerSocket serverSocket,
			MultiplayManager manager) {
		super();
		this.serverSocket = serverSocket;
		this.manager = manager;
	}

	@Override
	public void handshakeAndLoad() {

	}

	private void doHost() {
		getHandler().post(new Runnable() {

			@Override
			public void run() {
				try {
					BluetoothSocket socket = serverSocket
							.accept(Settings.HOST_TIMEOUT);
					manager.onPlayerConnected(socket);

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
		doHost();
	}

}
