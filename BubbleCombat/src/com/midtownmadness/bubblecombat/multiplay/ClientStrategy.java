package com.midtownmadness.bubblecombat.multiplay;

import static com.midtownmadness.bubblecombat.Settings.HOST_ID;

import java.io.IOException;
import java.net.ConnectException;

import com.midtownmadness.bubblecombat.Settings;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;

public class ClientStrategy extends BaseStrategy {

	private BluetoothDevice device;

	private BluetoothSocket hostSocket;

	private Callback<BluetoothSocket> callback;

	public ClientStrategy(Context context, BluetoothDevice device,
			Callback<BluetoothSocket> callback) {
		super(context);
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
					toast("Host socket found " + hostSocket.toString());
					hostSocket.connect();
					
					toast("Host socket connected succesfully" + hostSocket.toString());
					onPlayerConnected(HOST_ID, hostSocket);
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
	public void commenceGame(final MultiplayerGame game) {
		sendEmptyMessage(MessageType.COMMENCE_GAME, super.getConnectedPlayers()
				.get(Settings.HOST_ID));
	}

}
