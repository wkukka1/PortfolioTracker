package use_case.delete_user;

public class DeleteOutputData {
    private final String user;
    private final boolean success;
    public DeleteOutputData(String user, boolean success){
        this.user = user;
        this.success = success;
    }
    public String getUser() {
        return user;
    }

    public boolean isSuccess() {
        return success;
    }
}
