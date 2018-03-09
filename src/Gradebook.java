import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/*Implemented by: Felipe and Redden*/

/**
 * This is the "main" class that contains the main method.
 * Opens up Initial view of Gradebook and offers options
 * to select from depending on if a user has already been
 * created. Then creates new objects and calls there starting
 * functions to take the user to the next command prompt or
 * profile input.
 */

public class Gradebook {

    /**
     * used to keep track of existing user
     * */
    private static boolean UserExists = false;

    public static File UserDir;
    public static String userPath = System.getProperty("user.dir")+"/user_profile/";

    /**
     * Main function for program.  Prints welcome and goodbye
     * messages and calls startup from a Gradebook object.
     * @param   args    command line arguments(unused)
     * */
    public static void main(String[] args) {
        System.out.println("---------------------------------------");
        System.out.println("\tWelcome To GradeBook");
        System.out.println("---------------------------------------");
        UserDir = new File(userPath);

        if (UserDir.exists()) { UserExists = true; }

        CreateUser createUser = new CreateUser();

        String input;
        printDashboard();
        while(!(input = getInput()).matches("q")) {

            if (input.matches("1") && UserExists) {
                UserProfile user = new UserProfile();
                user.startup();
            }
            else if(input.matches("1") && !UserExists){
                UserExists = true;
                UserDir.mkdir();
                createUser.createNewUser();
            }
            else if(input.matches("2") && UserExists) {
                deleteDir(UserDir);
                UserExists = false;
            }
            else if(input.matches("3") && UserExists) {
                if(!createUser.createTestUser()) System.out.println("User has not been overwritten");
            }
            else if(input.matches("2") && !UserExists) {
                UserDir.mkdir();
                if(!createUser.createTestUser()) {deleteDir(UserDir);
                System.out.println("Test User not created");}
                else UserExists = true;
            }
            else System.out.println("Sorry, Try Again.");
            printDashboard();
        }
        System.out.println("Thank You For Using GradeBook!");
    }

    /**
     * Deletes a directory recursively.
     * @param   dir a File object of the user_profile directory
     * @return      the success/failure of the deletion
     * */
    private static boolean deleteDir(File dir) {
        File[] files = dir.listFiles();
        if(files != null) {
            for (File f : files) {
                deleteDir(f);
            }
        }
        return dir.delete();
    }

    /**
     * Simple printing function that prints the command
     * dashboard to the console.
     * Different command options are printed if a user
     * profile has been created.
     * */
    private static void printDashboard() {
        System.out.println("---------------------------------------");
        System.out.println("\tEnter the Option Number:");
        if(UserExists) {
            System.out.println("\t1: Access User Profile");
            System.out.println("\t2: Reset User Profile(Will Delete Current User)");
            System.out.println("\t3: Create Test Profile(Will OverWrite Current User)");
            System.out.println("\tq: Exit");
        } else {
            System.out.println("\t1: Create User Profile");
            System.out.println("\t2: Create Test Profile");
            System.out.println("\tq: Exit");
        }
        System.out.println("---------------------------------------");
    }

    /**
     * Scans the command input for specific command options 1-4.
     * If bad input is given, 0 is returned.
     * @return      input code corresponding to command option
     * */
    private static String getInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
