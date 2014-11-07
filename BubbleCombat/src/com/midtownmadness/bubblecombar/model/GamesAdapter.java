package com.midtownmadness.bubblecombar.model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.midtownmadness.bubblecombat.views.MenuGameView;
import com.midtownmadness.bubblecombat.views.MenuView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class GamesAdapter extends ArrayAdapter<GameModel> {

	private final List<GameModel> model;

	public GamesAdapter(Context context, int textViewResourceId,
			GameModel[] items) {
		super(context, textViewResourceId, items);
		model = new LinkedList<GameModel>();
		model.addAll(Arrays.asList(items));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final GameModel model = getItem(position);
		final MenuGameView view = new MenuGameView(parent.getContext());
		return view;
	}

	@Override
	public int getCount() {
		return model.size();
	}

	@Override
	public GameModel getItem(int position) {
		return model.get(position);
	}

	@Override
	public void add(GameModel object) {
		model.add(object);
	}

}
