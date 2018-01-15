package Squares;

import java.awt.Color;

import domain.Asset;
import domain.Game;
import domain.Main;
import domain.Player;
import domain.PositionPair;

public class HollandTunnelSquare extends Square implements SquareFunc {
	
	
	Main m ;
	Game game;
	Player current_player;
	
	

	public HollandTunnelSquare(String name, String title, Asset asset, String color) {
		super(name, title, asset, color);
		// TODO Auto-generated constructor stub
		m = Main.m;
		game = m.game;
	}
	
	public void _func() {
		current_player = game.getCurrentPlayer();
		PositionPair newPos;
		//SET POSITION PAIR!
		int layer = current_player.getPosition().layer_position;
		int square = current_player.getPosition().square_position;
		if(layer == 0) {
			layer = 2;
			square = 18;
			newPos = new PositionPair(layer,square);
			
		}else {
			layer =  0;
			square = 14;
			newPos = new PositionPair(layer,square);
		}
		System.out.println("HOLLAND TUNNEL!");
		current_player.setPosition(newPos);
		
	}

}
