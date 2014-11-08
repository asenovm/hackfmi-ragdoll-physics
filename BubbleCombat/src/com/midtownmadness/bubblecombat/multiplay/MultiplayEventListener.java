package com.midtownmadness.bubblecombat.multiplay;

import com.midtownmadness.bubblecombat.multiplay.commobjects.GoMessageObject;

public interface MultiplayEventListener {
	void onMultiplayEvent(MultiplayEvent e);

	void onPlayerConnected(int playerId);

	void onGameDiscovered(MultiplayerGame multiplayerGame);

	void onGameSynced(MultiplayerGame game);

	void onGameCommenced(final long syncStamp);

	void onError();
}
