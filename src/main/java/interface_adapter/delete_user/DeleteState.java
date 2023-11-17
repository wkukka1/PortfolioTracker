package interface_adapter.delete_user;

public class DeleteState {
    private String user;
    private boolean sucsess;
    private String error = null;
    public DeleteState(DeleteState copy){
        this.user = copy.user;
        this.error = copy.error;
    }

    public DeleteState(){};
    public void setUser(String user){this.user = user;}

    public void setSucsess(boolean sucsess){this.sucsess = sucsess;}
    public void error(String error){this.error = error;}

}
