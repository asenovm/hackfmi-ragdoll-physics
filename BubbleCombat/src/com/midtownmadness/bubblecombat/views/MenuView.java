package com.midtownmadness.bubblecombat.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.midtownmadness.bubblecombat.R;

public class MenuView extends LinearLayout {

	public MenuView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		LayoutInflater.from(context).inflate(R.layout.menu_layout, this);
		setBackgroundResource(R.color.light_gray);
	}

	public MenuView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MenuView(Context context) {
		this(context, null);
	}

}
