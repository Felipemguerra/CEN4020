import java.io.*;
import java.util.Scanner;

/*Implemented by Felipe and Brian*/

/**
 * Used to update the files in the user profile.
 * */
public class CreateUser {

    /**
     * Called from starting gradebook console to create a new user
     * or test user.
     * mode 0: Real User
     * mode 1: Test Use
     * */
    public void createNewUser() {
        populateFromConsole(null);
        //possible implementation of from file for user
    }

    /**
     * Takes in user information from console, copies it to
     * a file and calls populateFromFile.  Takes in string if
     * just changing major.
     * @param name  if changing major("firs\nlast"), otherwise null
     * */
    public void populateFromConsole(String name) {
        String buffer;
        File temp = new File(System.getProperty("user.dir")+"/console_temp");
        Scanner scanner = new Scanner(System.in);
        try {
            BufferedWriter BuffWriter = new BufferedWriter(new FileWriter(temp));
            if (name != null) {
                BuffWriter.write(name);
                BuffWriter.newLine();
            }
            else{
                buffer = getFirstName();
                BuffWriter.write(buffer);
                BuffWriter.newLine();
                buffer = getLastName();
                BuffWriter.write(buffer);
                BuffWriter.newLine();
            }

            buffer = getMajor();
            BuffWriter.write(buffer);
            BuffWriter.newLine();
            do {
                buffer = getAClass();
                BuffWriter.write(buffer);
                System.out.println("---------------------------------------");
                System.out.println("0:Add Another Class");
                System.out.println("1:Submit");
                System.out.println("---------------------------------------");
                buffer = scanner.nextLine();
                if(buffer.matches("0")) BuffWriter.newLine();
            } while(buffer.matches("0"));
            BuffWriter.close();
        }
        catch (FileNotFoundException FNF) {}
        catch (IOException IOE) {}
        populateFromFile(temp);
        temp.delete();
    }

    /**
     * Reads a provided file and creates a new user profile based
     * on it.  Assumes a valid input file.  Future error checking and
     * boolean return possible.
     * @param   input    input file
     * */
    private boolean populateFromFile(File input) {
        //ile input = new File(filename);
        try {
            BufferedReader BuffReader = new BufferedReader(new FileReader(input));

            String buffer;
            String[] s;

            buffer = BuffReader.readLine();
            if(!checkUserName(buffer)) {BuffReader.close();System.out.println("Bad First Name in Input File");return false;}

            buffer = BuffReader.readLine();
            if(!checkUserName(buffer)) {BuffReader.close();System.out.println("Bad Last Name in Input File");return false;}

            buffer = BuffReader.readLine();
            if(!checkMajor(buffer)) {BuffReader.close();System.out.println("Bad Major in Input File");return false;}

            if((buffer = BuffReader.readLine()) == null) {BuffReader.close();System.out.println("No Classes in Input File");return false;}

            while(buffer != null){
                s = buffer.split("\\+");
                if(!checkClassSubject(s[0])) {BuffReader.close();System.out.println("Bad Subject Code in Input File");return false;}
                if(!checkClassCode(s[1])) {BuffReader.close();System.out.println("Bad Class Code in Input File");return false;}
                if(!checkClassName(s[2])) {BuffReader.close();System.out.println("Bad Class Name in Input File");return false;}
                if(!checkCreditHours(s[3])) {BuffReader.close();System.out.println("Bad Credit Hours in Input File");return false;}
                buffer = BuffReader.readLine();
            }
            BuffReader.close();
        }
        catch (FileNotFoundException FNF) {System.err.println("Missing input File");return false;}
        catch (IOException IOE) {System.err.println("Error Reading From File");return false;}

        File user = new File(UserProfile.userPath);
        File major = new File(Major.majorPath);
        File prog = new File(Major.progressPath);
        try {
            BufferedReader BuffReader = new BufferedReader(new FileReader(input));
            BufferedWriter BuffWriter = new BufferedWriter(new FileWriter(user));
            BufferedWriter ProgWriter = new BufferedWriter(new FileWriter(prog));

            String buffer;

            buffer = BuffReader.readLine();
            BuffWriter.write(buffer);
            BuffWriter.newLine();

            buffer = BuffReader.readLine();
            BuffWriter.write(buffer);
            BuffWriter.close();

            BuffWriter = new BufferedWriter(new FileWriter(major));
            buffer = BuffReader.readLine();
            BuffWriter.write(buffer);
            ProgWriter.write("=");
            while((buffer = BuffReader.readLine()) != null){
                BuffWriter.newLine();
                ProgWriter.newLine();
                BuffWriter.write(buffer);
                ProgWriter.write(buffer);
            }
            BuffReader.close();
            BuffWriter.close();
            ProgWriter.close();
        }
        catch (FileNotFoundException FNF) {System.err.println("Missing input File"); return false;}
        catch (IOException IOE) {System.err.println("Error Reading From File"); return false;}
        return true;
    }

