package src;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * A Database class which writes information of the user, as specified by the User class,
 * into files in order to keep data permanence. Also saves direct message info as well.
 * In addition to writing, the class also reads from the files and returns information from
 * the files.
 *
 * @author klakoji npien-purdue rpadhye
 * @version December 7, 2024
 */

public class Database implements DatabaseInterface {
    private final String userInfo;
    private final String directMessages;
    private final String profilePictures;
    private final static Object DIRECT_MESSAGE_SYNC = new Object();
    private final static Object PROFILE_PICTURE_SYNC = new Object();
    private final static Object USER_INFO_SYNC = new Object();

    //UserInfo.txt File Formatting:
    //1,bob,hello123,true      ->UserID, loginUsername, loginPassword, friend only DM restriction
    //I am bob.                ->bio
    //2,3,4,                   ->friend list
    //5,6,7,                   ->blocked list
    //8,9                      ->friend request list...as in another person requested to be this person's friend
    //2,steve,123hello,false   ->UserID, loginUsername, loginPassword, friend only DM restriction
    //...continues

    //DirectMessages.txt File Formatting: "#" is used to separate messages from the actual splitting itself
    //#1-2            -> means User 1 and User 2 are talking   #-# has to be smaller on the left
    //$1*1:ouou       -> format: $messageID*userID:message
    //$2*2:ouou       -> format: $messageID*userID:message
    //$3*1:ouou       -> format: $messageID*userID:message
    //$4*2:thuu       -> format: $messageID*userID:message
    //#ENDDM          -> means the DM ends here
    //#1-3            -> means User 1 and User 3 are talking   #-# has to be smaller on the left
    //...continues

    public Database(String userInfo, String directMessages, String profilePictures) {
        if (userInfo == null || directMessages == null || profilePictures == null) {
            throw new NullPointerException("path cannot be null");
        }
        if (userInfo.isEmpty() || directMessages.isEmpty() || profilePictures.isEmpty()) {
            throw new IllegalArgumentException("path cannot be empty");
        }
        this.userInfo = userInfo;
        this.directMessages = directMessages;
        this.profilePictures = profilePictures;
    }

