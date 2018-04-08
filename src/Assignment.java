import javax.jws.soap.SOAPBinding;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

/*
* implemented by Felipe and Brian
*/
public class Assignment extends JPanel implements ActionListener{
    private File Assignment;
    private File Section;
    private File Class;
    private File Semester;

    private JButton backBtn;
    private JButton changeBtn;
    private JButton submitBtn;

    private JTextField pointInput;

    private boolean changingGrade = false;

    public  Assignment(File a, File sec, File c, File sem) {
        Assignment = a;
        Section = sec;
        Class = c;
        Semester = sem;
        assignComponents();
        setComponents();
    }

    private void assignComponents() {
        backBtn = new JButton("Go Back");
        backBtn.addActionListener(this);
        changeBtn = new JButton("Change Grade");
        changeBtn.addActionListener(this);
        submitBtn = new JButton("Submit");
        submitBtn.addActionListener(this);
        pointInput = new JTextField();
    }

    private void setComponents() {
        removeAll();

        setLayout(new GridLayout(3, 2, 15, 15));
        JPanel[][] panels = new JPanel[3][2];
        for (int i = 0; i < 3; ++i) {
            for (int e = 0; e < 2; e++) {
                panels[i][e] = new JPanel();
                add(panels[i][e]);
            }
        }

        if(!changingGrade) {
            JLabel gpa = new JLabel("GPA: " + UserProfile.getGrade(new File(Semesters.semestersPath)));
            gpa.setFont(new Font("gpa", 1, 20));
            gpa.setHorizontalAlignment(JLabel.CENTER);
            panels[0][0].setLayout(new BorderLayout());
            panels[0][0].add(gpa);
            if (hasGrade()) {
                JLabel title = new JLabel("Score");
                title.setFont(new Font("title", 1, 30));
                title.setHorizontalAlignment(JLabel.CENTER);
                JLabel grade = new JLabel(getScore() + " / " + getTotal());
                grade.setFont(new Font("grade", 1, 26));
                grade.setHorizontalAlignment(JLabel.CENTER);
                panels[0][1].setLayout(new BorderLayout());
                panels[0][1].add(title);
                panels[1][1].setLayout(new BorderLayout());
                panels[1][1].add(grade);

            } else {
                JLabel grade = new JLabel("No Grade");
                grade.setFont(new Font("grade", 1, 30));
                grade.setHorizontalAlignment(JLabel.CENTER);
                JLabel total = new JLabel("Total: " + getTotal());
                total.setFont(new Font("total", 1, 20));
                total.setHorizontalAlignment(JLabel.CENTER);
                panels[0][1].setLayout(new BorderLayout());
                panels[1][1].setLayout(new BorderLayout());
                panels[0][1].add(grade);
                panels[1][1].add(total);
            }

            panels[1][0].add(changeBtn);
            panels[2][0].add(backBtn);
        }
        else {
            JLabel score = new JLabel("Enter Score:");
            score.setFont(new Font("score",0,14));
            panels[0][0].add(score);

            panels[0][1].setLayout(new BorderLayout());
            panels[0][1].add(pointInput);
            panels[2][1].add(submitBtn);
            panels[2][0].add(backBtn);
        }

        repaint();
        validate();
    }

    public void actionPerformed(ActionEvent ae) {
        resetComponents();
        if (ae.getSource() == changeBtn) {
            changingGrade = true;
        } else if (ae.getSource() == backBtn) {
            if (changingGrade) {
                pointInput.setText("");
                changingGrade = false;
            }
            else Gradebook.changeToSection(Section, Class, Semester);
        } else if (ae.getSource() == submitBtn) {
            if (!errorInChange()) {
                changingGrade = false;
                changeGrade();
            } else {
                pointInput.setBackground(new Color(255, 204, 204));
            }
        }
        setComponents();
    }

    private void resetComponents() {
        pointInput.setBackground(new Color(255, 255, 255));
    }

    private boolean errorInChange() {
        if (!pointInput.getText().matches("[1-9][0-9]*") && !pointInput.getText().matches("[1-9][0-9]*\\.[0-9]+")&& !pointInput.getText().matches("[0-9]")) return true;
        if (Float.parseFloat(pointInput.getText()) < 0) return true;
        if (Float.parseFloat(pointInput.getText()) > getTotal()) return true;
        return false;
    }

    public static float getGrade(File assignment) {
        float grade = 0;
        try {
            String[] buffer;
            BufferedReader BuffReader = new BufferedReader(new FileReader(assignment));
            buffer = BuffReader.readLine().split("\\+");
            BuffReader.close();
            if(buffer[0].matches("-1")) return -1;
            grade = Float.parseFloat(buffer[0])/Float.parseFloat(buffer[1]);
        }
        catch(IOException IOE) {}
        return grade;
    }

    private float getTotal() {
        float total = 0;
        try {
            BufferedReader BuffReader = new BufferedReader(new FileReader(Assignment));
            String[] old = BuffReader.readLine().split("\\+");
            total = Float.parseFloat(old[1]);
            BuffReader.close();
        }catch(IOException IOE) {}
        return total;
    }

    private float getScore() {
        float score = 0;
        try {
            String[] buffer;
            BufferedReader BuffReader = new BufferedReader(new FileReader(Assignment));
            buffer = BuffReader.readLine().split("\\+");
            BuffReader.close();
            score = Float.parseFloat(buffer[0]);
        }
        catch(IOException IOE) {}
        return score;
    }

    private boolean hasGrade() {
        try {
            String[] buffer;
            BufferedReader BuffReader = new BufferedReader(new FileReader(Assignment));
            buffer = BuffReader.readLine().split("\\+");
            BuffReader.close();
            if(buffer[0].matches("-1")) return false;
        }
        catch(IOException IOE) {}
        return true;
    }

    private void changeGrade() {
        try {
            BufferedReader BuffReader = new BufferedReader(new FileReader(Assignment));
            String[] old = BuffReader.readLine().split("\\+");
            BuffReader.close();
            old[0] = pointInput.getText();
            BufferedWriter BuffWriter = new BufferedWriter(new FileWriter(Assignment));
            BuffWriter.write(old[0]);
            BuffWriter.write("+");
            BuffWriter.write(old[1]);
            BuffWriter.close();
        }
        catch(IOException IOE) {}
    }
}