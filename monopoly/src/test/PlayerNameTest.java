package test;

import static org.junit.Assert.*;

import org.junit.Test;

import domain.Player;

public class PlayerNameTest {
	Player test_player;

	@Test
	public void test() {

		//test constructor's name assignment
		test_player = new Player("first-name");
		String expectedFirst = test_player.getName();
		assertEquals(expectedFirst, "first-name");
		
		
		//Test setName() method
		test_player.setName("second-name");
		String expectedSecond = test_player.getName();
		assertEquals(expectedSecond, "second-name");
		
		//---------------TRUE!
		
	}

}
