package test;

import static org.junit.Assert.*;
import domain.*;

import org.junit.Test;

public class PlayerIsInJailTest {

	@Test
	public void test() {
		

		Game game = Game.getInstance();
		game.conf_game();
		//Jail is created?
		assertNotNull(game.jail);
		
		Player test_player = new Player("test_player");
		test_player.game = game;
		//Jail is empty now?
		assertEquals(0, game.jail.prisoners.size());
		
		//player goes to Jail now
		test_player.goToJail();
		
		//Jail has someone?
		assertEquals(1, game.jail.prisoners.size());
		
		//Is his name "test_player" ?
		assertEquals("test_player",game.jail.prisoners.get(0).getName());
		
		//test_player knows he is in jail?
		assertEquals(true, test_player.inJail);
		
		
		//--------------TRUE!
		
		
	}

}
