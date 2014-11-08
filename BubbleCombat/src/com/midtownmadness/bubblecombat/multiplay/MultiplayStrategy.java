package com.midtownmadness.bubblecombat.multiplay;

import android.os.Handler;

public interface MultiplayStrategy {
	public void handshakeAndLoad();
	public Handler getHandler();
	public void start();
	public void commenceGame();
	public void close();
}
