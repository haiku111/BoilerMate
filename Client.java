package src;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * A Client class that starts the client side of the client to server connection and manages data passed
 * in by the User
 *
 * @author npien-purdue klakoji rpadye
 * @version December 7, 2024
 */

public class Client extends Thread implements ClientInterface {
    private ObjectOutputStream pw;
    private ObjectInputStream br;
    GUI g;
    Socket socket;

    public static void main(String[] args) throws IOException, InterruptedException {
        //Server start
        String serverFile;
        String serverFile2;
        File f = new File("Database/ServerStartedEnded");
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        serverFile = br.readLine();
        br.close();
        sleep(2000);
        FileReader fr2 = new FileReader(f);
        BufferedReader br2 = new BufferedReader(fr2);
        serverFile2 = br2.readLine();
        br2.close();
        if (serverFile == null || serverFile.equals(serverFile2)) {
            JOptionPane.showMessageDialog(new JFrame("Warning"), "Server has not been started. Please start server!",
                    "Server Not Started", JOptionPane.WARNING_MESSAGE);
        } else {
            //Client start
            Client c = new Client();
            c.start();
        }
        br.close();
    }

    public void run() {
        try {
            socket = new Socket("localhost", 4242);
            pw = new ObjectOutputStream(socket.getOutputStream());
            br = new ObjectInputStream(socket.getInputStream());

            // Ensure GUI is started on the EDT
            SwingUtilities.invokeLater(() -> {
                g = new GUI(this);
                g.run();
            });

            // Start server response listener
            new Thread(() -> {
                try {
                    String serverResponse;
                    while (true) {
                        try {
                            serverResponse = "" + br.readObject();
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        if (!serverResponse.isEmpty()) {
                            final String response = serverResponse; // For use in the EDT
                            Object lock = new Object(); // Create a lock object
                            synchronized (lock) {
                                SwingUtilities.invokeLater(() -> {
                                    try {
                                        handleServerResponse(response);
                                    } catch (IOException | ClassNotFoundException e) {
                                        throw new RuntimeException(e);
                                    } finally {
                                        synchronized (lock) {
                                            lock.notify(); // Notify the waiting thread
                                        }
                                    }
                                });
                                lock.wait(); // Wait for the invokeLater task to complete
                            }
                        }
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleServerResponse(String serverResponse) throws IOException, ClassNotFoundException {
        // Update GUI safely based on the server response
        if (serverResponse != null) {
            if (serverResponse.startsWith("sendDM")) {
                if (serverResponse.endsWith("BLOCKED")) {
                    g.sendDM(serverResponse);
                    //ask gui to send warning message "blocked"
                } else if (serverResponse.endsWith("SUCCESS")) {
                    g.sendDM(serverResponse);
                    //ask gui to get DMChat and redraw
                } else if (serverResponse.endsWith("FRIENDONLY")) {
                    g.sendDM(serverResponse);
                    //ask gui to send warning message "need to be friends to DM"
                }
            } else if (serverResponse.startsWith("removeDM")) {
                if (serverResponse.endsWith("SUCCESS")) {
                    g.removeDM(serverResponse);
                }
            } else if (serverResponse.startsWith("getDMChat")) {
                if (serverResponse.endsWith("NONE")) {
                    g.getDMChat(serverResponse, new ArrayList<>());
                    //tell gui no previous chat history
                } else if (serverResponse.endsWith("SUCCESS")) {
                    ArrayList<String> chat = new ArrayList<>();
                    String text = "";
                    text = br.readObject() + "";
                    while (!(text.equals("getDMChat:END"))) {
                        chat.add(text);
                        text = "" + br.readObject();
                    }
                    g.getDMChat(serverResponse, chat);
                    //ask gui to get print chat
                }
            } else if (serverResponse.startsWith("editUserInfo")) {
                g.editUserInfo(serverResponse);
            } else if (serverResponse.startsWith("requestFriend")) {
                g.requestFriend(serverResponse);
            } else if (serverResponse.startsWith("acceptFriend")) {
                if (serverResponse.endsWith("SUCCESS")) {
                    g.acceptFriend(serverResponse);
                    //tell gui to send info message successful accept
                }
            } else if (serverResponse.startsWith("rejectFriend")) {
                if (serverResponse.endsWith("SUCCESS")) {
                    g.rejectFriend(serverResponse);
                    //tell gui to send info message successful reject
                }
            } else if (serverResponse.startsWith("addBlocked")) {
                if (serverResponse.endsWith("SUCCESS")) {
                    g.addBlocked(serverResponse);
                }
            } else if (serverResponse.startsWith("removeBlocked")) {
                if (serverResponse.endsWith("SUCCESS")) {
                    g.removeBlocked(serverResponse);
                    //tell gui to send info message successful block removal
                }
            } else if (serverResponse.startsWith("addUser")) {
                g.addUser(serverResponse);
            } else if (serverResponse.startsWith("userSearch")) {
                if (serverResponse.endsWith("SUCCESS")) {
                    String searchOutput = "" + br.readObject();
                    String[] info = searchOutput.split(",");
                    g.userSearch(serverResponse, info);
                    //tell gui to display searchOutput
                } else if (serverResponse.endsWith("NO RESULTS")) {
                    //tell gui to display no results found
                    g.userSearch(serverResponse, new String[0]);
                }
            } else if (serverResponse.startsWith("getProfilePicture")) {
                if (serverResponse.endsWith("SUCCESS")) {
                    int length = 0;
                    try {
                        length = Integer.parseInt("" + br.readObject());
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    byte[] pic = null;
                    if (length > 0) {
                        try {
                            pic = (byte[]) (br.readObject()); // read the message
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    g.getProfilePicture(pic);
                } else if (serverResponse.endsWith("NONE")) {
                    g.getProfilePicture(null);
                }
            } else if (serverResponse.startsWith("saveProfilePicture")) {
                g.saveProfilePicture(serverResponse);
            } else if (serverResponse.startsWith("seeUserInfo")) {
                if (serverResponse.endsWith("MYSELF")) {
                    String username = "" + br.readObject();
                    String password = "" + br.readObject();
                    String isFriendOnly = "" + br.readObject();
                    String bio = "" + br.readObject();
                    String friends = "" + br.readObject();
                    String blocked = "" + br.readObject();
                    String requested = "" + br.readObject();
                    String[] info = {username, password, isFriendOnly, bio, friends, blocked, requested};
                    g.seeUserInfo(serverResponse, info);
                    //tell gui to display the info above
                } else if (serverResponse.endsWith("FRIEND")) {
                    String username = "" + br.readObject();
                    String bio = "" + br.readObject();
                    String requested = "" + br.readObject();
                    String blocked = "" + br.readObject();
                    String[] info = {username, bio, requested, blocked};
                    g.seeUserInfo(serverResponse, info);
                    //tell gui to display the info above
                } else if (serverResponse.endsWith("STRANGER")) {
                    String username = "" + br.readObject();
                    String bio = "" + br.readObject();
                    String[] info = {username, bio};
                    g.seeUserInfo(serverResponse, info);
                    //tell gui to display the info above
                }
            } else if (serverResponse.startsWith("logIn")) {
                g.logIn(serverResponse);
            } else if (serverResponse.startsWith("removeUser")) {
                if (serverResponse.endsWith("SUCCESS")) {

                    //tell gui to send info user deletion success, log out
                }
            } else if (serverResponse.startsWith("removeFriend")) {
                if (serverResponse.endsWith("SUCCESS")) {
                    g.removeFriend(serverResponse);
                    //tell gui to send info remove friend success
                }
            } else if (serverResponse.startsWith("getPublicUsers:")) {
                g.getPublicUsers(serverResponse.substring(serverResponse.indexOf(':') + 1));
            } else if (serverResponse.startsWith("getBlockedByOther:")) {
                g.getBlockedByOther(serverResponse.substring(serverResponse.indexOf(':') + 1));
            }
        }
    }

    public ObjectOutputStream getPw() {
        return pw;
    }

    public ObjectInputStream getBr() {
        return br;
    }

    public void setPw(ObjectOutputStream pw) {
        this.pw = pw;
    }

    public void setBr(ObjectInputStream br) {
        this.br = br;
    }

    public void sendMessage(String message) {
        if (pw != null) {
            try {
                pw.writeObject(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void sendDM(String message, String username1, String username2) {
        if (pw != null) {
            try {
                pw.writeObject("sendDM:" + username1 + ":" + username2 + ":" + message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void removeDM(int id, String username1, String username2) {
        // first request full chat, then identify right message id, then this method
        // or when deleting ask the user for which message number to delete, calculate exact message id from that
        if (pw != null) {
            try {
                pw.writeObject("removeDM:" + username1 + ":" + username2 + ":" + id);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void getDMChat(String username1, String username2) {
        if (pw != null) {
            try {
                pw.writeObject("getDMChat:" + username1 + ":" + username2);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void editUserInfo(String oldUsername, String newUsername,
                             String password, String bio, boolean friendRestricted) {
        if (pw != null) {
            try {
                pw.writeObject("editUserInfo:" + newUsername + ":" + password + ":" + friendRestricted);
                pw.writeObject(bio);
                pw.writeObject(oldUsername);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void requestFriend(String requester, String requested) {
        if (pw != null) {
            try {
                pw.writeObject("requestFriend:" + requester + ":" + requested);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void acceptFriend(String requested, String requester) {
        if (pw != null) {
            try {
                pw.writeObject("acceptFriend:" + requested + ":" + requester);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void rejectFriend(String requested, String requester) {
        if (pw != null) {
            try {
                pw.writeObject("rejectFriend:" + requested + ":" + requester);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void addBlocked(String username1, String username2) {
        if (pw != null) {
            try {
                pw.writeObject("addBlocked:" + username1 + ":" + username2);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void removeBlocked(String username1, String username2) {
        if (pw != null) {
            try {
                pw.writeObject("removeBlocked:" + username1 + ":" + username2);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void addUser(String username, String password, String bio, boolean friendRestricted) {
        if (pw != null) {
            try {
                pw.writeObject("addUser:" + username + ":" + password + ":" + friendRestricted);
                pw.writeObject(bio);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void userSearch(String input) {
        if (pw != null) {
            try {
                pw.writeObject("userSearch:" + input);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void getProfilePicture(String username) {
        if (pw != null) {
            try {
                pw.writeObject("getProfilePicture:" + username);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void saveProfilePicture(String username, byte[] image) throws IOException {
        if (pw != null) {
            pw.writeObject("saveProfilePicture:" + username);
            pw.writeObject("" + image.length);
            pw.flush();

            // Send the actual image
            pw.writeObject(image);
            pw.flush();
        }
    }

    public void seeUserInfo(String username, int privacyLevel) {
        if (pw != null) {
            try {
                pw.writeObject("seeUserInfo:" + username + ":" + privacyLevel);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void logIn(String username, String password) {
        if (pw != null) {
            try {
                pw.writeObject("logIn:" + username + ":" + password);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void removeUser(String username) {
        if (pw != null) {
            try {
                pw.writeObject("removeUser:" + username);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void removeFriend(String user, String saidFriend) {
        if (pw != null) {
            try {
                pw.writeObject("removeFriend:" + user + ":" + saidFriend);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void getPublicUsers() {
        if (pw != null) {
            try {
                pw.writeObject("getPublicUsers:");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void getBlockedByOther(String user) {
        if (pw != null) {
            try {
                pw.writeObject("getBlockedByOther:" + user);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void closeSocket() throws IOException {
        if (pw != null) {
            pw.writeObject("closeSocket");
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            //
        }

        pw.close();
        br.close();
        socket.close();
    }
}
