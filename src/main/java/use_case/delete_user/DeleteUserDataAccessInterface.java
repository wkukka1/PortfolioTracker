package use_case.delete_user;

public interface DeleteUserDataAccessInterface {
    void deleteUser(String username);
    int getUserId(String username);
}
