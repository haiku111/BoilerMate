package src;

import java.util.ArrayList;

/**
 * A UserInterface interface which contains all the key methods specific to the user class
 *
 * @author klakoji, npien-purdue
 * @version November 17, 2024
 */
public interface UserInterface {
    boolean isFriendOnly();

    void setFriendOnly(boolean friendOnly);

    String getUsername();

    String getPassword();

    boolean verifyPassword(String password);

    int getUserID();

    String getBio();

    void setBio(String bio);

    void setUsername(String username) throws InvalidUserNamePasswordException;

    void setPassword(String password) throws InvalidUserNamePasswordException;

    ArrayList<Integer> getFriends();

    void setFriends(ArrayList<Integer> friends);

    void setBlockedUsers(ArrayList<Integer> blockedUsers);

    ArrayList<Integer> getBlockedUsers();

    void removeRequested(int userID);

    boolean blockUser(User user);

    boolean unblockUser(User user);

    boolean addFriend(User user);

    void setRequested(ArrayList<Integer> requested);

    ArrayList<Integer> getRequested();

    void addRequested(int userID);

    boolean removeFriend(User user);

    void setUserID(int id);
}
