package Squares;

import java.awt.Color;

import domain.Asset;
import domain.Game;
import domain.Main;
import domain.Player;

public class GoToJailSquare extends Square implements SquareFunc{

	Main m;
	Game game;
	Player current_player;
	
	public GoToJailSquare(String name, String title, Asset asset, String color) {
		super(name, title, asset, color);
		// TODO Auto-generated constructor stub
		m = Main.m;
		game = m.game;
	}

	public void _func() {
		current_player = game.getCurrentPlayer();
		current_player.goToJail();
		
	}
}
