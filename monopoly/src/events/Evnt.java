package events;

import java.io.Serializable;

public class Evnt implements Serializable{
	
	/*
	static int lastIndex = 0;
	public static enum EventTypes
	{
		
		DEFAULT(0),
		ROLL_BUTTON,
		BUY_BUTTON(5),
		SAVE_BUTTON,
		
		
		
		LAST;
		
		
		
		public int index;
		
		private EventTypes(int i) {
			this.index = i;
		}
		private EventTypes() {
			this.index = ++Event.lastIndex;
		}
	}
	*/
	
	/*private int eventType;
	
	
	
	public Event(int eventType){
		this.eventType = eventType;
	}*/
	public final int eventType;
	
	public Evnt() {
		this(Evnt.DEFAULT);
	}
	
	public Evnt(int eventType) {
		this.eventType = eventType;
	}
			
	
	public int getEventType() {
		return eventType;
	}
	
	public final static int DEFAULT = 0;
	public final static int BUTTON = 1;
	public final static int ROLL = 2;
	public final static int BUY = 3;
	public final static int SAVE = 4;
	public final static int LOAD = 5;
	public final static int BUILD = 6;
	public final static int ENDTURN = 7;
	public final static int MOVE = 8;
	public final static int TURN = 9;
	public final static int MRMONOPOLY = 10;
	public final static int THEBUS = 11;
	public final static int NEWTURN = 12;
	public final static int DOMRMONOPOLY = 13;
	public final static int DOTHEBUS = 14;
	public final static int CONT = 15;
	public final static int JAIL = 16;
	public final static int BANKRUPT = 17;
	public final static int WIN = 18;
	public final static int PAYMENT = 19;
	public final static int WAIT = 20;

	public final static int LAST = 21;
	
}
