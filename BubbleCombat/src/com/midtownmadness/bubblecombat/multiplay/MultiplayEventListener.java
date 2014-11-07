package com.midtownmadness.bubblecombat.multiplay;

public interface MultiplayEventListener {
	void onMultiplayEvent(MultiplayEvent e);
	void onPlayerConnected(int playerId);
	void onGameDiscovered(MultiplayerGame multiplayerGame);
}
