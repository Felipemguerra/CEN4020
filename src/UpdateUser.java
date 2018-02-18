import java.io.*;
import java.util.Scanner;

/*Implemented by Felipe and Brian*/

/**
 * Used to update the files in the user profile.
 * */
public class UpdateUser {

    /**
     * Called from starting gradebook console to create a new user
     * or test user.
     * mode 0: Real User
     * mode 1: Test Use
     * */
    public void make() {
        populateFromConsole(null);
        //possible implementation of from file for user
    }

    /**
     * Calls the populateFromConsole method with the previously
     * given name as a parameter.
     * @param first current first name
     * @param last current last name
     * */
    public void changeMajor(String first, String last) {
        populateFromConsole(first+"\n"+last);
    }

    /**
     * Takes in user information from console, copies it to
     * a file and calls populateFromFile.  Takes in string if
     * just changing major.
     * @param name  if changing major("firs\nlast"), otherwise null
     * */
    private void populateFromConsole(String name) {
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
        populateFromFile(temp.getAbsolutePath());
        temp.delete();
    }

    /**
     * Reads a provided file and creates a new user profile based
     * on it.  Assumes a valid input file.  Future error checking and
     * boolean return possible.
     * @param   filename    the pathname of the given input file
     * */
    private boolean populateFromFile(String filename) {
        File input = new File(filename);
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

        File user = new File(System.getProperty("user.dir")+"/user_profile/user");
        File major = new File(System.getProperty("user.dir")+"/user_profile/major");
        File prog = new File(System.getProperty("user.dir")+"/user_profile/progress");
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
        return input;
    }

    /**
     * Format check for name.
     * @param s the given name that will be checked.
     * @return  whether given name is valid or not
     * */
    private boolean checkUserName(String s) {
        if(s == null) return false;
        if (s.matches("")) return false;
        if (!s.chars().allMatch(Character::isLetter)) return false;
        return true;
    }

    /**
     * Format check for major.
     * @param s the given major that will be checked.
     * @return  whether given major is valid or not
     * */
    private boolean checkMajor(String s) {
        if(s == null) return false;
        if (s.matches("")) return false;
        if (!s.matches("[a-zA-Z\\s]+")) return false;
        return true;
    }

    /**
     * Format check for class subject code.
     * @param s the given class subject code tha will be checked.
     * @return  whether given class subject code is valid or not
     * */
    private boolean checkClassSubject(String s) {
        if(s == null) return false;
        if (s.matches("")) return false;
        if(!s.matches("[a-zA-Z]+") || s.length() != 3) return false;
        return true;
    }

    /**
     * Format check for class code.
     * @param s the given class code that will be checked.
     * @return  whether given class code is valid or not
     * */
    private boolean checkClassCode(String s) {
        if(s == null) return false;
        if (s.matches("")) return false;
        if(!s.matches("[0-9]+") || s.length() != 4) return false;
        return true;
    }

    /**
     * Format check for class name.
     * @param s the given class name that will be checked.
     * @return  whether given class name is valid or not
     * */
    private boolean checkClassName(String s) {
        if(s == null) return false;
        if(s.matches("")) return false;
        return true;
    }

    /**
     * Format check for credit hours.
     * @param s the given credit hours that will be checked.
     * @return  whether given credit hours is valid or not
     * */
    private boolean checkCreditHours(String s) {
        if(s == null) return false;
        if (s.matches("")) return false;
        if(!s.matches("[1-9]")) return false;
        return true;
    }

    /*
    *
    * Testing Methods
    *
    * */

    /**
     * Run when the user chooses to run tests from main
     * gradebook command prompt.  Takes input to get test
     * to be run.  Returns a valid test user to continue testing.
     * @return   whether valid_user file is valid
     * */
    public boolean runTests() {
        int input;
        printTestDashboard();
        while((input = getTestInput()) != 9) {
            if(input == 0) printTestDashboard();
            else {
                if(input == 1) {System.out.println("The Result of Test1: ");test("test1");}
                else if(input == 2) System.out.println("The Result of Test2: " + test("test2"));
                else if(input == 3) System.out.println("The Result of Test3: " + test("test3"));
                else if(input == 4) System.out.println("The Result of Test4: " + test("test4"));
                else if(input == 5) System.out.println("The Result of Test5: " + test("test5"));
                else if(input == 6) System.out.println("The Result of Test6: " + test("test6"));
                else if(input == 7) System.out.println("The Result of Test7: " + test("test7"));
                else if(input == 8) System.out.println("The Result of Test8: " + test("test8"));
                else  System.out.println("Bad Command: Try Again");
            System.out.println("Press 0 For Menu");
            }
        }
        if (!populateFromFile(System.getProperty("user.dir") + "/test_files/valid_user")) {System.out.println("Bad valid_user file. Can't create test user");return false;}
        else return true;
    }

    /**
     * Run a given test from the test_files directory.
     * @param s name of test file
     * @return  the result of the test
     * */
    private boolean test(String s) {
	System.out.println(System.getProperty("user.dir")+"/test_files/"+s);
        return populateFromFile(System.getProperty("user.dir")+"/test_files/"+s);
    }

    /**
     * Test dashboard options.
     * */
    private void printTestDashboard() {
        System.out.println("---------------------------------------");
        System.out.println("\tEnter the Option Number");
        System.out.println("\t0: Show Menu");
        System.out.println("\t1: Run Test 1(Name contains non-letter character)");
        System.out.println("\t2: Run Test 2(Name has empty first name)");
        System.out.println("\t3: Run Test 3(Major contains non-letter character)");
        System.out.println("\t4: Run Test 4(Empty Major Submitted)");
        System.out.println("\t5: Run Test 5(No Classes submitted)");
        System.out.println("\t6: Run Test 6(Class Subject Has non-letter character or isn't 3 characters long)");
        System.out.println("\t7: Run Test 7(Class Number Has non-number character or isn't 4 characters long)");
        System.out.println("\t8: Run Test 8(Credit Hours is not a single digit integer or isn't a number)");
        System.out.println("\t9: Go Back(Will create valid test user to continue testing)");
        System.out.println("\tfalse  = failed test, true = passed test");
        System.out.println("---------------------------------------");
    }

    /**
     * Console input for command option.
     * @return  input code, -1 if bad input
     * */
    private int getTestInput() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        if (input.matches("0")) return 0;
        if (input.matches("1")) return 1;
        if (input.matches("2")) return 2;
        if (input.matches("3")) return 3;
        if (input.matches("4")) return 4;
        if (input.matches("5")) return 5;
        if (input.matches("6")) return 6;
        if (input.matches("7")) return 7;
        if (input.matches("8")) return 8;
        if (input.matches("9")) return 9;
        return -1;
    }
}
