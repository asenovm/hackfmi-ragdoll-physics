package com.midtownmadness.bubblecombar.model;

public class GameModel {

	private final String gameName;

	public GameModel(final String gameName) {
		this.gameName = gameName;
	}

	public String getGameName() {
		return gameName;
	}

	@Override
	public String toString() {
		return "GameModel [gameName=" + gameName + "]";
	}

}
