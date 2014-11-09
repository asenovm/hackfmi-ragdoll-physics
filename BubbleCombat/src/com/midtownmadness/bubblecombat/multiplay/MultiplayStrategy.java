package com.midtownmadness.bubblecombat.multiplay;

import java.util.Map;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;

public interface MultiplayStrategy {
	void handshakeAndLoad();

	Handler getHandler();

	void start();

	void commenceGame();

	void onPlayerConnected(final int playerId, final BluetoothSocket socket);

	void close();

	public Map<Integer, BluetoothSocket> getConnectedPlayers();

	void action();
	
	void endGame();

	void add(MultiplayEvent event);

}
