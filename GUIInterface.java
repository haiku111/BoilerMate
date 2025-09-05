package src;

import javax.swing.border.TitledBorder;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
/**
 * A GUIInterface interface which contains all the key methods specific to the GUI class
 *
 * @author npien-purdue
 * @version December 7, 2024
 */
public interface GUIInterface {
    void fetchChatHistory(String selectedUser);

    void updateChatPanel(String selectedUser, ArrayList<String> chat);

    void handleActionEvent(ActionEvent e);

    void run();

    TitledBorder createStyledTitledBorder(String title);

    void updateSearchResults(String[] results);

    void updateDMSearchList(String searchTerm);

    void updateFriendSearchResults(String[] results);

    void updateRequestedSearchResults(String[] results);

    void updateBlockedSearchResults(String[] results);

    void logIn(String result);

    void seeUserInfo(String result, String[] info);

    void editUserInfo(String result);

    void addUser(String result);

    void getProfilePicture(byte[] pic);

    void saveProfilePicture(String result);

    void requestFriend(String result);

    void acceptFriend(String result);

    void rejectFriend(String result);

    void removeFriend(String result);

    void userSearch(String result, String[] info);

    void addBlocked(String result);

    void removeBlocked(String result);

    void sendDM(String result);

    void removeDM(String result);

    void getDMChat(String result, ArrayList<String> chat);

    void getPublicUsers(String result);

    void getBlockedByOther(String result);
}
