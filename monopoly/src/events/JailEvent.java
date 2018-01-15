package events;

import java.io.Serializable;

public class JailEvent extends Evnt implements Serializable{
	
	public JailEvent() {
		super(Evnt.JAIL);
	}
}
