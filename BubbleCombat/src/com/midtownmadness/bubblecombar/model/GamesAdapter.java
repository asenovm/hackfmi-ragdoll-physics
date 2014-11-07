package com.midtownmadness.bubblecombar.model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.midtownmadness.bubblecombat.multiplay.MultiplayerGame;
import com.midtownmadness.bubblecombat.views.MenuGameView;
import com.midtownmadness.bubblecombat.views.MenuView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class GamesAdapter extends ArrayAdapter<MultiplayerGame> {

	private final List<MultiplayerGame> model;

	public GamesAdapter(Context context, int textViewResourceId,
			MultiplayerGame[] items) {
		super(context, textViewResourceId, items);
		model = new LinkedList<MultiplayerGame>();
		model.addAll(Arrays.asList(items));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO start reusing the convert view
		final MenuGameView view = new MenuGameView(parent.getContext());
		view.populateFromModel(getItem(position));
		return view;
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
