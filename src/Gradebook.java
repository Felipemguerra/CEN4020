import javax.swing.*;
import java.awt.*;

public class Gradebook{

    private static JFrame frame;
    private static Container container;

    public static void main(String[] args) {
        frame =  new JFrame();
        container = frame.getContentPane();
        frame.setName("Gradebook");
        frame.setSize(500,400);
        frame.setResizable(false);
        MenuBar m = new MenuBar();
        frame.setMenuBar(m);
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

    public static void changeToCreateUser() {
        container.removeAll();
        container.add(new CreateUser());
        frame.repaint();
        frame.validate();
    }

    public static void changeToSemester() {}
    public static void changeToClass() {}
    public static void changeToSection() {}

    public void changeToAssignment() {}
}