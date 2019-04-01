package Tikit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * To Do
 * <p>
 * Setup send mail
 */
public class Stage2login extends absGUI {

    public void getMainDisplay() {
        setGUI("Main Login");

        JLabel userLabel = new JLabel("USERNAME : ", JLabel.CENTER);
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(new Font("Ariel", Font.BOLD, 18));
        JTextField userTextF = new JTextField("glennmarm@gmail.com", 10);

        JLabel passLabel = new JLabel("PASSWORD : ", JLabel.CENTER);
        passLabel.setForeground(Color.WHITE);
        passLabel.setFont(new Font("Ariel", Font.BOLD, 18));
        JPasswordField PasswordField = new JPasswordField("123", 10);

        topPanel.setLayout(new GridLayout(2, 2, 5, 5));
        topPanel.add(userLabel);
        topPanel.add(userTextF);
        topPanel.add(passLabel);
        topPanel.add(PasswordField);

        JButton loginButton = new JButton("LOGIN");
        loginButton.addActionListener((ActionEvent evt) -> {
            System.out.println("EVT INFO: " + evt.getActionCommand() + " button was press");
            String userName = userTextF.getText();
            String Password = PasswordField.getText();

//            boolean logicResult = isCredentialsProper(userName, Password);
//            System.out.println("The logic result for loginButton is: " + logicResult);
//
            String query = "SELECT userID FROM ROOT.USERS where username ='" + userName + "' and password='" + Password + "'";
            int userID = intQuerry(query);

            if (userID > 0) {
                System.out.println("Password: " + Password + " coresponds to user: " + userName);
                User user = cacheUser(userID);
                Stage3Home Stage3Home = new Stage3Home(user);
                Stage3Home.getMainDisplayDSP();
                mainFrame.dispose();
            } else {
                System.out.println("[ERROR] The password: " + Password + "  is NOT valid for user: " + userName);
                JOptionPane.showMessageDialog(mainFrame, "Your Credentials are not Valid", "info.", 0);
            }
        });

        JButton resetPas = new JButton("Reset Passwod");
        resetPas.addActionListener((ActionEvent evt) -> {
            System.out.println("EVT INFO: " + evt.getActionCommand() + " button was press");
            getGatherIdentityDataDSP();
        });

        JButton CreateAcnt = new JButton("Create account");
        CreateAcnt.addActionListener((ActionEvent evt) -> {
            System.out.println("EVT INFO: " + evt.getActionCommand() + " button was press");
            createNewUserDSP();
        });

        bottomPanel.add(loginButton);
        bottomPanel.add(resetPas);
        bottomPanel.add(CreateAcnt);

        JLabel imageLabel = new JLabel(new ImageIcon(getClass().getResource("/Media/MovieTheaterLogo.png")));
        setGUI(imageLabel);
        mainFrame.setLocationRelativeTo(null);
    }

    void getGatherIdentityDataDSP() {
        String dspName = "Gather Identity Data";
        setGUI(dspName);

        JLabel userLabel = new JLabel("USERNAME : ", JLabel.CENTER);
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(new Font("Ariel", Font.BOLD, 18));
        JTextField userTextF = new JTextField("glennmarm@gmail.com", 10);

        topPanel.setLayout(new GridLayout(1, 2, 5, 5));
        topPanel.add(userLabel);
        topPanel.add(userTextF);

        JButton setTokenButton = new JButton("Entered Token");
        setTokenButton.addActionListener((ActionEvent evt) -> {
            System.out.println("EVT INFO: " + evt.getActionCommand() + " button was press");
            isTokenValidDSP(userTextF.getText());
        });

        JButton getTokenButton = new JButton("Get Token");
        getTokenButton.addActionListener((ActionEvent evt) -> {
            System.out.println("EVT INFO: " + evt.getActionCommand() + " button was press");
            String userName = userTextF.getText();
            System.out.println("Verifying if user exist: " + userName);

            String query = "SELECT Fname FROM ROOT.USERS where Email ='" + userName + "'";
            ArrayList<String> querryResults = ExeDDL(query);
            boolean logicResult = !querryResults.isEmpty();
            System.out.println("The logic result for isTokenValid is: " + logicResult);
            if (logicResult) {
                System.out.println(userName + " Is a valid user name");
                setToken(userName);
            } else {
                System.out.println(userName + " Is a NOT valid user name");
            }
        });

        JButton closeButton = new JButton("Cancel");
        closeButton.addActionListener((ActionEvent evt) -> {
            System.out.println("EVT INFO: " + evt.getActionCommand() + " button was press");
            getMainDisplay();
        });

        bottomPanel.add(setTokenButton);
        bottomPanel.add(getTokenButton);
        bottomPanel.add(closeButton);

        JLabel imageLabel = new JLabel(new ImageIcon(getClass().getResource("/Media/MovieTheaterLogo.png")));
        setGUI(imageLabel);
        mainFrame.setLocationRelativeTo(null);
    }

