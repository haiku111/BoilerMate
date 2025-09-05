package src;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Stream;

import static org.junit.Assert.*;

/**
 * A RunLocalTestCase class which runs test cases for
 * each method/constructor in the User and Database class.
 * Also checks to see whether the classes are implementing the right things.
 *
 * @author klakoji npien-purdue rpadhye
 * @version December 7, 2024
 */

@RunWith(Enclosed.class)
public class RunLocalTestCase {
    public static void main(String[] args) {
        System.out.println("\nTesting User Class...");
        Result result = JUnitCore.runClasses(TestUser.class);
        System.out.printf("User Class Test Count: %d.\n", result.getRunCount());
        if (result.wasSuccessful()) {
            System.out.print("Excellent - all User Class tests ran successfully.\n");
        } else {
            System.out.printf("User Class Tests failed: %d.\n", result.getFailureCount());
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }

        System.out.println("\nTesting Database Class...");
        Result result2 = JUnitCore.runClasses(TestDatabase.class);
        System.out.printf("Database Class Test Count: %d.\n", result2.getRunCount());
        if (result2.wasSuccessful()) {
            System.out.print("Excellent - all Database Class tests ran successfully.\n");
        } else {
            System.out.printf("Database Class Tests failed: %d.\n", result2.getFailureCount());
            for (Failure failure : result2.getFailures()) {
                System.out.println(failure.toString());
            }
        }

        System.out.println("\nTesting Client Class...");
        Result result3 = JUnitCore.runClasses(TestClient.class);
        System.out.printf("Client Class Test Count: %d.\n", result3.getRunCount());
        if (result3.wasSuccessful()) {
            System.out.print("Excellent - all Client Class tests ran successfully.\n");
        } else {
            System.out.printf("Client Class Tests failed: %d.\n", result3.getFailureCount());
            for (Failure failure : result3.getFailures()) {
                System.out.println(failure.toString());
            }
        }

        System.out.println("\nTesting Server Class...");
        Result result4 = JUnitCore.runClasses(TestServer.class);
        System.out.printf("Server Class Test Count: %d.\n", result4.getRunCount());
        if (result4.wasSuccessful()) {
            System.out.print("Excellent - all Server Class tests ran successfully.\n");
        } else {
            System.out.printf("Server class Tests failed: %d.\n", result4.getFailureCount());
            for (Failure failure : result4.getFailures()) {
                System.out.println(failure.toString());
            }
        }

        System.out.println("\nTesting GUI Class...");
        Result result5 = JUnitCore.runClasses(TestServer.class);
        System.out.printf("GUI Class Test Count: %d.\n", result5.getRunCount());
        if (result5.wasSuccessful()) {
            System.out.print("Excellent - all GUI Class tests ran successfully.\n");
        } else {
            System.out.printf("GUI class Tests failed: %d.\n", result5.getFailureCount());
            for (Failure failure : result5.getFailures()) {
                System.out.println(failure.toString());
            }
        }
    }

    /**
     * A TestUser class which runs test cases for
     * each method/constructor in the User class.
     * Also checks to see whether the classes are implementing the right things.
     *
     * @author klakoji npien-purdue rpadhye
     * @version November 17, 2024
     */

    public static class TestUser {
        User user;
        String username;
        String password;
        int userID;
        String bio;
        User friend1;
        User friend2;
        User block1;
        User block2;
        User requested1;
        User requested2;
        ArrayList<Integer> friends;
        ArrayList<Integer> blocked;
        ArrayList<Integer> requested;

        public TestUser() {
            username = "testUser";
            password = "password123";
            userID = 1;
            bio = "This is a test bio.";
            friend1 = new User("friend1", "loginPassword", 2, "im a friend1", true);
            friend2 = new User("friend2", "loginPassword", 3, "im a friend2", false);
            block1 = new User("blocked1", "loginPassword", 4, "im blocked1", true);
            block2 = new User("blocked2", "loginPassword", 5, "im blocked2", false);
            requested1 = new User("requested1", "loginPassword", 6, "im requested1", true);
            requested2 = new User("requested2", "loginPassword", 7, "im requested2", false);
            friends = new ArrayList<>();
            blocked = new ArrayList<>();
            requested = new ArrayList<>();
            friends.add(friend1.getUserID());
            friends.add(friend2.getUserID());
            blocked.add(block1.getUserID());
            blocked.add(block2.getUserID());
            requested.add(requested1.getUserID());
            requested.add(requested2.getUserID());
            user = new User(username, password, userID, bio, true);
            user.setFriends(friends);
            user.setBlockedUsers(blocked);
            user.setRequested(requested);
        }

        @Test
        public void testUserConstructorAndToString() {
            // Expected output from the toString method.
            String expected = "UserID: 1\nUsername: testUser\nPassword: password123\n" +
                    "Receive DM from friends only: true\nBio: This is a test bio." + "\n" +
                    "Friends: 2, 3\nBlocked Users: 4, 5\nFriend Requests: 6, 7";

            // Retrieves the output from the toString() method
            String stuOut = user.toString();

            // Normalizes line endings and verifies correctness
            stuOut = stuOut.replace("\r\n", "\n");
            assertEquals("Error: Output does not match expected toString format",
                    expected.trim(), stuOut.trim());


            //No friends and no blocked user test
            expected = "UserID: 1\nUsername: testUser\nPassword: password123\n" +
                    "Receive DM from friends only: true\nBio: This is a test bio." + "\n" +
                    "Friends: \nBlocked Users: \nFriend Requests: ";

            // Retrieves the output from the toString() method
            user.setFriends(new ArrayList<>());
            user.setBlockedUsers(new ArrayList<>());
            user.setRequested(new ArrayList<>());
            stuOut = user.toString();

            // Normalizes line endings and verifies correctness
            stuOut = stuOut.replace("\r\n", "\n");
            assertEquals("Error: Output does not match expected toString format",
                    expected.trim(), stuOut.trim());


            //triple digit user id
            user.setUserID(234);
            expected = "UserID: 234\nUsername: testUser\nPassword: password123\n" +
                    "Receive DM from friends only: true\nBio: This is a test bio." + "\n" +
                    "Friends: \nBlocked Users: \nFriend Requests: ";

            // Retrieves the output from the toString() method
            stuOut = user.toString();

            // Normalizes line endings and verifies correctness
            stuOut = stuOut.replace("\r\n", "\n");
            assertEquals("Error: Output does not match expected toString format",
                    expected.trim(), stuOut.trim());


            //null field test
            user.setBio(null);
            expected = "UserID: 234\nUsername: testUser\nPassword: password123\n" +
                    "Receive DM from friends only: true\nBio: " + "\n" +
                    "Friends: \nBlocked Users: \nFriend Requests: ";

            // Retrieves the output from the toString() method
            stuOut = user.toString();

            // Normalizes line endings and verifies correctness
            stuOut = stuOut.replace("\r\n", "\n");
            assertEquals("Error: Output does not match expected toString format",
                    expected.trim(), stuOut.trim());
        }

        @Test(timeout = 1000)
        public void userClassDeclarationTest() {
            Class<?> clazz;
            int modifiers;
            Class<?> superclass;
            Class<?>[] superinterfaces;

            // Set the class being tested
            clazz = User.class;

            // Perform tests

            modifiers = clazz.getModifiers();

            superclass = clazz.getSuperclass();

            superinterfaces = clazz.getInterfaces();

            assertTrue("Ensure that `User` is `public`!", Modifier.isPublic(modifiers));

            assertFalse("Ensure that `User` is NOT `abstract`!", Modifier.isAbstract(modifiers));

            assertEquals("Ensure that `User` extends `Object`!", Object.class, superclass);

            assertEquals("Ensure that `User` implements the UserInterface", UserInterface.class,
                    superinterfaces[0]);
        }
    }

    /**
     * A TestDatabase class which runs test cases for
     * each method/constructor in the Database class.
     * Also checks to see whether the classes are implementing the right things.
     *
     * @author klakoji npien-purdue rpadhye
     * @version November 17, 2024
     */

    public static class TestDatabase {
        private final Database db;
        User user;
        String username;
        String password;
        int userID;
        String bio;
        User friend1;
        User friend2;
        User block1;
        User block2;
        User requested1;
        User requested2;
        ArrayList<Integer> friends;
        ArrayList<Integer> blocked;
        ArrayList<Integer> requested;

        public TestDatabase() {
            restoreTestFile();
            db = new Database("TestFiles/TestUserInfo", "TestFiles/TestDirectMessages",
                    "TestFiles/TestProfilePictures");
            username = "testUser";
            password = "password123";
            userID = 7;
            bio = "This is a test bio.";
            friend1 = new User("friend1", "loginPassword", 10, "im a friend1", true);
            friend2 = new User("friend2", "loginPassword", 11, "im a friend2", false);
            block1 = new User("blocked1", "loginPassword", 12, "im blocked1", true);
            block2 = new User("blocked2", "loginPassword", 13, "im blocked2", false);
            requested1 = new User("requested1", "loginPassword", 14, "im requested1",
                    true);
            requested2 = new User("requested2", "loginPassword", 15, "im requested2",
                    false);
            friends = new ArrayList<>();
            blocked = new ArrayList<>();
            requested = new ArrayList<>();
            friends.add(friend1.getUserID());
            friends.add(friend2.getUserID());
            blocked.add(block1.getUserID());
            blocked.add(block2.getUserID());
            requested.add(requested1.getUserID());
            requested.add(requested2.getUserID());
            user = new User(username, password, userID, bio, true);
            user.setFriends(friends);
            user.setBlockedUsers(blocked);
            user.setRequested(requested);
        }

