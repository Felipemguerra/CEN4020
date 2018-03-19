import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Class {

    private File Class;

    private String classPath;

    public void startup(String name) {
        Class = new File(name);
        classPath = name;
        if(Class.list().length == 0) initializeClass();
    }

    private void initializeClass() {
        File classFile = new File(classPath + "/class");
        try{ classFile.createNewFile();}
        catch(IOException IOE) {}
        System.out.println("---------------------------------------");
        System.out.println("Set Up Your Class");
        System.out.println("---------------------------------------");

        Scanner scanner = new Scanner(System.in);
        boolean get = true;
        String input = "";
        String buffer = "";
        String section = "";
        String sections = "";
        int total = 0;
        while(get) {
            get = false;
            System.out.println("Enter Credit Hours: ");
            buffer = scanner.nextLine();
            if(!buffer.matches("[1-9]")) {
                get = true;
                System.out.println("Bad Credit Hours, Try Again");
            }
        }
        input += buffer + "\n";
        get = true;
        while(get) {
            while (get) {
                System.out.println("---------------------------------------");
                System.out.println("Enter Subject Info");
                System.out.println("---------------------------------------");
                while (get) {
                    System.out.println("Enter Subject Name: ");
                    buffer = scanner.nextLine();
                    if (!buffer.matches("[a-zA-Z]+")) System.out.println("Bad Subject Name, Try Again");
                    else get = false;
                }
                section += buffer+"+";
                get = true;
                while (get) {
                    System.out.println("Enter Subject Weight Percentage: ");
                    buffer = scanner.nextLine();
                    if (!buffer.matches("[0-9]+") || Integer.parseInt(buffer) > 100 || Integer.parseInt(buffer) < 1) {
                        System.out.println("Bad Percentage, Try Again");
                    } else get = false;
                }
                section += buffer+"\n";
                sections += section;
                section = "";
                total += Integer.parseInt(buffer);
                do {
                    System.out.println("---------------------------------------");
                    System.out.println("0:Add Another Subject");
                    System.out.println("1:Submit");
                    System.out.println("---------------------------------------");
                    buffer = scanner.nextLine();
                    if(buffer.matches("0")) {get = true; break;}
                } while(!buffer.matches("0") && !buffer.matches("1"));
            }
            if(total != 100) {
                System.out.println("Total is not 100, Try Again");
                get = true;
                sections = "";
                total = 0;
            }
            else get = false;
        }
        input += sections;
        input += "0";

        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(classFile));
            writer.write(input);
            writer.close();
        } catch (IOException IOE) {}
    }
}
