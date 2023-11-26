package use_case.show;

public class ShowInputData {
    final private int userID;
    public ShowInputData(int userID) {
        this.userID = userID;
    }

    public int getUserID() {
        return userID;
    }
}
