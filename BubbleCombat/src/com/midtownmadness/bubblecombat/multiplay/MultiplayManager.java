package com.midtownmadness.bubblecombat.multiplay;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.midtownmadness.bubblecombat.Settings;
import com.midtownmadness.bubblecombat.multiplay.commobjects.EventMessageObject;

public class MultiplayManager implements Closeable {

	public static final String SERVICE_NAME = MultiplayManager.class
			.getSimpleName();

	private static final String NAME = Settings.GAME_NAME;

	static final UUID UUID = java.util.UUID
			.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");

	private static final String TAG = MultiplayManager.class.getSimpleName();

	private static int nextId = Settings.HOST_ID + 1;

	private Handler handler = new Handler(Looper.getMainLooper());

	private List<MultiplayEventListener> listeners = new ArrayList<MultiplayEventListener>();

	private BroadcastReceiver broadcastReceiver;

	private MultiplayStrategy strategy;

	private Context context;
	
	
	private class DeviceReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (BluetoothDevice.ACTION_FOUND.equals(intent.getAction())) {
				// Get the BluetoothDevice object from the Intent
				BluetoothDevice device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				Log.e(TAG, "DEVICE FOUND!" + device.toString());
				onDeviceDiscovered(device);
			}
			Log.e(TAG, "ON RECEIVE!");
		}

	}

	public MultiplayManager(Context context) {
		// log by default>
		this.context = context;
		this.listeners.add(new LoggingListener());

		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);

		broadcastReceiver = new DeviceReceiver();
		context.registerReceiver(broadcastReceiver, filter);
		
	}

	private void setupMock() {
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				strategy.add(new EventMessageObject((int) (Math.random() * 300), (int) (Math.random() * 300))); 
				handler.postDelayed(this, 1000);
			}
		}, 1000);
	}

	protected void onDeviceDiscovered(BluetoothDevice device) {
		// XXX this should maybe not be part of ClientStrategy?
		// XXX move this to a List<ClientStrategy>, when you see multiple hosts
		// XXX this for the moment allows us to see no more than one bluetooth
		// game - the first one
		// TODO generalize
		if (strategy == null) {
			this.strategy = new ClientStrategy(context, device,
					new Callback<BluetoothSocket>() {

						@Override
						public void call(BluetoothSocket argument) {
							if (argument == null) {
								Log.e(TAG, "Socket receive failure");
							} else {
								onGameDiscovered(argument);
							}
						}
					}, this);
			onStrategySet();
			strategy.start();
		}
	}

	private void onStrategySet() {
		setupMock();
	}

	private void onGameDiscovered(BluetoothSocket clientSocket) {
		for (MultiplayEventListener listener : listeners) {
			listener.onGameDiscovered(new MultiplayerGame(clientSocket));
		}
	}

	public void addListener(MultiplayEventListener listener) {
		this.listeners.add(listener);
	}

	public void refresh() {
		searchForGames();
	}

	public void host() throws IOException {
		BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
		BluetoothServerSocket serverSocket = adapter
				.listenUsingInsecureRfcommWithServiceRecord(NAME, UUID);
		this.strategy = new HostStrategy(context, serverSocket, this);
		onStrategySet();
		this.strategy.start();
	}

	public void joinGame() {
		strategy.commenceGame();
	}

	public void searchForGames() {
		BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
		if (!adapter.isDiscovering()) {
			adapter.startDiscovery();
		}
	}

	void onPlayerConnected(BluetoothSocket socket) {
		strategy.onPlayerConnected(nextId, socket);

		for (MultiplayEventListener listener : listeners) {
			listener.onPlayerConnected(nextId);
		}

		nextId++;
	}

	public void removeListener(MultiplayEventListener menuActivity) {
		listeners.remove(menuActivity);
	}

	public void close() {
		if (broadcastReceiver != null) {
			context.unregisterReceiver(broadcastReceiver);
			broadcastReceiver = null;
		}
		if (strategy != null) {
			strategy.close();
		}
	}

	public List<Integer> getPlayerIds() {
		return new ArrayList<Integer>(strategy.getConnectedPlayers().keySet());
	}

	/**
	 * XXXX REMOVE THIS METHOD USE getPlayers()[0]
	 * 
	 * @return
	 */
	public int getPlayerId() {
		return getPlayerIds().get(0);
	}

	public void onGameCommenced(final long syncStamp) {
		BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
		
		for (MultiplayEventListener listener : listeners) {
			listener.onGameCommenced(syncStamp);
		}
		
	}

	public void action() {
		strategy.action();
	}
}
