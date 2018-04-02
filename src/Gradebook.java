import javax.swing.*;
import java.awt.*;

public class Gradebook{

    private static JFrame frame;
    private static Container container;

    public static void main(String[] args) {
        frame =  new JFrame();
        container = frame.getContentPane();
        frame.setTitle("Gradebook");
        frame.setSize(600,400);
        frame.setResizable(false);
        //add a menu?
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        changeToStartMenu();
    }

    public static void changeToStartMenu() {
        container.removeAll();
        container.add(new StartMenu());
        frame.repaint();
        frame.validate();
    }

    public static void changeToUserProfile() {
        container.removeAll();
        container.add(new UserProfile());
        frame.repaint();
        frame.validate();
    }

    public static void changeToMajor() {
        container.removeAll();
        container.add(new Major());
        frame.repaint();
        frame.validate();
    }

    public static void changeToCreateUser(boolean isMajorChange) {
        container.removeAll();
        container.add(new CreateUser(isMajorChange));
        frame.repaint();
        frame.validate();
    }

    public static void changeToSemester() {}
    public static void changeToClass() {}
    public static void changeToSection() {}
    public static void changeToAssignment() {}
}