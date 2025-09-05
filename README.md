# README

## Instructions

All classes and code are located within the src folder. For phase 3 of the project, we shifted the focus to implementing
the graphical user interface (GUI) that interacts with both the Client and Server components developed in previous phases. 
The primary class of focus for this phase is GUI.java, which provides a user-friendly interface for logging in, creating 
accounts, viewing profiles, searching for users, sending messages, and managing friend and blocked lists. The GUI relies 
on the Client and Server classes to communicate and display real-time updates, ensuring seamless functionality. To run the 
GUI, you must first start the Server, followed by the Client. Once the Client is running, the GUI window will appear, 
allowing you to interact with the program. Other relevant classes for phase 3 include the GUIInterface and various GUI 
panel classes that work together to produce a cohesive user experience. During this phase, the RunLocalTestCase class 
remains available to verify that existing functionalities (including those from phases 1 and 2) continue to operate as 
expected, ensuring the overall integrity and reliability of the system.

To run the GUI program, first start the Server.java file. Once the server is running, launch the Client.java file. 
After that, the GUI window will appear, allowing you to interact with the program. 

## Submission Info

- Noah Pien submitted the Vocareum workspace.
- Rishikesh Padhye submitted the presentation video.
- Karthikeya Lakoji submitted the project report.

## Description of each class:

- Database.java


- The Database class handles the data persistence aspect for user information, direct messages, and profile pictures for
  our social media project. This class stores and retrieves user data from text files. This allows the data to persist
  even after reboots. The class implements the DatabaseInterface interface. The class uses the User class and object.
  The Database object is used in RunLocalTestCase.

- Methods:

    - addUser() - Writes the user info into a UserInfo.txt file which acts as the database

    - editUserInfo(User user) - Edits user info in UserInfo.txt file to the new user info contained in the User object

    - removeUser(int userID) - Removes the user with the given userID in UserInfo.txt file

    - getUserName(int userID) - gets the username of the given userID

    - getAllUserName() - returns an arraylist of all usernames in the UserInfo file

    - getPassword(int userID) - gets the password of the given userID

    - getFriendRestricted(int userID) - gets the status for whether the user wants to restrict direct messages to all
      users or friends only

    - getBio(int userID) - gets the user bio of given userID

    - getFriends(int userID) - gets the friend list of the userID

    - getBlockedUsers(int userID) - gets the blocked list of the userID

    - addDM(User texter, User receiver, String message) - writes the direct message between two users into the
      DirectMessages file

    - removeDM(User texter, User receiver, int messageID) - removes the text message with the given messageID between
      the two users from the DirectMessages file

    - getDMChat(User texter, User receiver) - gets the entire chat history between two users (omits deleted messages
      because those are removed from the chat history)

    - getProfilePicture(int userID) - gets the profile picture of the given userID in the form of a byte array

    - saveProfilePicture(int userID, byte[] profilePicture) - saves the profile picture of a given userID

    - getRequested(int userID) - gets the friend requests of the userID

- DatabaseInterface.java

    - This is the interface for the Database class. It defines the core functionalities for data management.

- InvalidUserNamePasswordException.java

    - The InvalidUserNamePasswordException class is a custom exception for whenever the password for the given username
      is incorrect, the username or password contains non-alphanumeric values when creating it, the username and
      password is null or empty when creating it, or if the password is too short when creating it.

- InvalidUserNamePasswordExceptionInterface.java

    - This is the interface for the InvalidUserNamePasswordException class. It defines the core functionalities for the
      exception.

