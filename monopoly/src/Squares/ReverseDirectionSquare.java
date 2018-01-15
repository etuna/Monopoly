package Squares;

import java.awt.Color;
import domain.*;

import domain.Asset;
import domain.Main;

public class ReverseDirectionSquare extends Square implements SquareFunc {
	
	Main m;

	public ReverseDirectionSquare(String name, String title, Asset asset, String color) {
		super(name, title, asset, color);
		// TODO Auto-generated constructor stub
	}

	public void _func() {
		this.m = Main.m;
		Player current_player = m.game.currentPlayer;
		current_player.move_direction=2;
		
	}
}
