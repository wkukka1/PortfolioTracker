package interface_adapter.logout_user;

import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInputData;

public class LogoutController {
    final LogoutInputBoundary logoutUseCaseInteractor;
    public LogoutController(LogoutInputBoundary logoutUseCaseInteractor){
        this.logoutUseCaseInteractor = logoutUseCaseInteractor;
    }
    public void execute(String username){
        LogoutInputData logoutInputData = new LogoutInputData(username);

        logoutUseCaseInteractor.execute(logoutInputData);
    }

}
