package com.midtownmadness.bubblecombat.multiplay;

public interface MultiplayEventListener {
	void onMultiplayEvent(MultiplayEvent e, final int playerId);

	void onPlayerConnected(int playerId);

	void onGameDiscovered(MultiplayerGame multiplayerGame);

	void onGameSynced(MultiplayerGame game);

	void onGameCommenced(final long syncStamp);

	void onError();
}
