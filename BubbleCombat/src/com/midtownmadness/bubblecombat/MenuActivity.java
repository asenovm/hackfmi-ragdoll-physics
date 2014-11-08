package com.midtownmadness.bubblecombat;

import java.io.IOException;

import junit.framework.Assert;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.midtownmadness.bubblecombar.listeners.GameRoomListener;
import com.midtownmadness.bubblecombat.multiplay.MultiplayEvent;
import com.midtownmadness.bubblecombat.multiplay.MultiplayEventListener;
import com.midtownmadness.bubblecombat.multiplay.MultiplayManager;
import com.midtownmadness.bubblecombat.multiplay.MultiplayerGame;
import com.midtownmadness.bubblecombat.views.MenuView;

public class MenuActivity extends BaseActivity implements OnClickListener,
		MultiplayEventListener, GameRoomListener {

	private static final int REQUEST_CODE_BLUETOOTH_VISIBILITY = 0xFACE;
	private MultiplayManager multiplayManager;
	private BluetoothGamesAdapter gameAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		gameAdapter = new BluetoothGamesAdapter(this, -1);
		gameAdapter.setGameRoomListener(this);
		setContentView(new MenuView(this, this, gameAdapter));

		multiplayManager = (MultiplayManager) getSystemService(MultiplayManager.SERVICE_NAME);
		Assert.assertNotNull(multiplayManager);

		multiplayManager.addListener(this);
		multiplayManager.searchForGames();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.host_button:
			host();
			break;
		case R.id.quit_button:
			finish();
			break;
		case R.id.play_button:
			play();
			break;

		default:
			break;
		}
	}

	private void host() {
		try {
			multiplayManager.host();
			Intent getVisible = new Intent(
					BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
			startActivityForResult(getVisible,
					REQUEST_CODE_BLUETOOTH_VISIBILITY);
		} catch (IOException e) {
			toast("hosting fucked up");
			e.printStackTrace();
		}
	}

	private void play() {
		final Intent playIntent = new Intent(getApplicationContext(),
				GameActivity.class);
		startActivity(playIntent);
	}

	private void toast(String string) {
		Toast.makeText(this, string, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onMultiplayEvent(MultiplayEvent e) {

	}

	@Override
	public void onPlayerConnected(int playerId) {

	}

	@Override
	public void onGameDiscovered(final MultiplayerGame multiplayerGame) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(MenuActivity.this, multiplayerGame.toString(),
						Toast.LENGTH_LONG).show();
				gameAdapter.add(multiplayerGame);
				gameAdapter.notifyDataSetChanged();
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE_BLUETOOTH_VISIBILITY) {
			if (resultCode > 0) {
				doHost();
			} else {
				toast("User has denied bluetooth hosting");
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void doHost() {
		try {
			multiplayManager.host();
		} catch (IOException e) {
			e.printStackTrace();
			toast("Hosting has fucked up");
		}
	}

	@Override
	public void onGameEntered(MultiplayerGame model) {
		Toast.makeText(this, "attempted to enter " + model.toString(),
				Toast.LENGTH_SHORT).show();

	}

}
