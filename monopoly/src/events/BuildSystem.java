package events;

import domain.Main;

public class BuildSystem extends Systm{

//	private static int systemType = Systm.BUILD;
	
	@Override
	public void process(Evnt e) {
		BuildEvent ee = (BuildEvent) e;
		if (Main.game.getCurrentPlayer().getCurrentSquare().getAsset()!= null) {
			Main.game.bank.payment_system.receiveOrder(Main.game.bank.payment_system.BUILD
					, Main.game.getCurrentPlayer()
					, Main.game.getCurrentPlayer().getCurrentSquare().getAsset());
		}
	}

	public BuildSystem() {
		super(Systm.BUILD);
	}
	
/*	@Override
	public int getSystemType() {
		return systemType;
	}*/

}
