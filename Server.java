package src;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * A Server class that allows for a server to run and interact with the database and the client
 *
 * @author npien-purdue klakoji rpadye
 * @version December 7, 2024
 */

public class Server extends Thread implements ServerInterface {
    public static final Object O = new Object();
    private final Database db;

    public Server() {
        db = new Database("Database/UserInfo", "Database/DirectMessages", "Database/ProfilePictures");
    }

    public Database getDatabase() {
        return db;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        //Server start
        String serverFile;
        String serverFile2;
        File f = new File("Database/ServerStartedEnded");
        BufferedReader br = new BufferedReader(new FileReader(f));
        serverFile = br.readLine();
        br.close();
        sleep(2000);
        BufferedReader br2 = new BufferedReader(new FileReader(f));
        serverFile2 = br2.readLine();
        br2.close();
        if (serverFile == null || serverFile.equals(serverFile2)) {
            Server s = new Server();
            s.start();
        } else {
            JOptionPane.showMessageDialog(new JFrame("Warning"), "Server is already running!",
                    "Server Already Running", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void run() {
        try {
            //Update Server Status
            new ServerStatus().start();

            //Continuously accept client connections
            ServerSocket ss = new ServerSocket(4242);
            while (true) {
                Socket s = ss.accept();
                new ClientHandler(s).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * A ServerStatus class that prevents another server from running on the same system
     *
     * @author npien-purdue
     * @version November 17, 2024
     */
    private class ServerStatus extends Thread implements ServerStatusInterface {
        public int serverInt;

        public ServerStatus() throws IOException {
            BufferedReader br = null;
            try {
                File f = new File("Database/ServerStartedEnded");
                FileReader fr = new FileReader(f);
                br = new BufferedReader(fr);
                String serverFile = br.readLine();
                int serverFileInt;
                try {
                    if (serverFile != null)
                        serverFileInt = Integer.parseInt(serverFile);
                    else
                        serverFileInt = 0;
                } catch (NumberFormatException e) {
                    serverFileInt = 0;
                }
                serverInt = serverFileInt;
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                br.close();
            }
        }

        public void run() {
            File f;
            FileOutputStream fos2;
            PrintWriter bw2 = null;
            try {
                f = new File("Database/ServerStartedEnded");
                fos2 = new FileOutputStream(f);
                bw2 = new PrintWriter(fos2);
                while (true) {
                    synchronized (O) {
                        String serverInt3 = "" + serverInt;
                        bw2.write(serverInt3);
                        bw2.flush();
                    }
                    sleep(1000);
                    serverInt++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                bw2.close();

            }
        }
    }

    /**
     * A ClientHandler class that allows for a server process information
     * from multiple clients
     *
     * @author npien-purdue
     * @version November 17, 2024
     */
    private class ClientHandler extends Thread implements ClientHandlerInterface {
        private final Socket clientSocket;
        private ObjectOutputStream out;
        private ObjectInputStream in;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try {
                // Initialize I/O streams
//                out = new PrintWriter(clientSocket.getOutputStream(), true);
//                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new ObjectOutputStream(clientSocket.getOutputStream());
                in = new ObjectInputStream(clientSocket.getInputStream());
                String clientMessage;
                // Read and respond to messages from the client
                while (true) {
                    try {
                        clientMessage = "" + in.readObject();
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    if (clientMessage != null) {

                        // Respond to the client
                        out.writeObject("Server received: " + clientMessage);
                        if (clientMessage.startsWith("sendDM:")) {
                            clientMessage = clientMessage.substring(clientMessage.indexOf(":") + 1);
                            String username1 = clientMessage.substring(0, clientMessage.indexOf(":"));
                            clientMessage = clientMessage.substring(clientMessage.indexOf(":") + 1);
                            String username2 = clientMessage.substring(0, clientMessage.indexOf(":"));
                            String message = clientMessage.substring(clientMessage.indexOf(":") + 1);
                            User u1 = db.getUser(username1);
                            User u2 = db.getUser(username2);
                            if (db.getBlockedUsers(u2.getUserID()).contains(u1.getUserID()))
                                out.writeObject("sendDM:BLOCKED");
                            else if ((!u1.isFriendOnly() && !u2.isFriendOnly())
                                    || db.getFriends(u2.getUserID()).contains(u1.getUserID())) {
                                db.addDM(u1, u2, message);
                                out.writeObject("sendDM:SUCCESS");
                            } else
                                out.writeObject("sendDM:FRIENDONLY");
                        } else if (clientMessage.startsWith("removeDM:")) {
                            clientMessage = clientMessage.substring(clientMessage.indexOf(":") + 1);
                            String username1 = clientMessage.substring(0, clientMessage.indexOf(":"));
                            clientMessage = clientMessage.substring(clientMessage.indexOf(":") + 1);
                            String username2 = clientMessage.substring(0, clientMessage.indexOf(":"));
                            String id = clientMessage.substring(clientMessage.indexOf(":") + 1);
                            int messageID = Integer.parseInt(id);
                            User u1 = db.getUser(username1);
                            User u2 = db.getUser(username2);
                            db.removeDM(u1, u2, messageID);
                            out.writeObject("removeDM:SUCCESS");
                        } else if (clientMessage.startsWith("getDMChat:")) {
                            clientMessage = clientMessage.substring(clientMessage.indexOf(":") + 1);
                            String username1 = clientMessage.substring(0, clientMessage.indexOf(":"));
                            String username2 = clientMessage.substring(clientMessage.indexOf(":") + 1);
                            User u1 = db.getUser(username1);
                            User u2 = db.getUser(username2);
                            ArrayList<String> chat = db.getDMChat(u1, u2);
                            if (chat == null)
                                out.writeObject("getDMChat:NONE");
                            else {
                                out.writeObject("getDMChat:SUCCESS");
                                for (int i = 0; i < chat.size(); i++) {
                                    String c = chat.get(i);
                                    String u = db.getUserName(Integer.parseInt(
                                            c.substring(c.indexOf("*") + 1, c.indexOf(":"))));
                                    String k = c.substring(c.indexOf("$") + 1, c.indexOf("*"));
                                    String h = k + "$" + u + ": " + c.substring(c.indexOf(":") + 1);
                                    out.writeObject(h);
                                }
                                out.writeObject("getDMChat:END");
                            }
                        } else if (clientMessage.startsWith("editUserInfo:")) {
                            clientMessage = clientMessage.substring(clientMessage.indexOf(":") + 1);
                            String newUsername = clientMessage.substring(0, clientMessage.indexOf(":"));
                            clientMessage = clientMessage.substring(clientMessage.indexOf(":") + 1);
                            String password = clientMessage.substring(0, clientMessage.indexOf(":"));
                            String friendRestricted = clientMessage.substring(clientMessage.indexOf(":") + 1);
                            String bio = null;
                            String oldUsername = "";
                            try {
                                bio = "" + in.readObject();
                                oldUsername = "" + in.readObject();
                            } catch (ClassNotFoundException e) {
                                throw new RuntimeException(e);
                            }

                            boolean restricted = Boolean.parseBoolean(friendRestricted);
                            int userID = db.getUserID(oldUsername);
                            ArrayList<Integer> friends = db.getFriends(userID);
                            ArrayList<Integer> blocked = db.getBlockedUsers(userID);
                            User u = new User(newUsername, password, userID, bio, restricted);
                            u.setFriends(friends);
                            u.setBlockedUsers(blocked);
                            boolean success = true;
                            try {
                                u.setUsername(newUsername);
                            } catch (InvalidUserNamePasswordException e) {
                                out.writeObject("editUserInfo:INVALID USERNAME");
                                success = false;
                            }
                            if (success) {
                                try {
                                    u.setPassword(password);
                                } catch (InvalidUserNamePasswordException e) {
                                    out.writeObject("editUserInfo:INVALID PASSWORD");
                                    success = false;
                                }
                            }
                            if (success) {
                                db.editUserInfo(u);
                                out.writeObject("editUserInfo:SUCCESS");
                            }
                        } else if (clientMessage.startsWith("requestFriend:")) {
                            clientMessage = clientMessage.substring(clientMessage.indexOf(":") + 1);
                            String requester = clientMessage.substring(0, clientMessage.indexOf(":"));
                            String requested = clientMessage.substring(clientMessage.indexOf(":") + 1);

                            int requesterID = db.getUserID(requester);
                            int requestedID = db.getUserID(requested);

                            User u = db.getUser(requested);
                            if (!u.getBlockedUsers().contains(requesterID)) {
                                u.addRequested(requesterID);
                                db.editUserInfo(u);
                                out.writeObject("requestFriend:SUCCESS");
                            } else {
                                out.writeObject("requestFriend:BLOCKED");
                            }
                        } else if (clientMessage.startsWith("acceptFriend:")) {
                            clientMessage = clientMessage.substring(clientMessage.indexOf(":") + 1);
                            String requested = clientMessage.substring(0, clientMessage.indexOf(":"));
                            String requester = clientMessage.substring(clientMessage.indexOf(":") + 1);

                            int requesterID = db.getUserID(requester);
                            int requestedID = db.getUserID(requested);

                            User requestedU = db.getUser(requested);
                            User requesterU = db.getUser(requester);

                            requestedU.removeRequested(requesterID);
                            requestedU.addFriend(requesterU);
                            requesterU.addFriend(requestedU);

                            db.editUserInfo(requestedU);
                            db.editUserInfo(requesterU);

                            out.writeObject("acceptFriend:SUCCESS");
                        } else if (clientMessage.startsWith("rejectFriend:")) {
                            clientMessage = clientMessage.substring(clientMessage.indexOf(":") + 1);
                            String requested = clientMessage.substring(0, clientMessage.indexOf(":"));
                            String requester = clientMessage.substring(clientMessage.indexOf(":") + 1);

                            int requesterID = db.getUserID(requester);
                            User requestedU = db.getUser(requested);
                            requestedU.removeRequested(requesterID);

                            db.editUserInfo(requestedU);
                            out.writeObject("rejectFriend:SUCCESS");
                        } else if (clientMessage.startsWith("addBlocked:")) {
                            clientMessage = clientMessage.substring(clientMessage.indexOf(":") + 1);
                            String username1 = clientMessage.substring(0, clientMessage.indexOf(":"));
                            String username2 = clientMessage.substring(clientMessage.indexOf(":") + 1);

                            int user1ID = db.getUserID(username1);
                            int user2ID = db.getUserID(username2);

                            User u1 = db.getUser(username1);
                            User u2 = db.getUser(username2);

                            u1.blockUser(u2);
                            u1.removeRequested(user2ID);
                            u2.removeRequested(user1ID);
                            u1.removeFriend(u2);
                            u2.removeFriend(u1);
                            db.editUserInfo(u1);
                            db.editUserInfo(u2);
                            out.writeObject("addBlocked:SUCCESS");
                        } else if (clientMessage.startsWith("removeBlocked:")) {
                            clientMessage = clientMessage.substring(clientMessage.indexOf(":") + 1);
                            String username1 = clientMessage.substring(0, clientMessage.indexOf(":"));
                            String username2 = clientMessage.substring(clientMessage.indexOf(":") + 1);

                            int user1ID = db.getUserID(username1);
                            int user2ID = db.getUserID(username2);

                            User u1 = db.getUser(username1);
                            User u2 = db.getUser(username2);

                            u1.unblockUser(u2);

                            db.editUserInfo(u1);
                            out.writeObject("removeBlocked:SUCCESS");
                        } else if (clientMessage.startsWith("addUser:")) {
                            clientMessage = clientMessage.substring(clientMessage.indexOf(":") + 1);
                            String username = clientMessage.substring(0, clientMessage.indexOf(":"));
                            clientMessage = clientMessage.substring(clientMessage.indexOf(":") + 1);
                            String password = clientMessage.substring(0, clientMessage.indexOf(":"));
                            String friendRestricted = clientMessage.substring(clientMessage.indexOf(":") + 1);
                            String bio = null;
                            try {
                                bio = "" + in.readObject();
                            } catch (ClassNotFoundException e) {
                                throw new RuntimeException(e);
                            }

                            User u = new User(username, password, 0, bio, Boolean.parseBoolean(friendRestricted));
                            boolean success = true;
                            try {
                                u.setUsername(username);
                            } catch (InvalidUserNamePasswordException e) {
                                out.writeObject("addUser:INVALID USERNAME");
                                success = false;
                            }
                            if (success) {
                                try {
                                    u.setPassword(password);
                                } catch (InvalidUserNamePasswordException e) {
                                    out.writeObject("addUser:INVALID PASSWORD");
                                    success = false;
                                }
                            }
                            if (success) {
                                db.addUser(u);
                                out.writeObject("addUser:SUCCESS");
                            }
                        } else if (clientMessage.startsWith("userSearch:")) {
                            String input = clientMessage.substring(clientMessage.indexOf(":") + 1);
                            ArrayList<String> all = db.getAllUserName();
                            String output = "";
                            for (String s : all) {
                                if (s.toLowerCase().contains(input.toLowerCase()))
                                    output = output + s + ",";
                            }
                            if (!output.isEmpty()) {
                                output = output.substring(0, output.length() - 1);
                                out.writeObject("userSearch:SUCCESS");
                                out.writeObject(output);
                            } else {
                                out.writeObject("userSearch:NO RESULTS");
                            }
                        } else if (clientMessage.startsWith("getProfilePicture:")) {
                            String username = clientMessage.substring(clientMessage.indexOf(":") + 1);
                            int userID = db.getUserID(username);
                            try {
                                byte[] pic = db.getProfilePicture(userID);
                                out.writeObject("getProfilePicture:SUCCESS");
                                out.writeObject("" + pic.length);
                                out.flush();
                                // Send the actual image
                                out.writeObject(pic);
                                out.flush();
                            } catch (FileNotFoundException e) {
                                out.writeObject("getProfilePicture:NONE");
                            }
                        } else if (clientMessage.startsWith("saveProfilePicture:")) {
                            String username = clientMessage.substring(clientMessage.indexOf(":") + 1);
                            int userID = db.getUserID(username);
                            int length = 0;
                            try {
                                length = Integer.parseInt("" + in.readObject());
                            } catch (ClassNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                            byte[] pic = null;
                            if (length == 0)
                                db.saveProfilePicture(userID, new byte[0]);
                            else if (length > 0) {
                                try {
                                    pic = (byte[]) (in.readObject()); // read the message
                                } catch (ClassNotFoundException e) {
                                    throw new RuntimeException(e);
                                }
                                db.saveProfilePicture(userID, pic);
                            }
                            out.writeObject("saveProfilePicture:SUCCESS");
                        } else if (clientMessage.startsWith("seeUserInfo:")) {
                            clientMessage = clientMessage.substring(clientMessage.indexOf(":") + 1);
                            String username = clientMessage.substring(0, clientMessage.indexOf(":"));
                            String myself = clientMessage.substring(clientMessage.indexOf(":") + 1);
                            int userID = db.getUserID(username);
                            int forMyself = Integer.parseInt(myself);
                            User u = db.getUser(username);
                            if (forMyself == 0) {
                                out.writeObject("seeUserInfo:MYSELF");
                                out.writeObject(u.getUsername());
                                out.writeObject(u.getPassword());
                                out.writeObject(u.isFriendOnly() + "");
                                out.writeObject(u.getBio());
                                String friends = "";
                                for (int s : u.getFriends()) {
                                    friends += db.getUserName(s) + ",";
                                }
                                if (!friends.isEmpty()) {
                                    friends = friends.substring(0, friends.length() - 1);
                                } else {
                                    friends = "NONE";
                                }
                                out.writeObject(friends);
                                String blocked = "";
                                for (int s : u.getBlockedUsers()) {
                                    blocked += db.getUserName(s) + ",";
                                }
                                if (!blocked.isEmpty()) {
                                    blocked = blocked.substring(0, blocked.length() - 1);
                                } else {
                                    blocked = "NONE";
                                }
                                out.writeObject(blocked);
                                String requested = "";
                                for (int s : u.getRequested()) {
                                    requested += db.getUserName(s) + ",";
                                }
                                if (!requested.isEmpty()) {
                                    requested = requested.substring(0, requested.length() - 1);
                                } else {
                                    requested = "NONE";
                                }
                                out.writeObject(requested);
                            } else if (forMyself == 1) {
                                out.writeObject("seeUserInfo:FRIEND");
                                out.writeObject(u.getUsername());
                                out.writeObject(u.getBio());
                                String requested = "";
                                for (int s : u.getRequested()) {
                                    requested += db.getUserName(s) + ",";
                                }
                                if (!requested.isEmpty()) {
                                    requested = requested.substring(0, requested.length() - 1);
                                } else {
                                    requested = "NONE";
                                }
                                out.writeObject(requested);
                                String blocked = "";
                                for (int s : u.getBlockedUsers()) {
                                    blocked += db.getUserName(s) + ",";
                                }
                                if (!blocked.isEmpty()) {
                                    blocked = blocked.substring(0, blocked.length() - 1);
                                } else {
                                    blocked = "NONE";
                                }
                                out.writeObject(blocked);
                            } else if (forMyself == 2) {
                                out.writeObject("seeUserInfo:STRANGER");
                                out.writeObject(u.getUsername());
                                out.writeObject(u.getBio());
                            }
                        } else if (clientMessage.startsWith("logIn:")) {
                            clientMessage = clientMessage.substring(clientMessage.indexOf(":") + 1);
                            String username = clientMessage.substring(0, clientMessage.indexOf(":"));
                            String password = clientMessage.substring(clientMessage.indexOf(":") + 1);
                            int userID = db.getUserID(username);
                            ArrayList<String> all = db.getAllUserName();
                            if (!all.contains(username)) {
                                out.writeObject("logIn:USERNAME NOT FOUND");
                            } else if (!db.getPassword(userID).equals(password)) {
                                out.writeObject("logIn:INCORRECT PASSWORD");
                            } else {
                                out.writeObject("logIn:SUCCESS");
                            }
                        } else if (clientMessage.startsWith("removeUser:")) {
                            String username = clientMessage.substring(clientMessage.indexOf(":") + 1);
                            int userID = db.getUserID(username);
                            db.removeUser(userID);
                            out.writeObject("removeUser:SUCCESS");
                        } else if (clientMessage.startsWith("removeFriend:")) {
                            clientMessage = clientMessage.substring(clientMessage.indexOf(":") + 1);
                            String user = clientMessage.substring(0, clientMessage.indexOf(":"));
                            String saidFriend = clientMessage.substring(clientMessage.indexOf(":") + 1);
                            User user1 = db.getUser(user);
                            User user2 = db.getUser(saidFriend);
                            user1.removeFriend(user2);
                            user2.removeFriend(user1);
                            db.editUserInfo(user1);
                            out.writeObject("removeFriend:SUCCESS");
                        } else if (clientMessage.startsWith("getPublicUsers:")) {
                            ArrayList<String> all = db.getAllUserName();
                            for (String s : all) {
                                if (db.getFriendRestricted(db.getUserID(s))) {
                                    all.remove(s);
                                }
                            }
                            String pub = "";
                            for (String s : all) {
                                pub += s + ",";
                            }
                            if (!pub.isEmpty()) {
                                pub = pub.substring(0, pub.length() - 1);
                            } else {
                                pub = "";
                            }
                            out.writeObject("getPublicUsers:" + pub);
                        } else if (clientMessage.startsWith("getBlockedByOther:")) {
                            int e = db.getUserID(clientMessage.substring(clientMessage.indexOf(":") + 1));
                            ArrayList<String> all = db.getAllBlockedByOther(e);
                            String ack = "";
                            for (String ret : all) {
                                ack += ret + ",";
                            }
                            if (!ack.isEmpty()) {
                                ack = ack.substring(0, ack.length() - 1);
                            } else {
                                ack = "";
                            }
                            out.writeObject("getBlockedByOther:" + ack);
                        } else if (clientMessage.startsWith("closeSocket")) {
                            break;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
