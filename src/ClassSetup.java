import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class ClassSetup extends JPanel implements ActionListener {

    private File Class;
    private File Semester;

    private String writeOut = "";
    private int total = 0;
    private LinkedList<String> dirs = new LinkedList<>();

    private JButton submitBtn;
    private JButton backBtn;
    private JButton addBtn;

    private boolean addingSection = false;

    private JTextField hoursInput;
    private JTextField nameInput;
    private JTextField weightInput;

    public ClassSetup(File c, File s) {
        Class = c;
        Semester = s;
        assignComponents();
        setComponents();
    }

    private void assignComponents() {
        submitBtn = new JButton("Submit");
        submitBtn.addActionListener(this);
        backBtn = new JButton("Go Back");
        backBtn.addActionListener(this);
        addBtn = new JButton("Add Another Section");
        addBtn.addActionListener(this);

        hoursInput = new JTextField();
        nameInput = new JTextField();
        weightInput = new JTextField();
    }

    private void setComponents() {
        removeAll();
        setLayout(new GridLayout(5,2,0,0));

        JPanel[][] panels = new JPanel[5][2];
        for(int i = 0; i < 5; ++i) {
            for(int e = 0; e < 2; ++e) {
                panels[i][e] = new JPanel();
                add(panels[i][e]);
            }
        }

        if(addingSection) {
            JLabel cCode = new JLabel("Enter Section Name:");
            cCode.setFont(new Font("name",0,14));
            panels[1][0].add(cCode);

            JLabel name = new JLabel("Enter Class Weight Percentage:");
            name.setFont(new Font("weight",0,14));
            panels[2][0].add(name);

            panels[1][1].setLayout(new BorderLayout());
            panels[1][1].add(nameInput);
            panels[2][1].setLayout(new BorderLayout());
            panels[2][1].add(weightInput);

            panels[4][1].add(addBtn);
            panels[4][1].add(submitBtn);
            panels[4][0].add(backBtn);
        }
        else {
            JLabel Hours = new JLabel("Enter Credit Hours:");
            Hours.setFont(new Font("hours",0,14));
            panels[1][0].add(Hours);

            panels[0][1].setLayout(new BorderLayout());
            panels[0][1].add(hoursInput);

            panels[4][1].add(submitBtn);
        }

        repaint();
        validate();
    }

    public void actionPerformed(ActionEvent ae) {
        resetComponents();
        if(ae.getSource() == backBtn) {
            addingSection = false;
            writeOut = "";
            dirs.clear();
            total = 0;
            nameInput.setText("");
            weightInput.setText("");
        }
        else if(ae.getSource() == submitBtn) {
            if(addingSection) {
                if(!checkSection() || total != 100) addBtn.setBackground(new Color(255,204,204));
                else {
                    initializeClass();
                    Gradebook.changeToClass(Class,Semester);
                }
            }
            else {
                if(checkHours()) addingSection = true;
                else submitBtn.setBackground(new Color(255,204,204));
            }
        }
        else if(ae.getSource() == addBtn) {
            if(!checkSection()) addBtn.setBackground(new Color(255,204,204));
            else {
                nameInput.setText("");
                weightInput.setText("");
            }
        }
        setComponents();
    }

    private void resetComponents() {
        addBtn.setBackground(null);
        submitBtn.setBackground(null);
    }

    private boolean checkHours() {
        if(!hoursInput.getText().matches("[1-9]")) return false;
        writeOut += hoursInput.getText() + "\n";
        return true;
    }

    private boolean checkSection() {
        if (!nameInput.getText().matches("[a-zA-Z]+")) return false;
        if (!weightInput.getText().matches("[0-9]+") || Integer.parseInt(weightInput.getText()) > 100 || Integer.parseInt(weightInput.getText()) < 1) return false;
        writeOut += nameInput.getText()+"+"+weightInput.getText()+"\n";
        total += Integer.parseInt(weightInput.getText());
        dirs.add(nameInput.getText());
        return true;
    }

    private boolean initializeClass() {
        File classFile = new File(Class.getAbsolutePath() + "/class");
        writeOut += "0";
        try{
            classFile.createNewFile();
            for(String i : dirs) {
                new File(Class.getAbsolutePath()+"/"+i).mkdir();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(classFile));
            writer.write(writeOut);
            writer.close();
        }
        catch(IOException IOE) {return false;}
        return true;
    }
}
