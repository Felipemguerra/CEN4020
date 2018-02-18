import java.io.*;

/*Implemented by Daniel and Brian*/

/**
 * A class that offers functionality to view and edit major.
 * */
public class Major {

    /**
     * Prints major from user profile.
     * */
    public void ShowMajor() {
        File TestUserFile = new File(System.getProperty("user.dir")+"/user_profile/major");
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
            BuffReader.close();
        }
        catch (FileNotFoundException FNF) {}
        catch (IOException IOE) {}
    }

    /**
     * prints progress from progress file in user profile.
     */
    public void ShowProgress() {
        File TestUserFile = new File(System.getProperty("user.dir")+"/user_profile/progress");
        try {
            FileReader InputStream = new FileReader(TestUserFile);
            BufferedReader BuffReader = new BufferedReader(InputStream);
            int total = 0;
            int finished = 0;

            System.out.println("---------------------------------------");
            System.out.println("Progress Report\n");
            String buffer = BuffReader.readLine();
            if(!buffer.matches("=")) System.out.println("Finished Classes:");
            String[] split;
            while (!buffer.matches("=")) {
                split = buffer.split("\\+");
                System.out.println("\t" + split[0] + "-" + split[1] + ": " + split[2]);
                total += Integer.parseInt(split[3]);
                finished += Integer.parseInt(split[3]);
                buffer = BuffReader.readLine();
            }
            buffer = BuffReader.readLine();
            if(buffer != null)System.out.println("Unfinished Classes:");
            while (buffer != null) {
                split = buffer.split("\\+");
                System.out.println("\t" + split[0] + "-" + split[1] + ": " + split[2]);
                total += Integer.parseInt(split[3]);
                buffer = BuffReader.readLine();
            }
            System.out.println("\n\t" +finished+ "/" +total+ " Hours Completed");

            System.out.println("---------------------------------------");
            BuffReader.close();
        }
        catch (FileNotFoundException FNF) {}
        catch (IOException IOE) {}
    }

    /**
     * Uses the updateUser class to change major.
     * @param first first name to keep name when changing major
     * @param last last name to keep name when changing major
     * */
    public void ChangeMajor(String first, String last) {
        UpdateUser temp = new UpdateUser();
        temp.changeMajor(first, last);
    }
}
