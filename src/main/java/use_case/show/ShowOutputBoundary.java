package use_case.show;

public interface ShowOutputBoundary {
    void prepareSuccessView(ShowOutputData data);

    void prepareFailView(String error);
}
