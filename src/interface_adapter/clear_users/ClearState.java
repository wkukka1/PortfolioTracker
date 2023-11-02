package interface_adapter.clear_users;

// TODO Complete me

import java.util.ArrayList;

public class ClearState {
    private ArrayList<String> users;
    private boolean sucsess;
    private String error = null;
    public ClearState(ClearState copy){
        this.users = copy.users;
        this.error = copy.error;
    }
    public ClearState(){}
    public void setUsers(ArrayList<String> users){this.users = users;}
    public void setSucsess(boolean sucsess){this.sucsess = sucsess;}
    public void error(String error){this.error = error;}
    public String toString(){
        String result = "";
        if(users != null) {
            for (int i = 0; i < users.size(); i++) {
                result = result + users.get(i) + ", ";
            }
            return result;
        }
        return "No users found";

    }

}
