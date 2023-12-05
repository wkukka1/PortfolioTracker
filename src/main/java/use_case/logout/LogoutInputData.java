package use_case.logout;

public class LogoutInputData {
    final private String username;

    public LogoutInputData(String username){
        this.username = username;
    }

    String getUsername(){return username;}
}
