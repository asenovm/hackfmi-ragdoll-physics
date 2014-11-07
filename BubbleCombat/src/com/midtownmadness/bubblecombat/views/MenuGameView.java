package com.midtownmadness.bubblecombat.views;

import com.midtownmadness.bubblecombat.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class MenuGameView extends LinearLayout {

	public MenuGameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		LayoutInflater.from(context).inflate(R.layout.menu_game_view, this);
	}

	public MenuGameView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MenuGameView(Context context) {
		this(context, null);
	}

}
