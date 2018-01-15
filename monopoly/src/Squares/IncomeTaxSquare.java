package Squares;

import java.awt.Color;

import domain.Asset;
import domain.Game;
import domain.Main;
import domain.PaymentSystem;
import domain.Player;

public class IncomeTaxSquare extends Square implements SquareFunc {
	
	Main m ;
	Game game ;
	Player current_player;
	PaymentSystem payment_system;
	int pay_selection = -1;
	
	

	public IncomeTaxSquare(String name, String title, Asset asset, String color) {
		super(name, title, asset, color);
		m = Main.m;
		game = m.game;
		payment_system = game.bank.payment_system;
		
		// TODO Auto-generated constructor stub
	}

	
	public void _func() {
		current_player = game.getCurrentPlayer();
		
		if(pay_selection != -1) {
			if(pay_selection == PAY10PERCENT) {
				//Do smt
				pay_selection = -1;
				
			}else if(pay_selection == PAY200) {
				payment_system.acceptPay(current_player, 200);
				pay_selection = -1;
			}
			
		}
		
		
	}
	
	
	private final int PAY10PERCENT = 1;
	private final int PAY200 = 2;
	
	
}
