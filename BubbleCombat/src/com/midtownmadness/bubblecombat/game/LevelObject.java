package com.midtownmadness.bubblecombat.game;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.PointF;

public class LevelObject {
	public PointF size;
	public PointF scale;
	public List<GameObject> objects;
	public Bitmap background;
	private PlayerObject thisPlayer;
	
	public PlayerObject getThisPlayer() {
		return thisPlayer;
	}
	
	public void setThisPlayer(PlayerObject player) {
		thisPlayer = player;
	}

	public PlayerObject getPlayerObject(int playerId) {
		for(GameObject obj : objects) {
			if(obj instanceof PlayerObject && ((PlayerObject)obj).getPlayerId() == playerId)
				return (PlayerObject) obj;
		}
		return null;
	}
}
