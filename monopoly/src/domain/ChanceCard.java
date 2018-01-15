package domain;

import java.util.ArrayList;

public class ChanceCard extends Card implements CardInterface{
	Main m;
	
	

	public ChanceCard(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void _func(Player p, int op) {
		
		if(op==func0) {
			m.gUI.info.setText(m.gUI.info.getText()+"\nChance Card!\n--->LOAN MATURES!\nCollect $ 150! :)\n");
			m.game.bank.payment_system.pay(p, 202);
			
			ChanceCard c = new ChanceCard("ChanceCard(Take $150)");
			p.cards.add(c);
			p.ticketsNcards.add(c);
						
		}else if(op==func1) {
			Player current_player = m.game.currentPlayer;
			m.gUI.info.setText(m.gUI.info.getText()+"\nPlayer "+current_player.getName()+" goes to jail!\n");
			m.gUI.info.setText(m.gUI.info.getText()+"\nChance Card!\n--->YOU ARE COUGHT AT INSIDER TRADING\nPay the bank a fine equal to"
					+ "devidents on all stocks you have, and go to jail!\n");
			
			int amount = 0;
			
			for(Asset a : p.getAssets()) {
				amount += a.getValue();
			}
			
			m.game.bank.payment_system.acceptPay(current_player, amount);
			
			
			if(current_player.inJail && current_player.numturn_inJail != 0) {
			//current_player.getOutOfJail();
			current_player.numturn_inJail=0;
			//set position of the player!
			} else {
			current_player = m.game.getCurrentPlayer();
			current_player.goToJail();
			}
			
			ChanceCard c = new ChanceCard("ChanceCard(COUGHT AT INSIDER TRADING!)");
			p.cards.add(c);
			p.ticketsNcards.add(c);
			
			
		}else if(op==func2) {
			m.gUI.info.setText(m.gUI.info.getText()+"\nChance Card!\n--->SERVICE PAY!\nPay $50 to each player! :(\n");
			ArrayList<Player> players = (ArrayList<Player>) m.game.getPlayers();
			int cp = m.game.getCurrentPlayerIndex();
			
			for(Player mp:m.game.getPlayers()) {
				if(mp != p) {
					p.payMoneyToPlayer(mp, 50);
				}
			}
			
			m.gUI.playerAssets.get(cp).setText("$ "+p.getBalance());
			ChanceCard c = new ChanceCard("ChanceCard(SERVICE PAY- Pay $50 to each)");
			p.cards.add(c);
			p.ticketsNcards.add(c);
			
		}else if(op==func3) {
			m.gUI.info.setText(m.gUI.info.getText()+"\nChance Card!\nADVANCE TO STOCK EXCHANGE\nIf you pass GO,collect $200:)");
			ChanceCard c = new ChanceCard("ChanceCard(GO - Collect $200)");
			p.cards.add(c);
			p.ticketsNcards.add(c);
		}else if(op==func4){
			m.gUI.info.setText(m.gUI.info.getText()+"\nChance Card!\nSCHOOL FEES!\nPay $150 immediately :)");
			m.game.bank.payment_system.acceptPay(p, 150);
			int cp = m.game.getCurrentPlayerIndex();
			m.gUI.playerBalances.get(cp).setText("$ "+p.getBalance());
		}else {
			m.gUI.info.setText(m.gUI.info.getText()+"\nChance Card!\nGO BACK 3 SPACES!\n");
			int square = p.getPosition().square_position;
			int layer = p.getPosition().layer_position;
			
			square -=3;
			if(square<0) {
				square += layer;
			}
			p.setPosition(new PositionPair(square,layer));
			
			
		}

		
		
	}
	
	
	public void setMain(Main m) {
		this.m = m;
	}
	
	
	
	
	public static int func0 = 0;//TAKE $100
	public static int func1 = 1;//GO TO JAIL
	public static int func2 = 2;//PAY $50 each player
	public static int func3 = 3;//
	public static int func4 = 4;
	@Override
	public void _func() {
		// TODO Auto-generated method stub
		
	}

	
}
