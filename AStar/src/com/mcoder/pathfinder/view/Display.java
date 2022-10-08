package com.mcoder.pathfinder.view;

import java.awt.Canvas;

public abstract class Display extends Canvas implements Drawable {
	protected Display(int width, int height) {
		super();
		setSize(width, height);
		Screen.getInstance().addDrawer(this);
	}
}
