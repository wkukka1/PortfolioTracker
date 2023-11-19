package interface_adapter.delete_user;

import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.login.LoginViewModel;
import use_case.delete_user.DeleteOutputBoundary;
import use_case.delete_user.DeleteOutputData;

import javax.swing.*;


public class DeletePresenter implements DeleteOutputBoundary{
    private final DeleteViewModel deleteViewModel;
    private final ViewManagerModel viewManagerModel;
    private final LoggedInViewModel loggedInViewModel;
    private final LoginViewModel loginViewModel;

    public DeletePresenter(DeleteViewModel deleteViewModel, ViewManagerModel viewManagerModel,
                           LoggedInViewModel loggedInViewModel, LoginViewModel loginViewModel){
        this.deleteViewModel = deleteViewModel;
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
        this.loginViewModel = loginViewModel;
    }

    @Override
    public void prepareSuccessView(DeleteOutputData user){
        DeleteState deleteState = deleteViewModel.getState();
        deleteState.setUser(user.getUser());
        deleteState.setSuccess(true);
        viewManagerModel.setActiveView(loginViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        JOptionPane.showMessageDialog(null, "There was an error in deleting your account",
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}
