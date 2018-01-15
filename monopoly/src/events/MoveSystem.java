package events;

import domain.Main;
import domain.Player;
import domain.PositionPair;

public class MoveSystem extends Systm {

//	private static final int systemType = Systm.MOVE;

	@Override
	public void process(Evnt e) {
		// TODO Auto-generated method stub
		int moveTo;
		Player player;
		
		player = Main.game.getCurrentPlayer();
		
		MoveEvent ee = (MoveEvent) e;
		if (ee.moveToInstead) {
			Main.game.getPlayers().get(Main.game.getCurrentPlayerIndex()).setPosition(new PositionPair(ee.posLayer, ee.posSquare));
		} else { //move by ee.moveBy
			
			moveTo = (player.getPosition().square_position + ee.moveBy ) % Main.game.board.squares.get(player.getPosition().layer_position).size() ;
			player.setPosition(new PositionPair( player.getPosition().layer_position, moveTo));
			
		}
	}

	public MoveSystem() {
		super(Systm.MOVE);
	}
	
/*	@Override
	public int getSystemType() {
		// TODO Auto-generated method stub
		return systemType;
	}*/

}