    public void addUser(User user) {    //Writes the user info into a UserInfo.txt file which acts as the database
        synchronized (USER_INFO_SYNC) {
            try (BufferedReader bfr = new BufferedReader(new FileReader(userInfo))) {
                ArrayList<String> lines = new ArrayList<>();
                while (true) {
                    String line = bfr.readLine();
                    if (line == null) break;
                    lines.add(line);
                }
                for (int i = 0; i < lines.size(); i += 5) {
                    if (Integer.parseInt(lines.get(i).split(",")[0]) == user.getUserID()) {
                        return;
                    }
                }
                ArrayList<String> allUsernames = getAllUserName();
                int maxID = 0;

                for (String username : allUsernames) {
                    if (getUserID(username) > maxID) {
                        maxID = getUserID(username);
                    }
                }
                maxID++;
                PrintWriter pw = new PrintWriter(new FileWriter(userInfo, true));
                pw.printf("\n%d,%s,%s,%s\n%s", maxID, user.getUsername(), user.getPassword(),
                        user.isFriendOnly(), user.getBio());
                pw.println();
                String friend = "";
                for (int users : user.getFriends()) {
                    friend = friend + users + ",";
                }
                if (!friend.isEmpty())
                    friend = friend.substring(0, friend.length() - 1);
                else
                    friend = "NONE";
                pw.println(friend);

                String blocked = "";
                for (int users : user.getBlockedUsers()) {
                    blocked = blocked + users + ",";
                }
                if (!blocked.isEmpty())
                    blocked = blocked.substring(0, blocked.length() - 1);
                else {
                    blocked = "NONE";
                }
                pw.println(blocked);

                String requested = "";
                for (int users : user.getRequested()) {
                    requested = requested + users + ",";
                }
                if (!requested.isEmpty())
                    requested = requested.substring(0, requested.length() - 1);
                else {
                    requested = "NONE";
                }
                pw.print(requested);
                pw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getUserInfoPath() {
        return userInfo;
    }

    public String getDirectMessagesPath() {
        return directMessages;
    }

    public String getProfilePicturesPath() {
        return profilePictures;
    }

    public void editUserInfo(User user) { //Edit user info in UserInfo.txt file
        int userID = user.getUserID();
        synchronized (USER_INFO_SYNC) {
            try (BufferedReader bfr = new BufferedReader(new FileReader(userInfo))) {
                ArrayList<String> lines = new ArrayList<>();
                while (true) {
                    String line = bfr.readLine();
                    if (line == null) break;
                    lines.add(line);
                }
                String newData = String.format("%d,%s,%s,%s", userID, user.getUsername(),
                        user.getPassword(), user.isFriendOnly());
                String newBio = user.getBio();
                ArrayList<Integer> blocked = user.getBlockedUsers();
                String newBlocked = "";
                for (int users : blocked) {
                    newBlocked = newBlocked + users + ",";
                }
                if (!newBlocked.isEmpty())
                    newBlocked = newBlocked.substring(0, newBlocked.length() - 1);
                else {
                    newBlocked = "NONE";
                }
                ArrayList<Integer> friend = user.getFriends();
                String newFriends = "";
                for (int users : friend) {
                    newFriends = newFriends + users + ",";
                }
                if (!newFriends.isEmpty())
                    newFriends = newFriends.substring(0, newFriends.length() - 1);
                else {
                    newFriends = "NONE";
                }
                ArrayList<Integer> requested = user.getRequested();
                String newRequested = "";
                for (int users : requested) {
                    newRequested = newRequested + users + ",";
                }
                if (!newRequested.isEmpty())
                    newRequested = newRequested.substring(0, newRequested.length() - 1);
                else {
                    newRequested = "NONE";
                }
                for (int i = 0; i < lines.size(); i += 5) {
                    String[] data = lines.get(i).split(",");
                    if (Integer.parseInt(data[0]) == userID) {
                        lines.set(i, newData);
                        lines.set(i + 1, newBio);
                        lines.set(i + 2, newFriends);
                        lines.set(i + 3, newBlocked);
                        lines.set(i + 4, newRequested);
                        break;
                    }
                }
                PrintWriter pw = new PrintWriter(new FileWriter(userInfo, false));
                String finalS = "";
                for (String string : lines)
                    finalS = finalS + string + "\n";
                finalS = finalS.substring(0, finalS.length() - 1);
                pw.print(finalS);
                pw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeUser(int userID) { //Removes user in UserInfo.txt file
        ArrayList<String> s = new ArrayList<>();
        synchronized (USER_INFO_SYNC) {
            try (BufferedReader br = new BufferedReader(new FileReader(userInfo))) {
                while (true) {
                    String line = br.readLine();
                    if (line == null) {
                        break;
                    }
                    s.add(line);
                }
                for (int i = 0; i < s.size(); i += 5) {
                    if (Integer.parseInt(s.get(i).split(",")[0]) == userID) {
                        s.remove(i + 4);
                        s.remove(i + 3);
                        s.remove(i + 2);
                        s.remove(i + 1);
                        s.remove(i);
                    }
                }
            } catch (IOException ie) {
                ie.printStackTrace();
            }
            try (PrintWriter pw = new PrintWriter(new FileWriter(userInfo, false))) {
                StringBuilder string = new StringBuilder();
                for (int i = 0; i < s.size(); i++) {
                    string.append(s.get(i) + "\n");
                }
                string.substring(0, string.length() - 1);
                pw.print(string);
            } catch (IOException ie) {
                ie.printStackTrace();
            }
        }
    }

    public int getUserID(String username) {
        ArrayList<String> s = new ArrayList<>();
        if (username == null) {
            return -1;
        }
        synchronized (USER_INFO_SYNC) {
            try (BufferedReader br = new BufferedReader(new FileReader(userInfo))) {
                while (true) {
                    String line = br.readLine();
                    if (line == null) break;
                    s.add(line);
                }
                for (int i = 0; i < s.size(); i += 5) {
                    if (username.equals(s.get(i).split(",")[1])) {
                        return Integer.parseInt(s.get(i).split(",")[0]);
                    }
                }
            } catch (IOException ie) {
                ie.printStackTrace();
            }
        }
        return -1;
    }

    public String getUserName(int userID) {
        ArrayList<String> s = new ArrayList<>();
        if (userID <= 0)
            return "User not found";
        synchronized (USER_INFO_SYNC) {
            try (BufferedReader br = new BufferedReader(new FileReader(new File(userInfo)))) {
                while (true) {
                    String line = br.readLine();
                    if (line == null) break;
                    s.add(line);
                }
                for (int i = 0; i < s.size(); i += 5) {
                    if (Integer.parseInt(s.get(i).split(",")[0]) == userID) {
                        return s.get(i).split(",")[1];
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "User not found";
    }

    public User getUser(String username) {
        ArrayList<String> s = new ArrayList<>();
        if (username == null) {
            return null;
        }
        synchronized (USER_INFO_SYNC) {
            try (BufferedReader br = new BufferedReader(new FileReader(userInfo))) {
                while (true) {
                    String line = br.readLine();
                    if (line == null) break;
                    s.add(line);
                }
                for (int i = 0; i < s.size(); i += 5) {
                    String[] data = s.get(i).split(",");
                    if (username.equals(data[1])) {
                        String bio = s.get(i + 1);
                        User user = new User(data[1], data[2], Integer.parseInt(data[0]),
                                bio, Boolean.parseBoolean(data[3]));
                        ArrayList<Integer> fr = getFriends(Integer.parseInt(data[0]));
                        ArrayList<Integer> bl = getBlockedUsers(Integer.parseInt(data[0]));
                        ArrayList<Integer> rq = getRequested(Integer.parseInt(data[0]));
                        user.setFriends(fr);
                        user.setBlockedUsers(bl);
                        user.setRequested(rq);
                        return user;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public ArrayList<String> getAllUserName() {
        synchronized (USER_INFO_SYNC) {
            ArrayList<String> s = new ArrayList<>();
            ArrayList<String> usernames = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(new File(userInfo)))) {
                while (true) {
                    String line = br.readLine();
                    if (line == null) break;
                    s.add(line);
                }
                for (int i = 0; i < s.size(); i += 5) {
                    usernames.add(s.get(i).split(",")[1]);
                }
                return usernames;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public ArrayList<String> getAllBlockedByOther(int userID) {
        synchronized (USER_INFO_SYNC) {
            ArrayList<String> s = new ArrayList<>();
            ArrayList<String> usernames = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(new File(userInfo)))) {
                while (true) {
                    String line = br.readLine();
                    if (line == null) break;
                    s.add(line);
                }
                for (int i = 3; i < s.size(); i += 5) {
                    if (s.get(i).equals("NONE"))
                        continue;
                    String[] data = s.get(i).split(",");
                    for (int j = 0; j < data.length; j++) {
                        if (Integer.parseInt(data[j]) == userID) {
                            usernames.add(s.get(i - 3).split(",")[1]);
                            break;
                        }
                    }
                }
                return usernames;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String getPassword(int userID) {
        ArrayList<String> s = new ArrayList<>();
        synchronized (USER_INFO_SYNC) {
            try (BufferedReader br = new BufferedReader(new FileReader(new File(userInfo)))) {
                while (true) {
                    String line = br.readLine();
                    if (line == null) break;
                    s.add(line);
                }
                for (int i = 0; i < s.size(); i += 5) {
                    if (Integer.parseInt(s.get(i).split(",")[0]) == userID) {
                        return s.get(i).split(",")[2];
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public boolean getFriendRestricted(int userID) {
        ArrayList<String> s = new ArrayList<>();
        synchronized (USER_INFO_SYNC) {
            try (BufferedReader br = new BufferedReader(new FileReader(new File(userInfo)))) {
                while (true) {
                    String line = br.readLine();
                    if (line == null) break;
                    s.add(line);
                }
                for (int i = 0; i < s.size(); i += 5) {
                    if (Integer.parseInt(s.get(i).split(",")[0]) == userID) {
                        return s.get(i).split(",")[3].equals("true");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public String getBio(int userID) {
        ArrayList<String> s = new ArrayList<>();
        synchronized (USER_INFO_SYNC) {
            try (BufferedReader br = new BufferedReader(new FileReader(new File(userInfo)))) {
                while (true) {
                    String line = br.readLine();
                    if (line == null) break;
                    s.add(line);
                }
                for (int i = 0; i < s.size(); i += 5) {
                    if (Integer.parseInt(s.get(i).split(",")[0]) == userID) {
                        return s.get(i + 1);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public ArrayList<Integer> getFriends(int userID) {
        ArrayList<String> s = new ArrayList<>();
        ArrayList<Integer> friends = new ArrayList<>();
        synchronized (USER_INFO_SYNC) {
            try (BufferedReader br = new BufferedReader(new FileReader(new File(userInfo)))) {
                while (true) {
                    String line = br.readLine();
                    if (line == null) break;
                    s.add(line);
                }
                for (int i = 0; i < s.size(); i += 5) {
                    if (Integer.parseInt(s.get(i).split(",")[0]) == userID) {
                        if (s.get(i + 2).equals("NONE")) {
                            return friends;
                        }
                        String[] r = s.get(i + 2).split(",");
                        for (String k : r) {
                            friends.add(Integer.parseInt(k));
                        }
                        return friends;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public ArrayList<Integer> getBlockedUsers(int userID) {
        ArrayList<String> s = new ArrayList<>();
        ArrayList<Integer> blocked = new ArrayList<>();
        synchronized (USER_INFO_SYNC) {
            try (BufferedReader br = new BufferedReader(new FileReader(new File(userInfo)))) {
                while (true) {
                    String line = br.readLine();
                    if (line == null) break;
                    s.add(line);
                }
                for (int i = 0; i < s.size(); i += 5) {
                    if (Integer.parseInt(s.get(i).split(",")[0]) == userID) {
                        if (s.get(i + 3).equals("NONE")) {
                            return blocked;
                        }
                        String[] r = s.get(i + 3).split(",");
                        for (String k : r) {
                            blocked.add(Integer.parseInt(k));
                        }
                        return blocked;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public ArrayList<Integer> getRequested(int userID) {
        ArrayList<String> s = new ArrayList<>();
        ArrayList<Integer> requested = new ArrayList<>();
        synchronized (USER_INFO_SYNC) {
            try (BufferedReader br = new BufferedReader(new FileReader(new File(userInfo)))) {
                while (true) {
                    String line = br.readLine();
                    if (line == null) break;
                    s.add(line);
                }
                for (int i = 0; i < s.size(); i += 5) {
                    if (Integer.parseInt(s.get(i).split(",")[0]) == userID) {
                        if (s.get(i + 4).equals("NONE")) {
                            return requested;
                        }
                        String[] r = s.get(i + 4).split(",");
                        for (String k : r) {
                            requested.add(Integer.parseInt(k));
                        }
                        return requested;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void addDM(User texter, User receiver, String message) {
        //Adds the DM between users and creates a new section if it's a new DM
        //u1 is the one who wrote the message, u2 is the receiver
        ArrayList<String> s = new ArrayList<>();
        synchronized (DIRECT_MESSAGE_SYNC) {
            try (BufferedReader br = new BufferedReader(new FileReader(directMessages))) {
                while (true) {
                    String line = br.readLine();
                    if (line == null) break;
                    s.add(line);
                }
                int lesser;
                int greater;
                if (texter.getUserID() == receiver.getUserID())
                    return;
                if (texter.getUserID() < receiver.getUserID()) {
                    lesser = texter.getUserID();
                    greater = receiver.getUserID();
                } else {
                    lesser = receiver.getUserID();
                    greater = texter.getUserID();
                }
                String dMTag = "#" + lesser + "-" + greater;
                int messageNumber = 1;
                for (int i = 0; i < s.size(); i++) {
                    if (s.get(i).equals(dMTag)) {
                        for (int j = i; j < s.size(); j++) {
                            if (s.get(j).equals("#ENDDM")) {
                                messageNumber = Integer.parseInt(s.get(j - 1)
                                        .substring(1, s.get(j - 1).indexOf("*"))) + 1;
                                message = "$" + messageNumber + "*" + texter.getUserID() + ":" + message;
                                s.add(j, message);
                                PrintWriter pw = new PrintWriter(new FileWriter(directMessages, false));
                                String finalS = "";
                                for (String string : s)
                                    finalS = finalS + string + "\n";
                                finalS = finalS.substring(0, finalS.length() - 1);
                                pw.print(finalS);
                                pw.close();
                                return;
                            }
                        }
                    }
                }
                s.add(dMTag);
                message = "$" + messageNumber + "*" + texter.getUserID() + ":" + message;
                s.add(message);
                s.add("#ENDDM");
                PrintWriter pw = new PrintWriter(new FileWriter(directMessages, false));
                String finalS = "";
                for (String string : s)
                    finalS = finalS + string + "\n";
                finalS = finalS.substring(0, finalS.length() - 1);
                pw.print(finalS);
                pw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void removeDM(User texter, User receiver, int messageID) { //Deletes a DM message between users
        ArrayList<String> s = new ArrayList<>();
        synchronized (DIRECT_MESSAGE_SYNC) {
            try (BufferedReader br = new BufferedReader(new FileReader(new File(directMessages)))) {
                while (true) {
                    String line = br.readLine();
                    if (line == null) break;
                    s.add(line);
                }
                int lesser;
                int greater;
                if (texter.getUserID() < receiver.getUserID()) {
                    lesser = texter.getUserID();
                    greater = receiver.getUserID();
                } else {
                    lesser = receiver.getUserID();
                    greater = texter.getUserID();
                }
                String dMTag = "#" + lesser + "-" + greater;
                int dMStart = 0;
                int dMEnd = 0;
                for (int i = 0; i < s.size(); i++) {
                    if (s.get(i).equals(dMTag)) {
                        dMStart = i + 1;
                        for (int j = i; j < s.size(); j++) {
                            if (s.get(j).equals("#ENDDM")) {
                                dMEnd = j;
                                break;
                            }
                        }
                    }
                    if (dMEnd != 0)
                        break;
                }
                String msgID = messageID + "";
                for (int k = dMStart; k < dMEnd; k++) {
                    if (s.get(k).substring(1, s.get(k).indexOf("*")).equals(msgID)) {
                        s.remove(k);
                        break;
                    }
                }
                PrintWriter pw = new PrintWriter(new FileWriter(directMessages, false));
                String finalS = "";
                for (String string : s)
                    finalS = finalS + string + "\n";
                finalS = finalS.substring(0, finalS.length() - 1);
                pw.print(finalS);
                pw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<String> getDMChat(User texter, User receiver) {
        ArrayList<String> s = new ArrayList<>();
        synchronized (DIRECT_MESSAGE_SYNC) {
            try (BufferedReader br = new BufferedReader(new FileReader(new File(directMessages)))) {
                while (true) {
                    String line = br.readLine();
                    if (line == null) break;
                    s.add(line);
                }
                int lesser;
                int greater;
                if (texter.getUserID() < receiver.getUserID()) {
                    lesser = texter.getUserID();
                    greater = receiver.getUserID();
                } else {
                    lesser = receiver.getUserID();
                    greater = texter.getUserID();
                }
                String dMTag = "#" + lesser + "-" + greater;
                int dMStart = 0;
                int dMEnd = 0;
                for (int i = 0; i < s.size(); i++) {
                    if (s.get(i).equals(dMTag)) {
                        dMStart = i;
                        for (int j = i; j < s.size(); j++) {
                            if (s.get(j).equals("#ENDDM")) {
                                dMEnd = j;
                                break;
                            }
                        }
                    }
                    if (dMEnd != 0)
                        break;
                }
                if (dMStart == 0 && dMEnd == 0) {
                    return null;
                }
                ArrayList<String> dMChat = new ArrayList<>();
                for (int k = dMStart + 1; k < dMEnd; k++) {
                    dMChat.add(s.get(k));
                }
                return dMChat;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public byte[] getProfilePicture(int userID) throws IOException {
        synchronized (PROFILE_PICTURE_SYNC) {
            Path path = Path.of(profilePictures + "/" + userID + "_profilePicture.jpg");
            if (!Files.exists(path)) {
                throw new FileNotFoundException("Profile picture not found");
            }
            return Files.readAllBytes(path);
        }
    }

    public void saveProfilePicture(int userID, byte[] profilePicture) throws IOException {
        synchronized (PROFILE_PICTURE_SYNC) {
            Files.write(Path.of(profilePictures + "/" + userID + "_profilePicture.jpg"), profilePicture);
        }
    }
}
