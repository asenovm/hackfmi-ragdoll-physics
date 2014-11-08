package com.midtownmadness.bubblecombat.physics;

import org.jbox2d.dynamics.World;

import com.midtownmadness.bubblecombat.multiplay.Callback;

@SuppressWarnings("rawtypes")
public abstract class PhysicsRequest {
	private Callback callback;

	public PhysicsRequest(Callback callback) {
		this.callback = callback;
	}
	
	@SuppressWarnings("unchecked")
	public final void applyRequest(World world) {
		apply(world);
		if(callback != null)
			callback.call(this);
	}

	protected abstract void apply(World world);
}
