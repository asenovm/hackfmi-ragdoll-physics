package com.midtownmadness.bubblecombat.multiplay;

import android.os.Handler;
import android.os.Looper;

public class LooperThread extends Thread {
	private Handler handler;
	private Runnable emptyMessage = new Runnable() {
		@Override
		public void run() {
			// do nothing
			// XXX prevent the Looper from getting out of messages and quitting
			// the loop
			// not very elegant, but will do the trick
			handler.postDelayed(this, 1000);
		}
	};
	private Callback<Void> handlerReadyListener;

	public void run() {
		Looper.prepare();
		handler = new Handler();
		handler.post(emptyMessage);

		if (handlerReadyListener != null) {
			handlerReadyListener.call(null);
		}

		Looper.loop();
	};

	/**
	 * Use this instead of constructor
	 */
	public void setHandlerReadyListener(Callback<Void> callback) {
		this.handlerReadyListener = callback;
	}

	public Handler getHandler() {
		return handler;
	}
}
