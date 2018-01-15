package gui;

import java.awt.Point;

public class MousePos {
	private Point pos;

	public synchronized Point getPos() {
		return pos;
	}

	public synchronized void setPos(Point pos) {
		this.pos = pos;
	}
	
}
