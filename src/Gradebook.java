import javax.swing.*;
import java.awt.*;

public class Gradebook{

    private static JFrame frame;
    private static Container container;

    public static void main(String[] args) {
        frame =  new JFrame();
        container = frame.getContentPane();
        frame.setName("Gradebook");
        frame.setSize(400,300);
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

    public void changeToSemester() {}
    public void changeToClass() {}
    public void changeToSection() {}
    public void changeToMajor() {}
    public void changeToAssignment() {}
    public void changeToCreateUser() {}

}
