package use_case.clear_users;


import entity.UserFactory;
import use_case.signup.SignupUserDataAccessInterface;

import java.util.ArrayList;

public class ClearInteractor implements ClearInputBoundary {

    final ClearUserDataAccessInterface clearDataAcessObject;
    final ClearOutputBoundary clearPresenter;

    public ClearInteractor(SignupUserDataAccessInterface userDataAccessObject, ClearOutputBoundary clearOutputBoundary) {
        this.clearDataAcessObject = (ClearUserDataAccessInterface) userDataAccessObject;
        this.clearPresenter = clearOutputBoundary;
    }

    @Override
    public void execute() {
        ArrayList<String> users = clearDataAcessObject.getUserList();
        if (!users.isEmpty()) {
            ClearOutputData output = new ClearOutputData(users, true);
            clearDataAcessObject.clearUsers();
            clearPresenter.prepareSuccessView(output);
        } else {
            clearPresenter.prepareFail("No Users Found");
        }
    }
}
