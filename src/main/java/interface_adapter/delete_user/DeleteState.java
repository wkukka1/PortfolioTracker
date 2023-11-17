package interface_adapter.delete_user;

public class DeleteState {
    private String user;
    private boolean success;
    private String error = null;
    public DeleteState(DeleteState copy){
        this.user = copy.user;
        this.error = copy.error;
    }

    public DeleteState(){};
    public void setUser(String user){this.user = user;}

    public void setSuccess(boolean success){this.success = success;}
    public void error(String error){this.error = error;}

    public boolean isSuccess() {
        return success;
    }
}