- RunLocalTestCase.java (contains two subclass)

    - TestUser

		 -   TestUser is a subclass of the RunLocalTestCase.java class. It checks to make sure that the constructors for the User class work as expected when given certain edge cases and also that the class declaration itself is valid (public, not abstract, extends Object, implements UserInterface)        
		 -   Methods:  
		 
			 -   testUserConstructorAndToString() - tests the User constructor and toString() method to make sure they function as expected
      			 -   userClassDeclarationTest() - tests the User class declaration itself and that it is public, not abstract, extends Object, and implements the UserInterface interface  
    
	-   TestDatabase
    
		-   TestDatabase is a subclass of the RunLocalTestCase.java class. It checks to make sure that the methods work as expected when given certain edge cases.

		-   Methods:
			- restoreTestFile() - restores test files and directories to their original state after every test
	
			-  databaseClassDeclarationTest() - verifies that the Database class is public,not abstract, extends Object, and implements the DatabaseInterface interface
  
			-   testAddUser() - runs multiple tests for the addUser() method in the Database class
   
			-   testEditUserInfo() - runs multiple tests for the editUserInfo() method in the Database class

			-   testGetUserName() - runs multiple tests for the getUserName() method in the Database class

			-   testGetAllUserName() - runs multiple tests for the getAllUserName() method in the Database class

			-   testGetPassword() - runs multiple tests for the getPassword() method in the Database class

			-   testGetFriendRestricted() - runs multiple tests for the getFriendRestricted() method in the Database class

			-   testGetBio() - runs multiple tests for the getBio() method in the Database class

			-   testGetFriends() - runs multiple tests for the getFriends() method in the Database class

			-   testGetBlocked() - runs multiple tests for the getBlockedUsers() method in the Database class

			-   testAddDM() - runs multiple tests for the addDM() method in the Database class

			-   testRemoveDM() - runs multiple tests for the removeDM() method in the Database class

			-   testGetDMChat() - runs multiple tests for the getDMChat() method in the Database class

			-   testGetProfilePicture() throws IOException - runs multiple tests for the getProfilePicture() method in the Database class

			-   testSaveProfilePicture() throws IOException - runs multiple tests for the saveProfilePicture() method in the Database class

			-   testRemoveUser() - runs multiple tests for the removeUser() method in the Database class

			-   testGetRequested() - runs multiple tests for the getRequested() method in the Database class

    -   TestClient (Phase 2)
    

		-   TestClient is a subclass of the RunLocalTestCase.java class. It checks to make sure the Client methods work as expected.
    
		-   Methods:
    

			-   setUp() - sets up the pseudo-server environment to check to see if the method outputs are as expected
			    
			-   tearDown() - closes the pipedInputStream and pipedOutputStream
			    
			-   clientClassDeclarationTest() - Makes sure that the class is declared properly
			    
			-   testSendMessage() throws IOException - sends a message to the server to let it know important updates
			    
			-   testSendDM() throws IOException - runs multiple tests for sendDM() method in Client class
			    
			-   testRemoveDM() throws IOException - runs multiple tests for removeDM() method in Client class
			    
			-   testGetDMChat() throws IOException - runs multiple tests for getDMChat() method in Client class
			    
			-   testEditUserInfo() throws IOException - runs multiple tests for editUserInfo() method in Client class
			    
			-   testRequestFriend() throws IOException - runs multiple tests for requestFriend() method in Client class
			    
			-   testAcceptFriend() throws IOException - runs multiple tests for acceptFriend() method in Client class
			    
			-   testRejectFriend() throws IOException - runs multiple tests for rejectFriend() method in Client class
			    
			-   testAddBlocked() throws IOException - runs multiple tests for addBlocked() method in Client
			    
			-   testRemoveBlocked() throws IOException - runs multiple tests for removeBlocked() method in Client class
			    
			-   testAddUser() throws IOException - runs multiple tests for addUser() method in Client class
			    
			-   testUserSearch() throws IOException - runs multiple tests for userSearch() method in Client class
			    
			-   testGetProfilePicture() throws IOException - runs multiple tests for getProfilePicture() method in Client class
			    
			-   testSaveProfilePicture() throws IOException - runs multiple tests for saveProfilePicture() method in Client class
			    
			-   testSeeUserInfo() throws IOException - runs multiple tests for seeUserInfo() method in Client class
			    
			-   testLogIn() throws IOException - runs multiple tests for logIn() method in Client class
			    
			-   testRemoveUser() throws IOException - runs multiple tests for removeUser() in Client class
			    
			-   testRemoveFriend() throws IOException - runs multiple tests for removeFriend() method in Client class


	-   TestServer (Phase 2)
	
	
		-   TestServer is a subclass of the RunLocalTestCase.java class. It checks to make sure the class declaration and Server constructor function as expected
		    
		-   Methods:
	    
	
			-   serverClassDeclarationTest() - makes sure the class declaration is properly working
			    
			-   testServerConstructor() - makes sure the server constructor properly accesses the database

