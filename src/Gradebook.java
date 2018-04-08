import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

public class Gradebook implements ActionListener{

    private static JFrame frame;
    private static Container container;
    private static JMenuBar menuBar;
    private static JMenu menu;
    private static JMenuItem startMenu;
    private static JMenuItem userProfile;
    private static JMenuItem major;
    private static JMenuItem semesters;

    public static void main(String[] args) {
        new Gradebook().initialize();
    }

    private void initialize() {
        frame =  new JFrame();
        container = frame.getContentPane();
        frame.setTitle("Gradebook");
        frame.setSize(600,400);
        frame.setResizable(false);

        //add a menu
        menuBar = new JMenuBar();
        JMenu menu = new JMenu("Options");
        menuBar.add(menu);
        startMenu = new JMenuItem("Start Menu");
        startMenu.addActionListener(this);
        userProfile = new JMenuItem("User Profile");
        userProfile.addActionListener(this);
        major = new JMenuItem("Major");
        major.addActionListener(this);
        semesters = new JMenuItem("Semesters");
        semesters.addActionListener(this);
        menu.add(startMenu);
        menu.add(userProfile);
        menu.add(major);
        menu.add(semesters);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        changeToStartMenu();
    }

    public static void changeToStartMenu() {
        frame.setJMenuBar(null);
        container.removeAll();
        container.add(new StartMenu());
        frame.repaint();
        frame.validate();
    }

    public static void changeToUserProfile() {
        frame.setJMenuBar(menuBar);
        container.removeAll();
        container.add(new UserProfile());
        frame.repaint();
        frame.validate();
    }

    public static void changeToMajor() {
        frame.setJMenuBar(menuBar);
        container.removeAll();
        container.add(new Major());
        frame.repaint();
        frame.validate();
    }

    public static void changeToCreateUser(boolean isMajorChange) {
        if(isMajorChange) frame.setJMenuBar(menuBar);
        else frame.setJMenuBar(null);
        container.removeAll();
        container.add(new CreateUser(isMajorChange));
        frame.repaint();
        frame.validate();
    }

    public static void changeToSemesters() {
        frame.setJMenuBar(menuBar);
        container.removeAll();
        container.add(new Semesters());
        frame.repaint();
        frame.validate();
    }

    public static void changeToSemester(File semester) {
        frame.setJMenuBar(menuBar);
        container.removeAll();
        container.add(new Semester(semester));
        frame.repaint();
        frame.validate();
    }
    public static void changeToClass(File c, File s) {
        frame.setJMenuBar(menuBar);
        container.removeAll();
        container.add(new Class(c,s));
        frame.repaint();
        frame.validate();
    }

    public static void changeToClassSetup(File c, File s) {
        container.removeAll();
        container.add(new ClassSetup(c, s));
        frame.repaint();
        frame.validate();
    }

    public static void changeToSection(File sec, File c, File sem) {
        frame.setJMenuBar(menuBar);
        container.removeAll();
        container.add(new Section(sec,c, sem));
        frame.repaint();
        frame.validate();
    }
    public static void changeToAssignment(File a, File sec, File c, File sem) {
        frame.setJMenuBar(menuBar);
        container.removeAll();
        container.add(new Assignment(a,sec,c,sem));
        frame.repaint();
        frame.validate();
    }

    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == startMenu) changeToStartMenu();
        if(ae.getSource() == userProfile) changeToUserProfile();
        if(ae.getSource() == major) changeToMajor();
        if(ae.getSource() == semesters) changeToSemesters();
    }
}