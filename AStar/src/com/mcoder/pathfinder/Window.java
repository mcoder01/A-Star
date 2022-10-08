package com.mcoder.pathfinder;

import javax.swing.JFrame;

import com.mcoder.pathfinder.view.Screen;

public class Window {
	private static final String appName = "A* PathFinder";
	public static final int width = 700, height = 700;

	public static void main(String[] args) {
		JFrame frame = new JFrame(appName);
		frame.add(new Application(frame));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		Screen.getInstance().start();
	}
}