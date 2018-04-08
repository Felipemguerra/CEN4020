import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

/*
* implemented by Redden and Brian
*/

public class Section extends JPanel implements ActionListener{

    static private File Section;
    static private File Class;
    static private File Semester;

    static private File[] assignments;

    private JList<String> assignmentList;

    private JButton selectBtn;
    private JButton backBtn;
    private JButton addBtn;
    private JButton submitBtn;

    private JTextField nameInput;
    private JTextField totalInput;

    private boolean addingAssgnment = false;

    public Section(File sec, File c, File sem) {
        Section = sec;
        Class = c;
        Semester = sem;
        assignComponents();
        setComponents();
    }

    private void assignComponents() {
        selectBtn = new JButton("Select Assignment");
        selectBtn.addActionListener(this);
        backBtn = new JButton("Go Back");
        backBtn.addActionListener(this);
        addBtn = new JButton("Add Assignment");
        addBtn.addActionListener(this);
        submitBtn = new JButton("Submit");
        submitBtn.addActionListener(this);
        nameInput = new JTextField();
        totalInput = new JTextField();
        fillAssignments();
    }

    private static void getAssignments() {
        assignments = Section.listFiles();
    }

    private void fillAssignments() {
        getAssignments();
        DefaultListModel<String> list = new DefaultListModel<>();
        for(File i : assignments) list.addElement(i.getName());
        assignmentList = new JList<>(list);
    }

    private void setComponents() {
        removeAll();

        if(addingAssgnment) {
            setLayout(new GridLayout(3, 2, 15, 15));
            JPanel[][] panels = new JPanel[3][2];
            for (int i = 0; i < 3; ++i) {
                for(int e = 0; e < 2; e++) {
                    panels[i][e] = new JPanel();
                    add(panels[i][e]);
                }
            }
            JLabel name = new JLabel("Enter Name:");
            name.setFont(new Font("name",0,14));
            panels[0][0].add(name);

            JLabel points = new JLabel("Enter Total Points:");
            points.setFont(new Font("points",0,14));
            panels[1][0].add(points);

            panels[0][1].setLayout(new BorderLayout());
            panels[0][1].add(nameInput);
            panels[1][1].setLayout(new BorderLayout());
            panels[1][1].add(totalInput);
            panels[2][1].add(submitBtn);
            panels[2][0].add(backBtn);
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

            JLabel grade = new JLabel("Grade: " + String.format("%.2f", (getSectionGrade(Section) * 100)));
            grade.setFont(new Font("grade", 1, 20));
            grade.setHorizontalAlignment(JLabel.CENTER);
            JLabel instruct = new JLabel("Select an Assignment: ");
            instruct.setFont(new Font("instruct", 0, 16));
            instruct.setHorizontalAlignment(JLabel.CENTER);
            panels[0][0].setLayout(new GridLayout(2,1,15,15));
            panels[0][0].add(grade);
            panels[0][0].add(instruct);
            panels[1][0].add(selectBtn);
            panels[2][0].add(addBtn);
            panels[3][0].add(backBtn);

            add(Btns);
            JScrollPane scroll = new JScrollPane();
            scroll.setViewportView(assignmentList);
            add(scroll);
        }

        repaint();
        validate();
    }

    public void actionPerformed(ActionEvent ae) {
        resetComponents();
        if(ae.getSource() == backBtn) {
            if(addingAssgnment) {
                addingAssgnment = false;
                resetComponents();
            }
            else Gradebook.changeToClass(Class,Semester);
        }
        else if(ae.getSource() == selectBtn) {
            if(assignmentList.isSelectionEmpty()) selectBtn.setBackground(new Color(255,204,204));
            else Gradebook.changeToAssignment(new File(Section.getAbsolutePath()+"/"+assignmentList.getSelectedValue()),Section,Class,Semester);
        }
        else if(ae.getSource() == addBtn) {
            addingAssgnment = true;
        }
        else if(ae.getSource() == submitBtn) {
            if(!errorInInfo()) {
                addingAssgnment = false;
                addNewAssignment();
                fillAssignments();
                nameInput.setText("");
                totalInput.setText("");
            }
        }
        setComponents();
    }

    private void resetComponents() {
        selectBtn.setBackground(null);
        nameInput.setBackground(new Color(255,255,255));
        totalInput.setBackground(new Color(255,255,255));
    }

    private boolean errorInInfo() {
        if(nameInput.getText().matches("")) {
            nameInput.setBackground(new Color(255,204,204));
            return true;
        }
        for(File i : assignments) {
            if(i.getName().matches(nameInput.getText())) {
                nameInput.setBackground(new Color(255,204,204));
                return true;
            }
        }
        if(!totalInput.getText().matches("[1-9][0-9]*") ) {
            totalInput.setBackground(new Color(255,204,204));
            return true;
        }
        return false;
    }

    private void addNewAssignment() {
        File newAssignment = new File(Section.getAbsolutePath() + "/" +nameInput.getText());
        try {
            newAssignment.createNewFile();
            BufferedWriter BuffWriter = new BufferedWriter(new FileWriter(newAssignment));
            BuffWriter.write("-1");
            BuffWriter.write("+");
            BuffWriter.write(totalInput.getText());
            BuffWriter.close();
        }
        catch(IOException IOE) {}
    }

    public static float getSectionGrade(File section) {
        File[] files = section.listFiles();
        float grade = 0;
        int total = 0;
        boolean nullFile = true;
        if(files.length == 0) return -1;
        for(int i =0; i < files.length; ++i) {
            if(Assignment.getGrade(files[i]) >= 0) {
                grade += Assignment.getGrade(files[i]);
                total++;
                nullFile = false;
            }
        }
        if(nullFile) return -1;
        grade /= total;
        return grade;
    }
}