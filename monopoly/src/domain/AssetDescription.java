package domain;

import java.io.Serializable;

public class AssetDescription implements Serializable{

	// Variables ===================
	private Asset asset;
	int value, rent_price;
	boolean isBought;
	Player belongTo;
	// =============================

	// Constructor =================
	public AssetDescription(Asset asset) {
		this.asset = asset;
		value = asset.getValue();
		rent_price = asset.getRentPrice();
		isBought = asset.isBought;
		belongTo = asset.getBelongTo();
	}
	// =============================

	// Asset Description ===========
	public String toString() {
		String description = "Asset  :  " + asset.getName() + "\nPrice  :  " + value + "\nRent Price  :  " + rent_price
				+ "\nOwner  :  " + belongTo;
		return description;
	}
	// =============================

}
