package events;

public class MrMonopolyEvent extends Evnt{ //sent when mr monopoly is rolled
	
	
	@Override
	public int getEventType() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public MrMonopolyEvent(){
		super(Evnt.MRMONOPOLY);
	}
	
}
