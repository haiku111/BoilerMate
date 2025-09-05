package src;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A GUI class for building the GUI implementation of the program
 *
 * @author npien-purdue klakoji rapdye
 * @version December 7, 2024
 */
public class GUI extends JComponent implements Runnable, GUIInterface {
    private final Client c;
    private JFrame frame;
    private Container container;
    private CardLayout cardLayout;

    private String tempTemp;

    //Current User
    private String currentClientUsername;
    private String[] currentClientFriends;
    private String[] currentClientBlocked;
    private String[] currentClientRequested;
    private boolean loggedIn;
    private ArrayList<String> publicUsers;
    private ArrayList<String> currentClientBlockedByOther;

    //Initial Welcome Screen
    private final JButton logInButton;
    private final JButton signUpButton;

    //Login Screen
    private final JButton loggingInButton;
    private final JButton loginBack;
    private final JPasswordField loginPassword;
    private final JTextField loginUsername;

    //Sign Up Screen
    private final JButton signUpBack;
    private final JButton signUpCreate;
    private final JButton signUpFriendRestrict;
    private final JTextField signUpPassword;
    private final JTextField signUpUsername;
    private final JScrollPane signUpBio;
    private final JTextArea signUpBioText;
    private final JFileChooser signUpProfilePicture;
    private final JButton signUpProfilePictureButton;
    private String profilePicPath;

    //Edit Profile Screen
    private final JButton editProfileBack;
    private final JButton editProfileCreate;
    private final JButton editProfileFriendRestrict;
    private final JTextField editProfilePassword;
    private final JTextField editProfileUsername;
    private final JScrollPane editProfileBio;
    private final JTextArea editProfileBioText;
    private final JFileChooser editProfileProfilePicture;
    private final JButton editProfileProfilePictureButton;
    private String editProfileProfilePicPath;

    //Search User Screen
    private final JButton searchBackButton;
    private JPanel resultsPanel;

    //Personal Profile Screen
    private final JLabel personalUsername;
    private final JLabel personalPassword;
    private final JTextArea personalBioText;
    private final JScrollPane personalBio;
    private final JLabel personalRestrict;
    private final JLabel personalPFP;
    private final JButton personalProfileBackButton;
    private final JButton editProfileButton;
    private final JButton viewFriends;
    private final JButton viewBlocked;
    private final JButton viewRequested;

    //Other Profile Screen
    private final JButton otherProfileBackButton;
    private final JLabel otherProfileUsername;
    private final JTextArea otherProfileBioText;
    private final JScrollPane otherProfileBio;
    private final JLabel otherProfilePFP;
    private final JButton otherProfileAddRemoveFriendButton;
    private final JButton otherProfileAddRemoveBlockButton;

    //Dashboard Screen
    private final JButton dashboardSignOutButton;
    private final JTextField dashboardUserSearchField;
    private final JButton dashboardUserSearchButton;
    private final JButton dashboardUserProfileButton;
    private JPanel dashboardSearchPanel;
    private final JTextField dashboardSearchDMField;
    private final JButton dashboardSearchDMButton;
    private final JList<String> dashboardUserList;
    private final JTextArea dashboardMessageArea;
    private final JTextField dashboardNewMessageField;
    private final JButton dashboardSendMessageButton;
    private String[] userSearchList;
    private ArrayList<String> chatHistory;
    private final JButton seeAllDMUsers;
    private JPanel chatTextPanel;
    private JScrollPane chatScrollPane;
    private GridBagConstraints gbc6;
    private final JButton deleteDMButton;
    private final JTextField deleteDMID;

    //Friends List
    private final JTextField friendsListSearch;
    private final JButton friendsListSearchButton;
    private ArrayList<String> friendsList;
    private final JButton friendsListBackButton;
    private JPanel friendSearchResult;


    //Blocked List
    private final JTextField blockedListSearch;
    private final JButton blockedListSearchButton;
    private ArrayList<String> blockedList;
    private final JButton blockedListBackButton;
    private JPanel blockedSearchResult;

    //Requested List
    private final JTextField requestedListSearch;
    private final JButton requestedListSearchButton;
    private ArrayList<String> requestedList;
    private final JButton requestedListBackButton;
    private JPanel requestedSearchResult;

    JPanel suggestedAccountsPanel; // For displaying suggested accounts dynamically

