package events;

import java.io.Serializable;
import java.util.List;

import domain.Board;

public class EventManager implements Serializable{
	
	
	/*
	public static enum EventTypes
	{
		
		DEFAULT(0),
		ROLL_BUTTON,
		BUY_BUTTON(8),
		SAVE_BUTTON,
		
		
		
		LAST;
		
		private static final class LastIndex{
			private static int lastIndex = 0;
		}
		
		
		static int lastIndex = 0;
		public int index;
		
		private EventTypes(int i) {
			this.index = i;
			LastIndex.lastIndex = i;
		}
		private EventTypes() {
			this.index = ++LastIndex.lastIndex;
		}
	}
	
	*/
	public static EventManager eventManager;
	
	public static EventManager getInstance() {
		if (eventManager == null) {
			eventManager = new EventManager();
		}
		return eventManager;
	}
	
	
	//private List<System> Systems;
	private Systm SubsByType[][];
	private Systm systems[];
	//private int[] currentSizes;
	
	public EventManager() {
		SubsByType = new Systm[Evnt.LAST][Systm.LAST];
		systems = new Systm[Systm.LAST];
		//currentSizes = new int[Event.LAST];
	}
	
	public void notify(Evnt e) {
		/*for (int i=0; i<Event.LAST; i++) {
			for(int j=0; j<Systm.LAST; j++) {
				if(SubsByType[i][j]!=null) {
					SubsByType[i][j].process(e);
				}
				
			}
		}*/
		int type = e.getEventType();
		for(int i=0; i<Systm.LAST; i++) {
			if(SubsByType[type][i]!=null) {
				SubsByType[type][i].process(e);
			}
		}
	}
	
	public boolean addSub(int eventType, Systm system) {
		systems[system.getSystemType()] = system;
		if(SubsByType[eventType][system.getSystemType()] == null){
			SubsByType[eventType][system.getSystemType()] = system;
			return true;
		}
		else {
			SubsByType[eventType][system.getSystemType()] = system;
			return false;
			
		}
	}
	public boolean removeSub(int eventType, Systm system) {
		if(SubsByType[eventType][system.getSystemType()] == null) return false;
		else {
			SubsByType[eventType][system.getSystemType()] = null;
			return true;
		}
	}
	
	public boolean addSub(int eventType, int systemType) {
		if(systems[systemType] == null) return false;
		SubsByType[eventType][systemType] = systems[systemType];
		return true;
		
	}
	
	public boolean removeSub(int eventType, int systemType) {
		if(SubsByType[eventType][systemType] == null) return false;
		SubsByType[eventType][systemType] = systems[systemType];
		return true;
	}
	
	public boolean deleteSystem(int systemType) {
		if(systems[systemType] == null) return false;
		systems[systemType] = null;
		for(int i=0; i<Evnt.LAST; i++) {
			SubsByType[i][systemType] = null;
		}
		return true;
	}
	
	public void addSystem(Systm system) {
		systems[system.getSystemType()] = system;
	}
	
	public Systm getSystem(int systemType) {
		return systems[systemType];
	}
	
}
