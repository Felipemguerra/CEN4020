import java.io.*;
import java.util.Scanner;

/**
 * A class that offers functionality to add and view a semester
 * implemented by Felipe and Daniel
 * */

public class Semester {

    private File Semester;

    private File[] classes = {};

    private int classCount = 0;


    private String C_QUIT = "q";
    private String C_ADD = "\\+";
    private String C_DASH = "0";

    public void startup(File semester) {
        Semester = semester;
        getClasses();
        printClassesDashboard();
        String input;
        while(!(input = getInput()).matches(C_QUIT)) {
            if(input.matches(C_ADD)) {
                addClass();
                printClassesDashboard();
            }
            else if(input.matches(C_DASH)) printClassesDashboard();
            else if(input.matches("[1-9]+")) {
                int sem = Integer.parseInt(input);
                if(sem > 0 && sem <= classCount) {
                    new Class().startup(classes[sem-1]);
                    printClassesDashboard();
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

    private void printClassesDashboard() {
        System.out.println("---------------------------------------");
        System.out.println("\t"+C_QUIT+": Go Back");
        System.out.println("\t+: Add Class");
        System.out.println("\t0: Show Menu");
        printClasses();
        System.out.println("---------------------------------------");
    }

    private void printClasses() {
        for(int i = 0; i < classCount; ++i) {
            System.out.println("\t" + (i+1) + ": " + classes[i].getName());
        }
    }

    private void getClasses() {
        classes = Semester.listFiles();
        classCount = classes.length;
    }

    private void addClass() {
        boolean get = true;
        String input = "";
        while(get) {
            get = false;
            input = getClassFromConsole();
            for(File i : classes) {
                if(i.getName().matches(input)) {
                    get = true;
                    System.out.println("Class Already Exists, Try Again");
                    break;
                }
            }
        }
        addNewClass(input);
        getClasses();
    }

    private void addNewClass(String name) {
        File newSemester = new File(Semester.getAbsolutePath() + "/" +name);
        newSemester.mkdir();
    }

    private String getClassFromConsole() {
        Scanner scanner = new Scanner(System.in);
        String className = "", buffer = "";
        boolean get = true;
        while(get) {
            get = false;
            System.out.println("Enter Subject Code: ");
            buffer = scanner.nextLine();
            if(!buffer.matches("[a-zA-Z]+") || buffer.length() != 3) {
                get = true;
                System.out.println("Bad Code, Try Again");
            }
        }
        buffer = buffer.toUpperCase();
        className += buffer + " ";
        get = true;
        while(get) {
            get = false;
            System.out.println("Enter Class Code: ");
            buffer = scanner.nextLine();
            if(!buffer.matches("[0-9]+") || buffer.length() != 4) {
                get = true;
                System.out.println("Bad Class Code, Try Again");
            }
        }
        className += buffer;
        return className;
    }

    public static String[] getSemesterGrade(File semester) {
        String[] grades = new String[semester.list().length];
        File[] classes = semester.listFiles();
        int index = 0;
        for(File i : classes) {
            grades[index] = Class.getClassGrade(i);
            index++;
        }
        return grades;
    }
}