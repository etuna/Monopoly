package test;

import static org.junit.Assert.*;

import org.junit.Test;

import domain.*;

public class AssetBelongToTest {
	Player test_player;
	Asset test_asset;
	

	@Test
	public void test() {
		Game game = new Game();
		game.conf_game();
		game.board.config_board();
		game.bank.config_Bank();
		
		test_player = new Player("test_player");
		test_asset = new Asset("test_asset",100,10,"test_color");
	
		//Test asset is not owned yet
		assertNull(test_asset.getBelongTo());
		
		//Test - our asset must belong to test_player
		test_asset.setBelongTo(test_player);
		assertEquals(test_asset.getBelongTo(), test_player);
		
		
		//Reset asset.belongto
		test_asset.setBelongTo(null);
		assertNull(test_asset.getBelongTo());
		
		game.getPlayers().add(test_player);
		game.setCurrentPlayer(test_player);
		game.getAssets().add(test_asset);
		test_player.game = game;
		test_player.board = game.board;
		test_player.bank = game.bank;
		game.bank.payment_system.bank = game.bank;
		
		assertNotNull(test_player.bank);
		assertNotNull(game.bank.payment_system);
		
		//Test asset's belongTo is assigned to the buyer when a player buys this asset
		test_player.buyProperty(test_asset);
		assertEquals(test_asset.getBelongTo(), test_player);
		
		
		//-------------TRUE!
		
		
	}

}