    void isTokenValidDSP(String user) {
        System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()) + " Trace Info");

        String dspName = "Verify Token";
        setGUI(dspName);

        JLabel userLabel = new JLabel("USERNAME : ", JLabel.CENTER);
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(new Font("Ariel", Font.BOLD, 18));
        JTextField userTextF = new JTextField(user, 10);

        JLabel tokenLabel = new JLabel("Token : ", JLabel.CENTER);
        tokenLabel.setForeground(Color.WHITE);
        tokenLabel.setFont(new Font("Ariel", Font.BOLD, 18));

        JFormattedTextField tokenInt = new JFormattedTextField();
        tokenInt.setColumns(4);
        topPanel.add(tokenInt);

        topPanel.setLayout(new GridLayout(2, 2, 5, 5));
        topPanel.add(userLabel);
        topPanel.add(userTextF);
        topPanel.add(tokenLabel);
        topPanel.add(tokenInt);

        JButton isTokenValidButton = new JButton("Verify Token");
        isTokenValidButton.addActionListener((ActionEvent evt) -> {
            System.out.println("EVT INFO: " + evt.getActionCommand() + " button was press");
            boolean logicResult = isTokenValid(userTextF.getText(), Integer.parseInt(tokenInt.getText().replaceAll(",", "")));
            System.out.println("The logic result for vfyTokenButton is: " + logicResult);
            if (logicResult) {
                getResetPasswordDSP(userTextF.getText());
            } else {
                JOptionPane.showMessageDialog(mainFrame, "The Token: " + tokenInt.getText() + " is not valid for User: " + userTextF.getText(), "Error.", 0);
            }
        });

        JButton closeButton = new JButton("Cancel");
        closeButton.addActionListener((ActionEvent evt) -> {
            System.out.println("EVT INFO: " + evt.getActionCommand() + " button was press");
            getGatherIdentityDataDSP();
        });

        bottomPanel.add(isTokenValidButton);
        bottomPanel.add(closeButton);

        JLabel imageLabel = new JLabel(new ImageIcon(getClass().getResource("/Media/MovieTheaterLogo.png")));
        setGUI(imageLabel);
        mainFrame.setLocationRelativeTo(null);
    }

    boolean isTokenValid(String userName, int token) {
        System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()) + " Trace Info");
        System.out.println("will atempt validate Token: " + token + " for user: " + userName);

        ArrayList<String> querryResults;
        String query = "SELECT Fname FROM ROOT.USERS where Email ='" + userName + "' and Token=" + token;
        querryResults = ExeDDL(query);

        boolean logicResult = !querryResults.isEmpty();
        System.out.println("The logic result for isTokenValid is: " + logicResult);
        if (logicResult) {
            System.out.println("The token: " + token + " is valid for user: " + userName);
            return true;
        } else {
            System.out.println("[ERROR] The token: " + token + " is NOT valid for user: " + userName);
            return false;
        }
    }

    void setToken(String userName) {
        System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()) + " Trace Info");
        Utilities Utilities = new Utilities();
        String query;

        System.out.println("will atempt to create token for user: " + userName);
        int token = Utilities.GenerateRanNum(9999, 1000);

        query = "UPDATE Users set Token=" + token + " where username='" + userName + "'";
        System.out.println((ExeDML(query) == 0)
                ? "Token: " + token + " was aded to the user: " + userName
                : "[RROR] Fail to add token: " + token + " to user: " + userName);

        Runnable mailServer = () -> {
            System.out.println("Runnable running");
            ArrayList<String> querryResults;
            String query1 = "SELECT EMail FROM ROOT.USERS where username ='" + userName + "'";
            querryResults = ExeDDL(query1);
            String Email = querryResults.get(0);
            String Subject = "Reset Token";
            String message = "Your token is: " + token + "\n\t Thank you";
            System.out.println("will atempt to send toaken: " + token + " to Email: " + userName);
            Utilities.sendMail(Email, Subject, message);
            JOptionPane.showMessageDialog(mainFrame, "We will try to send a token to the Email: " + Email, "info.", 0);
        };

        Thread thread = new Thread(mailServer);
        thread.start();

    }

    void getResetPasswordDSP(String userName) {
        System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()) + " Trace Info");
        String screenName = "Create new password";
        setGUI(screenName);

        JLabel passLabel = new JLabel("Password: ", JLabel.CENTER);
        passLabel.setForeground(Color.WHITE);
        passLabel.setFont(new Font("Ariel", Font.BOLD, 18));
        JPasswordField passTextF = new JPasswordField("123", 10);

        JLabel repassLabel = new JLabel("Retype Password: ", JLabel.CENTER);
        repassLabel.setForeground(Color.WHITE);
        repassLabel.setFont(new Font("Ariel", Font.BOLD, 18));
        JPasswordField repassTextF = new JPasswordField("123", 10);

        topPanel.setLayout(new GridLayout(2, 2, 5, 5));
        topPanel.add(passLabel);
        topPanel.add(passTextF);
        topPanel.add(repassLabel);
        topPanel.add(repassTextF);

        JButton setpassButton = new JButton("Set Password");
        setpassButton.addActionListener((ActionEvent evt) -> {
            System.out.println("EVT INFO: " + evt.getActionCommand() + " button was press");
            boolean logicResult = Arrays.equals(passTextF.getPassword(), repassTextF.getPassword());
            System.out.println("The logic result for setpassButton is: " + logicResult);
            if (logicResult) {
                System.out.println("The passwords are matching");
                setPassword(userName, passTextF.getText()
                );
                JOptionPane.showMessageDialog(mainFrame, "Your assword has been updated.", "info.", 0);
                getMainDisplay();
            } else {
                System.out.println("The passwords are matching");
                JOptionPane.showMessageDialog(mainFrame, "The pasword did not match.\n Please retype it.", "info.", 0);
            }
        });

        JButton closeButton = new JButton("Cancel");
        closeButton.addActionListener((ActionEvent evt) -> {
            System.out.println("EVT INFO: " + evt.getActionCommand() + " button was press");
            isTokenValidDSP(userName);
        });

        bottomPanel.add(setpassButton);
        bottomPanel.add(closeButton);

        JLabel imageLabel = new JLabel(new ImageIcon(getClass().getResource("/Media/MovieTheaterLogo.png")));
        setGUI(imageLabel);
        mainFrame.setLocationRelativeTo(null);
    }

    void setPassword(String userName, String password) {
        System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()) + " Trace Info");
        System.out.println("Will atempt to reset password for user: " + userName);

        User user = new User();
        user.setUserName(userName);
        user.setPasword(password);

        String query = "UPDATE Users set password='" + user.getPasword() + "' where username='" + user.getUserName() + "'";
        System.out.println((ExeDML(query) == 0) ? "The password has been updated for user: " + user.getUserName()
                : "[RROR] Fail to update password for user: " + user.getUserName());
    }

    /**
     * Creates a new cacheUser. Intended to be called when the "Create account"
     * button is press.
     */
    public void createNewUserDSP() {
        System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()) + " Trace Info");
        String screenName = "Create Account";
        setGUI(screenName);

        String[] labelName = {"Name", "LName", "EMail", "Username", "pasword", "BankCard"};
        String textFName[] = {"Name", "Last Name", "some.one@some.where", "User Name", "Password", "Bank Card"};
        JLabel[] Label = new JLabel[labelName.length];
        JTextField[] TextField = new JTextField[textFName.length];

        topPanel.setLayout(new GridLayout(6, 2, 15, 10));

        for (int i = 0; i < Label.length; i++) {
            Label[i] = new JLabel(textFName[i]);
            Label[i].setFont(new Font("Ariel", Font.BOLD, 15));
            Label[i].setForeground(Color.black);
            Label[i].setOpaque(true);
            topPanel.add(Label[i]);

            TextField[i] = new JTextField(labelName[i] + "TField");
            TextField[i].setText(textFName[i]);
            TextField[i].setFont(new Font("Ariel", Font.BOLD, 16));
            topPanel.add(TextField[i]);
        }

        JButton setCustomerButton = new JButton("Create Account");
        setCustomerButton.addActionListener((ActionEvent evt) -> {
            System.out.println("EVT INFO: " + evt.getActionCommand() + " button was press");
            setUser(TextField);
        });

        JButton closeButton = new JButton("Cancel");
        closeButton.addActionListener((ActionEvent evt) -> {
            System.out.println("EVT INFO: " + evt.getActionCommand() + " button was press");
            getMainDisplay();
        });

        bottomPanel.add(setCustomerButton, BorderLayout.LINE_END);
        bottomPanel.add(closeButton, BorderLayout.LINE_END);

        basePanel.setLayout(new GridLayout(2, 1));

        JLabel imageLabel = new JLabel(new ImageIcon(getClass().getResource("/Media/MovieTheaterLogo.png")));
        setGUI(imageLabel);
        mainFrame.setLocationRelativeTo(null);
    }

    /**
     * Creates a new cacheUser. Intended to be called when the Create account
     * button is press.
     */
    void setUser(JTextField[] TextField) {
        System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()) + " Trace Info");
        System.out.println("Verifying Data...");

        User user = new User();
        user.setfName(TextField[0].getText());
        user.setlName(TextField[1].getText());
        user.setEMail(TextField[2].getText());
        user.setPasword(TextField[4].getText());
        user.setBankCard(TextField[4].getText());

        System.out.println("will create the fallwoing user");
        for (JTextField TextField1 : TextField) {
            System.out.println("\t" + TextField1.getText());
        }

        System.out.println("Will atempt put the user in the DB...");
        String query = "INSERT INTO Users(FNAME, lName, EMail,username, password, BankCard, isLogin, isAdmin)\n\t\t "
                + "VALUES('" + user.getfName() + "', '" + user.getlName() + "', '" + user.getEMail() + "', '" + user.getUserName() + "', '" + user.getPasword() + "', '" + user.getBankCard() + "', false, false)";
        System.out.println((ExeDML(query) == 0) ? "The user was incerted into the Database" : "Fail to incert user into the Database");
        JOptionPane.showMessageDialog(mainFrame, "Your User Account has been Created.", "info.", 0);
        getMainDisplay();
    }
}
