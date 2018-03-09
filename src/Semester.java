import java.io.*;

/**
 * A class that offers functionality to add and view a semester
 * */

public class Semester {

    private File semester;

    public String semestersPath = Gradebook.userPath+"semesters/";

    public void startup(String name) {
        semester = new File(semestersPath+name);
        System.out.println("im here in" + name);
    }

    public void addNewSemester(String name) {
        File newSemester = new File(semestersPath+name);
        try{
            newSemester.createNewFile();
        }
        catch(IOException IOE) {}
    }
}