- ClientHandlerInterface.java

	-   This is one of the interfaces for the Server class, more specifically the ClientHandler subclass. It defines the core functionalities of the ClientHandler subclass of the Server class.

- ClientInterface.java

	-   This is the interface for the Client class. It defines the core functionalities of the Client class with all of the implementable methods.

- ServerInterface.java

	-   This is the primary interface for the Server class. It defines the core functionalities of the Server class with the run method.

- ServerStatusInterface.java

	-   This is one of the interfaces for the Server class, more specifically the ServerStatus subclass. It defines the core functionalities of the ServerStatus subclass.  

- Server.java (contains two subclasses)

	-   This class is used to set up the connection between the Client and Server, which stores the information passed into the Server from Client. It then writes the information into the database by calling methods in Database on the Database object db. It has the private fields Object O and Database db. It contains two subclasses: ServerStatus and ClientHandler.
    
	-   main() - Starts a server if one hasn’t already been started and writes to the ServerStartedEnded file to indicate that a server has been started
    

	-   ServerStatus
    

		-   This class prevents another Server from running on the same system
    
		-   Methods
    

			-   run() - writes to the ServerStartedEnded file to indicate serverInt number
    

	-   ClientHandler
    

		-   This class handles all of the client-server interactions with the methods called in Client. It takes in input from the client and returns back what the client needs from the database or edits the database.
    
		-   Methods
    

			-   run() - reads and writes commands from the clientSocket where data is passed through. Messages are spread apart by new lines and colons to indicate different types of data. In the end, it closes the reader and writer as well as the socket.'

- Client.java

	-   This class is used to create transmission of data from the client to the server, and allows for writing to the socket connection between the two. It contains the private field variables BufferedReader br and PrintWriter pw to indicate that there is a need for the two in order to read and write to the files.
    
	-   Methods:
    

		-   main() - checks to see if a server has been started and if not, starts a new client
		    
		-   run() - communicates with the server between a socket connection between the two and returns results into the GUI, which has not yet been implemented
		    
		-   getPw() - returns the PrintWriter object
		    
		-   setPw(PrintWriter pw) - sets the PrintWriter to pw
		    
		-   getBr() - returns the BufferedReader object
		    
		-   setBr(BufferedReader br) - sets the BufferedReader to br
		    
		-   sendMessage(String message) - sends a message to the socket from the client
		    
		-   sendDM(String message, String username1, String username2) - sends the DM information in the specified format
		    
		-   removeDM(int id, String username1, String username2) - removes a DM between the two users using the DM chat tag id
		    
		-   getDMChat(String username1, String username2) - gets all of the DM chat between the two users
		    
		-   editUserInfo(String oldUsername, String newUsername, String password, String bio, boolean friendRestricted) - edits the user’s information in the specified format
		    
		-   requestFriend(String requester, String requested) - sends a friend request from the requester to the requested
		    
		-   acceptFriend(String requested, String requester) - accepts the friend request between the requested and requester
		    
		-   rejectFriend(String requested, String requester) - rejects the friend request between the requested and requester
		    
		-   removeFriend(String user, String saidFriend) - removes the saidFriend from user’s friends list
		    
		-   addBlocked(String username1, String username2) - adds username2 to username1’s blocked list
		    
		-   removeBlocked(String username1, String username2) - unblocks username2 from username1
		    
		-   addUser(String username, String password, String bio, boolean friendRestricted) - adds the user to the database
		    
		-   userSearch(String input) - searches for a user in the database
		    
		-   getProfilePicture(String username) - gets the profile picture from the username
		    
		-   saveProfilePicture(String username, byte[] image) throws IOException - saves a profile picture from the user
		    
		-   seeUserInfo(String username, int privacyLevel) - views user information based on level of privacy
		    
		-   logIn(String username, String password) - logs the user into the application
		    
		-   removeUser(String username) - removes the user from the database

