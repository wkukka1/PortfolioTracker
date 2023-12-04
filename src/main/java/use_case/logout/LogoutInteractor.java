package use_case.logout;

public class LogoutInteractor implements LogoutInputBoundary {
    final LogoutOutputBoundary logoutPresenter;

    public LogoutInteractor(LogoutOutputBoundary logoutPresenter) {
        this.logoutPresenter = logoutPresenter;
    }

    @Override
    public void execute(LogoutInputData logoutInputData) {
        try {
            if (logoutInputData.getUsername() == null) {
                throw new Exception();
            }
            logoutPresenter.prepareSuccessView();
        } catch (Exception e) {
            logoutPresenter.prepareFailView();
        }
    }
}
