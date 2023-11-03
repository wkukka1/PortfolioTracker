package use_case.login;

import view.SignupView;

public interface LoginInputBoundary {
    void execute(LoginInputData loginInputData);

    void signup();
}
