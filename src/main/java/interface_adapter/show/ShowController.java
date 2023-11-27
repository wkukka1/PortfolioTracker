package interface_adapter.show;

import use_case.show.ShowInputBoundary;
import use_case.show.ShowInputData;

public class ShowController {
    final ShowInputBoundary showUseCaseInteractor;


    public ShowController(ShowInputBoundary showUseCaseInteractor) {
        this.showUseCaseInteractor = showUseCaseInteractor;
    }

    public void execute(int userID) {
        ShowInputData showInputData = new ShowInputData(userID);
        showUseCaseInteractor.execute(showInputData);
    }
}
