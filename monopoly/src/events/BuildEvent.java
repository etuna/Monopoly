package events;

import domain.Main;
import domain.PositionPair;

public class BuildEvent extends Evnt{

//	private static final int eventType = Evnt.BUILD;
	
	public final int floor;
	public final int sqrPos;
	
	public BuildEvent(int floor, int position) {
		this.floor = floor;
		this.sqrPos = position;
	}
	public BuildEvent() {
		super(Evnt.BUILD);
		PositionPair pos = Main.game.getPlayers().get(Main.game.getCurrentPlayerIndex()).getPosition();
		this.floor = pos.layer_position;
		this.sqrPos = pos.square_position;
		
	}
	
	@Override
	public int getEventType() {
		// TODO Auto-generated method stub
		return eventType;
	}
}
