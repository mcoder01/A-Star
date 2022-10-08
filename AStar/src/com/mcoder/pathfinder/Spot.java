package com.mcoder.pathfinder;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Spot {
	private int row, col, width, height;
	private int g, f;
	private boolean wall, viewed;
	
	private Spot previous;
	
	public Spot(int row, int col, int width, int height, boolean wall) {
		this.row = row;
		this.col = col;
		this.width = width;
		this.height = height;
		this.wall = wall;
		viewed = false;
	}
	
	private int heuristic(Spot spot) {
		return (int) (Math.abs(spot.row-row)+
						Math.abs(spot.col-col));
	}
	
	public void calculateF(Spot end) {
		int h = heuristic(end);
		f = g+h;
	}
	
	public void show(Graphics g) {
		if (wall) {
			int w = width-13;
			int h = height-13;
			int offX = width/2-w/2;
			int offY = height/2-h/2;
			g.setColor(Color.BLACK);
			g.fillOval(col*width+offX, row*height+offY, w, h);
		}
	}
	
	public void showPath(Graphics g, Color color) {
		if (previous != null) {
			int pcol = previous.col;
			int prow = previous.row;
			int pw = previous.width;
			int ph = previous.height;
			
			Graphics2D g2d = (Graphics2D) g; 
			g2d.setColor(Color.BLUE);
			BasicStroke stroke = new BasicStroke(width/3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL);
			g2d.setStroke(stroke);
			g2d.drawLine(col*width+width/2, row*height+height/2, 
							pcol*pw+pw/2, prow*ph+ph/2);
		}
	}
	
	public void view() {
		viewed = true;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public int getF() {
		return f;
	}
	
	public boolean isWall() {
		return wall;
	}
	
	public void setWall(boolean wall) {
		this.wall = wall;
	}
	
	public boolean isViewed() {
		return viewed;
	}
	
	public Spot getPrevious() {
		return previous;
	}
	
	public void setPrevious(Spot previous) {
		this.previous = previous;
	}
}