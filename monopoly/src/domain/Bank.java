/*
 * 
 */
package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Bank.
 */
public class Bank implements Serializable{
	
	/** The bank. */
	public static Bank bank;
	
	/** The balance. */
	private int balance;
	
	/** The assets. */
	private List<Asset> assets;
	
	/** The payment system. */
	public PaymentSystem payment_system;
	
	/** The board. */
	public Board board;
	
	/** The houses. */
	public ArrayList<House> houses;
	
	/** The hotels. */
	public ArrayList<Hotel> hotels;
	
	/** The skyscrapers. */
	public ArrayList<Skyscraper> skyscrapers;
	
	
	/**
	 * Instantiates a new bank.
	 */
	public Bank() {
		//--		
	}
	
	/**
	 * Config bank.
	 * @requires that the game is in init state
	 * @modifies the bank
	 * @effects the game state
	 */
	public void config_Bank() {
		payment_system = PaymentSystem.getInstance();
		payment_system.conf_PS();
		board = Board.getInstance();
		balance = 100000;
		assets = new ArrayList<Asset>();
		houses = new ArrayList<House>();
		hotels = new ArrayList<Hotel>();
		skyscrapers = new ArrayList<Skyscraper>();
		populateBuildings();
	}
	
	
	/**
	 * Gets the single instance of Bank.
	 *
	 * @return single instance of Bank
	 */
	public static Bank getInstance() {
		if(bank== null) {
			bank = new Bank();
		}
		return bank;
	}
	
	/**
	 * Receive buy attempt.
	 *
	 * @param p the player to buy
	 * @param a the asset to buy
	 * @return true, if successful
	 * 
	 * @requires a player and a buy-able asset
	 * @modifies the player attempting to buy ant the asset ownership
	 * @effects the board and the asset and the player
	 */
	public boolean receiveBuyAttempt(Player p, Asset a) {
		payment_system.receiveOrder(0, p, a);
		return false;
	}
	
	/**
	 * Receive sell attempt.
	 *
	 * @param p the player to sell
	 * @param a the asset to sell
	 * @return true, if successful
	 * 
	 * @requires a player and a sell-able asset
	 * @modifies the player attempting to sell ant the asset ownership
	 * @effects the board and the asset and the player
	 */
	public boolean receiveSellAttempt(Player p , Asset a) {
		payment_system.receiveOrder(1, p, a);
		return false;
	}


	/**
	 * Gets the balance.
	 *
	 * @return the balance
	 */
	public int getBalance() {
		return balance;
	}


	/**
	 * Sets the balance.
	 *
	 * @param balance the new balance
	 */
	public void setBalance(int balance) {
		this.balance = balance;
	}


	/**
	 * Gets the assets.
	 *
	 * @return the assets
	 */
	public List<Asset> getAssets() {
		return assets;
	}


	/**
	 * Sets the assets.
	 *
	 * @param assets the new assets
	 */
	public void setAssets(List<Asset> assets) {
		this.assets = assets;
	}
	
	
	/**
	 * Populate buildings.
	 * 
	 * @requires the bank to be initiated
	 * @modifies the houses hotels and skyscrapers lists
	 * @effects the board and the bank
	 */
	public void populateBuildings() {
		
		for(int i = 0; i<81;i++) {
			House house = new House(10,10);
			houses.add(house);
		}
		
		for(int i = 0; i<31;i++) {
			Hotel hotel = new Hotel(20,20);
			hotels.add(hotel);
		}
		for(int i = 0; i<16;i++) {
			Skyscraper skyscraper = new Skyscraper(50,50);
			skyscrapers.add(skyscraper);
		}
		
		
		
		
		
	}

}
