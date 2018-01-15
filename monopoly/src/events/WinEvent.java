package events;

import java.io.Serializable;

public class WinEvent extends Evnt implements Serializable{
	
	public WinEvent() {
		super(Evnt.WIN);
	}
	

}
