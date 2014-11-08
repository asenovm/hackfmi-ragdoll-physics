package com.midtownmadness.bubblecombat.multiplay;

import static com.midtownmadness.bubblecombat.Settings.HOST_ID;

import java.io.IOException;
import java.net.ConnectException;

import com.midtownmadness.bubblecombat.Settings;
import com.midtownmadness.bubblecombat.multiplay.commobjects.GoMessageObject;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;

public class ClientStrategy extends BaseStrategy {

	private BluetoothDevice device;

	private BluetoothSocket hostSocket;

	private Callback<BluetoothSocket> callback;

	private MultiplayManager manager;

	public ClientStrategy(Context context, BluetoothDevice device,
			Callback<BluetoothSocket> callback, MultiplayManager manager) {
		super(context);
		this.device = device;
		this.callback = callback;
		this.manager = manager;

	}

	@Override
	public void handshakeAndLoad() {
		getHandler().post(new Runnable() {

			@Override
			public void run() {
				try {
					hostSocket = device
							.createInsecureRfcommSocketToServiceRecord(MultiplayManager.UUID);
					toast("Host socket found " + hostSocket.toString());

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
		toast("close");
		try {
			if (hostSocket != null) {
				hostSocket.getInputStream().close();
				hostSocket.getOutputStream().close();
				hostSocket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void commenceGame() {
		getHandler().post(new Runnable() {

			@Override
			public void run() {
				try {
					hostSocket.connect();
				} catch (IOException e) {
					toast("Network error");
					e.printStackTrace();
				}
				onPlayerConnected(HOST_ID, hostSocket);

				sendEmptyMessage(MessageType.JOIN_GAME, getConnectedPlayers()
						.get(Settings.HOST_ID));

				final BluetoothMessage response = obtainMessage(hostSocket);
				if (response.messageType == MessageType.GO) {
					final GoMessageObject meta = (GoMessageObject) response.payload;
					manager.onGameCommenced(meta.timestamp);
				}
			}
		});
	}
}
