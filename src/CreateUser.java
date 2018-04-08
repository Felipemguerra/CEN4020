import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

/*Implemented by Felipe and Brian*/

/**
 * Used to update the files in the user profile.
 * */
public class CreateUser extends JPanel implements ActionListener{

    private boolean isMajorChange;
    private boolean addingClass = false;

    private JButton submitBtn;
    private JButton addBtn;
    private JButton backBtn;

    private JTextField firstNameInput;
    private JTextField lastNameInput;
    private JTextField majorInput;

    private JTextField sCodeInput;
    private JTextField cCodeInput;
    private JTextField nameInput;
    private JTextField hoursInput;

    private String majorList = "";

    public CreateUser(boolean b) {
        isMajorChange = b;
        assignComponents();
        setComponents();
    }

    private void assignComponents() {
        submitBtn = new JButton();
        submitBtn.setText("Submit");
        submitBtn.addActionListener(this);
        addBtn = new JButton();
        addBtn.setText("Add Class");
        addBtn.addActionListener(this);
        backBtn = new JButton();
        backBtn.setText("Go Back");
        backBtn.addActionListener(this);
        //if major change, set these fields with defaults
        firstNameInput = new JTextField();
        lastNameInput = new JTextField();
        if (isMajorChange) {
            File user = new File(System.getProperty("user.dir")+"/user_profile/user");
            String FirstName = "";
            String LastName = "";
            try {
                FileReader InputStream = new FileReader(user);
                BufferedReader BuffReader = new BufferedReader(InputStream);
                FirstName = BuffReader.readLine();
                LastName = BuffReader.readLine();
                BuffReader.close();
            }
            catch (FileNotFoundException FNF) {}
            catch (IOException IOE) {}
            firstNameInput.setText(FirstName);
            lastNameInput.setText(LastName);
        }
        majorInput = new JTextField();
        sCodeInput = new JTextField();
        cCodeInput = new JTextField();
        nameInput = new JTextField();
        hoursInput = new JTextField();
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

        if(addingClass) {
            JLabel sCode = new JLabel("Enter Subject Code:");
            sCode.setFont(new Font("scode",0,14));
            panels[0][0].add(sCode);

            JLabel cCode = new JLabel("Enter Class Code:");
            cCode.setFont(new Font("ccode",0,14));
            panels[1][0].add(cCode);

            JLabel name = new JLabel("Enter Class Name:");
            name.setFont(new Font("name",0,14));
            panels[2][0].add(name);

            JLabel hours = new JLabel("Enter Credit Hours:");
            hours.setFont(new Font("hours",0,14));
            panels[3][0].add(hours);

            panels[0][1].setLayout(new BorderLayout());
            panels[0][1].add(sCodeInput);
            panels[1][1].setLayout(new BorderLayout());
            panels[1][1].add(cCodeInput);
            panels[2][1].setLayout(new BorderLayout());
            panels[2][1].add(nameInput);
            panels[3][1].setLayout(new BorderLayout());
            panels[3][1].add(hoursInput);

            panels[4][1].add(addBtn);
            panels[4][1].add(submitBtn);
            panels[4][0].add(backBtn);
        }
        else {
            JLabel fName = new JLabel("Enter Your First Name:");
            fName.setFont(new Font("fname",0,14));
            panels[0][0].add(fName);

            JLabel lName = new JLabel("Enter Your Last Name:");
            lName.setFont(new Font("lname",0,14));
            panels[1][0].add(lName);

            JLabel major = new JLabel("Enter Your Major:");
            major.setFont(new Font("major",0,14));
            panels[2][0].add(major);

            panels[0][1].setLayout(new BorderLayout());
            panels[0][1].add(firstNameInput);
            panels[1][1].setLayout(new BorderLayout());
            panels[1][1].add(lastNameInput);
            panels[2][1].setLayout(new BorderLayout());
            panels[2][1].add(majorInput);

            panels[4][1].add(submitBtn);
            panels[4][0].add(backBtn);
        }

        repaint();
        validate();
    }
    public void actionPerformed(ActionEvent ae) {
        resetInputFields();
        if(ae.getSource() == submitBtn && !addingClass) {
            if(!errorInInfo()) { addingClass = true;}
        }
        else if(ae.getSource() == submitBtn && addingClass){
            if(!errorInClass()) {
                majorList += getAClass();
                populate();
                if(isMajorChange) {
                    //update progress
                    File semesters = new File(Semesters.semestersPath);
                    if(semesters.exists()) {
                        for(File i : semesters.listFiles()){
                            for(File e : i.listFiles()){
                                Class c = new Class(e,i);
                                if(c.isFinished()){
                                    Major.updateProgress(e.getName());
                                }
                            }
                        }

                    }
                    Gradebook.changeToMajor();
                }
                else Gradebook.changeToStartMenu();

            }
        }
        else if(ae.getSource() == addBtn){
            if(!errorInClass()) {
                majorList += getAClass() + "\n";
                clearInputFields();
            }
        }
        else if(ae.getSource() == backBtn) {
            if(addingClass) {
                addingClass = false;
                majorList = "";
            }
            else {
                if(isMajorChange) Gradebook.changeToMajor();
                else Gradebook.changeToStartMenu();
            }
        }
        setComponents();
    }

    private boolean errorInInfo() {
        if(!checkUserName(firstNameInput.getText())) {
            firstNameInput.setBackground(new Color(255,204,204));
            return true;
        }
        else if(!checkUserName(lastNameInput.getText())) {
            lastNameInput.setBackground(new Color(255,204,204));
            return true;
        }
        else if(!checkMajor(majorInput.getText())) {
            majorInput.setBackground(new Color(255,204,204));
            return true;
        }
        else return false;
    }

