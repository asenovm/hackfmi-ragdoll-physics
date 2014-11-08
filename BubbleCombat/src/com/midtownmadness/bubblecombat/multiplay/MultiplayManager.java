package com.midtownmadness.bubblecombat.multiplay;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
import android.util.Log;

import com.midtownmadness.bubblecombat.Settings;

public class MultiplayManager implements Closeable {

	public static final String SERVICE_NAME = MultiplayManager.class
			.getSimpleName();

	private static final String NAME = Settings.GAME_NAME;

	static final UUID UUID = java.util.UUID
			.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");

	private static final String TAG = MultiplayManager.class.getSimpleName();

	private static int nextId = Settings.HOST_ID;

	private Handler handler = new Handler();

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

	protected void onDeviceDiscovered(BluetoothDevice device) {
		// XXX this should maybe not be part of ClientStrategy?
		// XXX move this to a List<ClientStrategy>, when you see multiple hosts
		// XXX this for the moment allows us to see no more than one bluetooth
		// game - the first one
		// TODO generalize
		if (strategy == null) {
			this.strategy = new ClientStrategy(device,
					new Callback<BluetoothSocket>() {

						@Override
						public void call(BluetoothSocket argument) {
							if (argument == null) {
								Log.e(TAG, "Socket receive failure");
							} else {
								onGameDiscovered(argument);
							}
						}
					});
			strategy.start();
		}
	}

	private void onGameDiscovered(BluetoothSocket clientSocket) {
		for (MultiplayEventListener listener : listeners) {
			listener.onGameDiscovered(new MultiplayerGame(clientSocket));
		}
	}

	public void addListener(MultiplayEventListener listener) {
		this.listeners.add(listener);
	}

	public void host() throws IOException {
		BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
		BluetoothServerSocket serverSocket = adapter
				.listenUsingInsecureRfcommWithServiceRecord(NAME, UUID);
		this.strategy = new HostStrategy(serverSocket, null);
		this.strategy.start();
	}

	public void joinGame(final MultiplayerGame game) {
		strategy.commenceGame(game);
	}

	public void searchForGames(final int timeoutMillis) {

		final Runnable searchForGamesRunnable = new Runnable() {

			@Override
			public void run() {
				BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
				adapter.startDiscovery();
			}
		};
		searchForGamesRunnable.run();

		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				if (strategy == null) {
					// we have not picked client strategy
					searchForGamesRunnable.run();
					handler.postDelayed(this, timeoutMillis);
				}
			}
		}, timeoutMillis);

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


//	public void sendGoMessage(int... playerIds) {
//		Object payload = null;
//		for (int playerId : playerIds) {
//			sendMessage(payload, MessageType.GO, playerId);
//		}
//	}
//
//	private void sendMessage(Object payload, MessageType type, int... playerIds) {
//		byte[] message = BluetoothMessage.from(type, payload);
//
//		for (int playerId : playerIds) {
//			BluetoothSocket connectedPlayer = connectPlayers.get(playerId);
//			if (connectedPlayer == null) {
//				Log.d(TAG, "ERROR!: ATTEMPTED TO FIND PLAYER WITH id "
//						+ playerId + " => NOT PRESENT IN MULTIPLAY MANAGER!");
//				continue;
//			}
//
//			try {
//				connectedPlayer.getOutputStream().write(message);
//				connectedPlayer.getOutputStream().flush();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}

}
