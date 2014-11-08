package com.midtownmadness.bubblecombat.multiplay;

import java.util.Map;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;

public interface MultiplayStrategy {
	void handshakeAndLoad();

	Handler getHandler();

	void start();

	void commenceGame(final MultiplayerGame game,
			final MultiplayEventListener listener);

	void onPlayerConnected(final int playerId, final BluetoothSocket socket);

	void close();

	public Map<Integer, BluetoothSocket> getConnectedPlayers();
}
