package events;

public class EndTurnEvent extends Evnt {

/*	private static final int eventType = Evnt.ENDTURN;
	
	@Override
	public int getEventType() {
		return eventType;
	}*/
	public EndTurnEvent() {
		super(Evnt.ENDTURN);
	}

}
