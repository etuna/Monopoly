package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Asset  implements Serializable{

	// Variables ===================
	private String name;
	private int value, rentPrice;
	boolean isBought;
	private Player belongTo;
	private String color;
	private int buildingStatus;
	private List<Integer> buildingPriceList;
	private List<Integer> buildingRentList;
	public boolean isMortgaged;
	// =============================

	// Constructor =================
	/**
	 * Class Constructor
	 * @param name Asset name
	 * @param value Asset value
	 * @param rentPrice Asset rent price
	 * @param color Asset color
	 */
	public Asset(String name, int value, int rentPrice,String color) {
		this.name = name;
		this.value = value;
		this.rentPrice = rentPrice;
		this.color = color;
		isBought = false;
		isMortgaged = false;
		setBelongTo(null);
		buildingPriceList = new ArrayList<Integer>();
		buildingRentList = new ArrayList<Integer>();
		setBuildingStatus(NOBUILDING);
		
	}
	// =============================

	// Getters and Setters =========
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getRentPrice() {
		return rentPrice;
	}

	public void setRentPrice(int rentPrice) {
		this.rentPrice = rentPrice;
	}

	public Player getBelongTo() {
		return belongTo;
	}

	public void setBelongTo(Player belongTo) {
		this.belongTo = belongTo;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	// =============================
	
	public int getBuildingStatus() {
		return buildingStatus;
	}

	public void setBuildingStatus(int buildingStatus) {
		this.buildingStatus = buildingStatus;
	}
	public void build(int buildingType) {
		
	}




	public final static int NOBUILDING = 0;
	public final static int ONEHOUSE = 1;
	public final static int TWOHOUSES = 2;
	public final static int THREEHOUSES = 3;
	public final static int FOURHOUSES = 4;
	public final static int HOTEL = 5;
	public final static int SKYSCRAPER = 6;
	public final static int LAST = 7;

}
