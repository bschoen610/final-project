package library;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class AbstractBean {
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	
	public PropertyChangeSupport getPcs()
	{
		return pcs; 
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener)
	{
		this.pcs.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener)
	{
		this.pcs.removePropertyChangeListener(listener);
	}

}