- User.java

    - This class is used to create User objects to store in the Database file which we will be using to write and store
      information to a hard disk. It holds important information about the User object such as their username, password,
      ID number, and privacy settings. The class implements the UserInterface for all of its methods, which are mostly
      used to get the aforementioned private fields. The User object is used in RunLocalTestCase several times.

    - Methods:
         
		-   isFriendOnly() - Returns boolean friendsOnly        

		-   setFriendOnly(boolean friendOnly) - sets the boolean value friendOnly to the new boolean value  

        -   getUsername() - returns the String username  

        -   getPassword() - returns the String password  

        -   verifyPassword(String password) - returns the boolean value of if password equals the parameter passed in the method  

        -   getUserID() - returns the userID int 
 
        -   getBio() - returns the String bio  

        -   setUsername(String username) - this throws an InvalidUserNamePasswordException if the new username passed in the method parameter is null, empty, or contains special characters; otherwise, it will set the username to the parameter  

        -   setPassword(String password) - this throws an InvalidUserNamePasswordException if the new password passed in the method parameter is null, empty, contains special characters, or has a length less than 8; otherwise it will set the password to the parameter  

        -   setBio(String bio) - sets bio to what’s passed in the parameter  

        -   getFriends() - returns an ArrayList of Users that are friends with the user  

        -   setFriends(ArrayList<User> friends) - sets the ArrayList to something new in the parameter  

        -   getBlockedUsers() - return an ArrayList of Users that the user has blocked  

        -   setBlockedUsers(ArrayList<User> blockedUsers) - sets the ArrayList to something new in the parameter  

        -   blockUser(User user) - blocks the User described in the parameter if they are not already blocked  

        -   unblockUser(User user) - removes the user from the blockedUsers ArrayList  

        -   addFriend(User user) - adds the user to the friends list if not already in there and they are not in the blockedUsers list  

        -   removeFriend(User user) - removes the user from the friends ArrayList  

        -   setUserID(int id) - sets the userID to id

		-   setRequested(ArrayList<Integer> requested) - sets the list of friend request the user has

		-   getRequested() - sets the list of friend request the user has

		-   addRequested(int userID1) - adds to the list of friend requests the user has

		-   removeRequested(int userID1) - removes from the list of friend requests the user has

        -   toString() - returns the String which contains all of the user’s data line by line

- UserInterface.java

    - This is the interface for the User class. It defines the core functionalities for user info management.
  
