import java.io.*;

/*Implemented by Daniel and Brian*/

/**
 * A class that offers functionality to view and edit major.
 * */
public class Major {

    public static String majorPath = Gradebook.userPath+"major";
    public static String progressPath = Gradebook.userPath+"progress";

    /**
     * Prints major from user profile.
     * */
    public void ShowMajor() {
        File TestUserFile = new File(majorPath);
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
        File TestUserFile = new File(progressPath);
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
        new CreateUser().populateFromConsole(first+"\n"+last);
    }

    public static boolean updateProgress(String className) {
        String[] name = className.split(" ");

        File progress = new File(progressPath);
        boolean found = false;
        String newLine = "";

        try {
            BufferedReader BuffReader = new BufferedReader(new FileReader(progress));

            String buffer = BuffReader.readLine();
            String[] split;
            while (!buffer.matches("=")) ;
            buffer = BuffReader.readLine();
            while (buffer != null) {
                split = buffer.split("\\+");
                if(name[0].matches(split[0]) && name[1].matches(split[1])) {
                    found = true;
                    newLine = buffer;
                    break;
                }
                buffer = BuffReader.readLine();
            }
            BuffReader.close();
        }
        catch (FileNotFoundException FNF) {}
        catch (IOException IOE) {}

        if(!found) return false;
        else {
            File tempProg = new File(Gradebook.userPath+"temp");
            try {
                BufferedReader BuffReader = new BufferedReader(new FileReader(progress));
                BufferedWriter BuffWriter = new BufferedWriter(new FileWriter(tempProg));

                String buffer = BuffReader.readLine();
                String[] split;
                if(buffer.matches("=")) {
                    BuffWriter.write(newLine);
                    BuffWriter.write("\n");
                    BuffWriter.write(buffer);
                    BuffWriter.write("\n");
                }
                while (!buffer.matches("=")) {
                    BuffWriter.write(buffer);
                    BuffWriter.write("\n");
                    buffer = BuffReader.readLine();
                    if(buffer.matches("=")) {
                        BuffWriter.write(newLine);
                        BuffWriter.write("\n");
                        BuffWriter.write(buffer);
                        BuffWriter.write("\n");
                    }
                }

                buffer = BuffReader.readLine();
                while (buffer != null) {
                    split = buffer.split("\\+");
                    if(name[0].matches(split[0]) && name[1].matches(split[1])) {
                        buffer = BuffReader.readLine();
                        continue;
                    }
                    else{
                        BuffWriter.write(buffer);
                    }
                    buffer = BuffReader.readLine();
                    if(buffer != null) BuffWriter.write("\n");
                }
                BuffReader.close();
                BuffWriter.close();
                progress.delete();
                tempProg.renameTo(progress);
            }
            catch (FileNotFoundException FNF) {}
            catch (IOException IOE) {}
            tempProg.delete();
            return true;
        }
    }
}
