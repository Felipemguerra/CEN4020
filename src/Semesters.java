import java.io.File;
import java.util.Scanner;

public class Semesters {

    public static String semestersPath = StartMenu.userPath+"semesters/";

    private int semesterCount = 0;

    private File[] semesters = {};

    private String C_DASH = "0";
    private String C_QUIT = "q";
    private String C_ADD = "\\+";

    public Semesters() {
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

    private String getInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
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
