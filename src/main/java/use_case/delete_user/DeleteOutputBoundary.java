package use_case.delete_user;

public interface DeleteOutputBoundary {
    void prepareSuccessView(DeleteOutputData user);
    void prepareFailView(String error);
}
