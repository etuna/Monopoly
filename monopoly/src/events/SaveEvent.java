package events;

import java.io.Serializable;

public class SaveEvent extends Evnt{

//	private final static int eventType = Evnt.SAVE;
	public final String path;
	
/*	@Override
	public int getEventType() {
		return eventType;
	}*/
	
	public SaveEvent(String path){
		super(Evnt.SAVE);
		this.path = path;
	}
	
	public SaveEvent(){
		super(Evnt.SAVE);
		this.path = "./save.def";
	}
	
	
	
	
}
