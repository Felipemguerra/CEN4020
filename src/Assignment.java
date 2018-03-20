import java.io.*;
import java.util.Scanner;

/*
* implemented by Felipe and Brian
*/
public class Assignment {
    static private File Assignment;

    public static void startup(File assignment) {
        Assignment = assignment;
        printAssignmentDashboard();
        String input;
        while(!(input = getInput()).matches("q")) {
            if(input.matches("0")) {
                printAssignmentDashboard();
            }
            else if(input.matches("c")) {
                changeGrade();
                printAssignmentDashboard();
            }
            else System.out.println("Sorry, Try Again.");
            System.out.println("Press 0 For Menu");
        }
    }

    private static String getInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static float getGrade(File assignment) {
        float grade = 0;
        try {
            String[] buffer;
            BufferedReader BuffReader = new BufferedReader(new FileReader(assignment));
            buffer = BuffReader.readLine().split("\\+");
            BuffReader.close();
            if(buffer[0].matches("-1")) return -1;
            grade = Float.parseFloat(buffer[0])/Float.parseFloat(buffer[1]);
        }
        catch(IOException IOE) {}
        return grade;
    }

    private static void printAssignmentDashboard() {
        System.out.println("---------------------------------------");
        if(getGrade(Assignment) == -1) System.out.println("\tNo Grade");
        else System.out.println("\tGrade: " + String.format("%.2f",(getGrade(Assignment)*100)));
        System.out.println("---------------------------------------");
        System.out.println("\tq: Go Back");
        System.out.println("\tc: Change Grade");
        System.out.println("\t0: Show Menu");
        System.out.println("---------------------------------------");
    }

    private static void changeGrade() {
        Scanner scanner = new Scanner(System.in);
        String buffer = "";
        boolean get = true;
        try {
            BufferedReader BuffReader = new BufferedReader(new FileReader(Assignment));
            String[] old = BuffReader.readLine().split("\\+");
            float total = Float.parseFloat(old[1]);
            BuffReader.close();

            while(get) {
                get = false;
                System.out.println("Enter New Score: ");
                buffer = scanner.nextLine();
                if (!buffer.matches("[1-9][0-9]*") && !buffer.matches("[1-9][0-9]*\\.[0-9]+")) {
                    get = true;
                    System.out.println("Invalid Score");
                }
                else if (Float.parseFloat(buffer) > total) {
                    get = true;
                    System.out.println("Invalid Score");
                }
            }
            old[0] = buffer;
            BufferedWriter BuffWriter = new BufferedWriter(new FileWriter(Assignment));
            BuffWriter.write(old[0]);
            BuffWriter.write("+");
            BuffWriter.write(old[1]);
            BuffWriter.close();
        }
        catch(IOException IOE) {}
    }
}