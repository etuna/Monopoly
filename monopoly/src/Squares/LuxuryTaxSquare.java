package Squares;

import java.awt.Color;

import domain.Asset;
import domain.Main;
import domain.PaymentSystem;
import domain.Player;

public class LuxuryTaxSquare extends Square implements SquareFunc  {
	
	public Main m;
	public PaymentSystem payment_system;
	public Player current_player;

	public LuxuryTaxSquare(String name, String title, Asset asset, String color) {
		super(name, title, asset, color);
		// TODO Auto-generated constructor stub
	
	}
	
	public void _func() {
		current_player = m.game.currentPlayer;
		if(current_player.getBalance() >75) {
			payment_system.acceptPay(current_player, 75);
		}else {
			//what then?
		}
		
		int cp_index = m.game.getCurrentPlayerIndex();
		Player p = m.game.currentPlayer;
		int GUI_cp_index = m.gUI.current_player;
		
		m.gUI.info.setText(m.gUI.info.getText()+"\nPlayer "+p.getName()+" paid birthday gift pay!");
		m.gUI.playerBalances.get(GUI_cp_index).setText("$ "+Integer.toString(p.getBalance()));
	}
	
	public void _conf(Main m) {
		this.m = m;
		payment_system = m.game.bank.payment_system;
		current_player = m.game.getCurrentPlayer();
	}

}
