package test;

import static org.junit.Assert.*;

import org.junit.Test;

import domain.Player;

public class PlayerInitTest {
	Player test_player;

	@Test
	public void test() {
		
		//Test whether player is initialized or not
		test_player = new Player("test");
		assertNotNull(test_player);
		
		//--------------TRUE!
		
	}

}
