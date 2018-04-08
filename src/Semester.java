import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * A class that offers functionality to add and view a semester
 * implemented by Felipe and Daniel
 * */

public class Semester extends JPanel implements ActionListener{

    private File Semester;

    private File[] classes = {};

    private JButton addBtn;
    private JButton backBtn;
    private JButton selectBtn;
    private JButton submitBtn;

    private JList<String> classesList;

    private JTextField subjectCode;
    private JTextField classCode;

    private boolean addingClass = false;

    public Semester(File semester) {
        Semester = semester;
        assignComponents();
        setComponents();
    }

    private void assignComponents() {
        addBtn = new JButton("Add Class");
        addBtn.addActionListener(this);
        backBtn = new JButton("Go Back");
        backBtn.addActionListener(this);
        selectBtn = new JButton("Select Class");
        selectBtn.addActionListener(this);
        submitBtn = new JButton("Submit Class");
        submitBtn.addActionListener(this);
        subjectCode = new JTextField();
        classCode = new JTextField();
        fillClasses();
    }

    private void fillClasses() {
        getClasses();
        DefaultListModel<String> list = new DefaultListModel<>();
        for(File i: classes) list.addElement(i.getName());
        classesList = new JList<>(list);
    }

    private void setComponents() {
        removeAll();
        if(addingClass) {
            setLayout(new GridLayout(4, 2, 0, 0));

            JPanel[][] panels = new JPanel[4][2];
            for (int i = 0; i < 4; ++i) {
                for(int e =0; e < 2; ++e) {
                    panels[i][e] = new JPanel();
                    add(panels[i][e]);
                }
            }

            panels[0][0].add(new JLabel("Subject Code:"));
            panels[1][0].add(new JLabel("Class Code:"));
            panels[3][1].add(submitBtn);
            panels[3][0].add(backBtn);
            panels[0][1].setLayout(new BorderLayout());
            panels[0][1].add(subjectCode);
            panels[1][1].setLayout(new BorderLayout());
            panels[1][1].add(classCode);
        }
        else {
            setLayout(new GridLayout(1, 2, 0, 0));

            JPanel Btns = new JPanel();
            Btns.setLayout(new GridLayout(4, 1, 0, 0));
            JPanel[][] panels = new JPanel[4][1];
            for (int i = 0; i < 4; ++i) {
                panels[i][0] = new JPanel();
                Btns.add(panels[i][0]);
            }

            JLabel instruct = new JLabel("Select a Class:");
            instruct.setFont(new Font("instruct",1,20));
            instruct.setHorizontalAlignment(JLabel.CENTER);
            panels[0][0].add(instruct);
            panels[1][0].add(selectBtn);
            panels[2][0].add(addBtn);
            panels[3][0].add(backBtn);

            add(Btns);
            JScrollPane scroll = new JScrollPane();
            scroll.setViewportView(classesList);
            add(scroll);
        }
        repaint();
        validate();
    }

    public void actionPerformed(ActionEvent ae) {
        resetComponents();
        if(ae.getSource() == backBtn) {
            if(addingClass) {
                addingClass = false;
                subjectCode.setText(null);
                classCode.setText(null);
                subjectCode.setBackground(new Color(255,255,255));
                classCode.setBackground(new Color(255,255,255));
            }
            else Gradebook.changeToSemesters();
        }
        else if(ae.getSource() == addBtn) {
            addingClass = true;
        }
        else if(ae.getSource() == selectBtn) {
            if(classesList.isSelectionEmpty()) selectBtn.setBackground(new Color(255,204,204));
            else Gradebook.changeToClass(new File(Semester.getAbsolutePath()+"/"+classesList.getSelectedValue()), Semester);
        }
        else if(ae.getSource() == submitBtn) {
            if(addClass()) {
                addingClass = false;
                subjectCode.setText(null);
                classCode.setText(null);
                subjectCode.setBackground(new Color(255,255,255));
                classCode.setBackground(new Color(255,255,255));
            }
            //else submitBtn.setBackground(new Color(255,204,204));
        }
        setComponents();
    }

    private void resetComponents() {
        selectBtn.setBackground(null);
        submitBtn.setBackground(null);
    }

    private void getClasses() {
        classes = Semester.listFiles();
    }

    private boolean addClass() {
        String sCode = subjectCode.getText();
        String cCode = classCode.getText();

        if(!sCode.matches("[a-zA-Z]+") || sCode.length() != 3) {
            subjectCode.setBackground(new Color(255,204,204));
            return false;
        }
        subjectCode.setText(sCode.toUpperCase());

        if(!cCode.matches("[0-9]+") || cCode.length() != 4) {
            classCode.setBackground(new Color(255,204,204));
            return false;
        }

        for(File i : classes)
            if(i.getName().matches(subjectCode.getText()+" "+classCode.getText())) {
                subjectCode.setBackground(new Color(255,204,204));
                classCode.setBackground(new Color(255,204,204));
                return false;
            }

        addNewClass(subjectCode.getText()+" "+classCode.getText());
        fillClasses();
        return true;
    }

    private void addNewClass(String name) {
        File newSemester = new File(Semester.getAbsolutePath() + "/" +name);
        newSemester.mkdir();
    }

    public static String[] getSemesterGrade(File semester) {
        String[] grades = new String[semester.list().length];
        File[] classes = semester.listFiles();
        int index = 0;
        for(File i : classes) {
            grades[index] = Class.getClassGrade(i);
            index++;
        }
        return grades;
    }
}