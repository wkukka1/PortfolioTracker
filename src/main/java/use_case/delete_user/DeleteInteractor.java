package use_case.delete_user;

import use_case.signup.SignupUserDataAccessInterface;
public class DeleteInteractor implements DeleteInputBoundary{
    final DeleteUserDataAccessInterface deleteDataAccessObject;
    final DeleteOutputBoundary deletePresenter;
    public DeleteInteractor(DeleteUserDataAccessInterface deleteDataAccessObject, DeleteOutputBoundary deletePresenter){
        this.deletePresenter = deletePresenter;
        this.deleteDataAccessObject = deleteDataAccessObject;
    }
    @Override
    public void execute() {
//        String user = deleteDataAccessObject.getUser();
    }
}
