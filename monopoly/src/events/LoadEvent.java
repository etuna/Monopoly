package events;

import java.io.Serializable;

public class LoadEvent extends Evnt{
	
//	private final static int eventType = Evnt.LOAD;
	public final String path;
	
	public LoadEvent(String path){
		super(Evnt.LOAD);
		this.path = path;
	}
	public LoadEvent(){
		super(Evnt.LOAD);
		this.path = "./save.def";
	}
	
	@Override
	public int getEventType() {
		return eventType;
	}
	

}
