import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.DecimalFormat;
import java.util.Scanner;

/*
* implemented by Daniel and Redden
*/

public class Class extends JPanel implements ActionListener{

    private File Class;
    private File Semester;

    private File classInfo;

    private File[] sections;

    private int sectionCount = 0;

    private JButton selectBtn;
    private JButton backBtn;
    private JButton finishBtn;

    private JList<String> sectionList;

    public Class(File c, File s) {
        Class = c;
        Semester = s;
        if(Class.list().length == 0) Gradebook.changeToClassSetup(c, s);
        else {
            assignComponents();
            setComponents();
        }
    }

    private void assignComponents() {
        backBtn = new JButton("Go Back");
        backBtn.addActionListener(this);
        selectBtn = new JButton("Select Section");
        selectBtn.addActionListener(this);
        finishBtn = new JButton("Mark As Finished");
        finishBtn.addActionListener(this);
        fillSections();
    }

    private void fillSections() {
        getSections();
        DefaultListModel<String> list = new DefaultListModel<>();
        for(File i: sections) list.addElement(i.getName());
        sectionList = new JList<>(list);
    }

    private void getSections() {
        File[] temp = Class.listFiles();
        sectionCount = temp.length - 1;
        sections = new File[sectionCount];
        int index = 0;
        for (int i = 0; i < temp.length; ++i) {
            if (!temp[i].getName().matches("class")) {
                sections[index] = temp[i];
                index++;
            }
            else
                classInfo = temp[i];
        }
    }

    private void setComponents() {
        removeAll();

        setLayout(new GridLayout(1, 2, 0, 0));

        JPanel Btns = new JPanel();
        Btns.setLayout(new GridLayout(4, 1, 0, 0));
        JPanel[][] panels = new JPanel[4][1];
        for (int i = 0; i < 4; ++i) {
            panels[i][0] = new JPanel();
            Btns.add(panels[i][0]);
        }

        JLabel grade = new JLabel("Grade: " + getClassGrade(Class).split("\\+")[0]);
        grade.setFont(new Font("grade",1,20));
        grade.setHorizontalAlignment(JLabel.CENTER);
        panels[0][0].setLayout(new BorderLayout());
        panels[0][0].add(grade);
        panels[1][0].add(selectBtn);
        panels[2][0].add(backBtn);
        if(!isFinished()) {
            panels[2][0].add(finishBtn);
            panels[3][0].add(backBtn);
        }

        add(Btns);
        JScrollPane scroll = new JScrollPane();
        scroll.setViewportView(sectionList);
        add(scroll);

        repaint();
        validate();
    }

    public void actionPerformed(ActionEvent ae) {
        resetComponents();
        if(ae.getSource() == backBtn) Gradebook.changeToSemester(Semester);
        else if(ae.getSource() == selectBtn) {
            if(sectionList.isSelectionEmpty()) selectBtn.setBackground(new Color(255,204,204));
            else Gradebook.changeToSection(new File(Class.getAbsolutePath()+"/"+sectionList.getSelectedValue()),Class,Semester);
        }
        else if(ae.getSource() == finishBtn) markClassFinished();
        setComponents();
    }

    private void resetComponents() {
        selectBtn.setBackground(null);
    }

    private boolean isFinished() {
        String buffer, code = "";
        try{
            BufferedReader BuffReader = new BufferedReader(new FileReader(classInfo));
            while((buffer = BuffReader.readLine()) != null) {
                code = buffer;
            }
            BuffReader.close();
        }
        catch(IOException IOE) {}
        if(code.matches("1")) return true;
        else return false;
    }

    public boolean markClassFinished() {
        String buffer, code = "";
        int index = 0;
        try{
            BufferedReader BuffReader = new BufferedReader(new FileReader(classInfo));
            while((buffer = BuffReader.readLine()) != null) {
                code = buffer;
                index++;
            }
            BuffReader.close();
        }
        catch(IOException IOE) {}
        if(code.matches("1")) return false;
        String[] output = new String[index];
        try{
            BufferedReader BuffReader = new BufferedReader(new FileReader(classInfo));
            for(int i = 0; i < index; ++i) output[i] = BuffReader.readLine();
            output[index-1] = "1";
            BuffReader.close();
            BufferedWriter BuffWriter = new BufferedWriter(new FileWriter(classInfo));
            for(int i = 0; i < index; ++i) {
                BuffWriter.write(output[i]);
                if(i != index-1) BuffWriter.write("\n");
            }
            BuffWriter.close();
        }
        catch(IOException IOE) {}
        Major.updateProgress(Class.getName());
        return true;
    }

    public static String getClassGrade(File classfile) {
        String[] sectionInfo = {};
        String creditHours = "";
        try {
            int total = 0;
            File classinfo = new File(classfile.getAbsolutePath()+"/class");
            BufferedReader BuffReader = new BufferedReader(new FileReader(classinfo));
            String buffer = BuffReader.readLine();
            creditHours = buffer;
            buffer = BuffReader.readLine();
            while(!buffer.matches("0") && !buffer.matches("1")) {
                total++;
                buffer = BuffReader.readLine();
            }
            sectionInfo = new String[total];
            BuffReader.close();

            total = 0;
            BuffReader = new BufferedReader(new FileReader(classfile.getAbsolutePath()+"/class"));
            buffer = BuffReader.readLine();//credit hours
            buffer = BuffReader.readLine();
            while(!buffer.matches("0") && !buffer.matches("1")) {
                sectionInfo[total] = buffer;
                buffer = BuffReader.readLine();
                total++;
            }
            BuffReader.close();
        }
        catch(IOException IOE) {}
        File[] sections = classfile.listFiles();
        float grade, total = 0;
        for(File i : sections) {
            if(!i.getName().matches("class")) {
                grade = Section.getSectionGrade(i);
                if(grade != -1) {
                    for (String s : sectionInfo) {
                        if (s.split("\\+")[0].matches(i.getName())) {
                            grade = grade * Integer.parseInt(s.split("\\+")[1]);
                            total += grade;
                            break;
                        }
                    }
                }
            }
        }

        return String.valueOf(total)+"+"+creditHours;
    }
}
