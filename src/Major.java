import java.io.*;
import java.util.Scanner;

public class Major {

    public void TestShowMajor() {
        File TestUserFile = new File(System.getProperty("user.dir")+"/src/test_profile/major");
        try {
            FileReader InputStream = new FileReader(TestUserFile);
            BufferedReader BuffReader = new BufferedReader(InputStream);

            System.out.println("---------------------------------------");
            System.out.println("\tMajor: " + BuffReader.readLine());
            String buffer = BuffReader.readLine();
            String[] split;
            while (buffer != null) {
                split = buffer.split("\\+");
                System.out.println("\t" + split[0] + "-" + split[1] + ": " + split[2] + " [" + split[3] + " Credit Hours]");
                buffer = BuffReader.readLine();
            }
            System.out.println("---------------------------------------");
        }
        catch (FileNotFoundException FNF) {}
        catch (IOException IOE) {}

        System.out.println("Press Enter To Go Back");
        Scanner scanner = new Scanner(System.in);
        while (!scanner.nextLine().matches("")){
            System.out.println("Try Again, Just Press Enter");
        }
    }

    public void TestChangeMajor() {
        System.out.println("Changing Major");
        File TestUserFile = new File(System.getProperty("user.dir")+"/src/test_profile/major");
    }

    public void TestShowProgress() {
        File TestUserFile = new File(System.getProperty("user.dir")+"/src/test_profile/progress");
        try {
            FileReader InputStream = new FileReader(TestUserFile);
            BufferedReader BuffReader = new BufferedReader(InputStream);
            int total = 0;
            int finished = 0;

            System.out.println("---------------------------------------");
            System.out.println("Progress Report\n");
            String buffer = BuffReader.readLine();
            System.out.println("Finished Classes:");
            String[] split;
            while (!buffer.matches("=")) {
                split = buffer.split("\\+");
                System.out.println("\t" + split[0] + "-" + split[1] + ": " + split[2]);
                total += Integer.parseInt(split[3]);
                finished += Integer.parseInt(split[3]);
                buffer = BuffReader.readLine();
            }
            buffer = BuffReader.readLine();
            System.out.println("Unfinished Classes:");
            while (buffer != null) {
                split = buffer.split("\\+");
                System.out.println("\t" + split[0] + "-" + split[1] + ": " + split[2]);
                total += Integer.parseInt(split[3]);
                buffer = BuffReader.readLine();
            }
            System.out.println("\n\t" +finished+ "/" +total+ " Hours Completed");

            System.out.println("---------------------------------------");
        }
        catch (FileNotFoundException FNF) {}
        catch (IOException IOE) {}

        System.out.println("Press Enter To Go Back");
        Scanner scanner = new Scanner(System.in);
        while (!scanner.nextLine().matches("")){
            System.out.println("Try Again, Just Press Enter");
        }
    }

    public void ShowMajor() {}

    public void ChangeMajor() {}

    public void ShowProgress() {}
}