- GUI.java
    - This class is used to create and manage the graphical user interface (GUI) components of the application. It 
      provides multiple screens for functionalities such as logging in, signing up, editing profiles, viewing personal 
      and other user profiles, searching for users, managing friend and blocked lists, and sending and receiving messages. 
      The GUI communicates with the Client class to send user requests to the server and updates its components based on 
      the responses received.

    - Methods:
        - GUI(Client c) - Constructor that initializes all GUI components, sets up action listeners, and configures the 
          CardLayout for managing multiple screens.
        - run() - Sets up the main application window, establishes the layout and panels for each screen, and makes the 
          frame visible. This is typically called from another thread to ensure proper GUI responsiveness.
        - handleActionEvent(ActionEvent e) - Handles all the button clicks and field submissions throughout the interface. 
          Depending on the source of the action, it navigates between panels, requests data from the client, updates 
          user information, or triggers other related actions.
        - fetchChatHistory(String selectedUser) - Communicates with the Client to retrieve the chat history between the 
          current logged-in user and the specified selectedUser. After fetching, it updates the chat panel.
        - updateChatPanel(String selectedUser, ArrayList<String> chat) - Clears and repopulates the chat display area 
          with the messages exchanged with selectedUser. Ensures that the most recent messages are visible and
          user-friendly. 
        - createStyledTitledBorder(String title) - Creates a stylized titled border for panels, improving UI clarity and 
          aesthetics. 
        - updateSearchResults(String[] results) - Refreshes the search results panel with a list of user buttons, each 
          representing a user that matches a search query. 
        - updateDMSearchList(String searchTerm) - Updates the displayed list of users for direct messaging (DM) based on 
          the given search term, filtering results from friends, public users, and other available data. 
        - updateFriendSearchResults(String[] results) - Updates the friends list panel after a search operation,
          displaying clickable buttons for each matched friend. 
        - updateRequestedSearchResults(String[] results) - Updates the requested friends list screen, showing pending 
          friend requests and providing options to accept or reject them. 
        - updateBlockedSearchResults(String[] results) - Refreshes the blocked users list screen, displaying the users 
          currently blocked by the logged-in user. 
        - logIn(String result) - Processes the server response to a login attempt, displaying success or error messages. 
          On success, transitions to the dashboard. 
        - seeUserInfo(String result, String[] info) - Handles server responses to user info requests, updating personal 
          or other user profile screens based on privacy, friendships, and blocked status. 
        - editUserInfo(String result) - Displays messages based on the outcome of editing user credentials and bio, 
          ensuring that invalid inputs are flagged and successful edits are acknowledged. 
        - addUser(String result) - Processes server feedback on account creation attempts, informing the user if their 
          new account was successfully created or if changes are needed. 
        - getProfilePicture(byte[] pic) - Updates the displayed profile picture in personal and other user profile 
          screens based on the retrieved image data. 
        - saveProfilePicture(String result) - Notifies the user about the success or failure of saving a profile picture
          to the server. 
        - requestFriend(String result) - Provides feedback to the user about sending a friend request, handling 
          scenarios where the request can fail if blocked. 
        - acceptFriend(String result), rejectFriend(String result), removeFriend(String result) - Each method handles 
          server responses related to friend management actions (accepting, rejecting, removing) and updates the GUI accordingly. 
        - userSearch(String result, String[] info) - Handles the outcome of a user search action, updating relevant 
          panels (search results, friends, requested, blocked) and navigating to appropriate screens. 
        - addBlocked(String result), removeBlocked(String result) - Informs the user about the success of blocking or 
          unblocking another user, updating the buttons and GUI states. 
        - sendDM(String result) - Shows error or success messages when sending direct messages, handling blocked or 
          privacy-restricted scenarios. 
        - removeDM(String result) - Refreshes the chat panel after removing a direct message, ensuring the chat display 
          stays current. 
        - getDMChat(String result, ArrayList<String> chat) - Receives and displays chat history from the server, 
          ensuring the user sees all recent messages. 
        - getPublicUsers(String result) - Updates the internal list of public users based on server responses, assisting
          with searches and messaging functionality. 
        - getBlockedByOther(String result) - Maintains an updated list of users who have blocked the current client, 
          affecting messaging and friend request options accordingly.
    - By integrating all these functionalities, the GUI class provides the end-user with a comprehensive and intuitive 
      interface for interacting with the various features of the social platform. It ensures that user actions are 
      translated into server requests and that the visual representation always reflects the latest state of the user’s 
      profile, connections, and messages.
      
- GUIInterface
    - This is the interface for the GUI class. It defines the core functionalities for managing the graphical interface, 
      handling user interactions, and updating visual components in response to changes in the system state.
        
        

