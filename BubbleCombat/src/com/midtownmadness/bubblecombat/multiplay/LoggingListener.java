package com.midtownmadness.bubblecombat.multiplay;

import android.util.Log;

public class LoggingListener implements MultiplayEventListener {

	private static final String TAG = LoggingListener.class.getSimpleName();

	@Override
	public void onMultiplayEvent(MultiplayEvent e) {
		Log.d(TAG, "Received multiplay event " + e.toString());
	}

	@Override
	public void onPlayerConnected(int playerId) {
		Log.d(TAG, "Received player connected! " + playerId);
	}

	@Override
	public void onGameDiscovered(MultiplayerGame multiplayerGame) {
		Log.d(TAG, "Game discoevered! " + multiplayerGame.toString());
	}

	@Override
	public void onGameSynced(MultiplayerGame game) {
		Log.d(TAG, "Game synced! " + game.toString());
	}

}
