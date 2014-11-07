package com.midtownmadness.bubblecombat.multiplay;

import android.bluetooth.BluetoothSocket;

public class MultiplayerGame {

	private BluetoothSocket socket;

	public MultiplayerGame(BluetoothSocket clientSocket) {
		this.socket = clientSocket;
	}
	
	public String getName(){
		return socket.getRemoteDevice().getName();
	}
	
	@Override
	public String toString() {
		return socket.getRemoteDevice().getAddress();
	}

}
