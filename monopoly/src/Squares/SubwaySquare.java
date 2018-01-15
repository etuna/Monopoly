package Squares;

import java.awt.Color;

import domain.Asset;
import domain.Main;

public class SubwaySquare extends Square implements SquareFunc {
	
	Main m;

	public SubwaySquare(String name, String title, Asset asset, String color) {
		super(name, title, asset, color);
		// TODO Auto-generated constructor stub
	}
	
	public void _func() {
		m = Main.m;
		
		m.game.currentPlayer.subway=true;
		m.game.currentPlayer.subwayMoved=false;
	}

}
