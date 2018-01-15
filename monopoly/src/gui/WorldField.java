package gui;

public class WorldField implements DisF{
	
	private Splite spl;
	public int minDex=0;
	
	public WorldField(Splite spl) {
		this.spl = spl;
	}
	
	@Override
	public double getDistance(Vecd2d p) {
		double d = 9999;//spl.getField().getDistance(p);
		double b = spl.getDistance(p);
		double dst;
		double brs = 1000;
		int dex=-1;
		for (DisF df : spl.getBricks()) {
			//df = spl.getBricks().get(i);
			dst = df.getDistance(p);
			if(brs>dst && ((Brick)df).alive) {
				brs = Math.min(dst, brs);
				dex = ((Brick)df).index;
			}
		}
		/*System.out.println("distance to bricks: "+brs+" index: "+dex);
		System.out.println("distance to field: "+d);
		System.out.println("distance to bar: "+b);*/
		if(brs<b) {
			d = brs;
			
		}
		else {
			d = b;
			dex = 9991;
		}
		if(b<d) {
			d = b;
			dex = 9998;
		}
		
		minDex = dex;
		return d;
	}

}
