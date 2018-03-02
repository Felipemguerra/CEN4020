import java.io.*;

/**
 * A class that offers functionality to add and view a semester
 * */

public class Semester {

		/**
 		* Prints semesters 
 		* */
 		public void printSemesters() {
        File semesters = new File(System.getProperty("user.dir")+"/user_profile/semesters");
        if(semesters.exists()){
            try {
                FileReader InputStream = new FileReader(semesters);
                BufferedReader BuffReader = new BufferedReader(InputStream);

                String buffer;
                String[] line;
                int counter = 2;

                while((buffer = BuffReader.readLine()) != null) {
                    line = buffer.split("\\+");
                    System.out.println("\t" + counter + ": " + line[0] + " " + line[1]);
                    counter++;
                }
                counter=counter-2;
                BuffReader.close();
            }
            catch (FileNotFoundException FNF) {}
            catch (IOException IOE) {}
        }
    }





}