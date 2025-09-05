package src;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A DatabaseInterface interface which allows for the Database class to utilize certain methods specific to the class
 *
 * @author npien-purdue, klakoji
 * @version November 17, 2024
 */

public interface DatabaseInterface {
    void addUser(User user);

    void editUserInfo(User user);

    void removeUser(int userID);

    String getUserName(int userID);

    User getUser(String username);

    int getUserID(String username);

    ArrayList<String> getAllUserName();

    String getPassword(int userID);

    boolean getFriendRestricted(int userID);

    String getBio(int userID);

    ArrayList<Integer> getFriends(int userID);

    ArrayList<Integer> getBlockedUsers(int userID);

    ArrayList<Integer> getRequested(int userID);

    void addDM(User texter, User receiver, String message);

    void removeDM(User texter, User receiver, int messageID);

    ArrayList<String> getDMChat(User texter, User receiver);

    byte[] getProfilePicture(int userID) throws IOException;

    void saveProfilePicture(int userID, byte[] profilePicture) throws IOException;
}
