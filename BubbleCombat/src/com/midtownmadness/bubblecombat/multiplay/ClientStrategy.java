package com.midtownmadness.bubblecombat.multiplay;

import java.io.IOException;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

public class ClientStrategy extends BaseStrategy {

	private BluetoothDevice device;
	private BluetoothSocket hostSocket;
	private Callback<BluetoothSocket> callback;

	public ClientStrategy(BluetoothDevice device,
			Callback<BluetoothSocket> callback) {
		super();
		this.device = device;
		this.callback = callback;

	}

	@Override
	public void handshakeAndLoad() {
		getHandler().post(new Runnable() {

			@Override
			public void run() {
				try {
					hostSocket = device
							.createInsecureRfcommSocketToServiceRecord(MultiplayManager.UUID);
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					callback.call(hostSocket);
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
			hostSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
