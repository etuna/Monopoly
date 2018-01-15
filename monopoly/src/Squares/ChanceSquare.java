package Squares;

import java.awt.Color;
import java.util.Random;

import domain.Asset;
import domain.ChanceCard;
import domain.Main;
import domain.Player;

public class ChanceSquare extends Square implements SquareFunc{
	
	public ChanceCard c;
	Main m;
	Random r;

	public ChanceSquare(String name, String title, Asset asset, String color) {
		super(name, title, asset, color);
		m = Main.m;
		r= new Random();
		// TODO Auto-generated constructor stub
	}

	
	public void _func() {
		Player current_player = m.game.currentPlayer;
		int cp = m.game.getCurrentPlayerIndex();
		int op = r.nextInt(5);
		
		c._func(current_player, op);
		
		
	}
	
	public void setChanceCard(ChanceCard c) {
		this.c = c;
	}
}
