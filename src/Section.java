import java.io.*;
import java.util.Scanner;

/*
* implemented by Redden and Brian
*/

public class Section {

    static private File Section;

    static private File[] assignments;

    static private int assignmentCount = 0;

    public static void startup(File section) {
        Section = section;
        getAssignments();
        printSectionDashboard();
        String input;
        while(!(input = getInput()).matches("q")) {
            if(input.matches("0")) {
                printSectionDashboard();
            }
            else if(input.matches("\\+")) {
                addAssignment();
                getAssignments();
                printSectionDashboard();
            }
            else if(input.matches("[1-9]+")) {
                int sec = Integer.parseInt(input);
                if(sec > 0 && sec <= assignmentCount+1) {
                    Assignment.startup(assignments[sec-1]);
                    printSectionDashboard();
                }
                else System.out.println("Sorry, Try Again.");
            }
            else System.out.println("Sorry, Try Again.");
            System.out.println("Press 0 For Menu");
        }
    }
    private static String getInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private static void getAssignments() {
        assignments = Section.listFiles();
        assignmentCount = assignments.length;
    }

    private static void printSectionDashboard() {
        System.out.println("---------------------------------------");
        if(getSectionGrade(Section) == -1) System.out.println("\tNo Grade");
        else System.out.println("\tGrade: " + String.format("%.2f",(getSectionGrade(Section)*100)));
        System.out.println("---------------------------------------");
        System.out.println("\tq: Go Back");
        System.out.println("\t+: Add Assignment");
        System.out.println("\t0: Show Menu");
        printAssignments();
        System.out.println("---------------------------------------");
    }

    private static void printAssignments() {
        for(int i = 0; i < assignmentCount; ++i) {
            System.out.println("\t" + (i+1) + ": " + assignments[i].getName());
        }
    }

    private static void addAssignment() {
        boolean get = true;
        String input = "";
        while(get) {
            get = false;
            input = getAssignmentNameFromConsole();
            for(File i : assignments) {
                if(i.getName().matches("(?i)"+input)) {
                    get = true;
                    System.out.println("Class Already Exists, Try Again");
                    break;
                }
            }
        }
        addNewAssignment(input);
    }

    private static void addNewAssignment(String name) {
        File newAssignment = new File(Section.getAbsolutePath() + "/" +name);
        try {
            newAssignment.createNewFile();
        }
        catch(IOException IOE) {}
        getAssignmentInfo(newAssignment);
    }

    static private String getAssignmentNameFromConsole() {
        Scanner scanner = new Scanner(System.in);
        String buffer = "";
        System.out.println("Enter Assignment Name: ");
        buffer = scanner.nextLine();
        return buffer;
    }

    static private void getAssignmentInfo(File assignment) {
        Scanner scanner = new Scanner(System.in);
        String buffer = "";
        boolean get = true;
        while(get) {
            get = false;
            System.out.println("Enter Point Total: ");
            buffer = scanner.nextLine();
            if(!buffer.matches("[1-9][0-9]*") ) {
                get = true;
                System.out.println("Try Again");
            }
        }
        try {
            BufferedWriter BuffWriter = new BufferedWriter(new FileWriter(assignment));
            BuffWriter.write("-1");
            BuffWriter.write("+");
            BuffWriter.write(buffer);
            BuffWriter.close();
        }
        catch(IOException IOE) {}
    }

    public static float getSectionGrade(File section) {
        File[] files = section.listFiles();
        float grade = 0;
        int total = 0;
        boolean nullFile = true;
        if(files.length == 0) return -1;
        for(int i =0; i < files.length; ++i) {
            if(Assignment.getGrade(files[i]) >= 0) {
                grade += Assignment.getGrade(files[i]);
                total++;
                nullFile = false;
            }
        }
        if(nullFile) return -1;
        grade /= total;
        return grade;
    }
}