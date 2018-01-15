/*package events;

import domain.Main;

public class StaticSystem {
	public static void setForRoll() {
		
	}
	private Evnt nextEvent;
	
	public void sendNextEvent() {
		Main.game.eventManager.notify(nextEvent);
	}
	
	
	public Evnt getNextEvent() {
		return nextEvent;
	}
	
	public void setNextEvent(Evnt e) {
		nextEvent = e;
	}
	
	public static void setSubs() {
		if(Main.game.isRollable()) {
			Main.game.eventManager.removeSub(Evnt.ENDTURN, Systm.ENDTURN);
		}else {
			Main.game.eventManager.addSub(Evnt.ENDTURN, Systm.ENDTURN);
		}
		
		
	}
}
*/