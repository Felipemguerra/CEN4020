import java.io.File;
import java.util.Scanner;

public class Gradebook {

    private boolean UserExists;

    public static void main(String[] args) {
        System.out.println("---------------------------------------");
        System.out.println("\tWelcome To GradeBook");
        System.out.println("---------------------------------------");

        Gradebook GB = new Gradebook();
        while(GB.startup() == 1){}
        System.out.println("Thank You For Using GradeBook!");
    }

    private int startup() {
        File UserDir = new File(System.getProperty("user.dir")+"/src/user_profile");
        if (UserDir.exists()) UserExists = true;
        else UserExists = false;

        int input;
        while((input = getInput()) == 0) {
            System.out.println("Sorry, Try Again.");
        }

        if (input == 1 && UserExists) {
            UserProfile user = new UserProfile();
            user.startup(0);
            return 1;
        }
        else if((input == 1 && !UserExists) || (input == 2 && UserExists)){
            UserDir.mkdir();/*
            CreateNewUser NewUser = new CreateNewUser();
            NewUser.make();*/
            return 1;
        }
        else if((input == 3 && UserExists) || (input == 2 && !UserExists)) {
            /*CreateNewUser TestUser = new CreateNewUser();*/
            UserProfile user = new UserProfile();
            user.startup(1);
            return 1;
        }
        else
            return 0;
    }

    private void printDashboard() {
        System.out.println("---------------------------------------");
        System.out.println("\tEnter the Option Number:");
        if (UserExists) {
            System.out.println("\t1: Access User Profile");
            System.out.println("\t2: Recreate User Profile");
            System.out.println("\t3: Run Test Profile");
            System.out.println("\t4: Exit");
        } else {
            System.out.println("\t1: Create User Profile");
            System.out.println("\t2: Run Test Profile");
            System.out.println("\t3: Exit");
        }
        System.out.println("---------------------------------------");
    }

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
        return 0;
    }
}