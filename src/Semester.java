import java.io.*;

/**
 * A class that offers functionality to add and view a semester
 * */

public class Semester {

    private File semester;

    public void startup(String name) {
        semester = new File(System.getProperty("user.dir")+"/user_profile/semesters/"+name);
        System.out.println("im here in" + name);
    }

    public void addNewSemester(String name) {
        File newSemester = new File(System.getProperty("user.dir") + "/user_profile/semesters/"+name);
        try{
            newSemester.createNewFile();
        }
        catch(IOException IOE) {}
    }
}