package Squares;

import java.awt.Color;

import domain.Asset;
import domain.Game;
import domain.Main;
import domain.Player;

public class JailSquare extends Square implements SquareFunc {
	
	public Main m;
	public Game game;
	public Player current_player;

	public JailSquare(String name, String title, Asset asset, String color) {
		super(name, title, asset, color);
		
		// TODO Auto-generated constructor stub
	}
	
	public void _func() {
		
		current_player = m.game.currentPlayer;
		m.gUI.info.setText(m.gUI.info.getText()+"\nPlayer "+current_player.getName()+" goes to jail!");
		
		if(current_player.inJail && current_player.numturn_inJail != 0) {
		//current_player.getOutOfJail();
		current_player.numturn_inJail=0;
		//set position of the player!
		} else {
		current_player = game.getCurrentPlayer();
		current_player.goToJail();
		}
		
	}
	
	public void _conf(Main m) {
		this.m = m;
		game = m.game;
	}

}
