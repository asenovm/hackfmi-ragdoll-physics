package com.midtownmadness.bubblecombat.multiplay;

import android.bluetooth.BluetoothSocket;

public class MultiplayerGame {

	// TODO synchronization!!!
	// XXX
	// change this to list!
	private BluetoothSocket otherPlayer;

	private long syncTimestamp;

	public MultiplayerGame(BluetoothSocket clientSocket) {
		this.otherPlayer = clientSocket;
	}

	public String getName() {
		return otherPlayer.getRemoteDevice().getName();
	}

	@Override
	public String toString() {
		return otherPlayer.getRemoteDevice().getAddress();
	}

	public void setSyncTimestamp(long syncTimestamp) {
		this.syncTimestamp = syncTimestamp;
	}

	public long getSyncTimestamp() {
		return syncTimestamp;
	}

}
