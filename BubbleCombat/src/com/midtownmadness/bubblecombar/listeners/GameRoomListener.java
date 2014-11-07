package com.midtownmadness.bubblecombar.listeners;

import com.midtownmadness.bubblecombar.model.GameModel;
import com.midtownmadness.bubblecombat.multiplay.MultiplayerGame;

public interface GameRoomListener {
	void onGameEntered(final MultiplayerGame model);
}
