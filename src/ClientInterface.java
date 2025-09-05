package src;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * A ClientInterface interface which allows for the CLient class to utilize certain methods specific to the class
 *
 * @author npien-purdue
 * @version November 17, 2024
 */
public interface ClientInterface {
    void run();

    ObjectOutputStream getPw();

    ObjectInputStream getBr();

    void setPw(ObjectOutputStream pw);

    void setBr(ObjectInputStream br);

    void sendMessage(String message);

    void sendDM(String message, String username1, String username2);

    void removeDM(int id, String username1, String username2);

    void getDMChat(String username1, String username2);

    void editUserInfo(String oldUsername, String newUsername, String password, String bio, boolean friendRestricted);

    void requestFriend(String requester, String requested);

    void acceptFriend(String requested, String requester);

    void rejectFriend(String requested, String requester);

    void addBlocked(String username1, String username2);

    void removeBlocked(String username1, String username2);

    void addUser(String username, String password, String bio, boolean friendRestricted);

    void userSearch(String input);

    void getProfilePicture(String username);

    void saveProfilePicture(String username, byte[] image) throws IOException;

    void seeUserInfo(String username, int privacyLevel);

    void logIn(String username, String password);

    void removeUser(String username);

    void removeFriend(String username1, String username2);

}