        /**
         * Sets up the test files with test data previously written
         */
        public void restoreTestFile() {
            // Restores each test text file by using their respective backups
            ArrayList<String> dM = new ArrayList<>();
            ArrayList<String> uInfo = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader("TestFiles/TestDirectMessagesBackUp"));
                 PrintWriter pw = new PrintWriter(new FileWriter("TestFiles/TestDirectMessages", false));
                 BufferedReader br2 = new BufferedReader(new FileReader("TestFiles/TestUserInfoBackUp"));
                 PrintWriter pw2 = new PrintWriter(new FileWriter("TestFiles/TestUserInfo", false))) {

                while (true) {
                    String line = br.readLine();
                    if (line == null) break;
                    dM.add(line);
                }
                String updatedDM = "";
                for (String line : dM)
                    updatedDM += line + "\n";
                updatedDM = updatedDM.substring(0, updatedDM.length() - 1);
                pw.print(updatedDM);

                while (true) {
                    String line = br2.readLine();
                    if (line == null) break;
                    uInfo.add(line);
                }
                String updatedUser = "";
                for (String line : uInfo)
                    updatedUser += line + "\n";
                updatedUser = updatedUser.substring(0, updatedUser.length() - 1);
                pw2.print(updatedUser);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Code from now restores the TestProfilePictures directory by using contents in TestProfilePicturesBackUp

            Path source = Path.of("TestFiles/TestProfilePicturesBackUp");
            Path destination = Path.of("TestFiles/TestProfilePictures");

            // Delete destination directory contents
            if (Files.exists(destination)) {
                try (Stream<Path> walkStream = Files.walk(destination)) {
                    walkStream.sorted(Comparator.reverseOrder())
                            .forEach(path -> {
                                try {
                                    Files.delete(path);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                Files.createDirectories(destination);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Copy backup to destination
            try (Stream<Path> walkStream = Files.walk(source)) {
                walkStream.forEach(sourcePath -> {
                    Path targetPath = destination.resolve(source.relativize(sourcePath));
                    try {
                        Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Test(timeout = 1000)
        public void databaseClassDeclarationTest() {
            Class<?> clazz;
            int modifiers;
            Class<?> superclass;
            Class<?>[] superinterfaces;

            // Set the class being tested
            clazz = Database.class;

            // Perform tests

            modifiers = clazz.getModifiers();

            superclass = clazz.getSuperclass();

            superinterfaces = clazz.getInterfaces();

            assertTrue("Ensure that `Database` is `public`!", Modifier.isPublic(modifiers));

            assertFalse("Ensure that `Database` is NOT `abstract`!", Modifier.isAbstract(modifiers));

            assertEquals("Ensure that `Database` extends `Object`!", Object.class, superclass);

            assertEquals("Ensure that `Database` implements the DatabaseInterface", DatabaseInterface.class,
                    superinterfaces[0]);
        }

        @Test
        public void testAddUser() {
            restoreTestFile();
            db.addUser(user);
            String actualDBOutput = "";
            try (BufferedReader br = new BufferedReader(new FileReader("TestFiles/TestUserInfo"))) {
                while (true) {
                    String line = br.readLine();
                    if (line == null)
                        break;
                    actualDBOutput = actualDBOutput + line + "\n";
                }
                actualDBOutput = actualDBOutput.substring(0, actualDBOutput.length() - 1);
            } catch (Exception e) {
                e.printStackTrace();
            }

            String expectedDBOutput = "1,bob,hello123,true\n" +
                    "I am bob.\n" +
                    "2,3,4\n" +
                    "5,6,7\n" +
                    "8\n" +
                    "2,steve,123hello,false\n" +
                    "I am steve!@#! lol\n" +
                    "4,5,6\n" +
                    "23,245,4\n" +
                    "111\n" +
                    "3,pat,u2u24odoe,true\n" +
                    "pat here. nice to meet you!\n" +
                    "1,2,3\n" +
                    "232,25,41\n" +
                    "NONE\n" +
                    "4,tim,loginPassword,false\n" +
                    "sad :(\n" +
                    "NONE\n" +
                    "23,245,4\n" +
                    "12\n" +
                    "5,thomas,u2u24o2id,true\n" +
                    "wheeeee #friendly\n" +
                    "1,2,3\n" +
                    "NONE\n" +
                    "134\n" +
                    "6,ron,nthoh2424,false\n" +
                    "no friends, no enemies\n" +
                    "NONE\n" +
                    "NONE\n" +
                    "NONE\n" +
                    "7,testUser,password123,true\n" +
                    "This is a test bio.\n" +
                    "10,11\n" +
                    "12,13\n" +
                    "14,15";
            restoreTestFile();
            assertEquals("Error: Output does not match expected string output",
                    expectedDBOutput, actualDBOutput);


            // test AddUser With Empty Bio
            restoreTestFile();

            // Add a user with an empty bio
            User userWithEmptyBio = new User("noBioUser", "emptyPass", 7,
                    "", true);
            db.addUser(userWithEmptyBio);

            actualDBOutput = "";
            try (BufferedReader br = new BufferedReader(new FileReader("TestFiles/TestUserInfo"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    actualDBOutput += line + "\n";
                }
                actualDBOutput = actualDBOutput.substring(0, actualDBOutput.length() - 1);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Expected output with new user ID 8 having an empty bio
            expectedDBOutput = "1,bob,hello123,true\n" +
                    "I am bob.\n" +
                    "2,3,4\n" +
                    "5,6,7\n" +
                    "8\n" +
                    "2,steve,123hello,false\n" +
                    "I am steve!@#! lol\n" +
                    "4,5,6\n" +
                    "23,245,4\n" +
                    "111\n" +
                    "3,pat,u2u24odoe,true\n" +
                    "pat here. nice to meet you!\n" +
                    "1,2,3\n" +
                    "232,25,41\n" +
                    "NONE\n" +
                    "4,tim,loginPassword,false\n" +
                    "sad :(\n" +
                    "NONE\n" +
                    "23,245,4\n" +
                    "12\n" +
                    "5,thomas,u2u24o2id,true\n" +
                    "wheeeee #friendly\n" +
                    "1,2,3\n" +
                    "NONE\n" +
                    "134\n" +
                    "6,ron,nthoh2424,false\n" +
                    "no friends, no enemies\n" +
                    "NONE\n" +
                    "NONE\n" +
                    "NONE\n" +
                    "7,noBioUser,emptyPass,true\n" +
                    "\n" +
                    "NONE\n" +
                    "NONE\n" +
                    "NONE";

            restoreTestFile();
            assertEquals("Error: Output does not match expected output for user with empty bio",
                    expectedDBOutput, actualDBOutput);


            // test AddUser With Special Characters In Name And Bio
            restoreTestFile();

            // Add a user with special characters in the loginUsername and bio
            User specialCharUser = new User("special@User!", "spPass123", 7,
                    "Bio with !@#$%^&*() special characters", true);
            db.addUser(specialCharUser);

            actualDBOutput = "";
            try (BufferedReader br = new BufferedReader(new FileReader("TestFiles/TestUserInfo"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    actualDBOutput += line + "\n";
                }
                actualDBOutput = actualDBOutput.substring(0, actualDBOutput.length() - 1);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Expected output with new user ID 9 having special characters in name and bio
            expectedDBOutput = "1,bob,hello123,true\n" +
                    "I am bob.\n" +
                    "2,3,4\n" +
                    "5,6,7\n" +
                    "8\n" +
                    "2,steve,123hello,false\n" +
                    "I am steve!@#! lol\n" +
                    "4,5,6\n" +
                    "23,245,4\n" +
                    "111\n" +
                    "3,pat,u2u24odoe,true\n" +
                    "pat here. nice to meet you!\n" +
                    "1,2,3\n" +
                    "232,25,41\n" +
                    "NONE\n" +
                    "4,tim,loginPassword,false\n" +
                    "sad :(\n" +
                    "NONE\n" +
                    "23,245,4\n" +
                    "12\n" +
                    "5,thomas,u2u24o2id,true\n" +
                    "wheeeee #friendly\n" +
                    "1,2,3\n" +
                    "NONE\n" +
                    "134\n" +
                    "6,ron,nthoh2424,false\n" +
                    "no friends, no enemies\n" +
                    "NONE\n" +
                    "NONE\n" +
                    "NONE\n" +
                    "7,special@User!,spPass123,true\n" +
                    "Bio with !@#$%^&*() special characters\n" +
                    "NONE\n" +
                    "NONE\n" +
                    "NONE";

            restoreTestFile();
            assertEquals("Error: Output does not match expected output for user with special characters in name, bio",
                    expectedDBOutput, actualDBOutput);


            // test Add Duplicate UserID() {
            restoreTestFile();

            // Attempt to add a user with a duplicate ID (ID 4, which is already in the database)
            User duplicateIDUser = new User("duplicateUser", "dupPass", 4,
                    "This should not be added", false);
            db.addUser(duplicateIDUser);

            actualDBOutput = "";
            try (BufferedReader br = new BufferedReader(new FileReader("TestFiles/TestUserInfo"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    actualDBOutput += line + "\n";
                }
                actualDBOutput = actualDBOutput.substring(0, actualDBOutput.length() - 1);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Expected output should remain unchanged because the user with ID 4 already exists
            expectedDBOutput = "1,bob,hello123,true\n" +
                    "I am bob.\n" +
                    "2,3,4\n" +
                    "5,6,7\n" +
                    "8\n" +
                    "2,steve,123hello,false\n" +
                    "I am steve!@#! lol\n" +
                    "4,5,6\n" +
                    "23,245,4\n" +
                    "111\n" +
                    "3,pat,u2u24odoe,true\n" +
                    "pat here. nice to meet you!\n" +
                    "1,2,3\n" +
                    "232,25,41\n" +
                    "NONE\n" +
                    "4,tim,loginPassword,false\n" +
                    "sad :(\n" +
                    "NONE\n" +
                    "23,245,4\n" +
                    "12\n" +
                    "5,thomas,u2u24o2id,true\n" +
                    "wheeeee #friendly\n" +
                    "1,2,3\n" +
                    "NONE\n" +
                    "134\n" +
                    "6,ron,nthoh2424,false\n" +
                    "no friends, no enemies\n" +
                    "NONE\n" +
                    "NONE\n" +
                    "NONE";

            restoreTestFile();
            assertEquals("Error: Output should remain unchanged when attempting to add a user with a duplicate ID",
                    expectedDBOutput, actualDBOutput);
        }

        @Test
        public void testEditUserInfo() {
            restoreTestFile();
            User changed = new User("changed", "diffPass", 1,
                    "changed bio", true);
            changed.setFriends(friends);
            changed.setBlockedUsers(blocked);
            changed.setRequested(requested);
            db.editUserInfo(changed);
            String actualDBOutput = "";
            try (BufferedReader br = new BufferedReader(new FileReader("TestFiles/TestUserInfo"))) {
                while (true) {
                    String line = br.readLine();
                    if (line == null)
                        break;
                    actualDBOutput = actualDBOutput + line + "\n";
                }
                actualDBOutput = actualDBOutput.substring(0, actualDBOutput.length() - 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String expectedDBOutput = "1,changed,diffPass,true\n" +
                    "changed bio\n" +
                    "10,11\n" +
                    "12,13\n" +
                    "14,15\n" +
                    "2,steve,123hello,false\n" +
                    "I am steve!@#! lol\n" +
                    "4,5,6\n" +
                    "23,245,4\n" +
                    "111\n" +
                    "3,pat,u2u24odoe,true\n" +
                    "pat here. nice to meet you!\n" +
                    "1,2,3\n" +
                    "232,25,41\n" +
                    "NONE\n" +
                    "4,tim,loginPassword,false\n" +
                    "sad :(\n" +
                    "NONE\n" +
                    "23,245,4\n" +
                    "12\n" +
                    "5,thomas,u2u24o2id,true\n" +
                    "wheeeee #friendly\n" +
                    "1,2,3\n" +
                    "NONE\n" +
                    "134\n" +
                    "6,ron,nthoh2424,false\n" +
                    "no friends, no enemies\n" +
                    "NONE\n" +
                    "NONE\n" +
                    "NONE";

            restoreTestFile();
            assertEquals("Error: Output does not match expected string output",
                    expectedDBOutput, actualDBOutput);


            // test EditUser with No Friends Or Blocked Users
            restoreTestFile();

            // Create a user with no friends or blocked users
            changed = new User("loner", "noFriends", 6,
                    "no friends, no enemies", false);
            db.editUserInfo(changed);

            // Capture the output after editing
            actualDBOutput = "";
            try (BufferedReader br = new BufferedReader(new FileReader("TestFiles/TestUserInfo"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    actualDBOutput += line + "\n";
                }
                actualDBOutput = actualDBOutput.substring(0, actualDBOutput.length() - 1);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Expected output with User ID 6 having updated info
            expectedDBOutput = "1,bob,hello123,true\n" +
                    "I am bob.\n" +
                    "2,3,4\n" +
                    "5,6,7\n" +
                    "8\n" +
                    "2,steve,123hello,false\n" +
                    "I am steve!@#! lol\n" +
                    "4,5,6\n" +
                    "23,245,4\n" +
                    "111\n" +
                    "3,pat,u2u24odoe,true\n" +
                    "pat here. nice to meet you!\n" +
                    "1,2,3\n" +
                    "232,25,41\n" +
                    "NONE\n" +
                    "4,tim,loginPassword,false\n" +
                    "sad :(\n" +
                    "NONE\n" +
                    "23,245,4\n" +
                    "12\n" +
                    "5,thomas,u2u24o2id,true\n" +
                    "wheeeee #friendly\n" +
                    "1,2,3\n" +
                    "NONE\n" +
                    "134\n" +
                    "6,loner,noFriends,false\n" +
                    "no friends, no enemies\n" +
                    "NONE\n" +
                    "NONE\n" +
                    "NONE";

            restoreTestFile();
            assertEquals("Error: Output does not match expected output for a " +
                            "user with no friends or blocked users",
                    expectedDBOutput, actualDBOutput);


            //test EditUser With Special Characters In Bio
            restoreTestFile();

            // Create a user with special characters in bio
            changed = new User("special", "pass123", 2,
                    "bio with special chars!@#$%^&*()", false);
            db.editUserInfo(changed);

            // Capture the output after editing
            actualDBOutput = "";
            try (BufferedReader br = new BufferedReader(new FileReader("TestFiles/TestUserInfo"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    actualDBOutput += line + "\n";
                }
                actualDBOutput = actualDBOutput.substring(0, actualDBOutput.length() - 1);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Expected output with User ID 2 having updated info
            expectedDBOutput = "1,bob,hello123,true\n" +
                    "I am bob.\n" +
                    "2,3,4\n" +
                    "5,6,7\n" +
                    "8\n" +
                    "2,special,pass123,false\n" +
                    "bio with special chars!@#$%^&*()\n" +
                    "NONE\n" +
                    "NONE\n" +
                    "NONE\n" +
                    "3,pat,u2u24odoe,true\n" +
                    "pat here. nice to meet you!\n" +
                    "1,2,3\n" +
                    "232,25,41\n" +
                    "NONE\n" +
                    "4,tim,loginPassword,false\n" +
                    "sad :(\n" +
                    "NONE\n" +
                    "23,245,4\n" +
                    "12\n" +
                    "5,thomas,u2u24o2id,true\n" +
                    "wheeeee #friendly\n" +
                    "1,2,3\n" +
                    "NONE\n" +
                    "134\n" +
                    "6,ron,nthoh2424,false\n" +
                    "no friends, no enemies\n" +
                    "NONE\n" +
                    "NONE\n" +
                    "NONE";

            restoreTestFile();
            assertEquals("Error: Output does not match expected output for " +
                            "a user with special characters in bio",
                    expectedDBOutput, actualDBOutput);


            // test Edit Non-Existent User
            restoreTestFile();

            // Attempt to edit a non-existent user (User ID 99)
            User nonExistent = new User("ghost", "noPass", 99, "I don't exist", true);
            db.editUserInfo(nonExistent);

            // Capture the output to verify no changes were made
            actualDBOutput = "";
            try (BufferedReader br = new BufferedReader(new FileReader("TestFiles/TestUserInfo"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    actualDBOutput += line + "\n";
                }
                actualDBOutput = actualDBOutput.substring(0, actualDBOutput.length() - 1);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Expected output should remain unchanged as the user does not exist
            expectedDBOutput = "1,bob,hello123,true\n" +
                    "I am bob.\n" +
                    "2,3,4\n" +
                    "5,6,7\n" +
                    "8\n" +
                    "2,steve,123hello,false\n" +
                    "I am steve!@#! lol\n" +
                    "4,5,6\n" +
                    "23,245,4\n" +
                    "111\n" +
                    "3,pat,u2u24odoe,true\n" +
                    "pat here. nice to meet you!\n" +
                    "1,2,3\n" +
                    "232,25,41\n" +
                    "NONE\n" +
                    "4,tim,loginPassword,false\n" +
                    "sad :(\n" +
                    "NONE\n" +
                    "23,245,4\n" +
                    "12\n" +
                    "5,thomas,u2u24o2id,true\n" +
                    "wheeeee #friendly\n" +
                    "1,2,3\n" +
                    "NONE\n" +
                    "134\n" +
                    "6,ron,nthoh2424,false\n" +
                    "no friends, no enemies\n" +
                    "NONE\n" +
                    "NONE\n" +
                    "NONE";

            restoreTestFile();
            assertEquals("Error: Output does not match expected output when " +
                            "attempting to edit a non-existent user",
                    expectedDBOutput, actualDBOutput);
        }

        @Test
        public void testGetUserID() {
            //CASE 1: EXISTING USER
            restoreTestFile();
            int actualDBOutput = db.getUserID("bob");
            int expectedDBOutput = 1;
            restoreTestFile();
            assertEquals("Error: Output does not match expected int output for a valid user",
                    expectedDBOutput, actualDBOutput);

            //CASE 2: NON-EXISTENT USERNAME
            int actualDBOutput2 = db.getUserID("bazooka");
            int expectedDBOutput2 = -1;
            restoreTestFile();
            assertEquals("Error: Output does not match expected int output for an invalid user",
                    expectedDBOutput2, actualDBOutput2);

            //CASE 3: NULL PASSED INTO METHOD PARAMETER
            int actualDBOutput3 = db.getUserID(null);
            int expectedDBOutput3 = -1;
            restoreTestFile();
            assertEquals("Error: Output does not match expected int output for a null loginUsername",
                    expectedDBOutput3, actualDBOutput3);
        }

        @Test
        public void testGetUserName() {
            // test normal loginUsername
            restoreTestFile();
            String actualDBOutput = db.getUserName(4);
            String expectedDBOutput = "tim";
            restoreTestFile();
            assertEquals("Error: Output does not match expected string output for a valid user",
                    expectedDBOutput, actualDBOutput);
            restoreTestFile();


            // test NonexistentUser
            // Reset the dummy database to ensure a clean slate
            restoreTestFile();

            // Test retrieving the loginUsername for a non-existent user (e.g., User ID 99)
            actualDBOutput = db.getUserName(99);
            expectedDBOutput = "User not found";

            // Restore the dummy test file to the original state
            restoreTestFile();

            // Assert that the actual output matches the expected output
            assertEquals("Error: Output does not match expected output for a non-existent user",
                    expectedDBOutput, actualDBOutput);


            //test NegativeUserID
            // Reset the dummy database to ensure a clean slate
            restoreTestFile();

            // Test retrieving the loginUsername with a negative user ID
            actualDBOutput = db.getUserName(-1);
            expectedDBOutput = "User not found";

            // Restore the dummy test file to the original state
            restoreTestFile();

            // Assert that the actual output matches the expected output
            assertEquals("Error: Output does not match expected output for a negative user ID",
                    expectedDBOutput, actualDBOutput);


            // test ZeroUserID
            // Reset the dummy database to ensure a clean slate
            restoreTestFile();

            // Test retrieving the loginUsername with a user ID of 0
            actualDBOutput = db.getUserName(0);
            expectedDBOutput = "User not found";

            // Restore the dummy test file to the original state
            restoreTestFile();

            // Assert that the actual output matches the expected output
            assertEquals("Error: Output does not match expected output for a user ID of 0",
                    expectedDBOutput, actualDBOutput);
        }

        @Test
        public void testGetUser() {
            // Case 1:  returns the correct User for regular User attributes
            restoreTestFile();
            User actualDBOutput = db.getUser("bob");
            User expectedDBOutput = new User("bob", "hello123", 1, "I am bob.", true);
            expectedDBOutput.setFriends(actualDBOutput.getFriends());
            expectedDBOutput.setBlockedUsers(actualDBOutput.getBlockedUsers());
            expectedDBOutput.setRequested(actualDBOutput.getRequested());

            restoreTestFile();
            assertEquals("Error: User information doesn't match expected User information",
                    expectedDBOutput.toString(), actualDBOutput.toString());
            // Case 2: User contains a "NONE" attribute
            User actualDBOutput2 = db.getUser("ron");
            User expectedDBOutput2 = new User("ron", "nthoh2424", 6,
                    "no friends, no enemies", false);
            restoreTestFile();
            assertEquals("Error: User information doesn't match expected User information when attribute is NONE.",
                    expectedDBOutput2.toString(), actualDBOutput2.toString());
            // Case 3: Return Null for a nonexistent loginUsername
            User actualDBOutput3 = db.getUser("Nonexistentusername");
            restoreTestFile();
            assertNull("Error: method should return null for a nonexistent user", actualDBOutput3);
//            // Case 4: Null attributes in User
//            restoreTestFile();
//            User alice = new User("alice", "password123", 7, "null", true);
//            ArrayList<Integer> aliceFriends = new ArrayList<>();
//            for (int i = 1; i <= 1000; i++) {
//                aliceFriends.add(i);
//            }
//            alice.setFriends(aliceFriends);
//            db.addUser(alice);
//            User actualDBOutput4 = db.getUser("alice");
//            restoreTestFile();
//            assertEquals("Error: User should be able to have large list of friends", alice, actualDBOutput4);
        }

        @Test
        public void testGetAllUserName() {
            restoreTestFile();
            ArrayList<String> actualDBOutput = db.getAllUserName();
            ArrayList<String> expectedDBOutput = new ArrayList<>();
            expectedDBOutput.add("bob");
            expectedDBOutput.add("steve");
            expectedDBOutput.add("pat");
            expectedDBOutput.add("tim");
            expectedDBOutput.add("thomas");
            expectedDBOutput.add("ron");
            restoreTestFile();
            assertEquals("Error: Output does not match expected string output",
                    expectedDBOutput, actualDBOutput);

            //empty file content test
            restoreTestFile();
            try {
                PrintWriter pw = new PrintWriter(new FileWriter("TestFiles/TestUserInfo"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            actualDBOutput = db.getAllUserName();
            expectedDBOutput = new ArrayList<>();
            restoreTestFile();
            assertEquals("Error: Output does not match expected string output",
                    expectedDBOutput, actualDBOutput);

            //empty loginUsername test
            restoreTestFile();
            try {
                PrintWriter pw = new PrintWriter(new FileWriter("TestFiles/TestUserInfo"));
                pw.write("1,,234234324,true");
                pw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            actualDBOutput = db.getAllUserName();
            expectedDBOutput = new ArrayList<>();
            expectedDBOutput.add("");
            restoreTestFile();
            assertEquals("Error: Output does not match expected string output",
                    expectedDBOutput, actualDBOutput);
        }

        @Test
        public void testGetPassword() {
            restoreTestFile();
            String actualDBOutput = db.getPassword(5);
            String expectedDBOutput = "u2u24o2id";
            restoreTestFile();
            assertEquals("Error: Output does not match expected string output",
                    expectedDBOutput, actualDBOutput);

            //nonexistent user test
            restoreTestFile();
            actualDBOutput = db.getPassword(9999);
            expectedDBOutput = "";
            restoreTestFile();
            assertEquals("Error: Output does not match expected string output",
                    expectedDBOutput, actualDBOutput);


            //negative value test
            restoreTestFile();
            actualDBOutput = db.getPassword(-10);
            expectedDBOutput = "";
            restoreTestFile();
            assertEquals("Error: Output does not match expected string output",
                    expectedDBOutput, actualDBOutput);
        }

        @Test
        public void testGetBio() {
            restoreTestFile();
            String actualDBOutput = db.getBio(6);
            String expectedDBOutput = "no friends, no enemies";
            restoreTestFile();
            assertEquals("Error: Output does not match expected string output",
                    expectedDBOutput, actualDBOutput);

            //nonexistent user test
            restoreTestFile();
            actualDBOutput = db.getBio(9999);
            expectedDBOutput = "";
            restoreTestFile();
            assertEquals("Error: Output does not match expected string output",
                    expectedDBOutput, actualDBOutput);


            //negative value test
            restoreTestFile();
            actualDBOutput = db.getBio(-10);
            expectedDBOutput = "";
            restoreTestFile();
            assertEquals("Error: Output does not match expected string output",
                    expectedDBOutput, actualDBOutput);
        }

        @Test
        public void testGetFriends() {
            restoreTestFile();
            ArrayList<Integer> actualDBOutput = db.getFriends(5);
            ArrayList<Integer> expectedDBOutput = new ArrayList<>();
            expectedDBOutput.add(1);
            expectedDBOutput.add(2);
            expectedDBOutput.add(3);
            restoreTestFile();
            assertEquals("Error: Output does not match expected string output",
                    expectedDBOutput, actualDBOutput);


            //nonexistent user test
            restoreTestFile();
            actualDBOutput = db.getFriends(9999);
            expectedDBOutput = null;
            restoreTestFile();
            assertEquals("Error: Output does not match expected string output",
                    expectedDBOutput, actualDBOutput);


            //negative value test
            restoreTestFile();
            actualDBOutput = db.getFriends(-10);
            restoreTestFile();
            assertEquals("Error: Output does not match expected string output",
                    expectedDBOutput, actualDBOutput);
        }

        @Test
        public void testGetRequested() {
            // Case 1: regular test case
            restoreTestFile();
            ArrayList<Integer> actualDBOutput = db.getRequested(5);
            ArrayList<Integer> expectedDBOutput = new ArrayList<>();
            expectedDBOutput.add(134);
            restoreTestFile();
            assertEquals("Error: Requested does not match expected",
                    expectedDBOutput, actualDBOutput);

            // Case 2: When requested is null/empty
            ArrayList<Integer> actualDBOutput2 = db.getRequested(6);
            ArrayList<Integer> expectedDBOutput2 = new ArrayList<>();
            restoreTestFile();
            assertEquals("Error: Requested does not match expected when empty",
                    expectedDBOutput2, actualDBOutput2);

            // Case 3: When userID is nonexistent (and a negative value)
            ArrayList<Integer> actualDBOutput3 = db.getRequested(-100);
            restoreTestFile();
            assertNull("Error: Requested does not match expected when empty", actualDBOutput3);
        }

        @Test
        public void testGetBlocked() {
            restoreTestFile();
            ArrayList<Integer> actualDBOutput = db.getBlockedUsers(4);
            ArrayList<Integer> expectedDBOutput = new ArrayList<>();
            expectedDBOutput.add(23);
            expectedDBOutput.add(245);
            expectedDBOutput.add(4);
            restoreTestFile();
            assertEquals("Error: Output does not match expected string output",
                    expectedDBOutput, actualDBOutput);


            //nonexistent user test
            restoreTestFile();
            actualDBOutput = db.getBlockedUsers(9999);
            expectedDBOutput = null;
            restoreTestFile();
            assertEquals("Error: Output does not match expected string output",
                    expectedDBOutput, actualDBOutput);


            //negative value test
            restoreTestFile();
            actualDBOutput = db.getBlockedUsers(-10);
            restoreTestFile();
            assertEquals("Error: Output does not match expected string output",
                    expectedDBOutput, actualDBOutput);
        }

        @Test
        public void testAddDM() {
            //CASE #1 ADDING TO AN ALREADY EXISTING DM
            restoreTestFile(); //Make sure to restore the test file before each method call
            db.addDM(new User("aoue", "eouoa", 1, "oeua", true),
                    new User("utho", "oounth", 3, "ntoh", true),
                    "(*&^@(D(@DEUD");
            //This should add a new DM between two users
            String actualDBOutput = "";
            //Read in the file and make the actual output that entire String
            try (BufferedReader br = new BufferedReader(new FileReader("TestFiles/TestDirectMessages"))) {
                while (true) {
                    String line = br.readLine();
                    if (line == null)
                        break;
                    actualDBOutput = actualDBOutput + line + "\n";
                }
                actualDBOutput = actualDBOutput.substring(0, actualDBOutput.length() - 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //This is the expected output from calling the method
            String expectedDBOutput = "#1-2\n" +
                    "$1*1:standard test\n" +
                    "$2*2:ouou\n" +
                    "$3*1:ouou\n" +
                    "$4*2:thuu\n" +
                    "#ENDDM\n" +
                    "#1-3\n" +
                    "$1*3:gibberish test\n" +
                    "$2*1::2u,'uo#$%^&*\n" +
                    "$3*1:@R*,2'eou5#$\"*(){}?\n" +
                    "$4*3:OUDHR2pp  ouo!@#$%^&*(){}?|+?_VZVW\"<>\n" +
                    "$5*3://onuth2\\\\ounth2 nhou\n" +
                    "$6*1:(*&^@(D(@DEUD\n" +
                    "#ENDDM\n" +
                    "#3-4\n" +
                    "$1*4:conversation test\n" +
                    "$2*3:im pat\n" +
                    "$3*3:nice to meet you\n" +
                    "$4*3:whats ur name\n" +
                    "$5*4:im tim\n" +
                    "$6*3:ok lets be friends\n" +
                    "$7*4:cool\n" +
                    "$8*3:so...\n" +
                    "$9*4:yea?\n" +
                    "$10*3:what do you like to do?\n" +
                    "$11*3:hello?\n" +
                    "#ENDDM\n" +
                    "#2-4\n" +
                    "$1*2:single sided conversation test\n" +
                    "$2*2:yo\n" +
                    "$3*2:you there?\n" +
                    "$4*2:hellooooo?\n" +
                    "#ENDDM\n" +
                    "#1-4\n" +
                    "$1*1:single text test\n" +
                    "#ENDDM";

            restoreTestFile();
            //Restore the file and make sure that both outputs equal each other
            assertEquals("Error: Output does not match expected string output when adding to a existing DM",
                    expectedDBOutput, actualDBOutput);

            //CASE #2 CREATING A NEW DM AND ADDING A MESSAGE
            //This method would create a new DM and add a message
            db.addDM(new User("aoue", "eouoa", 3, "oeua", true),
                    new User("utho", "oounth", 2, "ntoh", true),
                    "(*&^@(D(@DEUD");
            String actualDBOutput2 = "";
            //Read in the file to the actual string
            try (BufferedReader br = new BufferedReader(new FileReader("TestFiles/TestDirectMessages"))) {
                while (true) {
                    String line = br.readLine();
                    if (line == null)
                        break;
                    actualDBOutput2 = actualDBOutput2 + line + "\n";
                }
                actualDBOutput2 = actualDBOutput2.substring(0, actualDBOutput2.length() - 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //This is the expected output to compare it against
            String expectedDBOutput2 = "#1-2\n" +
                    "$1*1:standard test\n" +
                    "$2*2:ouou\n" +
                    "$3*1:ouou\n" +
                    "$4*2:thuu\n" +
                    "#ENDDM\n" +
                    "#1-3\n" +
                    "$1*3:gibberish test\n" +
                    "$2*1::2u,'uo#$%^&*\n" +
                    "$3*1:@R*,2'eou5#$\"*(){}?\n" +
                    "$4*3:OUDHR2pp  ouo!@#$%^&*(){}?|+?_VZVW\"<>\n" +
                    "$5*3://onuth2\\\\ounth2 nhou\n" +
                    "#ENDDM\n" +
                    "#3-4\n" +
                    "$1*4:conversation test\n" +
                    "$2*3:im pat\n" +
                    "$3*3:nice to meet you\n" +
                    "$4*3:whats ur name\n" +
                    "$5*4:im tim\n" +
                    "$6*3:ok lets be friends\n" +
                    "$7*4:cool\n" +
                    "$8*3:so...\n" +
                    "$9*4:yea?\n" +
                    "$10*3:what do you like to do?\n" +
                    "$11*3:hello?\n" +
                    "#ENDDM\n" +
                    "#2-4\n" +
                    "$1*2:single sided conversation test\n" +
                    "$2*2:yo\n" +
                    "$3*2:you there?\n" +
                    "$4*2:hellooooo?\n" +
                    "#ENDDM\n" +
                    "#1-4\n" +
                    "$1*1:single text test\n" +
                    "#ENDDM\n" +
                    "#2-3\n" +
                    "$1*3:(*&^@(D(@DEUD\n" +
                    "#ENDDM";
            restoreTestFile();
            //Restore the test file and make sure the outputs match
            assertEquals("Error: Output does not match expected string output when creating " +
                            "a new DM and adding a message",
                    expectedDBOutput2, actualDBOutput2);

            //CASE #3: ADDING A DM WITH EMPTY MESSAGE CONTENT
            //Call the method and make an empty message
            db.addDM(new User("testUser1", "testEmail1", 5, "testPass", true),
                    new User("testUser2", "testEmail2", 6, "testPass",
                            true), "");

            String actualDBOutput3 = "";
            //Read in the file after the method call to get the actual output
            try (BufferedReader br = new BufferedReader(new FileReader("TestFiles/TestDirectMessages"))) {
                while (true) {
                    String line = br.readLine();
                    if (line == null)
                        break;
                    actualDBOutput3 = actualDBOutput3 + line + "\n";
                }
                actualDBOutput3 = actualDBOutput3.substring(0, actualDBOutput3.length() - 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //This is the expected method which will have a new dm with an empty message
            String expectedDBOutput3 = "#1-2\n" +
                    "$1*1:standard test\n" +
                    "$2*2:ouou\n" +
                    "$3*1:ouou\n" +
                    "$4*2:thuu\n" +
                    "#ENDDM\n" +
                    "#1-3\n" +
                    "$1*3:gibberish test\n" +
                    "$2*1::2u,'uo#$%^&*\n" +
                    "$3*1:@R*,2'eou5#$\"*(){}?\n" +
                    "$4*3:OUDHR2pp  ouo!@#$%^&*(){}?|+?_VZVW\"<>\n" +
                    "$5*3://onuth2\\\\ounth2 nhou\n" +
                    "#ENDDM\n" +
                    "#3-4\n" +
                    "$1*4:conversation test\n" +
                    "$2*3:im pat\n" +
                    "$3*3:nice to meet you\n" +
                    "$4*3:whats ur name\n" +
                    "$5*4:im tim\n" +
                    "$6*3:ok lets be friends\n" +
                    "$7*4:cool\n" +
                    "$8*3:so...\n" +
                    "$9*4:yea?\n" +
                    "$10*3:what do you like to do?\n" +
                    "$11*3:hello?\n" +
                    "#ENDDM\n" +
                    "#2-4\n" +
                    "$1*2:single sided conversation test\n" +
                    "$2*2:yo\n" +
                    "$3*2:you there?\n" +
                    "$4*2:hellooooo?\n" +
                    "#ENDDM\n" +
                    "#1-4\n" +
                    "$1*1:single text test\n" +
                    "#ENDDM\n" +
                    "#5-6\n" +
                    "$1*5:\n" +
                    "#ENDDM";

            restoreTestFile();
            //Restore the test file and make sure the outputs are equal
            assertEquals("Error: Output does not match expected string output when " +
                            "adding a DM with empty content",
                    expectedDBOutput3, actualDBOutput3);

            // CASE 4 DMING YOURSELF (SHOULD NOT WORK)
            //Two users with the same userID
            db.addDM(new User("TU1", "testEmail1", 7, "testPass", true),
                    new User("TU1", "testEmail1", 7, "testPass", true),
                    "");

            String actualDBOutput4 = "";
            //Read in the actual output from the file after calling the method
            try (BufferedReader br = new BufferedReader(new FileReader("TestFiles/TestDirectMessages"))) {
                while (true) {
                    String line = br.readLine();
                    if (line == null)
                        break;
                    actualDBOutput4 = actualDBOutput4 + line + "\n";
                }
                actualDBOutput4 = actualDBOutput4.substring(0, actualDBOutput4.length() - 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //This is what is expected from the output (notice there's no change)
            String expectedDBOutput4 = "#1-2\n" +
                    "$1*1:standard test\n" +
                    "$2*2:ouou\n" +
                    "$3*1:ouou\n" +
                    "$4*2:thuu\n" +
                    "#ENDDM\n" +
                    "#1-3\n" +
                    "$1*3:gibberish test\n" +
                    "$2*1::2u,'uo#$%^&*\n" +
                    "$3*1:@R*,2'eou5#$\"*(){}?\n" +
                    "$4*3:OUDHR2pp  ouo!@#$%^&*(){}?|+?_VZVW\"<>\n" +
                    "$5*3://onuth2\\\\ounth2 nhou\n" +
                    "#ENDDM\n" +
                    "#3-4\n" +
                    "$1*4:conversation test\n" +
                    "$2*3:im pat\n" +
                    "$3*3:nice to meet you\n" +
                    "$4*3:whats ur name\n" +
                    "$5*4:im tim\n" +
                    "$6*3:ok lets be friends\n" +
                    "$7*4:cool\n" +
                    "$8*3:so...\n" +
                    "$9*4:yea?\n" +
                    "$10*3:what do you like to do?\n" +
                    "$11*3:hello?\n" +
                    "#ENDDM\n" +
                    "#2-4\n" +
                    "$1*2:single sided conversation test\n" +
                    "$2*2:yo\n" +
                    "$3*2:you there?\n" +
                    "$4*2:hellooooo?\n" +
                    "#ENDDM\n" +
                    "#1-4\n" +
                    "$1*1:single text test\n" +
                    "#ENDDM";

            restoreTestFile();
            //Restore the test file then check to see that both are equal
            assertEquals("Error: Output does not match expected string output when DMING oneself",
                    expectedDBOutput4, actualDBOutput4);
        }

        @Test
        public void testRemoveDM() {
            //CASE 1
            restoreTestFile(); //Always make sure that the file is restored to the original format
            //Next, you want to call the method
            db.removeDM(new User("aoue", "eouoa", 1, "oeua", true),
                    new User("utho", "oounth", 3, "ntoh", true), 2);
            //Fill out the actual output as it should if the method were to run as expected
            String actualDBOutput = "";
            try (BufferedReader br = new BufferedReader(new FileReader("TestFiles/TestDirectMessages"))) {
                while (true) {
                    String line = br.readLine();
                    if (line == null)
                        break;
                    //Append the output as such, constantly adding a new line through each runthrough
                    actualDBOutput = actualDBOutput + line + "\n";
                }
                //Since the file would end with a new line at the very end, you want to remove that new line with this
                actualDBOutput = actualDBOutput.substring(0, actualDBOutput.length() - 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //This is the expected output in string format (notice how the second dm between users 1 and 3 is gone)
            String expectedDBOutput = "#1-2\n" +
                    "$1*1:standard test\n" +
                    "$2*2:ouou\n" +
                    "$3*1:ouou\n" +
                    "$4*2:thuu\n" +
                    "#ENDDM\n" +
                    "#1-3\n" +
                    "$1*3:gibberish test\n" +
                    "$3*1:@R*,2'eou5#$\"*(){}?\n" +
                    "$4*3:OUDHR2pp  ouo!@#$%^&*(){}?|+?_VZVW\"<>\n" +
                    "$5*3://onuth2\\\\ounth2 nhou\n" +
                    "#ENDDM\n" +
                    "#3-4\n" +
                    "$1*4:conversation test\n" +
                    "$2*3:im pat\n" +
                    "$3*3:nice to meet you\n" +
                    "$4*3:whats ur name\n" +
                    "$5*4:im tim\n" +
                    "$6*3:ok lets be friends\n" +
                    "$7*4:cool\n" +
                    "$8*3:so...\n" +
                    "$9*4:yea?\n" +
                    "$10*3:what do you like to do?\n" +
                    "$11*3:hello?\n" +
                    "#ENDDM\n" +
                    "#2-4\n" +
                    "$1*2:single sided conversation test\n" +
                    "$2*2:yo\n" +
                    "$3*2:you there?\n" +
                    "$4*2:hellooooo?\n" +
                    "#ENDDM\n" +
                    "#1-4\n" +
                    "$1*1:single text test\n" +
                    "#ENDDM";
            restoreTestFile(); //Restore the test file
            assertEquals("Error: Output does not match expected string output",
                    expectedDBOutput, actualDBOutput); //If expectedDBOutput doesn't match actualDBOutput
            // print this error

            // CASE 2 MSGID DOESN'T EXIST
            //Again call the removeDM method but this time, the messageID doesn't exist
            db.removeDM(new User("aoue", "eouoa", 3, "oeua", true),
                    new User("utho", "oounth", 4, "ntoh", true), 12);
            String actualDBOutput2 = "";
            //Fill out the actualDBOutput2 for case 2
            try (BufferedReader br = new BufferedReader(new FileReader("TestFiles/TestDirectMessages"))) {
                while (true) {
                    String line = br.readLine();
                    if (line == null)
                        break;
                    actualDBOutput2 = actualDBOutput2 + line + "\n";
                }
                actualDBOutput2 = actualDBOutput2.substring(0, actualDBOutput2.length() - 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //Notice that the expected output is unchanged from its previous form
            String expectedDBOutput2 = "#1-2\n" +
                    "$1*1:standard test\n" +
                    "$2*2:ouou\n" +
                    "$3*1:ouou\n" +
                    "$4*2:thuu\n" +
                    "#ENDDM\n" +
                    "#1-3\n" +
                    "$1*3:gibberish test\n" +
                    "$2*1::2u,'uo#$%^&*\n" +
                    "$3*1:@R*,2'eou5#$\"*(){}?\n" +
                    "$4*3:OUDHR2pp  ouo!@#$%^&*(){}?|+?_VZVW\"<>\n" +
                    "$5*3://onuth2\\\\ounth2 nhou\n" +
                    "#ENDDM\n" +
                    "#3-4\n" +
                    "$1*4:conversation test\n" +
                    "$2*3:im pat\n" +
                    "$3*3:nice to meet you\n" +
                    "$4*3:whats ur name\n" +
                    "$5*4:im tim\n" +
                    "$6*3:ok lets be friends\n" +
                    "$7*4:cool\n" +
                    "$8*3:so...\n" +
                    "$9*4:yea?\n" +
                    "$10*3:what do you like to do?\n" +
                    "$11*3:hello?\n" +
                    "#ENDDM\n" +
                    "#2-4\n" +
                    "$1*2:single sided conversation test\n" +
                    "$2*2:yo\n" +
                    "$3*2:you there?\n" +
                    "$4*2:hellooooo?\n" +
                    "#ENDDM\n" +
                    "#1-4\n" +
                    "$1*1:single text test\n" +
                    "#ENDDM";
            restoreTestFile();
            //Restore the test file and make sure that it handles what to do with an improper message ID
            assertEquals("Error: Output does not handle improper message ID",
                    expectedDBOutput2, actualDBOutput2);

            //CASE 3 MSGID IN THE DOUBLE DIGITS
            //Call the removeDM method
            db.removeDM(new User("aoue", "eouoa", 3, "oeua", true),
                    new User("utho", "oounth", 4, "ntoh", true), 10);
            String actualDBOutput3 = "";
            //Fill out the actual dbOutput
            try (BufferedReader br = new BufferedReader(new FileReader("TestFiles/TestDirectMessages"))) {
                while (true) {
                    String line = br.readLine();
                    if (line == null)
                        break;
                    actualDBOutput3 = actualDBOutput3 + line + "\n";
                }
                actualDBOutput3 = actualDBOutput3.substring(0, actualDBOutput3.length() - 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //This is the expected output with messageID of 10 being removed
            String expectedDBOutput3 = "#1-2\n" +
                    "$1*1:standard test\n" +
                    "$2*2:ouou\n" +
                    "$3*1:ouou\n" +
                    "$4*2:thuu\n" +
                    "#ENDDM\n" +
                    "#1-3\n" +
                    "$1*3:gibberish test\n" +
                    "$2*1::2u,'uo#$%^&*\n" +
                    "$3*1:@R*,2'eou5#$\"*(){}?\n" +
                    "$4*3:OUDHR2pp  ouo!@#$%^&*(){}?|+?_VZVW\"<>\n" +
                    "$5*3://onuth2\\\\ounth2 nhou\n" +
                    "#ENDDM\n" +
                    "#3-4\n" +
                    "$1*4:conversation test\n" +
                    "$2*3:im pat\n" +
                    "$3*3:nice to meet you\n" +
                    "$4*3:whats ur name\n" +
                    "$5*4:im tim\n" +
                    "$6*3:ok lets be friends\n" +
                    "$7*4:cool\n" +
                    "$8*3:so...\n" +
                    "$9*4:yea?\n" +
                    "$11*3:hello?\n" +
                    "#ENDDM\n" +
                    "#2-4\n" +
                    "$1*2:single sided conversation test\n" +
                    "$2*2:yo\n" +
                    "$3*2:you there?\n" +
                    "$4*2:hellooooo?\n" +
                    "#ENDDM\n" +
                    "#1-4\n" +
                    "$1*1:single text test\n" +
                    "#ENDDM";
            restoreTestFile();
            assertEquals("Error: Output does not match expected string output (MSG ID > 9)",
                    expectedDBOutput3, actualDBOutput3);

            // CASE 4 REMOVING FROM NONEXISTENT DM
            //Create two users who have no history of dming each other
            User nonexistent1 = new User("nonUser1", "nonEmail1", 10, "bio1",
                    true);
            User nonexistent2 = new User("nonUser2", "nonEmail2", 11, "bio2",
                    true);
            //Call the method and try to remove the first DM from this nonexistent DM chat
            db.removeDM(nonexistent1, nonexistent2, 1);
            String actualDBOutput4 = "";
            try (BufferedReader br = new BufferedReader(new FileReader("TestFiles/TestDirectMessages"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    actualDBOutput4 += line + "\n";
                }
                if (actualDBOutput4.length() > 0) {
                    actualDBOutput4 = actualDBOutput4.substring(0, actualDBOutput4.length() - 1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //Again the expected output should be unchanged from its original form
            String expectedDBOutput4 = "#1-2\n" +
                    "$1*1:standard test\n" +
                    "$2*2:ouou\n" +
                    "$3*1:ouou\n" +
                    "$4*2:thuu\n" +
                    "#ENDDM\n" +
                    "#1-3\n" +
                    "$1*3:gibberish test\n" +
                    "$2*1::2u,'uo#$%^&*\n" +
                    "$3*1:@R*,2'eou5#$\"*(){}?\n" +
                    "$4*3:OUDHR2pp  ouo!@#$%^&*(){}?|+?_VZVW\"<>\n" +
                    "$5*3://onuth2\\\\ounth2 nhou\n" +
                    "#ENDDM\n" +
                    "#3-4\n" +
                    "$1*4:conversation test\n" +
                    "$2*3:im pat\n" +
                    "$3*3:nice to meet you\n" +
                    "$4*3:whats ur name\n" +
                    "$5*4:im tim\n" +
                    "$6*3:ok lets be friends\n" +
                    "$7*4:cool\n" +
                    "$8*3:so...\n" +
                    "$9*4:yea?\n" +
                    "$10*3:what do you like to do?\n" +
                    "$11*3:hello?\n" +
                    "#ENDDM\n" +
                    "#2-4\n" +
                    "$1*2:single sided conversation test\n" +
                    "$2*2:yo\n" +
                    "$3*2:you there?\n" +
                    "$4*2:hellooooo?\n" +
                    "#ENDDM\n" +
                    "#1-4\n" +
                    "$1*1:single text test\n" +
                    "#ENDDM";
            restoreTestFile();
            //Restore the file and assertEquals to check that the actual output and expected output are equal
            assertEquals("Error: Output should remain when attempting to remove a nonexistent DM",
                    expectedDBOutput4, actualDBOutput4);
        }

        @Test
        public void testGetDMChat() {
            //CASE 1
            //Restore the file to make sure it isn't changed
            restoreTestFile();
            //This should get the dm chats between two users
            ArrayList<String> actualDBOutput = db.getDMChat(new User("aoue", "eouoa",
                            3, "oeua", true),
                    new User("utho", "oounth", 4, "ntoh", true));
            ArrayList<String> expectedDBOutput = new ArrayList<>();
            //This list should have all the chats in this expectedDBOutput ArrayList
            expectedDBOutput.add("$1*4:conversation test");
            expectedDBOutput.add("$2*3:im pat");
            expectedDBOutput.add("$3*3:nice to meet you");
            expectedDBOutput.add("$4*3:whats ur name");
            expectedDBOutput.add("$5*4:im tim");
            expectedDBOutput.add("$6*3:ok lets be friends");
            expectedDBOutput.add("$7*4:cool");
            expectedDBOutput.add("$8*3:so...");
            expectedDBOutput.add("$9*4:yea?");
            expectedDBOutput.add("$10*3:what do you like to do?");
            expectedDBOutput.add("$11*3:hello?");

            restoreTestFile();
            //Restore the file again and make sure that the actual output matches the expected output
            assertEquals("Error: Output does not match expected string output",
                    expectedDBOutput, actualDBOutput);
            //CASE 2 DM BETWEEN TWO USERS DOES NOT EXIST
            //Again,the actual output should be null because that's what the method calls for when a dm does not exist
            ArrayList<String> actualDBOutput2 = db.getDMChat(new User("TU1", "tu1",
                            6, "uh", true),
                    new User("TU2", "tu2", 4, "huh", true));
            ArrayList<String> expectedDBOutput2 = null;
            restoreTestFile();
            //Restore the test file and then assertEquals to make sure that the outputs are both null;
            assertEquals("Error: Output is not equal to null",
                    expectedDBOutput2, actualDBOutput2);
            //CASE 3 SINGLE TEXT CHAT TEST
            //The actual output should just be a single String
            ArrayList<String> actualDBOutput3 = db.getDMChat(new User("TU1", "tu1",
                            1, "uh", true),
                    new User("TU2", "tu2", 4, "huh", true));
            ArrayList<String> expectedDBOutput3 = new ArrayList<>();
            expectedDBOutput3.add("$1*1:single text test");
            //The assertEquals and restore file should work as expected and check to see if both are equal
            restoreTestFile();
            assertEquals("Error: Output does not match single text DM value",
                    expectedDBOutput3, actualDBOutput3);
            //CASE 4 TWO EMPTY USER OBJECTS
            //This case makes sure that no DM between two empty user objects exists
            ArrayList<String> actualDBOutput4 = db.getDMChat(new User(), new User());
            ArrayList<String> expectedDBOutput4 = null;
            restoreTestFile();
            //The assertEquals makes sure that the outputs are equal to each other
            assertEquals("Error: Output does not equal null when both user objects are empty",
                    expectedDBOutput4, actualDBOutput4);
        }

        @Test
        public void testGetFriendRestricted() {
            //CASE 1 REGULAR TESTING
            //Reset the test file
            restoreTestFile();
            //Get the actual output from the method
            boolean actualOutput = db.getFriendRestricted(1);
            //Get the expected output based on the
            boolean expectedOutput = true;
            restoreTestFile();
            assertEquals("Error: output does not match true", expectedOutput, actualOutput);
            //CASE 2 OUTPUT SHOULD MATCH FOR NON-RESTRICTED USER
            boolean actualOutput2 = db.getFriendRestricted(2); // Assume userID 2 is not restricted
            //Expected output should be false for a non-restricted user
            boolean expectedOutput2 = false;
            restoreTestFile();
            assertEquals("Error: output does not match false for non-restricted user",
                    expectedOutput2, actualOutput2);
            //CASE 3 USER DOES NOT EXIST
            //Expected output should be false if user doesn't exist
            boolean actualOutput3 = db.getFriendRestricted(30);
            boolean expectedOutput3 = false;
            restoreTestFile();
            assertEquals("Error: output does not match false for non-existent user",
                    expectedOutput3, actualOutput3);
            //CASE 4 CHECKING MULTIPLE USERS
            restoreTestFile();
            //List of user IDs to check
            int[] userIDs = {1, 2, 30}; // Assume 1 is restricted, 2 is not, and 30 is a non-existent user
            boolean[] expectedOutputs = {true, false, false}; // Corresponding expected outputs
            for (int i = 0; i < userIDs.length; i++) {
                boolean actualOutput4 = db.getFriendRestricted(userIDs[i]);
                assertEquals("Error: output does not match for user ID " + userIDs[i],
                        expectedOutputs[i], actualOutput4);
            }
        }

        @Test
        public void testGetProfilePicture() throws IOException {
            //CASE 1 Regular Test case
            // Reset the dummy database to ensure a clean slate
            restoreTestFile();
            //Get the actual output from the method
            byte[] actualDBOutput = db.getProfilePicture(0);
            Path path = Path.of("TestFiles/TestProfilePictures/0_profilePicture.jpg");
            //Get the path of the test file and get the expected output from it
            byte[] expectedDBOutput = Files.readAllBytes(path);
            restoreTestFile();
            //Restore the test file and make sure that the outputs match
            assertArrayEquals("Error: Output does not match expected byte array output",
                    expectedDBOutput, actualDBOutput);
            //CASE 2 PFP doesn't exist
            restoreTestFile();
            try {
                db.getProfilePicture(99); // Assuming 99 is an invalid user ID
                fail("Expected FileNotFoundException for a non-existent profile picture");
            } catch (FileNotFoundException e) {
                // Test passes if FileNotFoundException is caught
            }
            restoreTestFile();
            //CASE 3 Corrupted File/Unreadable file (it's not a jpg file)
            try {
                db.getProfilePicture(1);
                fail("Expected IOException for a corrupted/unreadable file");
            } catch (IOException e) {
                // Test passes if it gets an IOException
            }
            // CASE 4 EMPTY PROFILE PICTURE FILE
            restoreTestFile();
            // Assume userID 2 has an empty profile picture file.
            byte[] actualDBOutput4 = db.getProfilePicture(2);
            byte[] expectedDBOutput4 = new byte[0]; // Expected empty byte array
            restoreTestFile();
            assertArrayEquals("Error: Expected an empty byte array for an empty profile picture file",
                    expectedDBOutput4, actualDBOutput4);
        }

        @Test
        public void testSaveProfilePicture() throws IOException {
            //CASE 1
            //Restore the test file
            restoreTestFile();
            Path path = Path.of("TestFiles/TestProfilePictures/0_profilePicture.jpg");
            //Create a byte array of the testProfilePicture
            byte[] testProfilePicture = Files.readAllBytes(path);
            //Call the method to save it
            db.saveProfilePicture(999, testProfilePicture);
            byte[] actualDBOutput =
                    Files.readAllBytes(Path.of("TestFiles/TestProfilePictures/999_profilePicture.jpg"));
            byte[] expectedDBOutput = Files.readAllBytes(path);
            restoreTestFile();
            //Make sure that the arrays are equal (so it saves properly)
            assertArrayEquals("Error: Profile picture did not save properly",
                    expectedDBOutput, actualDBOutput);
            //CASE 2 OVERWRITES A PREVIOUSLY SAVED PFP
            restoreTestFile();
            // Path to the initial profile picture for user ID 1
            Path originalPath = Path.of("TestFiles/TestProfilePictures/2_profilePicture.jpg");
            // Create a byte array for the initial profile picture
            byte[] newProfilePicture = Files.readAllBytes(originalPath);
            // Save a new profile picture for user ID 1
            db.saveProfilePicture(1, newProfilePicture); // Assuming testProfilePicture is a different image
            // Retrieve the saved profile picture to verify overwrite
            byte[] actualDBOutput2 =
                    Files.readAllBytes(Path.of("TestFiles/TestProfilePictures/2_profilePicture.jpg"));
            // Ensure the saved profile picture matches the new image (indicating successful overwrite)
            assertArrayEquals("Error: Profile picture did not overwrite as expected",
                    newProfilePicture, actualDBOutput2);
            restoreTestFile();
            //CASE 3 NULL BYTE ARRAY
            try {
                db.saveProfilePicture(1001, null);
                fail("Expected an IOException or NullPointerException when saving a null profile picture");
            } catch (IOException | NullPointerException e) {
                // Test passes if an exception is thrown
            }
            restoreTestFile();
            //CASE 4 CORRECT FILE NAME FORMAT
            restoreTestFile();
            // Save the profile picture
            db.saveProfilePicture(0, testProfilePicture);
            Path savedFilePath = Path.of("TestFiles/TestProfilePictures/0_profilePicture.jpg");
            // Assert that the file exists with the correct naming convention
            assertTrue("Error: The saved profile picture does not follow the expected naming convention.",
                    Files.exists(savedFilePath));
            restoreTestFile();
        }

        @Test
        public void testRemoveUser() {
            // Reset the dummy database to ensure a clean slate
            restoreTestFile();

            // Remove a valid user (User ID 3)
            db.removeUser(3);

            // Capture the database output after removal
            String actualDBOutput = "";
            try (BufferedReader br = new BufferedReader(new FileReader("TestFiles/TestUserInfo"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    actualDBOutput += line + "\n";
                }
                actualDBOutput = actualDBOutput.substring(0, actualDBOutput.length() - 1);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Expected output after User ID 3 is removed
            String expectedDBOutput = "1,bob,hello123,true\n" +
                    "I am bob.\n" +
                    "2,3,4\n" +
                    "5,6,7\n" +
                    "8\n" +
                    "2,steve,123hello,false\n" +
                    "I am steve!@#! lol\n" +
                    "4,5,6\n" +
                    "23,245,4\n" +
                    "111\n" +
                    "4,tim,loginPassword,false\n" +
                    "sad :(\n" +
                    "NONE\n" +
                    "23,245,4\n" +
                    "12\n" +
                    "5,thomas,u2u24o2id,true\n" +
                    "wheeeee #friendly\n" +
                    "1,2,3\n" +
                    "NONE\n" +
                    "134\n" +
                    "6,ron,nthoh2424,false\n" +
                    "no friends, no enemies\n" +
                    "NONE\n" +
                    "NONE\n" +
                    "NONE";

            // Restore the dummy test file to the original state
            restoreTestFile();

            // Assert that the actual output matches the expected output
            assertEquals("Error: Output does not match expected string output for removing an existing user",
                    expectedDBOutput, actualDBOutput);

            //test NonexistentUser
            // Reset the dummy database to ensure a clean slate
            restoreTestFile();

            // Attempt to remove a non-existent user (User ID 99)
            db.removeUser(99);

            // Capture the database output after attempted removal
            actualDBOutput = "";
            try (BufferedReader br = new BufferedReader(new FileReader("TestFiles/TestUserInfo"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    actualDBOutput += line + "\n";
                }
                actualDBOutput = actualDBOutput.substring(0, actualDBOutput.length() - 1);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Expected output should remain unchanged since the user doesn't exist
            expectedDBOutput = "1,bob,hello123,true\n" +
                    "I am bob.\n" +
                    "2,3,4\n" +
                    "5,6,7\n" +
                    "8\n" +
                    "2,steve,123hello,false\n" +
                    "I am steve!@#! lol\n" +
                    "4,5,6\n" +
                    "23,245,4\n" +
                    "111\n" +
                    "3,pat,u2u24odoe,true\n" +
                    "pat here. nice to meet you!\n" +
                    "1,2,3\n" +
                    "232,25,41\n" +
                    "NONE\n" +
                    "4,tim,loginPassword,false\n" +
                    "sad :(\n" +
                    "NONE\n" +
                    "23,245,4\n" +
                    "12\n" +
                    "5,thomas,u2u24o2id,true\n" +
                    "wheeeee #friendly\n" +
                    "1,2,3\n" +
                    "NONE\n" +
                    "134\n" +
                    "6,ron,nthoh2424,false\n" +
                    "no friends, no enemies\n" +
                    "NONE\n" +
                    "NONE\n" +
                    "NONE";

            // Restore the dummy test file to the original state
            restoreTestFile();

            // Assert that the actual output matches the expected output
            assertEquals("Error: Output does not match expected output when attempting to " +
                            "remove a non-existent user",
                    expectedDBOutput, actualDBOutput);


            // test removing LastUser
            // Reset the dummy database to ensure a clean slate
            restoreTestFile();

            // Remove the last user in the database (User ID 6)
            db.removeUser(6);

            // Capture the database output after removal
            actualDBOutput = "";
            try (BufferedReader br = new BufferedReader(new FileReader("TestFiles/TestUserInfo"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    actualDBOutput += line + "\n";
                }
                actualDBOutput = actualDBOutput.substring(0, actualDBOutput.length() - 1);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Expected output after the last user (User ID 6) is removed
            expectedDBOutput = "1,bob,hello123,true\n" +
                    "I am bob.\n" +
                    "2,3,4\n" +
                    "5,6,7\n" +
                    "8\n" +
                    "2,steve,123hello,false\n" +
                    "I am steve!@#! lol\n" +
                    "4,5,6\n" +
                    "23,245,4\n" +
                    "111\n" +
                    "3,pat,u2u24odoe,true\n" +
                    "pat here. nice to meet you!\n" +
                    "1,2,3\n" +
                    "232,25,41\n" +
                    "NONE\n" +
                    "4,tim,loginPassword,false\n" +
                    "sad :(\n" +
                    "NONE\n" +
                    "23,245,4\n" +
                    "12\n" +
                    "5,thomas,u2u24o2id,true\n" +
                    "wheeeee #friendly\n" +
                    "1,2,3\n" +
                    "NONE\n" +
                    "134";

            // Restore the dummy test file to the original state
            restoreTestFile();

            // Assert that the actual output matches the expected output
            assertEquals("Error: Output does not match expected string output for removing the last user",
                    expectedDBOutput, actualDBOutput);
        }
    }

    /**
     * A TestClient class which runs test cases for
     * each method/constructor in the Client class.
     * Also checks to see whether the classes are implementing the right things.
     *
     * @author klakoji npien-purdue rpadhye
     * @version November 17, 2024
     */

    public static class TestClient {
//        Client client;
//        PipedOutputStream pipedOutputStream;
//        PipedInputStream pipedInputStream;
//        PrintWriter testPrintWriter;
//        BufferedReader testBufferedReader;
//
//        @Before
//        public void setUp() throws IOException {
//            pipedOutputStream = new PipedOutputStream();
//            pipedInputStream = new PipedInputStream(pipedOutputStream);
//
//            testPrintWriter = new PrintWriter(pipedOutputStream, true);
//            testBufferedReader = new BufferedReader(new InputStreamReader(pipedInputStream));
//
//            Socket simulatedSocket = new Socket() {
//                @Override
//                public OutputStream getOutputStream() {
//                    return pipedOutputStream;
//                }
//
//                @Override
//                public InputStream getInputStream() {
//                    return pipedInputStream;
//                }
//            };
//
//
//            client = new Client();
//            client.socket = simulatedSocket;
////            client.setBr(testBufferedReader);
////            client.setPw(testPrintWriter);
//        }
//
//        @After
//        public void tearDown() throws IOException {
//            pipedOutputStream.close();
//            pipedInputStream.close();
//        }

        @Test(timeout = 1000)
        public void clientClassDeclarationTest() {
            Class<?> clazz;
            int modifiers;
            Class<?> superclass;
            Class<?>[] superinterfaces;

            // Set the class being tested
            clazz = Client.class;

            // Perform tests

            modifiers = clazz.getModifiers();

            superclass = clazz.getSuperclass();

            superinterfaces = clazz.getInterfaces();

            assertTrue("Ensure that `Client` is `public`!", Modifier.isPublic(modifiers));

            assertFalse("Ensure that `Client` is NOT `abstract`!", Modifier.isAbstract(modifiers));

            assertEquals("Ensure that `Client` extends `Thread`!", Thread.class, superclass);

            assertEquals("Ensure that `Client` implements the ClientInterface",
                    ClientInterface.class, superinterfaces[0]);
        }

        @Test
        public void testClientConstructor() {
            Client c = new Client();

            assertNotNull("Error: Client object should not be null", c);

        }

//        @Test
//        public void testSendMessage() throws IOException {
//            // CASE 1
//            client.sendMessage("helo from client");
//            String expected1 = "helo from client";
//            assertEquals("Error: outputs do not match", expected1, testBufferedReader.readLine());
//            // CASE 2
//            client.sendMessage("");
//            String expected2 = "";
//            assertEquals("Error: outputs do not match for empty message", expected2,
//                    testBufferedReader.readLine());
//            // CASE 3
//            try {
//                client.sendMessage(null);
//                String expected3 = "null"; // Assuming sendMessage handles null by sending nothing
//                assertEquals("Error: outputs do not match for null message", expected3,
//                        testBufferedReader.readLine());
//            } catch (Exception e) {
//                fail("Error: Null message caused an exception");
//            }
//            // CASE 4
//            client.sendMessage("Message with special chars: !@#$%^&*()");
//            String expected4 = "Message with special chars: !@#$%^&*()";
//            assertEquals("Error: outputs do not match for special characters", expected4,
//                    testBufferedReader.readLine());
//        }
//
//        @Test
//        public void testSendDM() throws IOException {
//            // CASE 1
//            client.sendDM("Hello, friend!", "user1", "user2");
//            String expected1 = "sendDM:user1:user2:Hello, friend!";
//            assertEquals("Error: outputs do not match", expected1, testBufferedReader.readLine());
//            // CASE 2
//            client.sendDM("", "user1", "user2");
//            String expected2 = "sendDM:user1:user2:";
//            assertEquals("Error: outputs do not match for empty message", expected2,
//                    testBufferedReader.readLine());
//            // CASE 3
//            client.sendDM("Message with special chars: @#*&$", "user1", "user2");
//            String expected3 = "sendDM:user1:user2:Message with special chars: @#*&$";
//            assertEquals("Error: outputs do not match for special characters", expected3,
//                    testBufferedReader.readLine());
//            // CASE 4
//            String longMessage = "A".repeat(1000); // Assuming 1000 characters is the upper limit
//            client.sendDM(longMessage, "user1", "user2");
//            String expected4 = "sendDM:user1:user2:" + longMessage;
//            assertEquals("Error: outputs do not match for long message", expected4,
//                    testBufferedReader.readLine());
//            //Case 5
//            client.sendDM("Hello!", "invalid_user", "user2");
//            String expected5 = "sendDM:invalid_user:user2:Hello!";
//            assertEquals("Error: outputs do not match for invalid loginUsername", expected5,
//                    testBufferedReader.readLine());
//        }
//
//        @Test
//        public void testRemoveDM() throws IOException {
//            // CASE 1
//            client.removeDM(42, "user1", "user2");
//            String expected1 = "removeDM:user1:user2:42";
//            assertEquals("Error: outputs do not match", expected1, testBufferedReader.readLine());
//            // CASE 2
//            client.removeDM(999, "user1", "user2"); // Assuming 999 is an ID that doesn't exist
//            String expected2 = "removeDM:user1:user2:999";
//            assertEquals("Error: outputs do not match for non-existent message ID", expected2,
//                    testBufferedReader.readLine());
//            // CASE 3
//            client.removeDM(-1, "user1", "user2");
//            String expected3 = "removeDM:user1:user2:-1";
//            assertEquals("Error: outputs do not match for negative message ID", expected3,
//                    testBufferedReader.readLine());
//            // CASE 4
//            client.removeDM(42, "invalid_user", "user2");
//            String expected4 = "removeDM:invalid_user:user2:42";
//            assertEquals("Error: outputs do not match for invalid loginUsername", expected4,
//                    testBufferedReader.readLine());
//            // CASE 5
//            client.removeDM(42, "user1", "user1");
//            String expected5 = "removeDM:user1:user1:42";
//            assertEquals("Error: outputs do not match when both usernames are the same", expected5,
//                    testBufferedReader.readLine());
//        }
//
//        @Test
//        public void testGetDMChat() throws IOException {
//            // CASE 1
//            client.getDMChat("user1", "user2");
//            String expected1 = "getDMChat:user1:user2";
//            assertEquals("Error: outputs do not match for valid DM chat request", expected1,
//                    testBufferedReader.readLine());
//            // CASE 2 EMPTY DM CHAT
//            client.getDMChat("user3", "user4");
//            String expected2 = "getDMChat:user3:user4";
//            assertEquals("Error: outputs do not match", expected2, testBufferedReader.readLine());
//            // CASE 3
//            client.getDMChat("invalid_user", "user2");
//            String expected3 = "getDMChat:invalid_user:user2";
//            assertEquals("Error: outputs do not match for invalid loginUsername", expected3,
//                    testBufferedReader.readLine());
//            // CASE 4
//            client.getDMChat("user1", "user1");
//            String expected4 = "getDMChat:user1:user1";
//            assertEquals("Error: outputs do not match when both usernames are the same", expected4,
//                    testBufferedReader.readLine());
//        }
//
//        @Test
//        public void testEditUserInfo() throws IOException {
//            // CASE 1
//            client.editUserInfo("oldUser", "newUser", "password123",
//                    "Updated bio", true);
//            String expected1 = "editUserInfo:newUser:password123:true";
//            String expectedBio = "Updated bio";
//            String expectedOldUser = "oldUser";
//            assertEquals("Error: outputs do not match", expected1, testBufferedReader.readLine());
//            assertEquals("Error: outputs do not match", expectedBio, testBufferedReader.readLine());
//            assertEquals("Error: outputs do not match", expectedOldUser, testBufferedReader.readLine());
//            // CASE 2
//            client.editUserInfo("oldUser", "invalid@newUser!", "password123",
//                    "Updated bio", true);
//            String expected2 = "editUserInfo:invalid@newUser!:password123:true";
//            String expectedBio2 = "Updated bio";
//            String expectedOldUser2 = "oldUser";
//            assertEquals("Error: outputs do not match for invalid new loginUsername", expected2,
//                    testBufferedReader.readLine());
//            assertEquals("Error: bio does not match", expectedBio2, testBufferedReader.readLine());
//            assertEquals("Error: old loginUsername does not match", expectedOldUser2,
//                    testBufferedReader.readLine());
//            // CASE 3
//            client.editUserInfo("oldUser", "newUser", "password123",
//                    "", true);
//            String expected3 = "editUserInfo:newUser:password123:true";
//            String expectedBio3 = "";
//            String expectedOldUser3 = "oldUser";
//            assertEquals("Error: outputs do not match for empty bio", expected3, testBufferedReader.readLine());
//            assertEquals("Error: bio does not match for empty bio", expectedBio3, testBufferedReader.readLine());
//            assertEquals("Error: old loginUsername does not match", expectedOldUser3, testBufferedReader.readLine());
//            // CASE 4
//            client.editUserInfo("oldUser", "oldUser", "password123",
//                    "Updated bio", true);
//            String expected4 = "editUserInfo:oldUser:password123:true";
//            String expectedBio4 = "Updated bio";
//            String expectedOldUser4 = "oldUser";
//            assertEquals("Error: outputs do not match for unchanged loginUsername", expected4,
//                    testBufferedReader.readLine());
//            assertEquals("Error: bio does not match for unchanged loginUsername", expectedBio4,
//                    testBufferedReader.readLine());
//            assertEquals("Error: old loginUsername does not match for unchanged loginUsername", expectedOldUser4,
//                    testBufferedReader.readLine());
//        }
//
//        @Test
//        public void testRequestFriend() throws IOException {
//            // CASE 1
//            client.requestFriend("user1", "user2");
//            String expected1 = "requestFriend:user1:user2";
//            assertEquals("Error: outputs do not match", expected1, testBufferedReader.readLine());
//            // CASE 2
//            client.requestFriend("user1", "user1");
//            String expected2 = "requestFriend:user1:user1";
//            assertEquals("Error: outputs do not match for self friend request", expected2,
//                    testBufferedReader.readLine());
//            // CASE 3
//            client.requestFriend("user1", "invalid@loginUsername!");
//            String expected3 = "requestFriend:user1:invalid@loginUsername!";
//            assertEquals("Error: outputs do not match for invalid target loginUsername", expected3,
//                    testBufferedReader.readLine());
//            // CASE 4
//            client.requestFriend("", "user2");
//            String expected4 = "requestFriend::user2";
//            assertEquals("Error: outputs do not match for empty requester loginUsername", expected4,
//                    testBufferedReader.readLine());
//            // CASE 5
//            client.requestFriend("user1", "");
//            String expected5 = "requestFriend:user1:";
//            assertEquals("Error: outputs do not match for empty recipient loginUsername", expected5,
//                    testBufferedReader.readLine());
//        }
//
//        @Test
//        public void testAcceptFriend() throws IOException {
//            // CASE 1
//            client.acceptFriend("user1", "user2");
//            String expected1 = "acceptFriend:user1:user2";
//            assertEquals("Error: outputs do not match", expected1, testBufferedReader.readLine());
//            // CASE 2
//            client.acceptFriend("user1", "user1");
//            String expected2 = "acceptFriend:user1:user1";
//            assertEquals("Error: outputs do not match for accepting friend request from self",
//                    expected2, testBufferedReader.readLine());
//            // CASE 3
//            client.acceptFriend("user1", "invalid@loginUsername!");
//            String expected3 = "acceptFriend:user1:invalid@loginUsername!";
//            assertEquals("Error: outputs do not match for accepting friend request with invalid loginUsername",
//                    expected3, testBufferedReader.readLine());
//            // CASE 4
//            client.acceptFriend("", "user2");
//            String expected4 = "acceptFriend::user2";
//            assertEquals("Error: outputs do not match for accepting friend request with " +
//                    "empty requester loginUsername", expected4, testBufferedReader.readLine());
//        }
//
//        @Test
//        public void testRejectFriend() throws IOException {
//            // CASE 1
//            client.rejectFriend("user1", "user2");
//            String expected1 = "rejectFriend:user1:user2";
//            assertEquals("Error: outputs do not match", expected1, testBufferedReader.readLine());
//            // CASE 2
//            client.rejectFriend("user1", "user1");
//            String expected2 = "rejectFriend:user1:user1";
//            assertEquals("Error: outputs do not match for rejecting friend request from self",
//                    expected2, testBufferedReader.readLine());
//            // CASE 3
//            client.rejectFriend("user1", "invalid@loginUsername!");
//            String expected3 = "rejectFriend:user1:invalid@loginUsername!";
//            assertEquals("Error: outputs do not match for rejecting friend request to an invalid loginUsername",
//                    expected3, testBufferedReader.readLine());
//            // CASE 4
//            client.rejectFriend("", "user2");
//            String expected4 = "rejectFriend::user2";
//            assertEquals("Error: outputs do not match for rejecting with empty requester loginUsername",
//                    expected4, testBufferedReader.readLine());
//        }
//
//        @Test
//        public void testAddBlocked() throws IOException {
//            // CASE 1
//            client.addBlocked("user1", "user2");
//            String expected1 = "addBlocked:user1:user2";
//            assertEquals("Error: outputs do not match", expected1, testBufferedReader.readLine());
//            // CASE 2
//            client.addBlocked("user1", "user1");
//            String expected2 = "addBlocked:user1:user1";
//            assertEquals("Error: outputs do not match for blocking oneself", expected2,
//                    testBufferedReader.readLine());
//            // CASE 3
//            client.addBlocked("user1", "invalid@loginUsername!");
//            String expected3 = "addBlocked:user1:invalid@loginUsername!";
//            assertEquals("Error: outputs do not match for blocking with invalid loginUsername", expected3,
//                    testBufferedReader.readLine());
//            // CASE 4
//            client.addBlocked("", "user2");
//            String expected4 = "addBlocked::user2";
//            assertEquals("Error: outputs do not match for blocking with empty requester loginUsername",
//                    expected4, testBufferedReader.readLine());
//        }
//
//        @Test
//        public void testRemoveBlocked() throws IOException {
//            // CASE 1
//            client.removeBlocked("user1", "user2");
//            String expected1 = "removeBlocked:user1:user2";
//            assertEquals("Error: outputs do not match", expected1, testBufferedReader.readLine());
//            // CASE 2
//            client.removeBlocked("user1", "user1");
//            String expected2 = "removeBlocked:user1:user1";
//            assertEquals("Error: outputs do not match for unblocking oneself", expected2,
//                    testBufferedReader.readLine());
//            // CASE 3
//            client.removeBlocked("user1", "invalid@loginUsername!");
//            String expected3 = "removeBlocked:user1:invalid@loginUsername!";
//            assertEquals("Error: outputs do not match for unblocking with invalid loginUsername",
//                    expected3, testBufferedReader.readLine());
//            // CASE 4
//            client.removeBlocked("", "user2");
//            String expected4 = "removeBlocked::user2";
//            assertEquals("Error: outputs do not match for removing block with empty requester loginUsername",
//                    expected4, testBufferedReader.readLine());
//        }
//
//        @Test
//        public void testAddUser() throws IOException {
//            // CASE 1
//            client.addUser("newUser", "password123", "Test bio", true);
//            String expected1 = "addUser:newUser:password123:true";
//            String expectedBio1 = "Test bio";
//            assertEquals("Error: outputs should match", expected1, testBufferedReader.readLine());
//            assertEquals("Error: bios should match", expectedBio1, testBufferedReader.readLine());
//            // CASE 2
//            client.addUser("us", "password123", "Invalid user", false);
//            String expected2 = "addUser:us:password123:false";
//            String expectedBio2 = "Invalid user";
//            assertEquals("Error: outputs should match for invalid loginUsername", expected2,
//                    testBufferedReader.readLine());
//            assertEquals("Error: bios should match for invalid loginUsername", expectedBio2,
//                    testBufferedReader.readLine());
//            // CASE 3
//            client.addUser("", "", "Empty loginUsername or loginPassword", false);
//            String expected3 = "addUser:::false";
//            String expectedBio3 = "Empty loginUsername or loginPassword";
//            assertEquals("Error: outputs should match for empty loginUsername or loginPassword", expected3,
//                    testBufferedReader.readLine());
//            assertEquals("Error: bios should match for empty loginUsername or loginPassword", expectedBio3,
//                    testBufferedReader.readLine());
//            // CASE 4
//            client.addUser("existingUser", "newpassword123", "Trying with an existing loginUsername",
//                    false);
//            String expected4 = "addUser:existingUser:newpassword123:false";
//            String expectedBio4 = "Trying with an existing loginUsername";
//            assertEquals("Error: outputs should match for existing loginUsername", expected4,
//                    testBufferedReader.readLine());
//            assertEquals("Error: bios should match for existing loginUsername", expectedBio4,
//                    testBufferedReader.readLine());
//        }
//
//        @Test
//        public void testUserSearch() throws IOException {
//            // CASE 1
//            client.userSearch("user1");
//            String expected1 = "userSearch:user1";
//            assertEquals("Error: outputs should match", expected1,
//                    testBufferedReader.readLine());
//            // CASE 2
//            client.userSearch("nonExistentUser");
//            String expected2 = "userSearch:nonExistentUser";
//            assertEquals("Error: outputs should match for non-existent loginUsername", expected2,
//                    testBufferedReader.readLine());
//            // CASE 3
//            client.userSearch("");
//            String expected3 = "userSearch:";
//            assertEquals("Error: outputs should match for empty loginUsername", expected3,
//                    testBufferedReader.readLine());
//            // CASE 4
//            client.userSearch("user@123!");
//            String expected4 = "userSearch:user@123!";
//            assertEquals("Error: outputs should match for loginUsername with special characters", expected4,
//                    testBufferedReader.readLine());
//        }
//
//        @Test
//        public void testGetProfilePicture() throws IOException {
//            // CASE 1
//            client.getProfilePicture("user1");
//            String expected1 = "getProfilePicture:user1";
//            assertEquals("Error: outputs should match", expected1, testBufferedReader.readLine());
//            // CASE 2
//            client.getProfilePicture("userWithoutPic");
//            String expected2 = "getProfilePicture:userWithoutPic";
//            assertEquals("Error: outputs should match for loginUsername with no profile picture",
//                    expected2, testBufferedReader.readLine());
//            // CASE 3
//            client.getProfilePicture("nonExistentUser");
//            String expected3 = "getProfilePicture:nonExistentUser";
//            assertEquals("Error: outputs should match for non-existent loginUsername",
//                    expected3, testBufferedReader.readLine());
//            // CASE 4
//            client.getProfilePicture("user@123!");
//            String expected4 = "getProfilePicture:user@123!";
//            assertEquals("Error: outputs should match for loginUsername with special characters",
//                    expected4, testBufferedReader.readLine());
//        }
//
//        @Test
//        public void testSaveProfilePicture() throws IOException {
//            // CASE 1
//            String username = "testUser";
//            byte[] imageData = {1, 2, 3, 4, 5};
//            client.saveProfilePicture(username, imageData);
//            assertEquals("saveProfilePicture:" + username, testBufferedReader.readLine());
//
//            // CASE 2
//            byte[] emptyProfilePicture = new byte[0];
//            client.saveProfilePicture("userWithoutPic", emptyProfilePicture);
//
//            BufferedReader readerEmpty = new BufferedReader(new InputStreamReader(pipedInputStream));
//            String emptyCommand = readerEmpty.readLine();
//            assertEquals("saveProfilePicture:userWithoutPic", emptyCommand);
//
//            byte[] emptyReceivedData = new byte[emptyProfilePicture.length];
//            int bytesRead = pipedInputStream.read(emptyReceivedData);
//            assertEquals(emptyProfilePicture.length, bytesRead);
//            assertArrayEquals(emptyProfilePicture, emptyReceivedData);
//        }
//
//        @Test
//        public void testSeeUserInfo() throws IOException {
//            // CASE 1
//            client.seeUserInfo("user1", 1);
//            String expected1 = "seeUserInfo:user1:1";
//            assertEquals("Error: outputs should match", expected1, testBufferedReader.readLine());
//            // CASE 2
//            client.seeUserInfo("loginUsername", 1);
//            String expected2 = "seeUserInfo:loginUsername:1";
//            assertEquals("Error: outputs should match", expected2, testBufferedReader.readLine());
//            // CASE 3
//            client.seeUserInfo("loginUsername", 2);
//            String expected3 = "seeUserInfo:loginUsername:2";
//            assertEquals("Error: outputs should match", expected3, testBufferedReader.readLine());
//            // CASE 4
//            client.seeUserInfo("invalidUsername", 0);
//            String expected4 = "seeUserInfo:invalidUsername:0";
//            assertEquals("Error: outputs should match", expected4, testBufferedReader.readLine());
//        }
//
//        @Test
//        public void testLogIn() throws IOException {
//            // CASE 1
//            client.logIn("user1", "password123");
//            String expected1 = "logIn:user1:password123";
//            assertEquals("Error: outputs should match", expected1, testBufferedReader.readLine());
//            // CASE 2
//            client.logIn("user1", "wrongPassword");
//            String expected2 = "logIn:user1:wrongPassword";
//            assertEquals("Error: outputs should match", expected2, testBufferedReader.readLine());
//            // CASE 3
//            client.logIn("nonExistentUser", "password123");
//            String expected3 = "logIn:nonExistentUser:password123";
//            assertEquals("Error: outputs should match", expected3, testBufferedReader.readLine());
//            // CASE 4
//            client.logIn("", "password123");
//            String expected4 = "logIn::password123";
//            assertEquals("Error: outputs should match", expected4, testBufferedReader.readLine());
//        }
//
//        @Test
//        public void testRemoveUser() throws IOException {
//            // CASE 1
//            client.removeUser("user1");
//            String expected1 = "removeUser:user1";
//            assertEquals("Error: outputs should match", expected1, testBufferedReader.readLine());
//            // CASE 2
//            client.removeUser("nonExistentUser");
//            String expected2 = "removeUser:nonExistentUser";
//            assertEquals("Error: outputs should match for non-existent user",
//                    expected2, testBufferedReader.readLine());
//            // CASE 3
//            client.removeUser("user_with_special@chars");
//            String expected3 = "removeUser:user_with_special@chars";
//            assertEquals("Error: outputs should match for special character user",
//                    expected3, testBufferedReader.readLine());
//        }
//
//        @Test
//        public void testRemoveFriend() throws IOException {
//            // CASE 1
//            client.removeFriend("user1", "user2");
//            String expected = "removeFriend:user1:user2";
//            assertEquals("Error: outputs should match", expected, testBufferedReader.readLine());
//            // CASE 2
//            client.removeFriend("user1", "nonExistentUser");
//            String expected2 = "removeFriend:user1:nonExistentUser";
//            assertEquals("Error: outputs should match for non-existent friend",
//                    expected2, testBufferedReader.readLine());
//            // CASE 3
//            client.removeFriend("user_with_special@chars", "friend_with_special#chars");
//            String expected3 = "removeFriend:user_with_special@chars:friend_with_special#chars";
//            assertEquals("Error: outputs should match for special character usernames",
//                    expected3, testBufferedReader.readLine());
//        }
    }

    /**
     * A TestServer class which runs test cases for
     * each method/constructor in the Server class.
     * Also checks to see whether the classes are implementing the right things.
     *
     * @author klakoji npien-purdue rpadhye
     * @version November 17, 2024
     */

    public static class TestServer {
        @Test(timeout = 1000)
        public void serverClassDeclarationTest() {
            Class<?> clazz;
            int modifiers;
            Class<?> superclass;
            Class<?>[] superinterfaces;

            // Set the class being tested
            clazz = Server.class;

            // Perform tests

            modifiers = clazz.getModifiers();

            superclass = clazz.getSuperclass();

            superinterfaces = clazz.getInterfaces();

            assertTrue("Ensure that `Server` is `public`!", Modifier.isPublic(modifiers));

            assertFalse("Ensure that `Server` is NOT `abstract`!", Modifier.isAbstract(modifiers));

            assertEquals("Ensure that `Server` extends `Thread`!", Thread.class, superclass);

            assertEquals("Ensure that `Server` implements the ServerInterface",
                    ServerInterface.class, superinterfaces[0]);
        }

        @Test
        public void testServerConstructor() {
            // CASE 1
            Server server = new Server();
            Database db = server.getDatabase();

            assertNotNull("Error: Database object should not be null", db);
            assertEquals("Error: UserInfo path is incorrect",
                    "Database/UserInfo", db.getUserInfoPath());
            assertEquals("Error: DirectMessages path is incorrect",
                    "Database/DirectMessages", db.getDirectMessagesPath());
            assertEquals("Error: ProfilePictures path is incorrect",
                    "Database/ProfilePictures", db.getProfilePicturesPath());

        }

    }

    /**
     * A TestServer class which runs test cases for
     * each method/constructor in the Server class.
     * Also checks to see whether the classes are implementing the right things.
     *
     * @author klakoji npien-purdue rpadhye
     * @version November 17, 2024
     */

    public static class TestGUI {
        @Test(timeout = 1000)
        public void serverClassDeclarationTest() {
            Class<?> clazz;
            int modifiers;
            Class<?> superclass;
            Class<?>[] superinterfaces;

            // Set the class being tested
            clazz = GUI.class;

            // Perform tests

            modifiers = clazz.getModifiers();

            superclass = clazz.getSuperclass();

            superinterfaces = clazz.getInterfaces();

            assertTrue("Ensure that `GUI` is `public`!", Modifier.isPublic(modifiers));

            assertFalse("Ensure that `GUI` is NOT `abstract`!", Modifier.isAbstract(modifiers));

            assertEquals("Ensure that `GUI` extends `JComponent`!", JComponent.class, superclass);

            assertEquals("Ensure that `GUI` implements the GUIInterface",
                    ServerInterface.class, superinterfaces[1]);
            assertEquals("Ensure that `GUI` implements  Runnable",
                    Runnable.class, superinterfaces[0]);
        }

        @Test
        public void testGUIConstructor() {
            GUI g = new GUI(new Client());

            assertNotNull("Error: GUI object should not be null", g);

        }

    }
}