    public GUI(Client c) {
        this.c = c;
        currentClientUsername = "";
        publicUsers = new ArrayList<>();
        currentClientBlockedByOther = new ArrayList<>();
        ActionListener actionListener = e -> SwingUtilities.invokeLater(() -> handleActionEvent(e));

        tempTemp = "";

        //Initial Welcome Screen
        logInButton = new JButton("Log In");
        logInButton.addActionListener(actionListener);
        signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(actionListener);

        //Login Screen
        loginPassword = new JPasswordField("", 10);
        loginPassword.addActionListener(actionListener);
        loginUsername = new JTextField("", 10);
        loginUsername.addActionListener(actionListener);
        loggingInButton = new JButton("Log In");
        loggingInButton.addActionListener(actionListener);
        loginBack = new JButton("Back");
        loginBack.addActionListener(actionListener);

        //Sign Up Screen
        signUpBack = new JButton("Back");
        signUpBack.addActionListener(actionListener);
        signUpCreate = new JButton("Create Account");
        signUpCreate.addActionListener(actionListener);
        signUpFriendRestrict = new JButton("Friends Only");
        signUpFriendRestrict.addActionListener(actionListener);
        signUpPassword = new JTextField("", 10);
        signUpUsername = new JTextField("", 10);
        signUpBioText = new JTextArea(4, 20);
        signUpBioText.setLineWrap(true);
        signUpBio = new JScrollPane(signUpBioText);
        signUpProfilePicture = new JFileChooser();
        signUpProfilePictureButton = new JButton("Select JPG");
        signUpProfilePictureButton.addActionListener(actionListener);
        profilePicPath = "";

        //Edit Profile Screen
        editProfileBack = new JButton("Back");
        editProfileBack.addActionListener(actionListener);
        editProfileCreate = new JButton("Save");
        editProfileCreate.addActionListener(actionListener);
        editProfileFriendRestrict = new JButton("Friends Only");
        editProfileFriendRestrict.addActionListener(actionListener);
        editProfilePassword = new JTextField("", 10);
        editProfileUsername = new JTextField("", 10);
        editProfileBioText = new JTextArea(4, 20);
        editProfileBioText.setLineWrap(true);
        editProfileBio = new JScrollPane(editProfileBioText);
        editProfileProfilePicture = new JFileChooser();
        editProfileProfilePictureButton = new JButton("Select JPG");
        editProfileProfilePictureButton.addActionListener(actionListener);
        editProfileProfilePicPath = "";

        //Personal Profile Screen
        personalProfileBackButton = new JButton("Back");
        personalProfileBackButton.addActionListener(actionListener);
        personalPFP = new JLabel(new ImageIcon("path_to_pfp")); // Placeholder PFP
        personalUsername = new JLabel("");
        personalBioText = new JTextArea(4, 20);
        personalBioText.setLineWrap(true);
        personalBioText.setEditable(false);
        personalBio = new JScrollPane(personalBioText);
        personalPassword = new JLabel("");
        personalRestrict = new JLabel("");
        editProfileButton = new JButton("Edit Profile");
        editProfileButton.addActionListener(actionListener);
        viewFriends = new JButton("View Friends");
        viewFriends.addActionListener(actionListener);
        viewBlocked = new JButton("View Blocked");
        viewBlocked.addActionListener(actionListener);
        viewRequested = new JButton("View Requested");
        viewRequested.addActionListener(actionListener);

        //Search User Screen
        searchBackButton = new JButton("Back");
        searchBackButton.addActionListener(actionListener);
        userSearchList = new String[0];

        //Other Profile Screen
        otherProfileBackButton = new JButton("Back");
        otherProfileBackButton.addActionListener(actionListener);
        otherProfilePFP = new JLabel(new ImageIcon("path_to_default_pfp.png")); // Placeholder PFP
        otherProfileUsername = new JLabel("");
        otherProfileBioText = new JTextArea(4, 20);
        otherProfileBioText.setLineWrap(true);
        otherProfileBioText.setEditable(false);
        otherProfileBio = new JScrollPane(otherProfileBioText);
        otherProfileAddRemoveFriendButton = new JButton("Add Friend");
        otherProfileAddRemoveFriendButton.addActionListener(actionListener);
        otherProfileAddRemoveBlockButton = new JButton("Block");
        otherProfileAddRemoveBlockButton.addActionListener(actionListener);

        //Dashboard Screen
        dashboardSignOutButton = new JButton("Sign Out");
        dashboardSignOutButton.addActionListener(actionListener);
        dashboardUserSearchField = new JTextField("Search All Users", 15);
        dashboardUserSearchButton = new JButton("Search");
        dashboardUserSearchButton.addActionListener(actionListener);
        dashboardUserProfileButton = new JButton("Profile");
        dashboardUserProfileButton.addActionListener(actionListener);
        dashboardSearchDMField = new JTextField("Search Chats", 15);
        dashboardSearchDMButton = new JButton("Search");
        dashboardSearchDMButton.addActionListener(actionListener);
        dashboardUserList = new JList<>();
        dashboardUserList.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Detect single click
                String selectedUser = dashboardUserList.getSelectedValue();
                if (selectedUser != null) {
                    fetchChatHistory(selectedUser);

                }
            }
        });
        dashboardMessageArea = new JTextArea();
        dashboardNewMessageField = new JTextField("", 15);
        dashboardSendMessageButton = new JButton("Send");
        dashboardSendMessageButton.addActionListener(actionListener);
        seeAllDMUsers = new JButton("See All");
        seeAllDMUsers.addActionListener(actionListener);
        deleteDMButton = new JButton("Delete");
        deleteDMButton.addActionListener(actionListener);
        deleteDMID = new JTextField("");

        //Friends List Screen
        friendsListSearch = new JTextField(15);
        friendsListSearchButton = new JButton("Search");
        friendsListSearchButton.addActionListener(actionListener);
        friendsList = new ArrayList<>();
        friendsListBackButton = new JButton("Back");
        friendsListBackButton.addActionListener(actionListener);

        //Blocked List Screen
        blockedListSearch = new JTextField(15);
        blockedListSearchButton = new JButton("Search");
        blockedListSearchButton.addActionListener(actionListener);
        blockedList = new ArrayList<>();
        blockedListBackButton = new JButton("Back");
        blockedListBackButton.addActionListener(actionListener);

        //Requested List Screen
        requestedListSearch = new JTextField(15);
        requestedListSearchButton = new JButton("Search");
        requestedListSearchButton.addActionListener(actionListener);
        requestedList = new ArrayList<>();
        requestedListBackButton = new JButton("Back");
        requestedListBackButton.addActionListener(actionListener);
    }

    public void fetchChatHistory(String selectedUser) {
        // Simulate server call to fetch chat history
        c.getDMChat(currentClientUsername, selectedUser);

        // Assume the server response is handled in the `getDMChat` method
        // Update chat area after fetching chat history
        updateChatPanel(selectedUser, chatHistory);
        repaint();
        revalidate();
    }

    public void updateChatPanel(String selectedUser, ArrayList<String> chat) {
        // Clear the chat area
        // dashboardMessageArea.setText("");
        chatTextPanel.removeAll();
        // Display chat history with the selected user
        if (chat != null && !chat.isEmpty()) {
            //for (String message : chat) {
            for (int i = 0; i < chatHistory.size(); i++) {
                String c2 = chatHistory.get(i);

                JTextArea text = new JTextArea(0, 50);
                text.setText(c2);
                text.setLineWrap(true);
                text.setWrapStyleWord(true); // Optional: wraps at word boundaries
                text.setEditable(false); // If you don't want it to be editable
                text.setFont(new Font("Arial", Font.PLAIN, 14));
                gbc6.gridy = i;
                chatTextPanel.add(text, gbc6);
            }
            //  dashboardMessageArea.append(message + "\n");
//            }
        }
        SwingUtilities.invokeLater(() -> {
            JScrollBar verticalScrollBar = chatScrollPane.getVerticalScrollBar();
            verticalScrollBar.setValue(verticalScrollBar.getMaximum());
            chatScrollPane.setVerticalScrollBar(verticalScrollBar);
        });
        chatTextPanel.revalidate();
        chatTextPanel.repaint();

    }


    public void handleActionEvent(ActionEvent e) {
        //Initial Welcome Screen
        if (e.getSource() == logInButton) {
            cardLayout.show(container, "login");
        }
        if (e.getSource() == signUpButton) {
            cardLayout.show(container, "signUp");
        }

        //Login Screen
        if (e.getSource() == loggingInButton) {
            c.logIn(loginUsername.getText(), loginPassword.getText());

        }
        if (e.getSource() == loginBack) {
            cardLayout.show(container, "logSignPanel");
        }
        //Sign Up Screen
        if (e.getSource() == signUpBack) {
            cardLayout.show(container, "logSignPanel");
        }
        if (e.getSource() == signUpCreate) {
            c.addUser(signUpUsername.getText(), signUpPassword.getText(),
                    signUpBioText.getText(), signUpFriendRestrict.getText().equals("Friends Only"));
            if (!signUpProfilePictureButton.getText().equals("Select JPG")) {
                Path path = Path.of(profilePicPath);
                try {
                    c.saveProfilePicture(signUpUsername.getText(), Files.readAllBytes(path));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        if (e.getSource() == signUpFriendRestrict) {
            if (signUpFriendRestrict.getText().equals("Friends Only")) {
                signUpFriendRestrict.setText("Anyone/Public");
            } else {
                signUpFriendRestrict.setText("Friends Only");
            }
        }
        if (e.getSource() == signUpProfilePictureButton) {
            int retVal = signUpProfilePicture.showOpenDialog(frame);
            if (retVal == JFileChooser.APPROVE_OPTION) {
                File file = signUpProfilePicture.getSelectedFile();
                if (file.getAbsolutePath().endsWith(".jpg")) {
                    profilePicPath = file.getAbsolutePath();
                    signUpProfilePictureButton.setText(file.getName());
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a JPG file!",
                            "Wrong File Type", JOptionPane.WARNING_MESSAGE);
                    profilePicPath = "";
                    signUpProfilePictureButton.setText("Select JPG");
                }
            }
        }
        //Edit Profile Screen
        if (e.getSource() == editProfileBack) {
            cardLayout.show(container, "dashboard");
        }
        if (e.getSource() == editProfileCreate) {
            c.editUserInfo(currentClientUsername, editProfileUsername.getText(),
                    editProfilePassword.getText(), editProfileBioText.getText(),
                    editProfileFriendRestrict.getText().equals("Friends Only"));
            if (!editProfileUsername.getText().isEmpty()) {
                if (!editProfileProfilePictureButton.getText().equals("Select JPG")) {
                    Path path = Path.of(editProfileProfilePicPath);
                    try {
                        c.saveProfilePicture(editProfileUsername.getText(), Files.readAllBytes(path));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    try {
                        c.saveProfilePicture(editProfileUsername.getText(), new byte[0]);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
            tempTemp = editProfileUsername.getText();
        }
        if (e.getSource() == editProfileFriendRestrict) {
            if (editProfileFriendRestrict.getText().equals("Friends Only")) {
                editProfileFriendRestrict.setText("Anyone/Public");
            } else {
                editProfileFriendRestrict.setText("Friends Only");
            }
        }
        if (e.getSource() == editProfileProfilePictureButton) {
            int retVal = editProfileProfilePicture.showOpenDialog(frame);
            if (retVal == JFileChooser.APPROVE_OPTION) {
                File file = editProfileProfilePicture.getSelectedFile();
                if (file.getAbsolutePath().endsWith(".jpg")) {
                    editProfileProfilePicPath = file.getAbsolutePath();
                    editProfileProfilePictureButton.setText(file.getName());
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a JPG file!",
                            "Wrong File Type", JOptionPane.WARNING_MESSAGE);
                    editProfileProfilePicPath = "";
                    editProfileProfilePictureButton.setText("Select JPG");
                }
            }
        }

        //Personal Profile Screen
        if (e.getSource() == personalProfileBackButton) {
            cardLayout.show(container, "dashboard");
        }
        if (e.getSource() == editProfileButton) {
            cardLayout.show(container, "editProfile");
        }
        if (e.getSource() == viewFriends) {
            friendSearchResult.removeAll();
            cardLayout.show(container, "friendsListScreen");
        }
        if (e.getSource() == viewBlocked) {
            blockedSearchResult.removeAll();
            cardLayout.show(container, "blockedListScreen");
        }
        if (e.getSource() == viewRequested) {
            cardLayout.show(container, "requestedListScreen");
        }

        //Search User Screen
        if (e.getSource() == searchBackButton) {
            cardLayout.show(container, "dashboard");
            dashboardUserSearchField.setText("");
        }

        //Other Profile Screen
        if (e.getSource() == otherProfileBackButton) {
            cardLayout.show(container, "dashboard");
        }
        if (e.getSource() == otherProfileAddRemoveFriendButton) {
            if (otherProfileAddRemoveFriendButton.getText().equals("Add Friend")) {
                c.requestFriend(currentClientUsername, otherProfileUsername.getText());
            } else if (otherProfileAddRemoveFriendButton.getText().equals("Remove Friend")) {
                c.removeFriend(currentClientUsername, otherProfileUsername.getText());
            } else if (otherProfileAddRemoveFriendButton.getText().equals("Requested")) {
                JOptionPane.showMessageDialog(frame, "ERROR: Wait for user to respond",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (e.getSource() == otherProfileAddRemoveBlockButton) {
            if (otherProfileAddRemoveBlockButton.getText().equals("Block")) {
                c.addBlocked(currentClientUsername, otherProfileUsername.getText());
            } else {
                c.removeBlocked(currentClientUsername, otherProfileUsername.getText());
            }
        }

        // Dashboard
        if (e.getSource() == dashboardSignOutButton) {
            cardLayout.show(container, "logSignPanel");
            dashboardUserSearchField.setText("Search All Users");
            dashboardSearchDMField.setText("Search Chats");
            deleteDMID.setText("");
            dashboardNewMessageField.setText("");
            dashboardMessageArea.setText("");
            friendsListSearch.setText("");
            friendsList.clear();
            updateFriendSearchResults(new String[0]);
            blockedListSearch.setText("");
            blockedList.clear();
            updateBlockedSearchResults(new String[0]);
            requestedListSearch.setText("");
            requestedList.clear();
            updateRequestedSearchResults(new String[0]);
            dashboardUserList.setListData(new String[0]);
            chatTextPanel.removeAll();
            //dashboardUserList.revalidate();
            //dashboardUserList.repaint();
            currentClientUsername = "";
        }
        if (e.getSource() == dashboardUserSearchButton) {
            String searchFieldText = dashboardUserSearchField.getText();
            c.userSearch(searchFieldText);
            dashboardUserSearchField.setText("");
            cardLayout.show(container, "searchUsers");
            //repaint();
            //revalidate();
        }
        if (e.getSource() == dashboardUserProfileButton) {
            c.seeUserInfo(currentClientUsername, 0);
            c.getProfilePicture(currentClientUsername);
            cardLayout.show(container, "personalUserProfile");
        }
        if (e.getSource() == dashboardSearchDMButton) {
            c.getPublicUsers();
            updateDMSearchList(dashboardSearchDMField.getText());
        }
        if (e.getSource() == deleteDMButton) {
            try {
                c.removeDM(Integer.parseInt(deleteDMID.getText()),
                        currentClientUsername, dashboardUserList.getSelectedValue());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid DM ID",
                        "Invalid DM ID", JOptionPane.WARNING_MESSAGE);
            }
            deleteDMID.setText("");
        }
        if (e.getSource() == seeAllDMUsers) {
            c.getPublicUsers();
            dashboardSearchDMField.setText("");
            updateDMSearchList("");
        }
        if (e.getSource() == dashboardSendMessageButton) {
            String text = dashboardNewMessageField.getText();
            dashboardNewMessageField.setText("");
            c.sendDM(text, currentClientUsername, dashboardUserList.getSelectedValue());
            updateChatPanel(dashboardUserList.getSelectedValue(), chatHistory);
        }

        // Friends List Screen
        if (e.getSource() == friendsListSearchButton) {
            String searchFieldText = friendsListSearch.getText();
            c.userSearch(searchFieldText);
            friendsListSearch.setText("");
            cardLayout.show(container, "friendsListScreen");
        }
        if (e.getSource() == friendsListBackButton) {
            cardLayout.show(container, "dashboard");
            friendsListSearch.setText("");
        }

        // Blocked List Screen
        if (e.getSource() == blockedListSearchButton) {
            String searchFieldText = blockedListSearch.getText();
            c.userSearch(searchFieldText);
            blockedListSearch.setText("");
            cardLayout.show(container, "blockedListScreen");
        }
        if (e.getSource() == blockedListBackButton) {
            cardLayout.show(container, "dashboard");
            blockedListSearch.setText("");
        }

        // Requested List Screen
        if (e.getSource() == requestedListSearchButton) {
            String searchFieldText = requestedListSearch.getText();
            c.userSearch(searchFieldText);
            requestedListSearch.setText("");
            cardLayout.show(container, "requestedListScreen");
        }
        if (e.getSource() == requestedListBackButton) {
            cardLayout.show(container, "dashboard");
            requestedListSearch.setText("");
        }
    }

    public void run() {
        // Create the main frame
        frame = new JFrame("BoilerMate");
        frame.setSize(1200, 800);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Clean up resources, close sockets, etc.
                performCleanup();
            }
        });



        // Create a CardLayout and set it to the container
        container = frame.getContentPane();
        cardLayout = new CardLayout();
        container.setLayout(cardLayout);

        //Initial Welcome Screen
        JPanel logSignScreen = new JPanel(new BorderLayout());
        JPanel logSign1 = new JPanel(new FlowLayout());
        JPanel logSign2 = new JPanel(new FlowLayout());

        JLabel title = new JLabel("BoilerMate");
        title.setFont(new Font("Arial", Font.PLAIN, 100));
        logInButton.setFont(new Font("Arial", Font.PLAIN, 18));
        logInButton.setPreferredSize(new Dimension(133, 33));
        signUpButton.setFont(new Font("Arial", Font.PLAIN, 18));
        signUpButton.setPreferredSize(new Dimension(133, 33));
        logSign1.add(title);
        logSign2.add(logInButton);
        logSign2.add(signUpButton);
        logSignScreen.add(logSign1, BorderLayout.CENTER);
        logSignScreen.add(logSign2, BorderLayout.SOUTH);

        //Login screen
        JPanel loginScreen = new JPanel(new BorderLayout());
        JPanel login1 = new JPanel(new GridBagLayout());
        JPanel login2 = new JPanel(new FlowLayout(FlowLayout.LEADING));
        JPanel login3 = new JPanel(new FlowLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel loginUsernameLabel = new JLabel("Username:");
        JLabel loginPasswordLabel = new JLabel("Password:");
        JLabel loginLogIn = new JLabel("Log In");
        loginLogIn.setFont(new Font("Arial", Font.PLAIN, 50));
        loginUsernameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        loginPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        loginUsername.setFont(new Font("Arial", Font.PLAIN, 18));
        loginPassword.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.ipadx = 160;
        gbc.ipady = 50;
        login1.add(loginLogIn, gbc);
        gbc.ipady = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 2;
        login1.add(loginUsernameLabel, gbc);
        gbc.gridx = 1;
        login1.add(loginUsername, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        login1.add(loginPasswordLabel, gbc);
        gbc.gridx = 1;
        login1.add(loginPassword, gbc);

        loginBack.setFont(new Font("Arial", Font.PLAIN, loginBack.getFont().getSize()));
        login2.add(loginBack);

        loggingInButton.setFont(new Font("Arial", Font.PLAIN, 18));
        loggingInButton.setPreferredSize(new Dimension(133, 33));
        login3.add(loggingInButton);
        loginScreen.add(login1, BorderLayout.CENTER);
        loginScreen.add(login2, BorderLayout.NORTH);
        loginScreen.add(login3, BorderLayout.SOUTH);

        //Sign Up Screen
        JPanel signUpScreen = new JPanel(new BorderLayout());
        JPanel signUp1 = new JPanel(new GridBagLayout());
        JPanel signUp2 = new JPanel(new FlowLayout(FlowLayout.LEADING));
        JPanel signUp3 = new JPanel(new FlowLayout());
        GridBagConstraints gbc2 = new GridBagConstraints();

        JLabel signUpUsernameLabel = new JLabel("Username:");
        JLabel signUpPasswordLabel = new JLabel("Password:");
        JLabel signUpBioLabel = new JLabel("Bio:");
        JLabel signUpFriendRestrictLabel = new JLabel("DM Restrictions:");
        JLabel signUpProfPicLabel = new JLabel("Profile Picture:");
        JLabel signUpSignUp = new JLabel("Create Account");
        signUpSignUp.setFont(new Font("Arial", Font.PLAIN, 50));
        signUpUsernameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        signUpPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        signUpBioLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        signUpFriendRestrictLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        signUpProfPicLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        signUpUsername.setFont(new Font("Arial", Font.PLAIN, 18));
        signUpPassword.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc2.insets = new Insets(5, 5, 5, 5);
        signUp1.add(signUpUsernameLabel, gbc2);
        gbc2.gridx = 1;
        signUp1.add(signUpUsername, gbc2);
        gbc2.gridx = 0;
        gbc2.gridy = 1;
        signUp1.add(signUpPasswordLabel, gbc2);
        gbc2.gridx = 1;
        signUp1.add(signUpPassword, gbc2);
        gbc2.gridx = 0;
        gbc2.gridy = 2;
        signUp1.add(signUpBioLabel, gbc2);
        gbc2.gridx = 1;
        signUp1.add(signUpBio, gbc2);
        gbc2.gridx = 0;
        gbc2.gridy = 3;
        signUp1.add(signUpFriendRestrictLabel, gbc2);
        gbc2.gridx = 1;
        signUp1.add(signUpFriendRestrict, gbc2);
        gbc2.gridx = 0;
        gbc2.gridy = 4;
        signUp1.add(signUpProfPicLabel, gbc2);
        gbc2.gridx = 1;
        signUp1.add(signUpProfilePictureButton, gbc2);

        signUpBack.setFont(new Font("Arial", Font.PLAIN, signUpBack.getFont().getSize()));
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        signUp2.add(signUpBack, gbc2);
        FontMetrics met = signUpSignUp.getFontMetrics(new Font("Arial", Font.PLAIN, 50));
        int width = met.stringWidth(signUpSignUp.getText());
        signUp2.add(Box.createRigidArea(new Dimension(frame.getWidth() / 2 - width / 2, 10)));
        signUp2.add(signUpSignUp);

        signUpCreate.setFont(new Font("Arial", Font.PLAIN, 18));
        signUpCreate.setPreferredSize(new Dimension(250, 33));
        signUp3.add(signUpCreate);
        signUpScreen.add(signUp1, BorderLayout.CENTER);
        signUpScreen.add(signUp2, BorderLayout.NORTH);
        signUpScreen.add(signUp3, BorderLayout.SOUTH);

        //Edit Profile Screen
        JPanel editProfileScreen = new JPanel(new BorderLayout());
        JPanel editProfile1 = new JPanel(new GridBagLayout());
        JPanel editProfile2 = new JPanel(new FlowLayout(FlowLayout.LEADING));
        JPanel editProfile3 = new JPanel(new FlowLayout());
        GridBagConstraints gbc3 = new GridBagConstraints();

        JLabel editProfileUsernameLabel = new JLabel("Username:");
        JLabel editProfilePasswordLabel = new JLabel("Password:");
        JLabel editProfileBioLabel = new JLabel("Bio:");
        JLabel editProfileFriendRestrictLabel = new JLabel("DM Restrictions:");
        JLabel editProfileProfPicLabel = new JLabel("Profile Picture:");
        JLabel editProfileSave = new JLabel("Edit Profile");
        editProfileSave.setFont(new Font("Arial", Font.PLAIN, 50));
        editProfileUsernameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        editProfilePasswordLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        editProfileBioLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        editProfileFriendRestrictLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        editProfileProfPicLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        editProfileUsername.setFont(new Font("Arial", Font.PLAIN, 18));
        editProfilePassword.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc3.insets = new Insets(5, 5, 5, 5);
        editProfile1.add(editProfileUsernameLabel, gbc3);
        gbc3.gridx = 1;
        editProfile1.add(editProfileUsername, gbc3);
        gbc3.gridx = 0;
        gbc3.gridy = 1;
        editProfile1.add(editProfilePasswordLabel, gbc3);
        gbc3.gridx = 1;
        editProfile1.add(editProfilePassword, gbc3);
        gbc3.gridx = 0;
        gbc3.gridy = 2;
        editProfile1.add(editProfileBioLabel, gbc3);
        gbc3.gridx = 1;
        editProfile1.add(editProfileBio, gbc3);
        gbc3.gridx = 0;
        gbc3.gridy = 3;
        editProfile1.add(editProfileFriendRestrictLabel, gbc3);
        gbc3.gridx = 1;
        editProfile1.add(editProfileFriendRestrict, gbc3);
        gbc3.gridx = 0;
        gbc3.gridy = 4;
        editProfile1.add(editProfileProfPicLabel, gbc3);
        gbc3.gridx = 1;
        editProfile1.add(editProfileProfilePictureButton, gbc3);

        editProfileBack.setFont(new Font("Arial", Font.PLAIN, editProfileBack.getFont().getSize()));
        gbc3.gridx = 0;
        gbc3.gridy = 0;
        editProfile2.add(editProfileBack, gbc3);
        FontMetrics met2 = editProfileSave.getFontMetrics(new Font("Arial", Font.PLAIN, 50));
        int width2 = met2.stringWidth(editProfileSave.getText());
        editProfile2.add(Box.createRigidArea(new Dimension(frame.getWidth() / 2 - width2 / 2, 10)));
        editProfile2.add(editProfileSave);

        editProfileCreate.setFont(new Font("Arial", Font.PLAIN, 18));
        editProfileCreate.setPreferredSize(new Dimension(250, 33));
        editProfile3.add(editProfileCreate);
        editProfileScreen.add(editProfile1, BorderLayout.CENTER);
        editProfileScreen.add(editProfile2, BorderLayout.NORTH);
        editProfileScreen.add(editProfile3, BorderLayout.SOUTH);

        // Other Profile Screen
        JPanel otherProfileScreen = new JPanel(new BorderLayout());
        JPanel otherProfilePanel1 = new JPanel(new GridBagLayout());
        JPanel otherProfilePanel2 = new JPanel(new FlowLayout(FlowLayout.LEADING));
        GridBagConstraints gbc4 = new GridBagConstraints();

        gbc4.insets = new Insets(5, 5, 5, 5);
        gbc4.gridx = 0;
        otherProfilePanel1.add(otherProfilePFP, gbc4);
        gbc4.gridx = 1;
        otherProfilePanel1.add(otherProfileUsername, gbc4);
        gbc4.gridy = 1;
        otherProfilePanel1.add(otherProfileBio, gbc4);
        gbc4.gridy = 2;
        otherProfilePanel1.add(otherProfileAddRemoveFriendButton, gbc4);
        gbc4.gridy = 3;
        otherProfilePanel1.add(otherProfileAddRemoveBlockButton, gbc4);

        otherProfileBackButton.setFont(new Font("Arial", Font.PLAIN, otherProfileBackButton.getFont().getSize()));
        otherProfilePanel2.add(otherProfileBackButton);

        otherProfileScreen.add(otherProfilePanel1, BorderLayout.CENTER);
        otherProfileScreen.add(otherProfilePanel2, BorderLayout.NORTH);

        // Search Users Screen
        JPanel searchUsersPanel = new JPanel(new BorderLayout());
        searchUsersPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(new EmptyBorder(0, 0, 10, 0));
        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        backButtonPanel.add(searchBackButton);
        topPanel.setOpaque(false);

        JLabel searchTitle = new JLabel("Search Users");
        searchTitle.setFont(new Font("Arial", Font.BOLD, 32));
        searchTitle.setHorizontalAlignment(SwingConstants.CENTER);

        topPanel.add(backButtonPanel, BorderLayout.WEST);
        topPanel.add(searchTitle, BorderLayout.CENTER);

        resultsPanel = new JPanel(new GridLayout(0, 1));
        JScrollPane resultsScrollPane = new JScrollPane(resultsPanel);

        searchUsersPanel.add(topPanel, BorderLayout.NORTH);
        searchUsersPanel.add(resultsScrollPane, BorderLayout.CENTER);

        // Personal Account Details Screen
        JPanel personalProfileScreen = new JPanel(new BorderLayout());
        JPanel personalProfilePanel1 = new JPanel(new GridBagLayout());
        JPanel personalProfilePanel2 = new JPanel(new FlowLayout(FlowLayout.LEADING));
        GridBagConstraints gbc5 = new GridBagConstraints();

        gbc5.insets = new Insets(5, 5, 5, 5);
        gbc5.gridx = 0;
        personalProfilePanel1.add(personalPFP, gbc5);
        gbc5.gridx = 1;
        personalProfilePanel1.add(personalUsername, gbc5);
        gbc5.gridy = 1;
        personalProfilePanel1.add(personalPassword, gbc5);
        gbc5.gridy = 2;
        personalProfilePanel1.add(personalBio, gbc5);
        gbc5.gridy = 3;
        personalProfilePanel1.add(personalRestrict, gbc5);
        gbc5.gridy = 4;
        personalProfilePanel1.add(viewFriends);
        gbc5.gridy = 5;
        personalProfilePanel1.add(viewBlocked);
        gbc5.gridy = 6;
        personalProfilePanel1.add(viewRequested);

        personalProfileBackButton.setFont(new Font("Arial",
                Font.PLAIN, personalProfileBackButton.getFont().getSize()));
        personalProfilePanel2.add(personalProfileBackButton);
        personalProfilePanel2.add(editProfileButton);

        personalProfileScreen.add(personalProfilePanel1, BorderLayout.CENTER);
        personalProfileScreen.add(personalProfilePanel2, BorderLayout.NORTH);

        // Dashboard
        JPanel dashboardScreen = new JPanel(new BorderLayout());
        chatHistory = new ArrayList<>();

        JPanel dashboardTopPanel = new JPanel();
        dashboardTopPanel.setLayout(new BoxLayout(dashboardTopPanel, BoxLayout.X_AXIS));
        dashboardTopPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        JPanel topLeftPanel = new JPanel();
        dashboardSignOutButton.setFont(new Font("Arial", Font.BOLD, 18));
        topLeftPanel.add(dashboardSignOutButton);
        JPanel topMiddlePanel = new JPanel();
        dashboardUserSearchField.setFont(new Font("Arial", Font.BOLD, 18));
        topMiddlePanel.add(dashboardUserSearchField);
        //dashboardUserSearchField.setMaximumSize(new Dimension(200, 30));
        dashboardUserSearchButton.setFont(new Font("Arial", Font.BOLD, 18));
        topMiddlePanel.add(dashboardUserSearchButton);
        JPanel topRightPanel = new JPanel();
        dashboardUserProfileButton.setFont(new Font("Arial", Font.BOLD, 18));
        topRightPanel.add(dashboardUserProfileButton);

        dashboardTopPanel.add(topLeftPanel, BorderLayout.WEST);
        dashboardTopPanel.add(topMiddlePanel, BorderLayout.CENTER);
        dashboardTopPanel.add(topRightPanel, BorderLayout.EAST);

        dashboardScreen.add(dashboardTopPanel, BorderLayout.NORTH);

        JPanel mainContentPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        mainContentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        JPanel userPanel = new JPanel(new BorderLayout());
        userPanel.setBorder(createStyledTitledBorder("Users"));

        dashboardSearchPanel = new JPanel();
        dashboardSearchPanel.setLayout(new BoxLayout(dashboardSearchPanel, BoxLayout.X_AXIS));
        dashboardSearchDMField.setFont(new Font("Arial", Font.BOLD, 18));
        dashboardSearchPanel.add(dashboardSearchDMField);
        dashboardSearchPanel.add(Box.createHorizontalStrut(5));
        dashboardSearchDMButton.setFont(new Font("Arial", Font.BOLD, 18));
        dashboardSearchPanel.add(dashboardSearchDMButton);
        seeAllDMUsers.setFont(new Font("Arial", Font.BOLD, 18));
        dashboardSearchPanel.add(seeAllDMUsers);

        // Implement a for loop to fill up the user list on the left panel in the dashboard
        JScrollPane userScrollPane = new JScrollPane(dashboardUserList);

        userPanel.add(dashboardSearchPanel, BorderLayout.NORTH);
        userPanel.add(userScrollPane, BorderLayout.CENTER);

        mainContentPanel.add(userPanel);

        JPanel chatPanel = new JPanel(new BorderLayout());
        chatPanel.setBorder(createStyledTitledBorder("Chat"));

        JPanel chatTextFlow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        chatTextPanel = new JPanel(new GridBagLayout());
        gbc6 = new GridBagConstraints();
        gbc6.insets = new Insets(5, 5, 5, 5);
        gbc6.anchor = GridBagConstraints.NORTH;
        gbc6.gridx = 0;
        gbc6.gridy = 0;

        chatTextFlow.add(chatTextPanel, gbc6);

        chatScrollPane = new JScrollPane(chatTextFlow);
        chatScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        chatScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        chatPanel.add(chatScrollPane, BorderLayout.CENTER);

        JPanel deletePanel = new JPanel(new BorderLayout());
        deleteDMID.setFont(new Font("Arial", Font.PLAIN, 18));
        deletePanel.add(deleteDMID, BorderLayout.CENTER);
        deleteDMButton.setFont(new Font("Arial", Font.BOLD, 18));
        deletePanel.add(deleteDMButton, BorderLayout.EAST);

        JPanel messageInputPanel = new JPanel(new BorderLayout());
        dashboardNewMessageField.setFont(new Font("Arial", Font.PLAIN, 18));
        messageInputPanel.add(dashboardNewMessageField, BorderLayout.CENTER);
        dashboardSendMessageButton.setFont(new Font("Arial", Font.BOLD, 18));
        messageInputPanel.add(dashboardSendMessageButton, BorderLayout.EAST);

        JPanel deleteAndMessagePanel = new JPanel(new BorderLayout());
        deleteAndMessagePanel.add(deletePanel, BorderLayout.NORTH);
        deleteAndMessagePanel.add(messageInputPanel, BorderLayout.SOUTH);

        chatPanel.add(deleteAndMessagePanel, BorderLayout.SOUTH);

        mainContentPanel.add(chatPanel);

        dashboardScreen.add(mainContentPanel, BorderLayout.CENTER);

        // Friends List Screen
        JPanel friendsListScreen = new JPanel(new BorderLayout());
        friendsListScreen.setBorder(createStyledTitledBorder("Friends"));

        JPanel friendsTopPanel = new JPanel();
        JPanel friendsSearchPanel = new JPanel(new FlowLayout());
        JPanel friendsBackButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));

        friendsListSearch.setFont(new Font("Arial", Font.BOLD, 18));
        friendsListSearchButton.setFont(new Font("Arial", Font.BOLD, 18));
        friendsSearchPanel.add(friendsListSearch);
        friendsSearchPanel.add(friendsListSearchButton);

        friendsBackButtonPanel.add(friendsListBackButton);

        friendsTopPanel.add(friendsBackButtonPanel, BorderLayout.WEST);
        friendsTopPanel.add(friendsSearchPanel, BorderLayout.CENTER);

        friendSearchResult = new JPanel(new GridLayout(0, 1));
        JScrollPane friendsListScrollPane = new JScrollPane(friendSearchResult);

        friendsListScreen.add(friendsListScrollPane, BorderLayout.CENTER);
        friendsListScreen.add(friendsTopPanel, BorderLayout.NORTH);

        // Blocked List Screen
        JPanel blockedListScreen = new JPanel(new BorderLayout());
        blockedListScreen.setBorder(createStyledTitledBorder("Blocked Users"));

        JPanel blockedListTopPanel = new JPanel();
        JPanel blockedSearchPanel = new JPanel(new FlowLayout());
        JPanel blockedBackButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));

        blockedListSearch.setFont(new Font("Arial", Font.BOLD, 18));
        blockedListSearchButton.setFont(new Font("Arial", Font.BOLD, 18));
        blockedSearchPanel.add(blockedListSearch);
        blockedSearchPanel.add(blockedListSearchButton);

        blockedBackButtonPanel.add(blockedListBackButton);

        blockedListTopPanel.add(blockedBackButtonPanel, BorderLayout.WEST);
        blockedListTopPanel.add(blockedSearchPanel, BorderLayout.CENTER);

        blockedSearchResult = new JPanel(new GridLayout(0, 1));
        JScrollPane blockedListScrollPane = new JScrollPane(blockedSearchResult);

        blockedListScreen.add(blockedListScrollPane, BorderLayout.CENTER);
        blockedListScreen.add(blockedListTopPanel, BorderLayout.NORTH);

        // Requested screen
        JPanel requestedListScreen = new JPanel(new BorderLayout());
        requestedListScreen.setBorder(createStyledTitledBorder("Requested"));

        JPanel requestedTopPanel = new JPanel();
        JPanel requestedSearchPanel = new JPanel(new FlowLayout());
        JPanel requestedBackButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));

        requestedListSearch.setFont(new Font("Arial", Font.BOLD, 18));
        requestedListSearchButton.setFont(new Font("Arial", Font.BOLD, 18));
        requestedSearchPanel.add(requestedListSearch);
        requestedSearchPanel.add(requestedListSearchButton);

        requestedBackButtonPanel.add(requestedListBackButton);

        requestedTopPanel.add(requestedBackButtonPanel, BorderLayout.WEST);
        requestedTopPanel.add(requestedSearchPanel, BorderLayout.CENTER);

        requestedSearchResult = new JPanel(new GridLayout(0, 1));
        JScrollPane requestedListScrollPane = new JScrollPane(requestedSearchResult);

        requestedListScreen.add(requestedListScrollPane, BorderLayout.CENTER);
        requestedListScreen.add(requestedTopPanel, BorderLayout.NORTH);

        // Add panels to CardLayout container
        container.add(logSignScreen, "logSignPanel");
        container.add(loginScreen, "login");
        container.add(signUpScreen, "signUp");
        container.add(editProfileScreen, "editProfile");
        container.add(otherProfileScreen, "otherUserProfile");
        container.add(personalProfileScreen, "personalUserProfile");
        container.add(searchUsersPanel, "searchUsers");
        container.add(dashboardScreen, "dashboard");
        container.add(friendsListScreen, "friendsListScreen");
        container.add(blockedListScreen, "blockedListScreen");
        container.add(requestedListScreen, "requestedListScreen");

        // Display the frame
        frame.setVisible(true);

        // Optionally show the initial panel explicitly
        cardLayout.show(container, "logSignPanel");
    }

    public TitledBorder createStyledTitledBorder(String title) {
        TitledBorder border = new TitledBorder(
                BorderFactory.createLineBorder(Color.black), title, TitledBorder.CENTER,
                TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.BOLD, 18));
        return border;
    }

    public void updateSearchResults(String[] results) {
        resultsPanel.removeAll();
        for (String username : results) {
            JButton userButton = new JButton(username);
            userButton.setFont(new Font("Arial", Font.PLAIN, 14));
            userButton.setHorizontalAlignment(SwingConstants.CENTER);

            userButton.addActionListener(e -> {
                String selectedUser = username;
                c.seeUserInfo(selectedUser, 1);
                c.getProfilePicture(selectedUser);
                cardLayout.show(container, "otherUserProfile");
            });
            resultsPanel.add(userButton);
        }
        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

    public void updateDMSearchList(String searchTerm) {
        dashboardUserList.removeAll(); // Clear the existing panel
        c.getBlockedByOther(currentClientUsername);

        ArrayList<String> combinedList = new ArrayList<>(Arrays.asList(currentClientFriends));
        for (String users : publicUsers) {
            if (!combinedList.contains(users)) {
                combinedList.add(users);
            }
        }
        searchTerm = searchTerm.toLowerCase();
        ArrayList<String> filteredList = new ArrayList<>();
        for (String user : combinedList) {
            if (user.toLowerCase().contains(searchTerm)) {
                filteredList.add(user);
            }
        }
        filteredList.remove(currentClientUsername);
        ArrayList<String> filteredList2 = new ArrayList<>();
        for (String user : filteredList) {
            if (!currentClientBlockedByOther.contains(user)) {
                filteredList2.add(user);
            }
        }
        dashboardUserList.setListData(filteredList2.toArray(new String[0]));

        dashboardUserList.revalidate();
        dashboardUserList.repaint();
    }


    public void updateFriendSearchResults(String[] results) {
        friendSearchResult.removeAll(); // Clear existing components
        for (String username : results) {
            JButton userButton = new JButton(username);
            userButton.setFont(new Font("Arial", Font.PLAIN, 14));
            userButton.setHorizontalAlignment(SwingConstants.LEFT);

            // Add action listener to handle button clicks
            userButton.addActionListener(e -> {
                String selectedUser = username;
                // Fetch user info from the server or database
                c.seeUserInfo(selectedUser, 1);
                c.getProfilePicture(selectedUser);
                cardLayout.show(container, "otherUserProfile");
            });
            friendSearchResult.add(userButton);
        }
        friendSearchResult.revalidate(); // Recompute layout
        friendSearchResult.repaint();   // Refresh the panel
    }

    public void updateRequestedSearchResults(String[] results) {
        requestedSearchResult.removeAll(); // Clear existing components
        for (String username : results) {
            JPanel panel = new JPanel(new GridLayout(1, 3));
            JButton userButton = new JButton(username);
            userButton.setFont(new Font("Arial", Font.PLAIN, 14));
            userButton.setHorizontalAlignment(SwingConstants.LEFT);

            // Add action listener to handle button clicks
            userButton.addActionListener(e -> {
                String selectedUser = username;
                // Fetch user info from the server or database
                c.seeUserInfo(selectedUser, 1);
                c.getProfilePicture(selectedUser);
                cardLayout.show(container, "otherUserProfile");
            });

            JButton acceptButton = new JButton("Accept");
            acceptButton.setFont(new Font("Arial", Font.PLAIN, 14));

            acceptButton.addActionListener(e -> {
                c.acceptFriend(currentClientUsername, username);
            });

            JButton rejectButton = new JButton("Reject");
            rejectButton.setFont(new Font("Arial", Font.PLAIN, 14));

            rejectButton.addActionListener(e -> {
                c.rejectFriend(currentClientUsername, username);
            });

            panel.add(userButton);
            panel.add(acceptButton);
            panel.add(rejectButton);

            requestedSearchResult.add(panel);
        }
        requestedSearchResult.revalidate(); // Recompute layout
        requestedSearchResult.repaint();   // Refresh the panel
    }

    public void updateBlockedSearchResults(String[] results) {
        blockedSearchResult.removeAll(); // Clear existing components
        for (String username : results) {
            JButton userButton = new JButton(username);
            userButton.setFont(new Font("Arial", Font.PLAIN, 14));
            userButton.setHorizontalAlignment(SwingConstants.LEFT);

            // Add action listener to handle button clicks
            userButton.addActionListener(e -> {
                String selectedUser = username;
                // Fetch user info from the server or database
                c.seeUserInfo(selectedUser, 1);
                c.getProfilePicture(selectedUser);
                cardLayout.show(container, "otherUserProfile");
            });
            blockedSearchResult.add(userButton);
        }
        blockedSearchResult.revalidate(); // Recompute layout
        blockedSearchResult.repaint();
    }

    public void logIn(String result) {
        if (result.endsWith("USERNAME NOT FOUND")) {
            loggedIn = false;
            JOptionPane.showMessageDialog(frame, "Username not found",
                    "Login error", JOptionPane.WARNING_MESSAGE);
        } else if (result.endsWith("INCORRECT PASSWORD")) {
            loggedIn = false;
            JOptionPane.showMessageDialog(frame, "Password Incorrect",
                    "Login error", JOptionPane.WARNING_MESSAGE);
        } else if (result.endsWith("SUCCESS")) {
            loggedIn = true;
            currentClientUsername = loginUsername.getText();
            loginUsername.setText("");
            loginPassword.setText("");
            c.seeUserInfo(currentClientUsername, 0);
            c.getPublicUsers();
            c.getBlockedByOther(currentClientUsername);
            cardLayout.show(container, "dashboard");
        }
    }

    public void seeUserInfo(String result, String[] info) {
        if (result.endsWith("MYSELF")) {
            personalUsername.setText(info[0]);
            personalPassword.setText(info[1]);
            if (info[2].equals("true"))
                personalRestrict.setText("Privacy: Friends Only");
            else
                personalRestrict.setText("Privacy: Anyone");
            personalBioText.setText(info[3]);
            if (info[4].contains("NONE")) {
                friendsList = new ArrayList<>();
                currentClientFriends = new String[0];
            } else {
                String[] friend = info[4].split(",");
                currentClientFriends = friend;
                friendsList.addAll(Arrays.asList(friend));
            }
            if (info[5].contains("NONE")) {
                blockedList = new ArrayList<>();
                currentClientBlocked = new String[0];
            } else {
                String[] block = info[5].split(",");
                currentClientBlocked = block;
                blockedList.addAll(Arrays.asList(block));
            }
            if (info[6].contains("NONE")) {
                requestedList = new ArrayList<>();
                currentClientRequested = new String[0];
            } else {
                String[] requested = info[6].split(",");
                currentClientRequested = requested;
                requestedList.addAll(Arrays.asList(requested));
            }
        } else if (result.endsWith("FRIEND")) {
            c.seeUserInfo(currentClientUsername, 0);
            otherProfileUsername.setText(info[0]);
            otherProfileBioText.setText(info[1]);
            ArrayList<String> fr = new ArrayList<>(Arrays.asList(currentClientFriends));
            if (info[2].contains(currentClientUsername)) {
                otherProfileAddRemoveFriendButton.setText("Requested");
            } else if (fr.contains(info[0])) {
                otherProfileAddRemoveFriendButton.setText("Remove Friend");
            } else {
                otherProfileAddRemoveFriendButton.setText("Add Friend");
            }

            ArrayList<String> fr2 = new ArrayList<>(Arrays.asList(currentClientBlocked));
            if (fr2.contains(otherProfileUsername.getText())) {
                otherProfileAddRemoveBlockButton.setText("Unblock");
            } else {
                otherProfileAddRemoveBlockButton.setText("Block");
            }
        } else if (result.endsWith("STRANGER")) {
            otherProfileUsername.setText(info[0]);
            otherProfileBioText.setText(info[1]);
        }
    }

    public void editUserInfo(String result) {
        if (result.endsWith("INVALID USERNAME")) {
            JOptionPane.showMessageDialog(frame, "Invalid Username",
                    "Invalid Username", JOptionPane.WARNING_MESSAGE);
        } else if (result.endsWith("INVALID PASSWORD")) {
            JOptionPane.showMessageDialog(frame, "Invalid Password",
                    "Invalid Password", JOptionPane.WARNING_MESSAGE);
        } else if (result.endsWith("SUCCESS")) {
            JOptionPane.showMessageDialog(frame, "Successful Save",
                    "Successful Save", JOptionPane.INFORMATION_MESSAGE);
            currentClientUsername = tempTemp;
        }
    }

    public void addUser(String result) {
        if (result.endsWith("INVALID USERNAME")) {
            JOptionPane.showMessageDialog(frame, "Invalid Username",
                    "Invalid Username", JOptionPane.WARNING_MESSAGE);
        } else if (result.endsWith("INVALID PASSWORD")) {
            JOptionPane.showMessageDialog(frame, "Invalid Password",
                    "Invalid Password", JOptionPane.WARNING_MESSAGE);
        } else if (result.endsWith("SUCCESS")) {
            JOptionPane.showMessageDialog(frame, "Successful Sign Up",
                    "Successful Sign Up", JOptionPane.INFORMATION_MESSAGE);
            currentClientUsername = signUpUsername.getText();
            cardLayout.show(container, "dashboard");
        }
    }

    public void getProfilePicture(byte[] pic) {
        if (pic == null) {
            BufferedImage blackImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = blackImage.createGraphics();
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, 100, 100); // Fill the image with black
            g2d.dispose();
            ImageIcon noPFP = new ImageIcon(blackImage);
            personalPFP.setIcon(noPFP);
            otherProfilePFP.setIcon(noPFP);
        } else {
            ImageIcon pfp = new ImageIcon(pic);
            Image originalImage = pfp.getImage();
            Image resizedImage = originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            ImageIcon resizedIcon = new ImageIcon(resizedImage);
            personalPFP.setIcon(resizedIcon);
            otherProfilePFP.setIcon(resizedIcon);
        }
    }


    public void saveProfilePicture(String result) {
        if (result.endsWith("SUCCESS")) {
            JOptionPane.showMessageDialog(frame, "Successful PFP Save",
                    "Successful PFP Save", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void requestFriend(String result) {
        if (result.endsWith("SUCCESS")) {
            JOptionPane.showMessageDialog(frame, "Successful Friend Request",
                    "Successful Friend Request", JOptionPane.INFORMATION_MESSAGE);
            otherProfileAddRemoveFriendButton.setText("Requested");
        } else if (result.endsWith("BLOCKED")) {
            JOptionPane.showMessageDialog(frame, "Failure to request. Blocked.",
                    "Blocked", JOptionPane.WARNING_MESSAGE);

        }
    }

    public void acceptFriend(String result) {
        if (result.endsWith("SUCCESS")) {
            JOptionPane.showMessageDialog(frame, "Successful Friend Accept",
                    "Successful Friend Accept", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void rejectFriend(String result) {
        if (result.endsWith("SUCCESS")) {
            JOptionPane.showMessageDialog(frame, "Successful Friend Reject",
                    "Successful Friend Reject", JOptionPane.INFORMATION_MESSAGE);
            SwingUtilities.invokeLater(() -> updateRequestedSearchResults(currentClientRequested));
        }
    }

    public void removeFriend(String result) {
        if (result.endsWith("SUCCESS")) {
            JOptionPane.showMessageDialog(frame, "Successful Unfriending",
                    "Successful Unfriending", JOptionPane.INFORMATION_MESSAGE);
            otherProfileAddRemoveFriendButton.setText("Add Friend");
        }
    }

    public void userSearch(String result, String[] info) {
        c.seeUserInfo(currentClientUsername, 0);
        if (result.endsWith("NO RESULTS") || (info.length == 1 && info[0].equals(currentClientUsername))) {
            cardLayout.show(container, "dashboard");
            JOptionPane.showMessageDialog(frame, "No user found",
                    "UserNotFound", JOptionPane.WARNING_MESSAGE);
            userSearchList = info;
            updateSearchResults(userSearchList);
        } else {
            ArrayList<String> temp = new ArrayList<String>(Arrays.asList(info));
            temp.remove(currentClientUsername);
            info = temp.toArray(new String[temp.size()]);
            userSearchList = info;
            updateSearchResults(userSearchList);
            ArrayList<String> friendMatch = new ArrayList<>();
            for (String s : currentClientFriends) {
                if (temp.contains(s)) {
                    friendMatch.add(s);
                }
            }
            String[] friends = friendMatch.toArray(new String[friendMatch.size()]);
            updateFriendSearchResults(friends);

            ArrayList<String> requestedMatch = new ArrayList<>();
            for (String s : currentClientRequested) {
                if (temp.contains(s)) {
                    requestedMatch.add(s);
                }
            }
            String[] requested = requestedMatch.toArray(new String[requestedMatch.size()]);
            updateRequestedSearchResults(requested);

            ArrayList<String> blockedMatch = new ArrayList<>();
            for (String s : currentClientBlocked) {
                if (temp.contains(s)) {
                    blockedMatch.add(s);
                }
            }
            String[] blocked = blockedMatch.toArray(new String[blockedMatch.size()]);
            updateBlockedSearchResults(blocked);
        }
    }

    public void addBlocked(String result) {
        if (result.endsWith("SUCCESS")) {
            JOptionPane.showMessageDialog(frame, "User successfully blocked",
                    "Blocked Message", JOptionPane.INFORMATION_MESSAGE);
            otherProfileAddRemoveBlockButton.setText("Unblock");
            otherProfileAddRemoveFriendButton.setText("Add Friend");
            otherProfileAddRemoveFriendButton.setEnabled(false);
        }
    }

    public void removeBlocked(String result) {
        if (result.endsWith("SUCCESS")) {
            JOptionPane.showMessageDialog(frame, "User successfully unblocked",
                    "Unblocked Message", JOptionPane.INFORMATION_MESSAGE);
            otherProfileAddRemoveBlockButton.setText("Block");
            otherProfileAddRemoveFriendButton.setEnabled(true);
        }
    }

    public void sendDM(String result) {
        if (result.endsWith("BLOCKED")) {
            JOptionPane.showMessageDialog(frame, "ERROR: User has blocked you or your have blocked them",
                    "BLOCKED", JOptionPane.ERROR_MESSAGE);
        } else if (result.endsWith("SUCCESS")) {
            c.getDMChat(currentClientUsername, dashboardUserList.getSelectedValue());

        } else if (result.endsWith("FRIENDONLY")) {
            JOptionPane.showMessageDialog(frame, "ERROR: Need to befriend user",
                    "Unreachable", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void removeDM(String result) {
        fetchChatHistory(dashboardUserList.getSelectedValue());
    }

    public void getDMChat(String result, ArrayList<String> chat) {
        if (result.endsWith("NONE")) {
            chatHistory = new ArrayList<>();
        } else if (result.endsWith("SUCCESS")) {
            chatHistory = chat;
        }
        SwingUtilities.invokeLater(() -> updateChatPanel(dashboardUserList.getSelectedValue(), chatHistory));
    }

    public void getPublicUsers(String result) {
        publicUsers = new ArrayList<>(Arrays.asList(result.split(",")));
    }

    public void getBlockedByOther(String result) {
        currentClientBlockedByOther = new ArrayList<>(Arrays.asList(result.split(",")));
    }

    private void performCleanup() {
        try {
            c.closeSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
