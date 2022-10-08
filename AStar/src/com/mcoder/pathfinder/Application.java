package com.mcoder.pathfinder;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;

import javax.swing.JFrame;

import com.mcoder.pathfinder.view.Display;
import com.mcoder.pathfinder.view.Screen;

public class Application extends Display {
	private final int rows = 25, cols = 25;
	private int w, h;
	
	private Spot[][] board;
	private LinkedList<Spot> openSet;
	private Spot current, end;

	public Application(JFrame frame) {
		super(Window.width, Window.height);
		setup();
	}

	public void setup() {
		w = Window.width/cols;
		h = Window.height/rows;
		
		board = new Spot[rows][cols];
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < cols; j++) {
				boolean wall = Math.random() < 0.3;
				board[i][j] = new Spot(i, j, w, h, wall);
			}
		
		Spot start = board[0][0];		// Start spot
		start.setWall(false);
		end = board[rows-1][cols-1];	// End spot
		end.setWall(false);
		
		// It contains all the unexplored spots
		openSet = new LinkedList<>();
		openSet.add(start);
	}
	
	// Executes the A* path finder algorithm which updates every frame
	@Override
	public void update() {
		if (openSet.size() > 0) {
			// Finds the next spot
			current = openSet.getFirst();
			for (Spot s : openSet)
				if (s.getF() < current.getF())
					current = s;
			
			// Explore the spot
			current.view();
			openSet.remove(current);
			
			// Checks if it has reached the ending spot
			if (current == end) {
				System.out.println("FINE!");
				Screen.getInstance().noLoop();
				return;
			}
			
			// Refreshes the neighboring spots
			LinkedList<Spot> neighbors = neighbors(current);
			for (Spot s : neighbors) {
				int tempG = current.getG()+1;
				
				boolean newPath = false;
				if (!s.isViewed()) {
					if (openSet.contains(s)) {
						if (tempG < s.getG()) {
							s.setG(tempG);
							newPath = true;
						}
					} else {
						s.setG(tempG);
						openSet.add(s);
						newPath = true;
					}
					
					if (newPath) {
						s.calculateF(end);
						s.setPrevious(current);
					}
				}
			}
		} else {
			System.out.println("Impossibile arrivare alla fine!");
			Screen.getInstance().noLoop();
			return;
		}
	}

	@Override
	public void show(Graphics2D g) {
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < cols; j++)
				board[i][j].show(g);
		
		Spot s = current;
		while(s != null) {
			s.showPath(g, Color.BLUE);
			s = s.getPrevious();
		}
	}
	
	/**
	 * Finds the neighboring spots of the given one
	 * @param spot The spot of which to find the neighborings
	 * @return A linked list containing the neighboring spots
	 */
	private LinkedList<Spot> neighbors(Spot spot) {
		LinkedList<Spot> neighbors = new LinkedList<>();
		for (int i = -1; i <= 1; i++)
			for (int j = -1; j <= 1; j++) {
				int r = spot.getRow()+i;
				int c = spot.getCol()+j;
				if (r >= 0 && c >= 0 && r < rows 
						&& c < cols && !board[r][c].isWall())
					neighbors.add(board[r][c]);
			}
		
		return neighbors;
	}
}