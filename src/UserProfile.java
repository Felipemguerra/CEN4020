import java.io.*;
import java.util.Scanner;

/*Implemented by Daniel and Redden*/

/**
 * This class offers a way to access the user profile and
 * is where further iterations will continue.
 * */
public class UserProfile {

    /**
     * stores first name from user profile
     * */
    private String FirstName;

    /**
     * stores last name from user profile
     * */
    private String LastName;

    /**
     * 0: user mode
     * 1: test mode
     */
    private int mode;

    /**
     * Starts up the command menu and allows user to select several options
     * to access and edit user profile.
     * @param   m   mode
     * */
    public void startup(int m) {
        mode = m;
        populate();
        Major UserMajor = new Major();
        int input;
        printDashboard();
        while((input = getInput()) != 6) {
            if(input == 0) printDashboard();
            else if(input == 1) UserMajor.ShowMajor();
            else if(input == 2) UserMajor.ShowProgress();
            else if(input == 3) UserMajor.ChangeMajor(FirstName, LastName);
            else if(input == 4) System.out.println("Showing GPA(Not Yet Implemented)");
            else if(input == 5) System.out.println("Going To Semesters(Not Yet Implemented)");
            else System.out.println("Sorry, Try Again.");
            System.out.println("Press 0 For Menu");
        }
    }

    /**takes the first and last name from the user profile in a given user directory
    * to populate the User Profile
     * */
    private void populate() {
        File user = new File(System.getProperty("user.dir")+"/../user_profile/user");
        try {
            FileReader InputStream = new FileReader(user);
            BufferedReader BuffReader = new BufferedReader(InputStream);
            FirstName = BuffReader.readLine();
            LastName = BuffReader.readLine();
            BuffReader.close();
        }
        catch (FileNotFoundException FNF) {}
        catch (IOException IOE) {}
    }

    /**
     * takes in input corresponding to Dashboard options
     * @return input code, -1 if bad input
     * */
    private int getInput() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        if (input.matches("0")) return 0;
        if (input.matches("1")) return 1;
        if (input.matches("2")) return 2;
        if (input.matches("3")) return 3;
        if (input.matches("4")) return 4;
        if (input.matches("5")) return 5;
        if (input.matches("6")) return 6;
        return -1;
    }

    /**
     * Command option printing method.
     * */
    private void printDashboard() {
        System.out.println("---------------------------------------");
        if (mode == 1)System.out.println("TEST");
        System.out.println("Hello " + FirstName + " " + LastName);
        System.out.println("\tEnter the Option Number");
        System.out.println("\t0: Show Menu");
        System.out.println("\t1: Show Major");
        System.out.println("\t2: Show Progress");
        System.out.println("\t3: Change Major");
        System.out.println("\t4: Show GPA(Not Yet Implemented)");
        System.out.println("\t5: Go To Semesters(Not Yet Implemented)");
        System.out.println("\t6: Go Back");
        System.out.println("---------------------------------------");
    }
}
