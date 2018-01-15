package test;

import static org.junit.Assert.*;

import org.junit.Test;
import domain.*;

public class BuyPropertyBalanceTest {

	@Test
	public void test() {

		Player test_player = new Player("test_player");
		Asset test_asset = new Asset("test_asset",100,10,"test_color");
		
		Game game = Game.getInstance();
		game.conf_game();
		
		test_player.game = game;
		test_player.bank = game.bank;
		game.bank.payment_system.bank = game.bank;
		game.setCurrentPlayer(test_player);
		
		//old balance of bank,before purchase
		int bank_oldBalance = game.bank.getBalance();
		
		//player buys this property(asset)
		test_player.buyProperty(test_asset);
		//Player starts game with 3200,after he buys,his balances decreases to 3200 - asset.value
		assertEquals(3200, test_player.getBalance()+test_asset.getValue());
		
		//Checking whether purchase is OK
		assertEquals(test_player, test_asset.getBelongTo());
		//TEST bank old balance and new balance
		assertEquals(bank_oldBalance, game.bank.getBalance()-test_asset.getValue());
		
		
		
		//----------TRUE!
		
		
	}

}
