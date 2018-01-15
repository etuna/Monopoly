package domain;

import java.io.Serializable;

import events.Evnt;

public class PaymentSystem implements Serializable {

	public static PaymentSystem payment_system;
	public Main m;
	public Game game;
	public Bank bank;

	public PaymentSystem() {
		
	}
	
	public void conf_PS() {
		m = Main.getInstance();
		game = m.game;
		bank = m.game.bank;
	}

	public static PaymentSystem getInstance() {
		if(payment_system == null) {
			payment_system = new PaymentSystem();
		}
		return payment_system;
	}

	public int checkBalance(Player p) {
		return p.getBalance();
	}

	public boolean checkBuyPropertyTradeValidity(Player p, Asset a) {
		boolean enuf_balance = checkBalance(p) >= a.getValue();
		boolean isTaken = a.isBought;

		return (enuf_balance && !isTaken);
	}

	public boolean acceptBuyPropertyOrder(Player p, Asset a) {

		if (checkBuyPropertyTradeValidity(p, a)) {
			
			p.setBalance(p.getBalance() - a.getValue());
			bank.setBalance(bank.getBalance() + a.getValue());
			bank.getAssets().remove(a);
			p.getAssets().add(a);
			a.setBelongTo(p);
			a.isBought=true;
			p.buy_property_successful = true;
			int cp = m.game.getCurrentPlayerIndex();
			m.gUI.playerBalances.get(cp).setText("$ "+p.getBalance());
			Game.getInstance().eventManager.notify(new Evnt(Evnt.PAYMENT));
			return true;
		}
		return false;
	}

	public boolean receiveOrder(int orderNo, Player p, Asset a) {
		boolean order_progress = false;
		// 0 buy , 1 sell, 2 build, 3 sell building
		if (orderNo == BUY) {
			order_progress = acceptBuyPropertyOrder(p, a);
		} else if (orderNo == SELL) {
			order_progress = acceptSellPropertyOrder(p, a);
		} else if (orderNo == BUILD) {
			order_progress = acceptBuildOrder(p, a);
		} else {
			return order_progress;
		}
		return order_progress;
	}

	private boolean acceptBuildOrder(Player p, Asset a) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean checkSellPropertyTradeValidity(Player p, Asset a) {
		boolean enuf_balance = bank.getBalance() >= a.getValue();
		boolean belongTo = a.getBelongTo() == p;
		return enuf_balance && belongTo;
	}

	public boolean acceptSellPropertyOrder(Player p, Asset a) {
		if (checkSellPropertyTradeValidity(p, a)) {
			p.getAssets().remove(a);
			p.setBalance(p.getBalance() + a.getValue()/2);
			bank.getAssets().add(a);
			bank.setBalance(bank.getBalance() - a.getValue()/2);
			a.isBought=false;
			a.setBelongTo(null);
			int cp = m.game.getCurrentPlayerIndex();
			m.gUI.playerBalances.get(cp).setText("$ "+p.getBalance());
			
			String assets = "";
			for(Asset as : p.getAssets()) {
				assets+="\n-->"+ as.getName();
			}
			
			m.gUI.playerAssets.get(cp).setText("ASSETS\n========="+assets);
			Game.getInstance().eventManager.notify(new Evnt(Evnt.PAYMENT));
			return true;
		}
		return false;
	}

	public boolean payBirthdayPay(Player p) {

		if (bank.getBalance() > 100) {
			bank.setBalance(bank.getBalance() - 100);
			p.setBalance(p.getBalance() + 100);
			int cp = m.game.getCurrentPlayerIndex();
			m.gUI.playerBalances.get(cp).setText("$ "+p.getBalance());
			Game.getInstance().eventManager.notify(new Evnt(Evnt.PAYMENT));
			return true;
		}
		return false;
	}

	// Bank pays
	public boolean pay(Player p, int order) {
		if (order == BIRTHDAYPAY) {
			payBirthdayPay(p);
			return true;
		}else if(order == BUILDINGPAY) {
			payBuildingPay();
			return true;
		}else if(order==GOPAY){
			payGoPay(p);
			return true;
		}else if(order==CHANCEPAY){
			payChancePay(p);
			
		}else{
			
		}
		return false;
	}
	
	public boolean payBuildingPay() {
		bank.setBalance(bank.getBalance()-50);
		game.getCurrentPlayer().setBalance(game.getCurrentPlayer().getBalance()+50);
		int cp = m.game.getCurrentPlayerIndex();
		m.gUI.playerBalances.get(cp).setText("$ "+game.currentPlayer.getBalance());
		Game.getInstance().eventManager.notify(new Evnt(Evnt.PAYMENT));
		return true;
	}
	
	public boolean payGoPay(Player p) {
		p.setBalance(p.getBalance()+200);
		bank.setBalance(bank.getBalance()-200);
		int cp = m.game.getCurrentPlayerIndex();
		m.gUI.playerBalances.get(cp).setText("$ "+p.getBalance());
		Game.getInstance().eventManager.notify(new Evnt(Evnt.PAYMENT));
		return true;
	}
	
	public boolean payChancePay(Player p) {
		p.setBalance(p.getBalance()+150);
		bank.setBalance(bank.getBalance()-150);
		int cp = m.game.getCurrentPlayerIndex();
		m.gUI.playerBalances.get(cp).setText("$ "+p.getBalance());
		Game.getInstance().eventManager.notify(new Evnt(Evnt.PAYMENT));
		return true;
	
	}
	
	// Someone pays to bank
	public boolean acceptPay(Player p, int amount) {
		p.checkBankrupt(amount);
		bank.setBalance(bank.getBalance() + amount);
		p.setBalance(p.getBalance() - amount);
		int cp = m.game.getCurrentPlayerIndex();
		m.gUI.playerBalances.get(cp).setText("$ "+p.getBalance());
		Game.getInstance().eventManager.notify(new Evnt(Evnt.PAYMENT));
		return true;
	}

	public static final int BUY = 0;
	public static final int SELL = 1;
	public static final int BUILD = 2;
	public static final int BIRTHDAYPAY = 101;
	public static final int GOPAY = 200;
	public static final int BUILDINGPAY = 201;
	public static final int CHANCEPAY = 202;

}
