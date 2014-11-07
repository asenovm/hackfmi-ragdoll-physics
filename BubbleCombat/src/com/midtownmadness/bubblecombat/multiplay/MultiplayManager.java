package com.midtownmadness.bubblecombat.multiplay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.midtownmadness.bubblecombat.Settings;

public class MultiplayManager {
	public static final String SERVICE_NAME = MultiplayManager.class
			.getSimpleName();
	private static final String NAME = Settings.GAME_NAME;
	private static final UUID UUID = java.util.UUID
			.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");
	private static final String TAG = MultiplayManager.class.getSimpleName();
	private static int nextId = Settings.HOST_ID;

	private List<MultiplayEventListener> listeners = new ArrayList<MultiplayEventListener>();
	private Map<Integer, BluetoothSocket> connectPlayers = new HashMap<Integer, BluetoothSocket>();
	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (BluetoothDevice.ACTION_FOUND.equals(intent.getAction())) {
				// Get the BluetoothDevice object from the Intent
				BluetoothDevice device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				Log.d(TAG, "DEVICE FOUND!" + device.toString());
				onDeviceDiscovered(device);
			}
			Log.d(TAG, "ON RECEIVE!");
		}
	};

	public MultiplayManager(Context context) {
		// log by default>
		this.listeners.add(new LoggingListener());

		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		context.registerReceiver(broadcastReceiver, filter);
	}

	protected void onDeviceDiscovered(BluetoothDevice device) {
		Exception exc = null;
		ClientThread clientThread = new ClientThread(device);

		try {
			clientThread.start();
		} catch (BluetoothConnectException e) {
			// it's a device, but it has nothing to discover
			e.printStackTrace();
			exc = e;
		} finally {
			if (exc == null) {
				onGameDiscovered(clientThread.clientSocket);
			}
		}

	}

	private void onGameDiscovered(BluetoothSocket clientSocket) {
		for (MultiplayEventListener listener : listeners){
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
		HostThread hostThread = new HostThread(serverSocket);
		hostThread.start();
	}

	public void searchForGames() {
		BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
		adapter.startDiscovery();
	}

	public class HostThread extends Thread {
		private BluetoothServerSocket serverSocket;

		public HostThread(BluetoothServerSocket serverSocket) {
			this.serverSocket = serverSocket;
		}

		@Override
		public void run() {
			try {
				BluetoothSocket socket = serverSocket
						.accept(Settings.HOST_TIMEOUT);
				onPlayerConnected(socket);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void onPlayerConnected(BluetoothSocket socket) {
		connectPlayers.put(nextId, socket);

		for (MultiplayEventListener listener : listeners) {
			listener.onPlayerConnected(nextId);
		}

		nextId++;
	}

	public void sendMessage(Object payload, MessageType type, int... playerIds) {
		byte[] message = BluetoothMessage.from(type, payload);

		for (int playerId : playerIds) {
			BluetoothSocket connectedPlayer = connectPlayers.get(playerId);
			if (connectedPlayer == null) {
				Log.d(TAG, "ERROR!: ATTEMPTED TO FIND PLAYER WITH id "
						+ playerId + " => NOT PRESENT IN MULTIPLAY MANAGER!");
				continue;
			}

			try {
				connectedPlayer.getOutputStream().write(message);
				connectedPlayer.getOutputStream().flush();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	private class ClientThread extends Thread {
		private BluetoothDevice device;
		private BluetoothSocket clientSocket;

		public ClientThread(BluetoothDevice device) {
			this.device = device;
		}

		@Override
		public void run() {
			try {
				clientSocket = this.device.createInsecureRfcommSocketToServiceRecord(UUID);
			} catch (IOException e) {
				e.printStackTrace();
				throw new BluetoothConnectException(e);
			}
		}
	}

}
