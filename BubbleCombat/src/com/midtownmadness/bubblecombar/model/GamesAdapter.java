package com.midtownmadness.bubblecombar.model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.midtownmadness.bubblecombar.listeners.GameRoomListener;
import com.midtownmadness.bubblecombat.multiplay.MultiplayerGame;
import com.midtownmadness.bubblecombat.views.MenuGameView;

public class GamesAdapter extends ArrayAdapter<MultiplayerGame> {

	private final List<MultiplayerGame> model;

	private static final String TAG = GamesAdapter.class.getSimpleName();

	private GameRoomListener listener;

	public GamesAdapter(Context context, int textViewResourceId,
			MultiplayerGame[] items) {
		super(context, textViewResourceId, items);
		model = new LinkedList<MultiplayerGame>();
		model.addAll(Arrays.asList(items));
	}

	public void setGameRoomListener(final GameRoomListener listener) {
		this.listener = listener;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MenuGameView result;
		if (convertView == null) {
			result = new MenuGameView(parent.getContext());
		} else {
			result = (MenuGameView) convertView;
		}
		result.populateFromModel(getItem(position));
		result.setListener(listener);
		return result;
	}

	@Override
	public int getCount() {
		return model.size();
	}

	@Override
	public MultiplayerGame getItem(int position) {
		return model.get(position);
	}

	@Override
	public void add(MultiplayerGame object) {
		model.add(object);
	}

}
