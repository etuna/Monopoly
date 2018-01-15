package events;

import java.io.Serializable;

import domain.Bobby;
import domain.Main;

public class BotSystem extends Systm implements Serializable{

	@Override
	public void process(Evnt e) {
		// TODO Auto-generated method stub
		switch(e.eventType) {
		case Evnt.BUTTON:
			Bobby.getInstance().setState(Bobby.NEUTRAL);
			break;
		case Evnt.JAIL:
			Bobby.getInstance().setState(Bobby.SAD);
			break;
		case Evnt.WIN:
			Bobby.getInstance().setState(Bobby.HAPPY);
			break;
		case Evnt.BANKRUPT:
			Bobby.getInstance().setState(Bobby.ANGRY);
			break;
		case Evnt.PAYMENT:
			Bobby.getInstance().setState(Bobby.ANGRY);
			break;
		case Evnt.MRMONOPOLY:
			Bobby.getInstance().setState(Bobby.HAPPY);
			break;
		case Evnt.WAIT:
			Bobby.getInstance().setState(Bobby.SAD);
			break;
		}
		
		Main.m.game.bobby_bot.setTimer(0);
		
		
	}
	
	public BotSystem() {
		super(Systm.BOT);
	}

}
