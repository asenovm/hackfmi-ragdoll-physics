package com.midtownmadness.bubblecombat;

import com.midtownmadness.bubblecombat.multiplay.MultiplayerGame;

public class MockMultiplayerGame extends MultiplayerGame{
	private String name;
	public MockMultiplayerGame(String name){
		super(null); //shte se naqdem na hoi
		this.name = name;
	}
	@Override
	public String getName() {
		return name;
	}
}
