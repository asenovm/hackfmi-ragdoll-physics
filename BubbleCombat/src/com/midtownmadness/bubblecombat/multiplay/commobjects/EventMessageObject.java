package com.midtownmadness.bubblecombat.multiplay.commobjects;

import java.io.Serializable;

public class EventMessageObject implements Serializable {
	public final int x;
	public final int y;

	public EventMessageObject(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