    /**
     * Console input of first name.  Only returns name
     * if it is valid.
     * @return  a string containing the first name
     * */
    private String getFirstName() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\tEnter Your First Name");
        String input = scanner.nextLine();
        while(!checkUserName(input)){
            System.out.println("Bad First Name, Try Again");
            input = scanner.nextLine();
        }
        return input;
    }

    /**
     * Console input of last name.  Only returns name
     * if it is valid.
     * @return  a string containing the last name
     * */
    private String getLastName() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\tEnter Your Last Name");
        String input = scanner.nextLine();
        while(!checkUserName(input)){
            System.out.println("Bad Last Name, Try Again");
            input = scanner.nextLine();
        }
        return input;
    }

    /**
     * Console input of major.  Only returns major
     * if it is valid.
     * @return  a string containing the major
     * */
    private String getMajor() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\tEnter Your Major");
        String input = scanner.nextLine();
        while(!checkMajor(input)){
            System.out.println("Bad Major, Try Again");
            input = scanner.nextLine();
        }
        return input;
    }

    /**
     * Console input of a class.  Only returns class
     * if it is valid.
     * @return  a string containing a class
     * */
    private String getAClass() {
        Scanner scanner = new Scanner(System.in);
        String input = "", buffer;
        System.out.println("\tEnter Class Subject Code ie. COP");
        buffer = scanner.nextLine();
        while (!checkClassSubject(buffer)){
            System.out.println("Bad Subject, Try Again");
            buffer = scanner.nextLine();
        }
        input += buffer;
        input += "+";
        System.out.println("\tEnter Class Code ie. 3330");
        buffer = scanner.nextLine();
        while (!checkClassCode(buffer)){
            System.out.println("Bad Class Code, Try Again");
            buffer = scanner.nextLine();
        }
        input += buffer;
        input += "+";
        System.out.println("\tEnter Class Name ie. Intro to Programming");
        buffer = scanner.nextLine();
        while (!checkClassName(buffer)){
            System.out.println("Bad Class Name, Try Again");
            buffer = scanner.nextLine();
        }
        input += buffer;
        input += "+";
        System.out.println("\tEnter Class Credit Hours ie. 3");
        buffer = scanner.nextLine();
        while (!checkCreditHours(buffer)){
            System.out.println("Bad Hours, Try Again");
            buffer = scanner.nextLine();
        }
        input += buffer;
        input+= "+";
        System.out.println("\tEnter the grade you received ie. A-)");
        buffer = scanner.nextLine();
        while (!checkGrade(buffer)){
            System.out.println("Bad Grade, Try Again");
            buffer = scanner.nextLine();
        }
        input += buffer;
        return input;
    }

    /**
     * Format check for name.
     * @param s the given name that will be checked.
     * @return  whether given name is valid or not
     * */
    public boolean checkUserName(String s) {
        if(s == null) return false;
        if (s.matches("")) return false;
        if(s.matches("[\\s]+")) return false;
        if (!s.chars().allMatch(Character::isLetter)) return false;
        return true;
    }

    /**
     * Format check for major.
     * @param s the given major that will be checked.
     * @return  whether given major is valid or not
     * */
    public boolean checkMajor(String s) {
        if(s == null) return false;
        if (s.matches("")) return false;
        if (s.matches("[\\s]+")) return false;
        if (!s.matches("[a-zA-Z\\s]+")) return false;
        return true;
    }

    /**
     * Format check for class subject code.
     * @param s the given class subject code tha will be checked.
     * @return  whether given class subject code is valid or not
     * */
    public boolean checkClassSubject(String s) {
        if(s == null) return false;
        if (s.matches("")) return false;
        if(s.matches("[\\s]+")) return false;
        if(!s.matches("[a-zA-Z]+") || s.length() != 3) return false;
        return true;
    }

    /**
     * Format check for class code.
     * @param s the given class code that will be checked.
     * @return  whether given class code is valid or not
     * */
    public boolean checkClassCode(String s) {
        if(s == null) return false;
        if (s.matches("")) return false;
        if(s.matches("[\\s]+")) return false;
        if(!s.matches("[0-9]+") || s.length() != 4) return false;
        return true;
    }

    /**
     * Format check for class name.
     * @param s the given class name that will be checked.
     * @return  whether given class name is valid or not
     * */
    public boolean checkClassName(String s) {
        if(s == null) return false;
        if(s.matches("")) return false;
        if(s.matches("[\\s]+")) return false;
        return true;
    }

    /**
     * Format check for credit hours.
     * @param s the given credit hours that will be checked.
     * @return  whether given credit hours is valid or not
     * */
    public boolean checkCreditHours(String s) {
        if(s == null) return false;
        if (s.matches("")) return false;
        if(s.matches("[\\s]+")) return false;
        if(!s.matches("[1-9]")) return false;
        return true;
    }

    public boolean checkGrade(String s) {
        if (s == null) return false;
        if (s.matches("")) return false;
        if (s.matches("[\\s]+")) return false;
        if(!s.matches("(A|A-|B+|B|B-|C+|C|C-|D+|D|D-|F)")) return false;
        return true;
    }
}
