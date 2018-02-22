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

    /*used to keep track of existing user*/
    private boolean UserExists = false;

    /* mode 0: Real User
     * mode 1: Test User
     */
    private int mode;

    /**
     * Main function for program.  Prints welcome and goodbye
     * messages and calls startup from a Gradebook object.
     * @param   args    command line arguments(unused)
     * */
    public static void main(String[] args) {
        System.out.println("---------------------------------------");
        System.out.println("\tWelcome To GradeBook");
        System.out.println("---------------------------------------");

        Gradebook GB = new Gradebook();
        GB.startup();
        System.out.println("Thank You For Using GradeBook!");
    }

    private void startup() {
        File UserDir = new File(System.getProperty("user.dir")+"/user_profile");     //get user_profile directory, existing or not
        File TestCheck = new File(UserDir+"/test");                                     //this file is only in user_profile if user is a test user

        if (UserDir.exists()) {                                                                   //check existence of user_profile
            UserExists = true;
            if (TestCheck.exists()) mode = 1;                                                     //if test file exists, change the mode
            else mode = 0;
        }

        boolean fin = true;                                                                       //only needed because there are multiple command codes to quit
        while(fin) {
            printDashboard();
            int input = getInput();

            if (input == 1 && UserExists) {                                                       //go to user profile if user exists, provide user mode
                UserProfile user = new UserProfile();
                user.startup(mode);
            }
            else if(input == 1 && !UserExists){                                                   //create new user from command input if user doesn't exist
                mode = 0;
                UserExists = true;
                UserDir.mkdir();
                UpdateUser NewUser = new UpdateUser();
                NewUser.make();
            }
            else if(input == 2 && UserExists) {                                                   //Delete current user
                deleteDir(UserDir);
                mode = 0;
                UserExists = false;
            }
            else if(input == 3 && UserExists) {                                                   //override current user with new test user
                UpdateUser TestUser = new UpdateUser();
                if(TestUser.runTests()) {
                    try {TestCheck.createNewFile();}
                    catch(IOException IOE) {}
                    mode = 1;
                }
                else System.out.println("User has not been overwritten");
            }
            else if(input == 2 && !UserExists) {                                                  //create new test user
                UserDir.mkdir();
                UpdateUser TestUser = new UpdateUser();
                if(TestUser.runTests()) {
                    try { TestCheck.createNewFile();}
                    catch(IOException IOE) {}
                    mode = 1;
                    UserExists = true;
                }
                else deleteDir(UserDir);
            }
            else if((input == 4 && UserExists) || (input == 3 && !UserExists)) fin = false;       //exit loop
            else System.out.println("Sorry, Try Again.");                                         //error checking for incorrect input command
        }
    }

    /**
     * Deletes a directory with only non-directory files.
     * First deletes each file in the directory and then
     * deletes the actual directory.
     * @param   dir a File object of the user_profile directory
     * @return      the success/failure of the deletion
     * */
    private boolean deleteDir(File dir) {
        String[] files = dir.list();
        if(files == null) return false;
        if(files.length == 0) return dir.delete();
        for(String f: files){
            File temp = new File(dir.getPath()+"/"+f);
            if(temp.isDirectory()){System.err.println("Directory contains another directory");return false;}
        }
        for(String f: files){
            File temp = new File(dir.getPath()+"/"+f);
            temp.delete();
        }
        return dir.delete();
    }

    /**
     * Simple printing function that prints the command
     * dashboard to the console.
     * Different command options are printed if a user
     * profile has been created.
     * */
    private void printDashboard() {
        System.out.println("---------------------------------------");
        System.out.println("\tEnter the Option Number:");
        if(UserExists) {
            System.out.println("\t1: Access User Profile");
            System.out.println("\t2: Reset User Profile(Will Current User)");
            System.out.println("\t3: Create Test Profile(Will OverWrite Current User)");
            System.out.println("\t4: Exit");
        } else {
            System.out.println("\t1: Create User Profile");
            System.out.println("\t2: Create Test Profile");
            System.out.println("\t3: Exit");
        }
        System.out.println("---------------------------------------");
    }

    /**
     * Scans the command input for specific command options 1-4.
     * If bad input is given, 0 is returned.
     * @return      input code corresponding to command option
     * */
    private int getInput() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        if (input.matches("1")) return 1;
        if (input.matches("2")) return 2;
        if (input.matches("3")) return 3;
        if (input.matches("4")) return 4;
        return 0;
    }
}
