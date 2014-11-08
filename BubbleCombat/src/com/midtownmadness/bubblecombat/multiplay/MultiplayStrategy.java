package com.midtownmadness.bubblecombat.multiplay;

import java.util.Map;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.SparseArray;

public interface MultiplayStrategy {
	void handshakeAndLoad();

	Handler getHandler();

	void start();

	void commenceGame(MultiplayerGame gamew);

	void onPlayerConnected(final int playerId, final BluetoothSocket socket);

	void close();
	
	public Map<Integer, BluetoothSocket> getConnectedPlayers();
}
