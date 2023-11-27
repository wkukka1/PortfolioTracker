package interface_adapter.show;

import interface_adapter.ViewManagerModel;
import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ShowViewModel extends ViewModel {
    private ShowState state = new ShowState();

    public ShowViewModel() {
        super("show");
    }

    public ShowState getState() {
        return state;
    }

    public void setState(ShowState state) {
        this.state = state;
    }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    // This is what the Show Presenter will call to let the ViewModel know
    // to alert the View
    @Override
    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
