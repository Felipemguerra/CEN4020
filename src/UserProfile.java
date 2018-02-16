import java.io.*;
import java.util.Scanner;

public class UserProfile {

    private String FirstName;
    private String LastName;

    public void startup(int mode) {
        /*mode 0: Real User
        * mode 1: Test User*/
        if(mode == 1) {
            File TestUserFile = new File(System.getProperty("user.dir")+"/src/test_profile/user");
            populate(TestUserFile);
            Major TestMajor = new Major();
            int input;
            while((input = getInput()) != 6) {
                if(input == 1) {
                    System.out.println("TEST: Showing major");
                    TestMajor.TestShowMajor();
                }
                else if(input == 2) {
                    System.out.println("TEST: Showing progress");
                    TestMajor.TestShowProgress();
                }
                else if(input == 3) {
                    TestMajor.TestChangeMajor();
                }
                else if(input == 4) {
                    System.out.println("Showing GPA(Not Yet Implemented)");
                }
                else if(input == 5) {
                    System.out.println("Going To Semesters(Not Yet Implemented)");
                }
                else System.out.println("TEST ERROR: BAD OPTION INPUT");
            }
        }
        else {
            File UserFile = new File(System.getProperty("user.dir")+"/src/user_profile/user");
            populate(UserFile);
            Major UserMajor = new Major();
            int input;
            while((input = getInput()) != 6) {
                if(input == 1) {
                    UserMajor.ShowMajor();
                }
                else if(input == 2) {
                    UserMajor.ShowProgress();
                }
                else if(input == 3) {
                    UserMajor.ChangeMajor();
                }
                else if(input == 4) {
                    System.out.println("Showing GPA(Not Yet Implemented)");
                }
                else if(input == 5) {
                    System.out.println("Going To Semesters(Not Yet Implemented)");
                }
                else System.out.println("Sorry, Try Again.");
            }
        }
    }

    /*takes the information from the user file in a given user directory
    * to populate the User Profile*/
    private void populate(File user) {
        try {
            FileReader InputStream = new FileReader(user);
            BufferedReader BuffReader = new BufferedReader(InputStream);
            FirstName = BuffReader.readLine();
            LastName = BuffReader.readLine();
        }
        catch (FileNotFoundException FNF) {}
        catch (IOException IOE) {}
    }

    /*takes in input corresponding to Dashboard options*/
    private int getInput() {
        printDashboard();
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        if (input.matches("1"))
            return 1;
        if (input.matches("2"))
            return 2;
        if (input.matches("3"))
            return 3;
        if (input.matches("4"))
            return 4;
        if (input.matches("5"))
            return 5;
        if (input.matches("6"))
            return 6;
        return 0;
    }

    private void printDashboard() {
        System.out.println("---------------------------------------");
        System.out.println("Hello " + FirstName + " " + LastName);
        System.out.println("\tEnter the Option Number");
        System.out.println("\t1: Show Major");
        System.out.println("\t2: Show Progress");
        System.out.println("\t3: Change Major");
        System.out.println("\t4: Show GPA(Not Yet Implemented)");
        System.out.println("\t5: Go To Semesters(Not Yet Implemented)");
        System.out.println("\t6: Go Back");
        System.out.println("---------------------------------------");
    }
}