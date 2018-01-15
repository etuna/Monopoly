package events;

public class ButtonEvent extends Evnt{

//	private static int eventType = Evnt.BUTTON;
	private final int buttonType;
	public final Evnt e;
	
	
	
	public ButtonEvent(int buttonType, Evnt e) {
		super(Evnt.BUTTON);
		this.buttonType = buttonType;
		this.e = e;
	}
	
	public ButtonEvent(Evnt e) {
		super(Evnt.BUTTON);
		this.buttonType = DEFAULTBUTTON;
		this.e = e;
	}
	
	public ButtonEvent() {
		super(Evnt.BUTTON);
		this.buttonType = DEFAULTBUTTON;
		this.e = new Evnt(Evnt.DEFAULT);
	}

	public int getButtonType() {
		return buttonType;
	}
	
	public final static int DEFAULTBUTTON = 0;
	public final static int BUYBUTTON = 1;
	public final static int ROLLBUTTON = 2;
	public final static int AUCTIONBUTTON = 3;
	public final static int BUSMOVEBUTTON = 4;
	public final static int BUYSTOCKBUTTON = 5;
	public final static int DRAWCARDBUTTON = 6;
	public final static int BUILDBUTTON = 7;
	public final static int ENDTURNBUTTON = 8;
	public final static int SAVEBUTTON = 9;
	public final static int LOADBUTTON = 10;


/*	@Override
	public int getEventType() {
		return eventType;
	}*/
	
}
