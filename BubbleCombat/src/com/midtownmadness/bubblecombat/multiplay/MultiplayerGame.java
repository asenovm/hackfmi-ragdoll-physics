package com.midtownmadness.bubblecombat.multiplay;

import android.bluetooth.BluetoothSocket;

public class MultiplayerGame {

	private BluetoothSocket socket;

	public MultiplayerGame(BluetoothSocket clientSocket) {
		this.socket = clientSocket;
	}

}
