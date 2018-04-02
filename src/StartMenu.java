import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import javax.swing.*;

/*Implemented by: Felipe and Redden*/

/**
 * This is the "main" class that contains the main method.
 * Opens up Initial view of StartMenu and offers options
 * to select from depending on if a user has already been
 * created. Then creates new objects and calls there starting
 * functions to take the user to the next command prompt or
 * profile input.
 */

public class StartMenu extends JPanel implements ActionListener{

    /**
     * used to keep track of existing user
     * */
    private static boolean UserExists = false;

    public static File UserDir;
    public static String userPath = System.getProperty("user.dir")+"/user_profile/";

    private static JButton accessBtn;
    private static JButton testBtn;
    private static JButton resetBtn;
    private static JButton exitBtn;
    private static JButton createBtn;

    public StartMenu() {
        //gradebook stuff
        UserDir = new File(userPath);
        if (UserDir.exists()) { UserExists = true; }

        //gui stuff
        assignButtons();
        setComponents();
        setVisible(true);
    }

    private void assignButtons() {
        accessBtn = new JButton();
        accessBtn.setText("Access User Profile");
        accessBtn.addActionListener(this);
        testBtn = new JButton();
        testBtn.setText("Create Test Profile");
        testBtn.addActionListener(this);
        resetBtn = new JButton();
        resetBtn.setText("Reset Profile");
        resetBtn.addActionListener(this);
        exitBtn = new JButton();
        exitBtn.setText("Exit");
        exitBtn.addActionListener(this);
        createBtn = new JButton();
        createBtn.setText("Create User Profile");
        createBtn.addActionListener(this);
    }

    private void setComponents() {
        removeAll();
        setLayout(new GridLayout(4,2,0,0));

        JPanel[][] panels = new JPanel[4][2];
        for(int i = 0; i < 4; ++i) {
            for(int e = 0; e < 2; ++e) {
                panels[i][e] = new JPanel();
                add(panels[i][e]);
            }
        }

        JLabel welcome = new JLabel("Welcome to Gradebook");
        welcome.setFont(new Font("name",1,20));
        panels[1][0].add(welcome);

        JLabel instruct = new JLabel("Choose an Option:");
        instruct.setFont(new Font("details",0,14));
        panels[1][0].add(instruct);

        if(UserExists) {
            accessBtn.setEnabled(true);
            resetBtn.setEnabled(true);
            testBtn.setEnabled(true);
            exitBtn.setEnabled(true);
            createBtn.setEnabled(false);
            panels[0][1].add(accessBtn);
            panels[1][1].add(resetBtn);
            panels[2][1].add(testBtn);
            panels[3][1].add(exitBtn);
        }
        else {
            accessBtn.setEnabled(false);
            resetBtn.setEnabled(false);
            testBtn.setEnabled(true);
            exitBtn.setEnabled(true);
            createBtn.setEnabled(true);
            panels[1][1].add(createBtn);
            panels[2][1].add(testBtn);
            panels[3][1].add(exitBtn);
        }
        repaint();
        validate();
    }

    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == accessBtn) {
            Gradebook.changeToUserProfile();
        }
        else if(ae.getSource() == createBtn) {
            Gradebook.changeToCreateUser(false);
            if(UserDir.exists()) UserExists = true;
        }
        else if(ae.getSource() == resetBtn) {
            deleteDir(UserDir);
            UserExists = false;
        }
        else if(ae.getSource() == testBtn) {
            if(!makeTestUser()) System.err.println("Missing Test Profile");
            else {
                if(!UserExists) UserExists = true;
            }
        }
        else if(ae.getSource() == exitBtn) {
            System.exit(0);
        }
        setComponents();
    }

    /**
     * Deletes a directory recursively.
     * @param   dir a File object of the user_profile directory
     * @return      the success/failure of the deletion
     * */
    private static boolean deleteDir(File dir) {
        File[] files = dir.listFiles();
        if(files != null) {
            for (File f : files) {
                deleteDir(f);
            }
        }
        return dir.delete();
    }

    private static boolean makeTestUser() {
        File test_user = new File(System.getProperty("user.dir")+"/test/user_profile");
        if(!test_user.exists()) return false;
        else
            try{
                FileUtils.copyDirectory(test_user, UserDir);
            }
            catch(IOException IOE) { return false;}
        return true;
    }
}
