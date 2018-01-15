package gui;

public class Vecd2d {
	public double x, y;
	
	public Vecd2d (double x, double y) {
		this.x = x;
		this.y = y;
	}
	public Vecd2d () {
		this.x = 0;
		this.y = 0;
	}
	
	public Vecd2d normalize() {
		double factor = getLength();
		this.x = x/factor;
		this.y = y/factor;
		return this;
	}
	
	public double getLength() {
		return Math.sqrt(getSquaredLength());
	}
	
	public double getSquaredLength() {
		
		return x*x + y*y;
	}
	
	public Vecd2d changeLength(double newLength) {
		this.normalize();
		this.x *= newLength;
		this.y *= newLength;
		return this;
	}
	
	public Vecd2d setToMaxLength(double maxLength) {
		if(this.getLength()>maxLength)
			this.changeLength(maxLength);
		return this;
	}
	
}