package interface_adapter.show;

import interface_adapter.ViewManagerModel;
import use_case.show.ShowOutputBoundary;
import use_case.show.ShowOutputData;

public class ShowPresenter implements ShowOutputBoundary {
    private final ShowViewModel showViewModel;
    private ViewManagerModel viewManagerModel;

    public ShowPresenter(ShowViewModel showViewModel, ViewManagerModel viewManagerModel) {
        this.showViewModel = showViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(ShowOutputData data) {
        ShowState showState = showViewModel.getState();
        showState.setPanel(data.getPanel());
        showState.setNetProfit(data.getNetProfit());
        this.showViewModel.setState(showState);
        this.showViewModel.firePropertyChanged();

        viewManagerModel.setActiveView(showViewModel.getViewName());
        viewManagerModel.firePropertyChanged();

    }

    @Override
    public void prepareFailView(String error) {

    }
}
