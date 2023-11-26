package use_case.login;

public class LoginOutputData {

    private final String username;
    private final int userID;
    private boolean useCaseFailed;

    public LoginOutputData(String username, int userID, boolean useCaseFailed) {
        this.username = username;
        this.userID = userID;
        this.useCaseFailed = useCaseFailed;
    }

    public String getUsername() {
        return username;
    }

    public int getUserID() {
        return userID;
    }
}
