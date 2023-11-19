package use_case.delete_user;

import data_access.FilePortfolioDataAccessObject;
import use_case.signup.SignupUserDataAccessInterface;
public class DeleteInteractor implements DeleteInputBoundary{
    final DeleteUserDataAccessInterface deleteDataAccessObject;
    final DeletePortfolioAccessInterface deletePortfolioDataAccessObject;
    final DeleteOutputBoundary deletePresenter;
    public DeleteInteractor(SignupUserDataAccessInterface deleteDataAccessObject, DeleteOutputBoundary deletePresenter, FilePortfolioDataAccessObject portfolioDataAccessObject){
        this.deletePresenter = deletePresenter;
        this.deleteDataAccessObject = (DeleteUserDataAccessInterface) deleteDataAccessObject;
        this.deletePortfolioDataAccessObject = portfolioDataAccessObject;
    }
    @Override
    public void execute(String username) {
        try{
            int id = deleteDataAccessObject.getUserId(username);
            deleteDataAccessObject.deleteUser(username);
            deletePortfolioDataAccessObject.deletePortfolio(id);
            DeleteOutputData output = new DeleteOutputData(username, true);

            deletePresenter.prepareSuccessView(output);
        } catch (Exception e) {
            deletePresenter.prepareFailView(e.toString());
        }

    }
}
