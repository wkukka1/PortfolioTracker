package interface_adapter.removeStock;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
public class RemoveStockViewModel extends ViewModel {
    private RemoveStockState state = new RemoveStockState();

    public final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public RemoveStockViewModel(){super("removeStock");}

    @Override
    public void firePropertyChanged(){
        support.firePropertyChange("state", null, this.state);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener){
        support.addPropertyChangeListener(listener);
    }

    public RemoveStockState getState(){return this.state;}
}
