package interface_adapter.delete_user;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class DeleteViewModel extends ViewModel {
    private DeleteState state = new DeleteState();
    public final PropertyChangeSupport support = new PropertyChangeSupport(this);
    public DeleteViewModel(){super("delete");}
    @Override
    public void firePropertyChanged() {support.firePropertyChange("state",null, this.state);}

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {support.addPropertyChangeListener(listener);}

    public DeleteState getState(){return state;}
}
