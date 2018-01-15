package gui;

public class DField implements DisF, Collidable{
	
	
	
	public final double sizeX, sizeY, centerX, centerY;
	public final double offsetX=0, offsetY=0;
	
	
	public double getSizeX() {
		return sizeX;
	}

	public double getSizeY() {
		return sizeY;
	}

	public DField(double x, double y) {
		this.sizeX = x;
		this.sizeY = y;
		centerX = sizeX/2;
		centerY = sizeY/2;
	}
	
	//@Override
	public double getDistance2(Vecd2d p) {
		double dx = 0, dy = 0;
		/*dx = Math.max(Math.min(p.x, sizeX), 0);
		dy = Math.max(Math.min(p.y, sizeY), 0);
		
		//return Math.sqrt((p.x-dx)*(p.x-dx) + (p.y-dy)*(p.y-dy));
		dx = Math.abs(p.x) - sizeX;
		dy = Math.abs(p.y) - sizeY;
		Vecd2d nv = new Vecd2d(Math.max(dx, 0),Math.max(dy, 0));*/
		
		//return  -(Math.min(Math.max(dx, dy), 0) + nv.getLength());
		
		dx = p.x <= centerX ? p.x : sizeX-p.x;
		dy = p.y <= centerY ? p.y : sizeY-p.y;
		
		//System.out.println("dx: " + dx + " dy: " + dy);
		//System.out.println("p.x: " + p.x + " p.y: " + p.y);
		return -Math.min(dx, dy);
		//return dx;
		
		//return new Vecd2d(Math.abs(p.x)-sizeX, Math.abs(p.y)-sizeY).getLength() ;
	}
	
	
	@Override
	public Vecd2d getGradient(Vecd2d p) {
		Vecd2d g = new Vecd2d(0.0, 0.0);
		g.x = getDistance(new Vecd2d(p.x + EPSILON, p.y)) - getDistance(new Vecd2d(p.x - EPSILON, p.y) );
		g.y = getDistance(new Vecd2d(p.x, p.y + EPSILON)) - getDistance(new Vecd2d(p.x, p.y - EPSILON) );
		return g;
	}
	public double getDistance(Vecd2d p) {
		return boxDistance(new Vecd2d(p.x-centerX-offsetX, p.y-centerY-offsetY));
	}
	
	public double boxDistance(Vecd2d p) {
		double dx = 0, dy = 0;
		double bx,by, dd, bb, cc;
		
		//dx = p.x <= centerX ? p.x : sizeX-p.x;
		//dy = p.y <= centerY ? p.y : sizeY-p.y;
		
		dx = Math.abs(p.x) - centerX;
		dy = Math.abs(p.y) - centerY;
		
		dd = new Vecd2d(Math.max(dx, 0.0), Math.max(dy, 0.0)).getLength();
		//dd = new Vecd2d(dx, dy).getLength();
		//dd = new Vecd2d(Math.max(dx, 0.0), Math.max(dy, 0.0));
		
		bb = Math.min(Math.max(dx, dy),0);
		
		cc = bb+dd;
		if(dd!=0) {
			//System.out.println("dd: " + dd + " bb: " + bb + " dd+bb: " + cc);
		}
		
		return cc;
		
		//return Math.min(dx, dy);
	}
	
}
