import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

/*Implemented by Daniel and Redden*/

/**
 * This class offers a way to access the user profile and
 * is where further iterations will continue.
 * */
public class UserProfile extends JPanel implements ActionListener{

    public static String userPath = StartMenu.userPath+"user";

    /**
     * stores first name from user profile
     * */
    private static String FirstName;

    /**
     * stores last name from user profile
     * */
    private static String LastName;

    private static JButton majorBtn;
    private static JButton semestersBtn;
    private static JButton backBtn;


    /**
     * Starts up the command menu and allows user to select several options
     * to access and edit user profile.
     * */
    public UserProfile() {
        getUserName();
        assignButtons();
        setComponents();
        setVisible(true);
    }

    private void assignButtons() {
        majorBtn = new JButton();
        majorBtn.setText("Go To Major");
        majorBtn.addActionListener(this);
        semestersBtn = new JButton();
        semestersBtn.setText("Go To Semesters");
        semestersBtn.addActionListener(this);
        backBtn = new JButton();
        backBtn.setText("Go Back");
        backBtn.addActionListener(this);
    }

    private void setComponents() {
        removeAll();
        setLayout(new GridLayout(5,2,0,0));

        JPanel[][] panels = new JPanel[5][2];
        for(int i = 0; i < 5; ++i) {
            for(int e = 0; e < 2; ++e) {
                panels[i][e] = new JPanel();
                add(panels[i][e]);
            }
        }

        panels[1][0].setLayout(new GridLayout(2,1,0,0));

        JLabel welcome = new JLabel("Hello "+FirstName+" "+LastName);
        welcome.setFont(new Font("name",1,20));
        welcome.setHorizontalAlignment(JLabel.CENTER);
        panels[1][0].add(welcome);

        JLabel instruct = new JLabel("Choose an Option:");
        instruct.setFont(new Font("details",0,14));
        instruct.setHorizontalAlignment(JLabel.CENTER);
        panels[1][0].add(instruct);

        JLabel gpa = new JLabel("GPA: " + getGrade(new File(Semesters.semestersPath)));
        gpa.setFont(new Font("gpa",1,20));
        panels[2][0].add(gpa);

        majorBtn.setEnabled(true);
        semestersBtn.setEnabled(true);
        backBtn.setEnabled(true);
        panels[1][1].add(majorBtn);
        panels[2][1].add(semestersBtn);
        panels[3][1].add(backBtn);

        repaint();
        validate();
    }

    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == majorBtn) {
            Gradebook.changeToMajor();
        }
        else if(ae.getSource() == semestersBtn) {
            Gradebook.changeToSemesters();
        }
        else if(ae.getSource() == backBtn) {
            Gradebook.changeToStartMenu();
        }
        setComponents();
    }

    /**takes the first and last name from the user profile in a given user directory
    * to populate the User Profile
     * */
    private static void getUserName() {
        File user = new File(System.getProperty("user.dir")+"/user_profile/user");
        try {
            FileReader InputStream = new FileReader(user);
            BufferedReader BuffReader = new BufferedReader(InputStream);
            FirstName = BuffReader.readLine();
            LastName = BuffReader.readLine();
            BuffReader.close();
        }
        catch (FileNotFoundException FNF) {}
        catch (IOException IOE) {}
    }

    private static float getGrade(File semDir) {
        float grade = 0;
        if(semDir.exists())
        {
        File[] semesters = semDir.listFiles();
        float semGrade = 0;
        int creditHours = 0;
        String[] grades;
        for(int i = 0; i < semesters.length; ++i) {
            grades = Semester.getSemesterGrade(semesters[i]);
            for(int e = 0; e < grades.length; ++e) {
                creditHours+= Integer.parseInt(grades[i].split("\\+")[1]);
                semGrade += getPoints(grades[e]);
            }
            grade += semGrade;
            semGrade = 0;
        }
        grade /= creditHours;
        }
        return grade;
    }

    private static double getPoints(String classGrade) {
        float points;
        float grade = Float.parseFloat(classGrade.split("\\+")[0]);
        if(grade >= 93) points = (float)4.00;
        else if(grade >= 90) points = (float)3.75;
        else if(grade >= 87) points = (float)3.25;
        else if(grade >= 83) points = (float)3.00;
        else if(grade >= 80) points = (float)2.75;
        else if(grade >= 77) points = (float)2.25;
        else if(grade >= 73) points = (float)2.00;
        else if(grade >= 70) points = (float)1.75;
        else if(grade >= 67) points = (float)1.25;
        else if(grade >= 63) points = (float)1.00;
        else if(grade >= 60) points = (float)0.75;
        else points = (float)0.00;
        points *= Integer.parseInt(classGrade.split("\\+")[1]);
        return points;
    }
}