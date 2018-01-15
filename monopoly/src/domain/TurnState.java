package domain;

import java.io.Serializable;

public class TurnState implements Serializable {
	
	public int turnPhase;
	public int rollCount;
	public int pips;
	public boolean isMrMonopoly;
	public boolean isTheBus;
	public boolean isDoubles;
	public boolean isTriples;
	
	public TurnState(){
		initState();
	}

	public TurnState initState() {
		turnPhase = 0;
		rollCount = 0;
		isMrMonopoly = false;
		isTheBus = false;
		isDoubles = false;
		isTriples = false;
		
		return this;
	}
	
}
