package gui;

import java.awt.Point;

public class KeyboardPos {
	private Point pos;
	private boolean left, right;
	public Splite spl;

	public KeyboardPos(Splite spl) {
		setLeft(false);
		setRight(false);
		pos = new Point(0,0);
		this.spl = spl;
	}
	
	public synchronized Point getPos() {
		return pos;
	}

	public synchronized void setPos(Point pos) {
		this.pos = pos;
	}

	public synchronized boolean isLeft() {
		return left;
	}

	public synchronized void setLeft(boolean left) {
		this.left = left;
	}

	public synchronized boolean isRight() {
		return right;
	}

	public synchronized void setRight(boolean right) {
		this.right = right;
	}
	
}