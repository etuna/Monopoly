package events;

import domain.Main;

public class EndTurnSystem extends Systm{

//	private final static int systemType = Systm.ENDTURN;
	
	
	@Override
	public void process(Evnt e) {
		if(Main.game.isRollable()) {
			return;
		}
	}

	public EndTurnSystem() {
		super(Systm.ENDTURN);
	}
	
/*	@Override
	public int getSystemType() {
		// TODO Auto-generated method stub
		return systemType;
	}*/
}
