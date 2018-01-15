package domain;

import java.io.Serializable;

public class Skyscraper implements Serializable {
	private int build_price,rent_price;
	
	public Skyscraper(int build_price, int rent_price) {
		setBuild_price(build_price);
		setRent_price(rent_price);
	}

	public int getBuild_price() {
		return build_price;
	}

	public void setBuild_price(int build_price) {
		this.build_price = build_price;
	}

	public int getRent_price() {
		return rent_price;
	}

	public void setRent_price(int rent_price) {
		this.rent_price = rent_price;
	}
}
