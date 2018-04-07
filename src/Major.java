import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/*Implemented by Daniel and Brian*/

/**
 * A class that offers functionality to view and edit major.
 * */
public class Major extends JPanel implements ActionListener {

    public static String majorPath = StartMenu.userPath+"major";
    public static String progressPath = StartMenu.userPath+"progress";

    private static String Major;

    private static JButton majorBtn;
    private static JButton progressBtn;
    private static JButton changeBtn;
    private static JButton backBtn;

    private static boolean showMajor = true;

    private static JScrollPane scrollPane;

    public Major() {
        assignComponents();
        setComponents();
        setVisible(true);
    }

    private void assignComponents() {
        majorBtn = new JButton();
        majorBtn.setText("Show Major");
        majorBtn.addActionListener(this);
        progressBtn = new JButton();
        progressBtn.setText("View Your Progress");
        progressBtn.addActionListener(this);
        changeBtn = new JButton();
        changeBtn.setText("Change Your Major");
        changeBtn.addActionListener(this);
        backBtn = new JButton();
        backBtn.setText("Go Back");
        backBtn.addActionListener(this);
        scrollPane = new JScrollPane();
    }

    private void setComponents() {
        removeAll();

        setLayout(new GridLayout(1, 2, 0, 0));

        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(4, 1, 0, 0));

        JPanel[][] panels = new JPanel[4][1];
        for (int i = 0; i < 4; ++i) {
            panels[i][0] = new JPanel();
            buttons.add(panels[i][0]);
        }
        add(buttons);

        getMajor();
        panels[0][0].setLayout(new GridLayout(2,1,0,0));
        JLabel major = new JLabel(Major);
        major.setFont(new Font("majorLabel", 0, 18));
        major.setHorizontalAlignment(JLabel.CENTER);
        panels[0][0].add(major);

        JLabel instruct = new JLabel("Choose an Option:");
        instruct.setFont(new Font("details", 0, 14));
        instruct.setHorizontalAlignment(JLabel.CENTER);
        panels[0][0].add(instruct);

        if (showMajor) {
            majorBtn.setEnabled(false);
            progressBtn.setEnabled(true);
            panels[1][0].add(progressBtn);
            scrollPane = new JScrollPane(getMajor());
            scrollPane.setPreferredSize(new Dimension(300, 100));
            add(scrollPane);
        } else {
            majorBtn.setEnabled(true);
            progressBtn.setEnabled(false);
            panels[1][0].add(majorBtn);
            scrollPane = new JScrollPane(getProgress());
            scrollPane.setPreferredSize(new Dimension(300, 100));
            add(scrollPane);
        }
        changeBtn.setEnabled(true);
        backBtn.setEnabled(true);

        panels[2][0].add(changeBtn);
        panels[3][0].add(backBtn);

        repaint();
        validate();
    }

    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == majorBtn) {
            showMajor = true;
        }
        else if(ae.getSource() == progressBtn) {
            showMajor = false;
        }
        else if(ae.getSource() == changeBtn) {
            Gradebook.changeToCreateUser(true);
        }
        else if(ae.getSource() == backBtn) {
            Gradebook.changeToUserProfile();
        }
        setComponents();
    }

    /**
     * Prints major from user profile.
     * */
    public static JTextArea getMajor() {
        File TestUserFile = new File(majorPath);
        JTextArea text = new JTextArea();
        try {
            FileReader InputStream = new FileReader(TestUserFile);
            BufferedReader BuffReader = new BufferedReader(InputStream);

            Major = BuffReader.readLine();
            String buffer = BuffReader.readLine();
            String[] split;
            while (buffer != null) {
                split = buffer.split("\\+");
                text.append(split[0] + "-" + split[1] + ": " + split[2] + " [" + split[3] + " Credit Hours]\n");
                buffer = BuffReader.readLine();
            }
            BuffReader.close();
        }
        catch (FileNotFoundException FNF) {}
        catch (IOException IOE) {}
        text.setEditable(false);
        return text;
    }

    public static JTextArea getProgress() {
        File TestUserFile = new File(progressPath);
        JTextArea text = new JTextArea();
        try {
            FileReader InputStream = new FileReader(TestUserFile);
            BufferedReader BuffReader = new BufferedReader(InputStream);
            int total = 0;
            int finished = 0;

            String buffer = BuffReader.readLine();
            if(!buffer.matches("=")) text.append("Finished Classes:\n");
            String[] split;
            while (!buffer.matches("=")) {
                split = buffer.split("\\+");
                text.append(split[0] + "-" + split[1] + ": " + split[2]+"\n");
                total += Integer.parseInt(split[3]);
                finished += Integer.parseInt(split[3]);
                buffer = BuffReader.readLine();
            }
            buffer = BuffReader.readLine();
            if(buffer != null)text.append("Unfinished Classes:\n");
            while (buffer != null) {
                split = buffer.split("\\+");
                text.append(split[0] + "-" + split[1] + ": " + split[2]+"\n");
                total += Integer.parseInt(split[3]);
                buffer = BuffReader.readLine();
            }
            text.append(finished+ "/" +total+ " Hours Completed");

            BuffReader.close();
        }
        catch (FileNotFoundException FNF) {}
        catch (IOException IOE) {}
        text.setEditable(false);
        return text;
    }

    public static boolean updateProgress(String className) {
        String[] name = className.split(" ");

        File progress = new File(progressPath);
        boolean found = false;
        String newLine = "";

        try {
            BufferedReader BuffReader = new BufferedReader(new FileReader(progress));

            String buffer = BuffReader.readLine();
            String[] split;
            while (!buffer.matches("=")) buffer = BuffReader.readLine();;
            buffer = BuffReader.readLine();
            while (buffer != null) {
                split = buffer.split("\\+");
                if(name[0].matches(split[0]) && name[1].matches(split[1])) {
                    found = true;
                    newLine = buffer;
                    break;
                }
                buffer = BuffReader.readLine();
            }
            BuffReader.close();
        }
        catch (FileNotFoundException FNF) {}
        catch (IOException IOE) {}

        if(!found) return false;
        else {
            File tempProg = new File(StartMenu.userPath+"temp");
            try {
                BufferedReader BuffReader = new BufferedReader(new FileReader(progress));
                BufferedWriter BuffWriter = new BufferedWriter(new FileWriter(tempProg));

                String buffer = BuffReader.readLine();
                String[] split;
                if(buffer.matches("=")) {
                    BuffWriter.write(newLine);
                    BuffWriter.write("\n");
                    BuffWriter.write(buffer);
                    BuffWriter.write("\n");
                }
                while (!buffer.matches("=")) {
                    BuffWriter.write(buffer);
                    BuffWriter.write("\n");
                    buffer = BuffReader.readLine();
                    if(buffer.matches("=")) {
                        BuffWriter.write(newLine);
                        BuffWriter.write("\n");
                        BuffWriter.write(buffer);
                        BuffWriter.write("\n");
                    }
                }

                buffer = BuffReader.readLine();
                while (buffer != null) {
                    split = buffer.split("\\+");
                    if(name[0].matches(split[0]) && name[1].matches(split[1])) {
                        buffer = BuffReader.readLine();
                        continue;
                    }
                    else{
                        BuffWriter.write(buffer);
                    }
                    buffer = BuffReader.readLine();
                    if(buffer != null) BuffWriter.write("\n");
                }
                BuffReader.close();
                BuffWriter.close();
                progress.delete();
                tempProg.renameTo(progress);
            }
            catch (FileNotFoundException FNF) {}
            catch (IOException IOE) {}
            tempProg.delete();
            return true;
        }
    }
}
