package com.midtownmadness.bubblecombat.multiplay.commobjects;

import java.io.Serializable;

public class GoMessageObject implements Serializable {
	public final long timestamp;

	public GoMessageObject(long timestamp){
		this.timestamp = timestamp;
	}
}
