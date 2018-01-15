/*package events;

import domain.Board;
import domain.Die;
import domain.Main;
import domain.TurnState;

public class TurnSystem extends Systm {

	// private static final int systemType = Systm.TURN;

	@Override
	public void process(Evnt e) {
		TurnState tState = Main.game.turnState;

		switch (tState.turnPhase) {

		case 1:
			if (e.getEventType() == Evnt.ROLL) {
				Board board = Main.game.board;
				Die dice[] = board.dice;
				board.rollDice();
				if (dice[3].getFaceValue() < 4) { // all pips
					tState.pips = dice[0].getFaceValue() + dice[1].getFaceValue() + dice[2].getFaceValue();
					tState.isTheBus = false;
					tState.isMrMonopoly = false;
					tState.isDoubles = dice[0].getFaceValue() == dice[1].getFaceValue();

				} else if (dice[3].getFaceValue() == Die.BUS) { // bus
					tState.pips = dice[0].getFaceValue() + dice[1].getFaceValue();
					tState.isTheBus = true;
					tState.isMrMonopoly = false;
					Main.game.eventManager.notify(new TheBusEvent());

				} else { // mr monopoly
					tState.pips = dice[0].getFaceValue() + dice[1].getFaceValue();
					tState.isTheBus = false;
					tState.isMrMonopoly = true;
					Main.game.eventManager.notify(new MrMonopolyEvent());
				}
				tState.turnPhase = 2;
				Main.game.eventManager.notify(new MoveEvent(tState.pips));
				Main.game.getCurrentPlayer().getCurrentSquare()._func();
				tState.turnPhase = 3;
			}
			System.out.println(Main.game.board.dice[0].getFaceValue());
			break;

		case 2:

			break;

		case 3:
			if (e.getEventType() == Evnt.DOMRMONOPOLY || e.getEventType() == Evnt.CONT) {
				if (tState.isMrMonopoly) {
					doMrMonopolyStuff();
					
				}
				
			}else if(e.getEventType() == Evnt.DOTHEBUS) {
				if (tState.isTheBus) {
					doTheBusStuff();
					
				}
			}
			Main.game.getCurrentPlayer().getCurrentSquare()._func();
			tState.turnPhase = 4;
			
			break;

		case 4:
			tState.turnPhase = 5;
			Main.game.eventManager.notify(new Evnt(Evnt.CONT));
			break;

		case 5:
			if (tState.isDoubles) {
				tState.turnPhase = 6;
				Main.game.eventManager.notify(new Evnt(Evnt.CONT));
			} else {
				Main.game.eventManager.notify(new NewTurnEvent());
				tState.turnPhase = 1;
			}
			break;

		case 6:
			if (tState.rollCount < 3) {

			} else {
				// send to jail
				Main.game.eventManager.notify(new NewTurnEvent());
			}

			break;

		}

	}

	public void doMrMonopolyStuff() {

	}

	public void doTheBusStuff() {

	}

	public TurnSystem() {
		super(Systm.TURN);
	}

	
	 * @Override public int getSystemType() { return systemType; }
	 

}
*/