package interface_adapter.Home;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;

public class HomeViewModel extends ViewModel {
    public HomeViewModel(String viewName) {
        super(viewName);
    }

    @Override
    public void firePropertyChanged() {

    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {

    }
}
