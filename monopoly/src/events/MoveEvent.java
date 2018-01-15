package events;

public class MoveEvent extends Evnt{

//	private static final int eventType = Evnt.MOVE;
	public final int moveBy;
	public final boolean moveToInstead;
	public final int posLayer;
	public final int posSquare;
	
	
	
	public MoveEvent(int moveBy, boolean moveToInstead) {
		super(Evnt.MOVE);
		this. moveBy = moveBy;
		this.moveToInstead = moveToInstead;
		this.posLayer = 0;
		this.posSquare = 0;
	}
	
	public MoveEvent(int moveBy) {
		super(Evnt.MOVE);
		this. moveBy = moveBy;
		this.moveToInstead = false;
		this.posLayer = 0;
		this.posSquare = 0;
	}
	
	public MoveEvent(int posLayer, int posSquare, boolean moveToInstead) {
		this. moveBy = 0;
		this.moveToInstead = moveToInstead;
		this.posLayer = posLayer;
		this.posSquare = posSquare;
	}
	
/*	@Override
	public int getEventType() {
		// TODO Auto-generated method stub
		return eventType;
	}*/

}
