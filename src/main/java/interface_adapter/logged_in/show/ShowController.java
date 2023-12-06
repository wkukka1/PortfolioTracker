package interface_adapter.logged_in.show;

import com.fasterxml.jackson.core.JsonProcessingException;
import use_case.show.ShowInputBoundary;
import use_case.show.ShowInputData;

public class ShowController {
    final ShowInputBoundary showUseCaseInteractor;


    public ShowController(ShowInputBoundary showUseCaseInteractor) {
        this.showUseCaseInteractor = showUseCaseInteractor;
    }

    public void execute(int userID) throws JsonProcessingException {
        ShowInputData showInputData = new ShowInputData(userID);
        showUseCaseInteractor.execute(showInputData);
    }
}
