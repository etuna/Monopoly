package gui;

import java.io.Serializable;

public class Brick implements DisF, Collidable, Serializable{

	public static final double sizeX = 19;
	public static final double sizeY = 8;
	public final double centerX = sizeX/2;;
	public final double centerY = sizeY/2;;
	public double posX,posY;
	public boolean alive;
	public int index;
	
	public Brick(double x, double y, int index) {
		this.posX = x;
		this.posY = y;
		//centerX = sizeX/2;
		//centerY = sizeY/2;
		alive = true;
		this.index = index;
	}
	@Override
	public double getDistance(Vecd2d p) {
		return boxDistance(new Vecd2d(p.x-posX, p.y-posY));
	}
	
	public double boxDistance(Vecd2d p) {
		double dx = 0, dy = 0;
		double bx,by, dd, bb;
		
		//dx = p.x <= centerX ? p.x : sizeX-p.x;
		//dy = p.y <= centerY ? p.y : sizeY-p.y;
		
		dx = Math.abs(p.x) - centerX;
		dy = Math.abs(p.y) - centerY;
		
		dd = new Vecd2d(Math.max(dx, 0.0), Math.max(dy, 0.0)).getLength();
		
		bb = Math.min(Math.max(dx, dy),0);
		
		return bb + dd;
		
		//return Math.min(dx, dy);
	}

}
