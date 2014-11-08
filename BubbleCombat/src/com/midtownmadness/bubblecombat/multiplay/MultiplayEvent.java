package com.midtownmadness.bubblecombat.multiplay;

import java.io.Serializable;

public class MultiplayEvent implements Serializable {

	/**
	 * {@value}
	 */
	private static final long serialVersionUID = 4852219829927505606L;

	public final float x;

	public final float y;

	public MultiplayEvent(final float x, final float y) {
		this.x = x;
		this.y = y;
	}

}
