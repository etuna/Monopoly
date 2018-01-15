package Squares;

import java.awt.Color;

import domain.Asset;
import domain.Bank;
import domain.BusTicket;
import domain.Main;
import domain.PaymentSystem;
import domain.Player;

public class BirthdayGiftSquare extends Square implements SquareFunc {

	private int birthday_selection = -1;
	public Main m;
	public Bank bank;
	public PaymentSystem payment_system;
	public Player current_player;

	public BirthdayGiftSquare(String name, String title, Asset asset, String color) {
		super(name, title, asset, color);
		m = Main.getInstance();
		bank = m.game.bank;
		payment_system = bank.payment_system;
		
		// TODO Auto-generated constructor stub
	}

	public void _func() {

		current_player = m.game.getCurrentPlayer();
		if (birthday_selection != -1) {
			if (birthday_selection == 1) {// $100
				payment_system.pay(current_player, BIRTHDAYPAY);

			} else if (birthday_selection == 2) { // Bus Ticket
				BusTicket bus_ticket = new BusTicket();
				current_player.tickets.add(bus_ticket);

			}
		}
		
		int cp_index = m.game.getCurrentPlayerIndex();
		Player p = m.game.currentPlayer;
		int GUI_cp_index = m.gUI.current_player;
		
		m.gUI.info.setText(m.gUI.info.getText()+"\nPlayer "+p.getName()+" paid birthday gift pay!");
		m.gUI.playerBalances.get(GUI_cp_index).setText("$ "+Integer.toString(p.getBalance()));

	}
	
	private final int BIRTHDAYPAY = 101;
}
