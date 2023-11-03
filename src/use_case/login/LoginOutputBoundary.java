package use_case.login;

import view.SignupView;

public interface LoginOutputBoundary {
    void prepareSuccessView(LoginOutputData user);

    void prepareFailView(String error);

    void setUpSignUp();
}