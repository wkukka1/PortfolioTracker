package data_access;

import entity.User;
import entity.UserFactory;
import use_case.delete_user.DeleteUserDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;
import use_case.removeStock.RemoveStockUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class FileUserDataAccessObject implements SignupUserDataAccessInterface, LoginUserDataAccessInterface, RemoveStockUserDataAccessInterface, DeleteUserDataAccessInterface {

    private final File csvFile;

    private final Map<String, Integer> headers = new LinkedHashMap<>();

    private final Map<String, User> accounts = new HashMap<>();

    private UserFactory userFactory;

    public FileUserDataAccessObject(String csvPath, UserFactory userFactory) throws IOException {
        this.userFactory = userFactory;

        csvFile = new File(csvPath);
        headers.put("username", 0);
        headers.put("password", 1);
        headers.put("creation_time", 2);
        headers.put("userID", 3);

        if (csvFile.length() == 0) {
            save();
        } else {

            try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
                String header = reader.readLine();

                // For later: clean this up by creating a new Exception subclass and handling it in the UI.
                assert header.equals("username,password,creation_time,userID");

                String row;
                while ((row = reader.readLine()) != null) {
                    String[] col = row.split(",");

                    String username = String.valueOf(col[headers.get("username")]);
                    String password = String.valueOf(col[headers.get("password")]);
                    String creationTimeText = String.valueOf(col[headers.get("creation_time")]);
                    int userID = Integer.parseInt(String.valueOf(col[headers.get("userID")]));
                    LocalDateTime ldt = LocalDateTime.parse(creationTimeText);

                    User user = userFactory.create(username, password, ldt, userID);
                    accounts.put(username, user);
                }
            }
        }
    }

    @Override
    public void save(User user) {
        accounts.put(user.getName(), user);
        this.save();
    }

    @Override
    public User get(String username) {
        return accounts.get(username);
    }


    /**
    Using the map attribute save overwrites the csv file with all the saved usernames
     */
    private void save() {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(csvFile));
            writer.write(String.join(",", headers.keySet()));
            writer.newLine();

            for (User user : accounts.values()) {
                String line = String.format("%s,%s,%s,%s",
                        user.getName(), user.getPassword(), user.getCreationTime(), user.getUserID());
                writer.write(line);
                writer.newLine();
            }

            writer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Return whether a user exists with username identifier.
     *
     * @param identifier the username to check.
     * @return whether a user exists with username identifier
     */
    @Override
    public boolean existsByName(String identifier) {
        return accounts.containsKey(identifier);
    }

    /**
     *
     * @return a arraylist of strings that contain the usernames of the users
     */
    public ArrayList<String> getUserList() {
        ArrayList<String> users = new ArrayList<>();
        BufferedReader reader;
        try {
            String line;
            reader = new BufferedReader(new FileReader(csvFile));
            int count = 0;
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                if (count > 0) {
                    users.add(columns[0]);
                }
                count++;
            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public void deleteUser(String username) {
        accounts.remove(username);
        this.save();
    }

    @Override
    public int getUserId(String username) {
        return accounts.get(username).getUserID();
    }
}
