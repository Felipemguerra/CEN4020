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
            setLayout(new GridLayout(4, 2, 0, 0));

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
            setLayout(new GridLayout(1, 2, 0, 0));

            JPanel Btns = new JPanel();
            Btns.setLayout(new GridLayout(3, 1, 0, 0));
            JPanel[][] panels = new JPanel[3][1];
            for (int i = 0; i < 3; ++i) {
                panels[i][0] = new JPanel();
                Btns.add(panels[i][0]);
            }

            panels[0][0].add(selectBtn);
            panels[1][0].add(addBtn);
            panels[2][0].add(backBtn);

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
            if(addingSemester) addingSemester = false;
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
            if(addSemester()) addingSemester = false;
            else submitBtn.setBackground(new Color(255,204,204));
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

        if(!name.matches("(?i)Fall") && !name.matches("(?i)Spring") && !name.matches("(?i)Summer")) return false;
        semName.setText(name.substring(0,1).toUpperCase() + name.substring(1));

        if(!year.matches("[0-9][0-9][0-9][0-9]")) return false;

        for(File i : semesters) {
            if(i.getName().matches(semName.getText()+" "+semYear.getText())) {
                return false;
            }
        }

        addNewSemester(semName.getText()+" "+semYear.getText());
        semName.setText("");
        semYear.setText("");
        fillSemesters();
        return true;
    }

    private void addNewSemester(String name) {
        File newSemester = new File(semestersPath + name);
        newSemester.mkdir();
    }
}
