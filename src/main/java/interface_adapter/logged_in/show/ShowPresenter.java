package interface_adapter.logged_in.show;

import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import use_case.show.ShowOutputBoundary;
import use_case.show.ShowOutputData;

public class ShowPresenter implements ShowOutputBoundary {
    private final LoggedInViewModel loggedInViewModel;

    public ShowPresenter(LoggedInViewModel loggedInViewModel) {
        this.loggedInViewModel = loggedInViewModel;
    }

    @Override
    public void prepareSuccessView(ShowOutputData data) {
        LoggedInState loggedInState = loggedInViewModel.getState();
        loggedInState.setPanel(data.getPanel());
        loggedInViewModel.firePropertyChanged();

    }

    @Override
    public void prepareFailView(String error) {

    }
}
