package com.midtownmadness.bubblecombat.multiplay;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;

public interface MultiplayStrategy {
	void handshakeAndLoad();

	Handler getHandler();

	void start();

	void commenceGame(MultiplayerGame gamew);

	void onPlayerConnected(final int playerId, final BluetoothSocket socket);

	void close();
}
