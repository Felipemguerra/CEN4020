import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Scanner;

public class Semesters extends JPanel implements ActionListener{

    public static String semestersPath = StartMenu.userPath+"semesters/";

    private File[] semesters = {};

    private boolean addingSemester = false;

    private JButton addBtn;
    private JButton selectBtn;
    private JButton backBtn;
    private JButton submitBtn;

    private JTextField semName;
    private JTextField semYear;

    private JList<String> semestersList;

    public Semesters() {
        assignComponents();
        setComponents();
    }

    private void assignComponents() {
        addBtn = new JButton("Add Semester");
        addBtn.addActionListener(this);
        selectBtn = new JButton("Select Semester");
        selectBtn.addActionListener(this);
        backBtn = new JButton("Go Back");
        backBtn.addActionListener(this);
        submitBtn = new JButton("Submit");
        submitBtn.addActionListener(this);

        semName = new JTextField();
        semYear = new JTextField();
        fillSemesters();
    }

    private void fillSemesters() {
        getSemesters();
        DefaultListModel<String> list = new DefaultListModel<>();
        for(File i: semesters) list.addElement(i.getName());
        semestersList = new JList<>(list);
    }

    private void setComponents() {
        removeAll();
        if(addingSemester) {
            setLayout(new GridLayout(4, 2, 15, 15));

            JPanel[][] panels = new JPanel[4][2];
            for (int i = 0; i < 4; ++i) {
                for(int e =0; e < 2; ++e) {
                    panels[i][e] = new JPanel();
                    add(panels[i][e]);
                }
            }

            panels[0][0].add(new JLabel("Semester Name:"));
            panels[1][0].add(new JLabel("Semester Year:"));
            panels[3][1].add(submitBtn);
            panels[3][0].add(backBtn);
            panels[0][1].setLayout(new BorderLayout());
            panels[0][1].add(semName);
            panels[1][1].setLayout(new BorderLayout());
            panels[1][1].add(semYear);
        }
        else {
            setLayout(new GridLayout(1, 2, 15, 15));

            JPanel Btns = new JPanel();
            Btns.setLayout(new GridLayout(4, 1, 15, 15));
            JPanel[][] panels = new JPanel[4][1];
            for (int i = 0; i < 4; ++i) {
                panels[i][0] = new JPanel();
                Btns.add(panels[i][0]);
            }

            JLabel instruct = new JLabel("Select a Semester:");
            instruct.setFont(new Font("instruct",1,20));
            instruct.setHorizontalAlignment(JLabel.CENTER);
            panels[0][0].add(instruct);
            panels[1][0].add(selectBtn);
            panels[2][0].add(addBtn);
            panels[3][0].add(backBtn);

            add(Btns);
            JScrollPane scroll = new JScrollPane();
            scroll.setViewportView(semestersList);
            add(scroll);
        }
        repaint();
        validate();
    }

    public void actionPerformed(ActionEvent ae) {
        resetComponents();
        if(ae.getSource() == backBtn) {
            if(addingSemester) {
                semName.setText(null);
                semYear.setText(null);
                semName.setBackground(new Color(255,255,255));
                semYear.setBackground(new Color(255,255,255));
                addingSemester = false;
            }
            else Gradebook.changeToUserProfile();
        }
        else if(ae.getSource() == addBtn) {
            addingSemester = true;
        }
        else if(ae.getSource() == selectBtn) {
            if(semestersList.isSelectionEmpty()) selectBtn.setBackground(new Color(255,204,204));
            else Gradebook.changeToSemester(new File(semestersPath+semestersList.getSelectedValue()));
        }
        else if(ae.getSource() == submitBtn) {
            if(addSemester()) {
                addingSemester = false;
                semName.setText(null);
                semYear.setText(null);
                semName.setBackground(new Color(255,255,255));
                semYear.setBackground(new Color(255,255,255));
            }
            //else submitBtn.setBackground(new Color(255,204,204));
        }
        setComponents();
    }

    private void resetComponents() {
        selectBtn.setBackground(null);
        submitBtn.setBackground(null);
    }

    private void getSemesters() {
        File sems = new File(System.getProperty("user.dir")+"/user_profile/semesters");
        if(!sems.exists()){ sems.mkdir(); }
        semesters = sems.listFiles();
    }

    private boolean addSemester() {
        String name = semName.getText();
        String year = semYear.getText();

        if(!name.matches("(?i)Fall") && !name.matches("(?i)Spring") && !name.matches("(?i)Summer")) {
            semName.setBackground(new Color(255,204,204));
            return false;
        }
        semName.setText(name.substring(0,1).toUpperCase() + name.substring(1));

        if(!year.matches("[0-9][0-9][0-9][0-9]")) {
            semYear.setBackground(new Color(255,204,204));
            return false;
        }

        for(File i : semesters) {
            if(i.getName().matches(semName.getText()+" "+semYear.getText())) {
                semName.setBackground(new Color(255,204,204));
                semYear.setBackground(new Color(255,204,204));
                return false;
            }
        }

        addNewSemester(semName.getText()+" "+semYear.getText());
        fillSemesters();
        return true;
    }

    private void addNewSemester(String name) {
        File newSemester = new File(semestersPath + name);
        newSemester.mkdir();
    }
}
