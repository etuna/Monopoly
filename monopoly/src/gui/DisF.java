package gui;

public interface DisF {
	
	public default Vecd2d getGradient(Vecd2d p){
		Vecd2d g = new Vecd2d(0.0, 0.0);
		g.x = getDistance(new Vecd2d(p.x + EPSILON, p.y)) - getDistance(new Vecd2d(p.x - EPSILON, p.y) );
		g.y = getDistance(new Vecd2d(p.x, p.y + EPSILON)) - getDistance(new Vecd2d(p.x, p.y - EPSILON) );
		return g;
	};
	public double getDistance(Vecd2d p);
	
	public static final double EPSILON = 3;
}
