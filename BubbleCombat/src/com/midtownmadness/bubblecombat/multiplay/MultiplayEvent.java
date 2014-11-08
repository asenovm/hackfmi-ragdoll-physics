package com.midtownmadness.bubblecombat.multiplay;

import java.io.Serializable;

public class MultiplayEvent implements Serializable {

	/**
	 * {@value}
	 */
	private static long serialVersionUID = 4852219829927505606L;

	public float x;
	public float y;
	public float health;
	public float dx;
	public float dy;
	public float vx;
	public float vy;
	public long timestamp;
}
