package library;

import java.util.ArrayList;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class ListDataChangeSupport {
	private ArrayList<ListDataListener> listOfListeners;
	private Object source; 

	public ListDataChangeSupport(Object source)
	{
		listOfListeners = new ArrayList<ListDataListener>(); 
		this.source = source; 
	}
	public void addListDataListener(ListDataListener listener)
	{
		listOfListeners.add(listener);
	}
	public void removeListDataListener(ListDataListener listener)
	{
		listOfListeners.remove(listener);
	}
	
	
	//announce that something has been added to a list
	public void fireIntervalAdded(int index0, int index1)
	{
		ListDataEvent listEvent = new ListDataEvent(source, ListDataEvent.INTERVAL_ADDED, index0, index1 ); 
		//This iteration mocks PropertyChangeSupport's fire method, but I cannot generalize it because 
		//the method of listener is intervalAdded for fireINtervalAdded and intervalRemoved for fireINtervalRemoved
		for(ListDataListener listener: listOfListeners)
		{
			listener.intervalAdded(listEvent);
		}
	}
	public void fireIntervalAdded(int index0)
	{
		this.fireIntervalAdded(index0, index0);
	}
	//announce that something has been removed from a list
	public void fireIntervalRemoved(int index0, int index1)
	{
		ListDataEvent listEvent = new ListDataEvent(source, ListDataEvent.INTERVAL_REMOVED, index0, index1 ); 
		for(ListDataListener listener: listOfListeners)
		{
			listener.intervalRemoved(listEvent);
		}
	}
	public void fireIntervalRemoved(int index0)
	{
		this.fireIntervalRemoved(index0, index0);
	}
	
}
