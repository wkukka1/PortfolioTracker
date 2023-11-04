package interface_adapter.clear_users;

import use_case.clear_users.ClearInputBoundary;

// TODO Complete me
public class ClearController {

    final ClearInputBoundary clearUseCaseInteractor;

    public ClearController(ClearInputBoundary clearInteractor) {
        this.clearUseCaseInteractor = clearInteractor;
    }

    public void execute() {
        clearUseCaseInteractor.execute();
    }
}
