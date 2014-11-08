package com.midtownmadness.bubblecombat.multiplay;

import android.os.Handler;

public abstract class BaseStrategy implements MultiplayStrategy {
	private LooperThread looper;

	public BaseStrategy() {
		this.looper = new LooperThread();
		this.looper.setHandlerReadyListener(new Callback<Void>() {

			@Override
			public void call(Void argument) {
				BaseStrategy.this.onLooperReady();
			}
		});
	}

	public void start() {
		looper.start();
	}

	public Handler getHandler() {
		return looper.getHandler();
	}

	public LooperThread getThread() {
		return looper;
	}

	public abstract void onLooperReady();
}
