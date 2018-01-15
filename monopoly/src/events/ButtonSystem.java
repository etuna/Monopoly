package events;

import domain.Game;
import domain.Main;

public class ButtonSystem extends Systm {
	
//	private final static int systemType = Systm.BUTTON;
	
	
	
	public void process(Evnt e) {
/*		ButtonEvent be = (ButtonEvent)e;
		if (be.getButtonType() == ButtonEvent.ROLLBUTTON) {
			
		}
		switch (be.getButtonType()) {
		case ButtonEvent.ROLLBUTTON: 
			Main.game.eventManager.notify(be.e);
			break;
		case ButtonEvent.BUYBUTTON:
			Main.game.eventManager.notify(be.e);
			break;
		case ButtonEvent.ENDTURNBUTTON: 
			Main.game.eventManager.notify(be.e);
			break;
		case ButtonEvent.BUILDBUTTON:
			Main.game.eventManager.notify(be.e);
		}
		
			
		Main.game.eventManager.notify(be.e);*/
		
	}

	public ButtonSystem() {
		super(Systm.BUTTON);
	}
	
/*	@Override
	public int getSystemType() {
		return systemType;
	}*/
	
	
	
}
