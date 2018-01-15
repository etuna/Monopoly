package events;

import java.io.Serializable;

public abstract class Systm implements Serializable{
	
	
	public abstract void process(Evnt e);
	
	public final int systemType;
	public int getSystemType() {
		return systemType;
	}
	
	
	
	public Systm() {
		this(Systm.DEFAULT);
	}
	public Systm(int systemType) {
		this.systemType = systemType;
	}
	
	
	public final static int DEFAULT = 0;
	public final static int BUTTON = 1;
	public final static int ROLL = 2;
	public final static int SAVE = 3;
	public final static int LOAD = 4;
	public final static int BUY = 5;
	public final static int ENDTURN = 6;
	public final static int MOVE = 7;
	public final static int BUILD = 8;
	public final static int TURN = 9;
	public final static int GOSQUARE = 10;
	public final static int MRMONOPOLY = 11;
	public final static int THEBUS = 12;
	public final static int BOT = 13;
	public final static int VELOCITY = 14;
	public final static int POSITION = 15;
	public final static int COLLISIONDETECTION = 16;
	public final static int COLLISIONRESPONSE = 17;
	public final static int FORCE = 18;
	public final static int LAST = 19;
}
