package library;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.event.ListDataListener;


public class AbstractBean {
	
	private transient final ListDataChangeSupport listDataChangeSupport = new ListDataChangeSupport(this); 

	private transient final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	
	public PropertyChangeSupport getPcs()
	{
		return pcs; 
	}
	
	public ListDataChangeSupport getListDataChangeSupport()
	{
		return listDataChangeSupport;
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener)
	{
		this.pcs.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener)
	{
		this.pcs.removePropertyChangeListener(listener);
	}
	
	public void addListDataChangeListener(ListDataListener listListener)
	{
		this.listDataChangeSupport.addListDataListener(listListener);
	}
	
	public void removeListDataChangeListener(ListDataListener listListener)
	{
		this.listDataChangeSupport.removeListDataListener(listListener);
	}

}
