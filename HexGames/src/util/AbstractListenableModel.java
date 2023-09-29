package src.util;

import java.util.ArrayList;

public abstract class AbstractListenableModel {
	
	protected ArrayList<Listener> listeners;

	/**
	 * @param listeners
	 */
	public AbstractListenableModel(ArrayList<Listener> listeners) {
		super();
		this.listeners = listeners;
	}
	
	public AbstractListenableModel(){
		this(new ArrayList<Listener>());
	}
	
	public void addListener(Listener e) {
		listeners.add(e);
		e.updateModel(e);
	}
	
	public void removeListener(Listener e) {
		listeners.remove(e);
	}
	
	protected void fireChange() {
		for(Listener e : listeners) {
			e.updateModel(this);
		}
	}
}
