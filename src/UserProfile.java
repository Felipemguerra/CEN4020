import java.io.*;
import java.util.Scanner;

/*Implemented by Daniel and Redden*/

/**
 * This class offers a way to access the user profile and
 * is where further iterations will continue.
 * */
public class UserProfile{

    public static String userPath = Gradebook.userPath+"user";

    public static String semestersPath = Gradebook.userPath+"semesters/";

    /**
     * stores first name from user profile
     * */
    private String FirstName;

    /**
     * stores last name from user profile
     * */
    private String LastName;

    private int semesterCount = 0;

    private File[] semesters = {};

    private String C_DASH = "0";
    private String C_MAJOR = "1";
    private String C_PROG = "2";
    private String C_CHANGE = "3";
    private String C_SEM = "4";
    private String C_QUIT = "q";
    private String C_ADD = "\\+";

    /**
     * Starts up the command menu and allows user to select several options
     * to access and edit user profile.
     * */
    public void startup() {
        getName();
        Major UserMajor = new Major();
        String input;
        printProfileDashboard();
        while(!(input = getInput()).matches(C_QUIT)) {
            if(input.matches(C_DASH)) printProfileDashboard();
            else if(input.matches(C_MAJOR)) UserMajor.ShowMajor();
            else if(input.matches(C_PROG)) UserMajor.ShowProgress();
            else if(input.matches(C_CHANGE)) {
                UserMajor.ChangeMajor(FirstName, LastName);
                printProfileDashboard();
            }
            else if(input.matches(C_SEM)) {
                Semesters();
                printProfileDashboard();
            }
            else System.out.println("Sorry, Try Again.");
            System.out.println("Press 0 For Menu");
        }
    }

    /**takes the first and last name from the user profile in a given user directory
    * to populate the User Profile
     * */
    private void getName() {
        File user = new File(System.getProperty("user.dir")+"/user_profile/user");
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
    private String getInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    /**
     * Command option printing method.
     * */
    private void printProfileDashboard() {
        System.out.println("---------------------------------------");
        System.out.println("\tGrade: " + getGrade());
        System.out.println("---------------------------------------");
        System.out.println("Hello " + FirstName + " " + LastName);
        System.out.println("\tEnter the Option Number");
        System.out.println("\t0: Show Menu");
        System.out.println("\t1: Show Major");
        System.out.println("\t2: Show Progress");
        System.out.println("\t3: Change Major");
        System.out.println("\t4: Go To Semesters");
        System.out.println("\tq: Go Back");
        System.out.println("---------------------------------------");
    }

    private static long getGrade() {
        return 0;
    }


    private void Semesters() {
        getSemesters();
        printSemestersDashboard();
        String input;
        while(!(input = getInput()).matches(C_QUIT)) {
            if(input.matches(C_ADD)) addSemester();
            else if(input.matches(C_DASH)) printSemestersDashboard();
            else if(input.matches("[1-9]+")) {
                int sem = Integer.parseInt(input);
                if(sem > 0 && sem <= semesterCount) {
                    new Semester().startup(semesters[sem-1]);
                    printSemestersDashboard();
                }
                else System.out.println("Sorry, Try Again.");
            }
            else System.out.println("Sorry, Try Again.");
            System.out.println("Press 0 For Menu");
        }
    }

    private void printSemestersDashboard() {
        System.out.println("---------------------------------------");
        System.out.println("\t"+C_QUIT+": Go Back");
        System.out.println("\t+: Add Semester");
        System.out.println("\t0: Show Menu");
        printSemesters();
        System.out.println("---------------------------------------");
    }

    private void printSemesters() {
        for(int i = 0; i < semesterCount; ++i) {
            System.out.println("\t" + (i+1) + ": " + semesters[i].getName());
        }
    }

    private void getSemesters() {
        File sems = new File(System.getProperty("user.dir")+"/user_profile/semesters");
        if(!sems.exists()){ sems.mkdir(); }
        semesters = sems.listFiles();
        semesterCount = semesters.length;
    }

    private void addSemester() {
        File dir = new File(System.getProperty("user.dir") + "/user_profile/semesters");
        boolean get = true;
        String input = "";
        while(get) {
            get = false;
            input = getSemesterFromConsole();
            for(File i : semesters) {
                if(i.getName().matches(input)) {
                    get = true;
                    System.out.println("Semester Already Exists, Try Again");
                    break;
                }}
        }
        addNewSemester(input);
        getSemesters();
    }

    private void addNewSemester(String name) {
        File newSemester = new File(semestersPath + name);
        newSemester.mkdir();
    }

    private String getSemesterFromConsole() {
        Scanner scanner = new Scanner(System.in);
        String semester = "", buffer = "";
        boolean get = true;
        while(get) {
            get = false;
            System.out.println("Enter Semester Name: ");
            buffer = scanner.nextLine();
            if(!buffer.matches("(?i)Fall") && !buffer.matches("(?i)Spring") && !buffer.matches("(?i)Summer")) {
                get = true;
                System.out.println("Bad Name, Try Again");
            }
        }
        buffer = buffer.substring(0, 1).toUpperCase() + buffer.substring(1);
        semester += buffer + " ";
        get = true;
        while(get) {
            get = false;
            System.out.println("Enter Semester Year: ");
            buffer = scanner.nextLine();
            if(!buffer.matches("[0-9][0-9][0-9][0-9]")) {
                get = true;
                System.out.println("Bad Year, Try Again");
            }
        }
        semester += buffer;
        return semester;
    }
}
