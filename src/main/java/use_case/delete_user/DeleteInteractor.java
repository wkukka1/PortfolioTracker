package use_case.delete_user;

import use_case.signup.SignupUserDataAccessInterface;
public class DeleteInteractor implements DeleteInputBoundary{
    final DeleteUserDataAccessInterface deleteDataAccessObject;
    final DeleteOutputBoundary deletePresenter;
    public DeleteInteractor(SignupUserDataAccessInterface deleteDataAccessObject, DeleteOutputBoundary deletePresenter){
        this.deletePresenter = deletePresenter;
        this.deleteDataAccessObject = (DeleteUserDataAccessInterface) deleteDataAccessObject;
    }
    @Override
    public void execute() {
//        String user = deleteDataAccessObject.getUser();
    }
}