    private boolean errorInClass() {
        if(!checkClassSubject(sCodeInput.getText())){
            sCodeInput.setBackground(new Color(255,204,204));
            return true;
        }
        else if(!checkClassCode(cCodeInput.getText())) {
            cCodeInput.setBackground(new Color(255,204,204));
            return true;
        }
        else if(!checkClassName(nameInput.getText())) {
            nameInput.setBackground(new Color(255,204,204));
            return true;
        }
        else if(!checkCreditHours(hoursInput.getText())) {
            hoursInput.setBackground(new Color(255,204,204));
            return true;
        }
        else return false;
    }

    private void resetInputFields(){
        firstNameInput.setBackground(new Color(255,255,255));
        lastNameInput.setBackground(new Color(255,255,255));
        majorInput.setBackground(new Color(255,255,255));
        sCodeInput.setBackground(new Color(255,255,255));
        cCodeInput.setBackground(new Color(255,255,255));
        nameInput.setBackground(new Color(255,255,255));
        hoursInput.setBackground(new Color(255,255,255));
    }

    private void clearInputFields() {
        sCodeInput.setText("");
        cCodeInput.setText("");
        nameInput.setText("");
        hoursInput.setText("");
    }

    private String getAClass() {
        return sCodeInput.getText().toUpperCase()+"+"+cCodeInput.getText()+"+"+nameInput.getText()+"+"+hoursInput.getText();
    }

    public void populate() {
        File temp = new File(System.getProperty("user.dir")+"/console_temp");
        try {
            BufferedWriter BuffWriter = new BufferedWriter(new FileWriter(temp));
            BuffWriter.write(firstNameInput.getText());
            BuffWriter.newLine();
            BuffWriter.write(lastNameInput.getText());
            BuffWriter.newLine();

            BuffWriter.write(majorInput.getText());
            BuffWriter.newLine();
            BuffWriter.write(majorList);
            BuffWriter.close();
        }
        catch (FileNotFoundException FNF) {}
        catch (IOException IOE) {}
        populateFromFile(temp);
        temp.delete();
    }

    private boolean populateFromFile(File input) {
        new File(StartMenu.userPath).mkdir();

        File user = new File(UserProfile.userPath);
        File major = new File(Major.majorPath);
        File prog = new File(Major.progressPath);
        try {
            BufferedReader BuffReader = new BufferedReader(new FileReader(input));
            BufferedWriter BuffWriter = new BufferedWriter(new FileWriter(user));
            BufferedWriter ProgWriter = new BufferedWriter(new FileWriter(prog));

            String buffer;

            buffer = BuffReader.readLine();
            BuffWriter.write(buffer);
            BuffWriter.newLine();

            buffer = BuffReader.readLine();
            BuffWriter.write(buffer);
            BuffWriter.close();

            BuffWriter = new BufferedWriter(new FileWriter(major));
            buffer = BuffReader.readLine();
            BuffWriter.write(buffer);
            ProgWriter.write("=");
            while((buffer = BuffReader.readLine()) != null){
                BuffWriter.newLine();
                ProgWriter.newLine();
                BuffWriter.write(buffer);
                ProgWriter.write(buffer);
            }
            BuffReader.close();
            BuffWriter.close();
            ProgWriter.close();
        }
        catch (FileNotFoundException FNF) {System.err.println("Missing input File"); return false;}
        catch (IOException IOE) {System.err.println("Error Reading From File"); return false;}
        return true;
    }

    /**
     * Format check for name.
     * @param s the given name that will be checked.
     * @return  whether given name is valid or not
     * */
    public boolean checkUserName(String s) {
        if(s == null) return false;
        if (s.matches("")) return false;
        if(s.matches("[\\s]+")) return false;
        if (!s.chars().allMatch(Character::isLetter)) return false;
        return true;
    }

    /**
     * Format check for major.
     * @param s the given major that will be checked.
     * @return  whether given major is valid or not
     * */
    public boolean checkMajor(String s) {
        if(s == null) return false;
        if (s.matches("")) return false;
        if (s.matches("[\\s]+")) return false;
        if (!s.matches("[a-zA-Z\\s]+")) return false;
        return true;
    }

    /**
     * Format check for class subject code.
     * @param s the given class subject code tha will be checked.
     * @return  whether given class subject code is valid or not
     * */
    public boolean checkClassSubject(String s) {
        if(s == null) return false;
        if (s.matches("")) return false;
        if(s.matches("[\\s]+")) return false;
        if(!s.matches("[a-zA-Z]+") || s.length() != 3) return false;
        return true;
    }

    /**
     * Format check for class code.
     * @param s the given class code that will be checked.
     * @return  whether given class code is valid or not
     * */
    public boolean checkClassCode(String s) {
        if(s == null) return false;
        if (s.matches("")) return false;
        if(s.matches("[\\s]+")) return false;
        if(!s.matches("[0-9]+") || s.length() != 4) return false;
        return true;
    }

    /**
     * Format check for class name.
     * @param s the given class name that will be checked.
     * @return  whether given class name is valid or not
     * */
    public boolean checkClassName(String s) {
        if(s == null) return false;
        if(s.matches("")) return false;
        if(s.matches("[\\s]+")) return false;
        return true;
    }

    /**
     * Format check for credit hours.
     * @param s the given credit hours that will be checked.
     * @return  whether given credit hours is valid or not
     * */
    public boolean checkCreditHours(String s) {
        if(s == null) return false;
        if (s.matches("")) return false;
        if(s.matches("[\\s]+")) return false;
        if(!s.matches("[1-9]")) return false;
        return true;
    }
}
