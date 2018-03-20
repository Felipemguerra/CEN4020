import java.io.*;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Class {

    private File Class;

    private File classInfo;

    private File[] sections;

    private int sectionCount = 0;

    public void startup(File c) {
        Class = c;
        if(Class.list().length == 0) initializeClass();

        getSections();
        printClassDashboard();
        String input;
        while(!(input = getInput()).matches("q")) {
            if(input.matches("0")) printClassDashboard();
            else if(input.matches("f")) {
                markClassFinished();
                printClassDashboard();
            }
            else if(input.matches("[1-9]+")) {
                int sec = Integer.parseInt(input);
                if(sec > 0 && sec <= sectionCount+1) {
                    Section.startup(sections[sec-1]);
                    printClassDashboard();
                }
                else System.out.println("Sorry, Try Again.");
            }
            else System.out.println("Sorry, Try Again.");
            System.out.println("Press 0 For Menu");
        }
    }

    private void printClassDashboard() {
        System.out.println("---------------------------------------");
        System.out.println("\tGrade: " + getClassGrade(Class).substring(0,5));
        System.out.println("---------------------------------------");
        System.out.println("\tq: Go Back");
        System.out.println("\tf: Mark Class As Finished");
        System.out.println("\t0: Show Menu");
        printSections();
        System.out.println("---------------------------------------");
    }

    private void printSections() {
        for(int i = 0; i < sectionCount; ++i) {
            System.out.println("\t" + (i+1) + ": " + sections[i].getName());
        }
    }

    private void getSections() {
        File[] temp = Class.listFiles();
        classInfo = temp[0];
        sectionCount = temp.length-1;
        sections = new File[sectionCount];
        int index = 0;
        for(int i = 0; i < temp.length; ++i) {
            if(!temp[i].getName().matches("class")) {
                sections[index] = temp[i];
                index++;
            }
        }
    }

    private String getInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private void initializeClass() {
        File classFile = new File(Class.getAbsolutePath() + "/class");
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
                    System.out.println("0:Add Another Section");
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

    public boolean markClassFinished() {
        String buffer, code = "";
        int index = 0;
        try{
            BufferedReader BuffReader = new BufferedReader(new FileReader(classInfo));
            while((buffer = BuffReader.readLine()) != null) {
                code = buffer;
                index++;
            }
            BuffReader.close();
        }
        catch(IOException IOE) {}
        if(code.matches("1")) return false;
        String[] output = new String[index];
        try{
            BufferedReader BuffReader = new BufferedReader(new FileReader(classInfo));
            for(int i = 0; i < index; ++i) output[i] = BuffReader.readLine();
            output[index-1] = "1";
            BuffReader.close();
            BufferedWriter BuffWriter = new BufferedWriter(new FileWriter(classInfo));
            for(int i = 0; i < index; ++i) {
                BuffWriter.write(output[i]);
                if(i != index-1) BuffWriter.write("\n");
            }
            BuffWriter.close();
        }
        catch(IOException IOE) {}
        Major.updateProgress(Class.getName());
        return true;
    }

    public static String getClassGrade(File classfile) {
        String[] sectionInfo = {};
        String creditHours = "";
        try {
            int total = 0;
            File classinfo = new File(classfile.getAbsolutePath()+"/class");
            BufferedReader BuffReader = new BufferedReader(new FileReader(classinfo));
            String buffer = BuffReader.readLine();
            creditHours = buffer;
            buffer = BuffReader.readLine();
            while(!buffer.matches("0") && !buffer.matches("1")) {
                total++;
                buffer = BuffReader.readLine();
            }
            sectionInfo = new String[total];
            BuffReader.close();

            total = 0;
            BuffReader = new BufferedReader(new FileReader(classfile.getAbsolutePath()+"/class"));
            buffer = BuffReader.readLine();
            buffer = BuffReader.readLine();
            while(!buffer.matches("0") && !buffer.matches("1")) {
                sectionInfo[total] = buffer;
                buffer = BuffReader.readLine();
                total++;
            }
            BuffReader.close();
        }
        catch(IOException IOE) {}
        File[] sections = classfile.listFiles();
        float grade, total = 0;
        for(File i : sections) {
            if(!i.getName().matches("class")) {
                grade = Section.getSectionGrade(i);
                if(grade != -1) {
                    for (String s : sectionInfo) {
                        if (s.split("\\+")[0].matches(i.getName())) {
                            grade = grade * Integer.parseInt(s.split("\\+")[1]);
                            total += grade;
                            break;
                        }
                    }
                }
            }
        }

        return String.valueOf(total)+"+"+creditHours;
    }
}
