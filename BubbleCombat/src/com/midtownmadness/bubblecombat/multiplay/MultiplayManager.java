package com.midtownmadness.bubblecombat.multiplay;

import java.util.ArrayList;
import java.util.List;

public class MultiplayManager {
	public static final String SERVICE_NAME = MultiplayManager.class
			.getSimpleName();
	private List<MultiplayEventListener> listeners = new ArrayList<MultiplayEventListener>();

	public void addListener(MultiplayEventListener listener) {
		this.listeners.add(listener);
	}
	
	
}
