package events;

import domain.Asset;
import domain.Main;
import domain.Player;

public class BuySystem extends Systm{

//	private static final int systemType = Systm.BUY;

	@Override
	public void process(Evnt e) {
		Player currentPlayer = Main.game.getPlayers().get(Main.game.getCurrentPlayerIndex());
		Asset asset = currentPlayer.getCurrentSquare().getAsset();
		if(asset != null) {
			Main.game.bank.payment_system.receiveOrder(Main.game.bank.payment_system.BUY, currentPlayer, asset);
		}
	}

	public BuySystem() {
		super(Systm.BUY);
	}
	
/*	@Override
	public int getSystemType() {
		return systemType;
	}*/

}
