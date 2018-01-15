/*package events;

import domain.Board;
import domain.Die;
import domain.Game;
import domain.Main;
import domain.TurnState;

public class RollSystem extends Systm {

//	private final static int systemType = Systm.ROLL;

	@Override
	public void process(Evnt e) {
		if (!Main.game.isRollable())
			return;
		int pips = 0;
		boolean triples = false;
		Main.game.board.rollDice();
		Main.game.setRollable(false);

		Main.game.playerRolled();
		if (Main.game.board.dice[2].getFaceValue() < 4) {
			pips = Main.game.board.dice[0].getFaceValue() + Main.game.board.dice[1].getFaceValue()
					+ Main.game.board.dice[2].getFaceValue();
			triples = (Main.game.board.dice[0].getFaceValue()== Main.game.board.dice[1].getFaceValue()) && (Main.game.board.dice[0].getFaceValue() == Main.game.board.dice[0].getFaceValue());
		} else if (Main.game.board.dice[2].getFaceValue() == Die.MRMONOPOLY
				|| Main.game.board.dice[2].getFaceValue() == Die.MRMONOPOLY2) {
			Main.game.mrMonopoly = true;
			triples = false;
		} else {
			Main.game.theBus = true;
			triples = false;
		}
		if ((Main.game.board.dice[0].getFaceValue() == Main.game.board.dice[1].getFaceValue())
				&& Main.game.getPlayerRolled() < 3 && !triples) {
			Main.game.setRollable(true);
		}
		Main.game.eventManager.notify(new MoveEvent(pips, false));
	}
	
	
	public void actualProcess(Evnt e) {
		TurnState gameState = Main.game.turnState;
	}
	
	public RollSystem() {
		super(Systm.ROLL);
	}

	@Override
	public int getSystemType() {
		return systemType;
	}

}
*/