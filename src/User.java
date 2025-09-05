package src;

import java.util.ArrayList;

/**
 * A User class which begins by creating a User object to be stored in the database class with all of the
 * relevant information, such as loginUsername, loginPassword, bio, etc.
 *
 * @author klakoji npien-purdue rpadhye
 * @version November 17, 2024
 */

public class User implements UserInterface {
    private String username;
    private String password;
    private String bio;
    private int userID;
    private ArrayList<Integer> friends;
    private ArrayList<Integer> blockedUsers;
    private ArrayList<Integer> requested;
    private boolean friendOnly; // true means only accept DM from friends, false means from anyone

    public User(String username, String password, int userID, String bio, boolean friendOnly) {
        this.username = username;
        this.password = password;
        this.bio = bio;
        this.userID = userID;
        friends = new ArrayList<Integer>();
        blockedUsers = new ArrayList<Integer>();
        requested = new ArrayList<Integer>();
        this.friendOnly = friendOnly;
    }

    public User() {
        username = "";
        password = "";
        bio = "";
        userID = -1;
        friends = new ArrayList<Integer>();
        blockedUsers = new ArrayList<Integer>();
        requested = new ArrayList<Integer>();
        friendOnly = false;
    }

    public boolean isFriendOnly() {
        return friendOnly;
    }

    public void setFriendOnly(boolean friendOnly) {
        this.friendOnly = friendOnly;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean verifyPassword(String password1) {
        return this.password.equals(password1);
    }

    public int getUserID() {
        return userID;
    }

    public String getBio() {
        return bio;
    }

    public void setUsername(String username) throws InvalidUserNamePasswordException {
        if (username == null || username.isEmpty() || !username.matches("^[a-zA-Z0-9]*$"))
            throw new InvalidUserNamePasswordException("Username can only contain alphanumeric characters.");
        this.username = username;
    }

    public void setPassword(String password) throws InvalidUserNamePasswordException {
        if (password == null || password.isEmpty() || !password.matches("^[a-zA-Z0-9]*$"))
            throw new InvalidUserNamePasswordException("Password can only contain alphanumeric characters.");
        else if (password.length() < 8)
            throw new InvalidUserNamePasswordException("Password must be at least 8 characters.");
        this.password = password;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public ArrayList<Integer> getFriends() {
        return friends;
    }

    public ArrayList<Integer> getBlockedUsers() {
        return blockedUsers;
    }

    public void setFriends(ArrayList<Integer> friends) {
        this.friends = friends;
    }

    public void setBlockedUsers(ArrayList<Integer> blockedUsers) {
        this.blockedUsers = blockedUsers;
    }

    public void setRequested(ArrayList<Integer> requested) {
        this.requested = requested;
    }

    public ArrayList<Integer> getRequested() {
        return requested;
    }

    public void addRequested(int userID1) {
        requested.add(userID1);
    }

    public void removeRequested(int userID1) {
        requested.remove(Integer.valueOf(userID1));
    }

    public boolean blockUser(User user) {
        if (!blockedUsers.contains(user.getUserID())) {
            blockedUsers.add(user.getUserID());
            friends.remove(Integer.valueOf(user.getUserID()));
            return true;
        }
        return false;
    }

    public boolean unblockUser(User user) {
        return blockedUsers.remove(Integer.valueOf(user.getUserID()));
    }

    public boolean addFriend(User user) {
        if (!friends.contains(user.userID) && !blockedUsers.contains(user.userID)) {
            friends.add(user.getUserID());
            blockedUsers.remove(Integer.valueOf(user.getUserID()));
            return true;
        }
        return false;

    }

    public boolean removeFriend(User user) {
        return friends.remove(Integer.valueOf(user.getUserID()));
    }

    public void setUserID(int id) {
        userID = id;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (bio == null)
            bio = "";
        String result = String.format("UserID: %d\nUsername: %s\nPassword: %s\n" +
                "Receive DM from friends only: %s\nBio: %s", userID, username, password, friendOnly, bio);
        sb.append(result);
        sb.append('\n');

        sb.append("Friends: ");
        String friend = "";
        for (int u : friends) {
            friend += u + ", ";
        }
        if (!friend.isEmpty())
            friend = friend.substring(0, friend.length() - 2);
        sb.append(friend);
        sb.append('\n');

        sb.append("Blocked Users: ");
        String blocked = "";
        for (int u : blockedUsers) {
            blocked += u + ", ";
        }
        if (!blocked.isEmpty())
            blocked = blocked.substring(0, blocked.length() - 2);
        sb.append(blocked);
        sb.append('\n');

        sb.append("Friend Requests: ");
        String requestedList = "";
        for (int u : requested) {
            requestedList += u + ", ";
        }
        if (!requestedList.isEmpty())
            requestedList = requestedList.substring(0, requestedList.length() - 2);
        sb.append(requestedList);
        return sb.toString();
    }
}
