package use_case.clear_users;


import java.util.ArrayList;

public class ClearOutputData {
    private final ArrayList<String> users;
    private final boolean sucess;

    public ClearOutputData(ArrayList<String> users, boolean sucess) {
        this.users = users;
        this.sucess = sucess;
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public boolean isSucess() {
        return sucess;
    }
}
