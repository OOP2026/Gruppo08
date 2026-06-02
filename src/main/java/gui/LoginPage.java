package gui;

import controller.Controller;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage {
    private static JFrame frame;
    private JPanel basePanel;
    private JComboBox<String> roleComboBox;
    private JTextField loginTextField;
    private JPasswordField pswdField;
    private JButton loginButton;
    private JButton regButton;
    private final Controller controller = new Controller();

    public static void main(String[] args) {
        frame = new JFrame("LoginPage");
        frame.setContentPane(new LoginPage().basePanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public LoginPage() {
        loginButton.addActionListener(e -> {
            String role = (String) roleComboBox.getSelectedItem();
            String login = loginTextField.getText();
            String pswd = String.valueOf(pswdField.getPassword());
            boolean success = true;

            switch (role) {
                case "Studente":
                    try {
                        controller.studenteLogin(login, pswd);
                    } catch (SecurityException se) {
                        JOptionPane.showMessageDialog(basePanel, se.getMessage());
                        success = false;
                    }
                    break;
                case "Docente":
                    try {
                        controller.docenteLogin(login, pswd);
                    } catch (SecurityException se) {
                        JOptionPane.showMessageDialog(basePanel, se.getMessage());
                        success = false;
                    }
                    break;
                case "Coordinatore":
                    try {
                        controller.coordinatoreLogin(login, pswd);
                    } catch (SecurityException se) {
                        JOptionPane.showMessageDialog(basePanel, se.getMessage());
                        success = false;
                    }
                    break;
                default:
                    JOptionPane.showMessageDialog(frame, "Invalid role");
                    break;
            }
            if (success) {
                JOptionPane.showMessageDialog(basePanel, "Login Success");
                new HomePage(controller);
                frame.dispose();
            }
        });

        regButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegPage(frame, controller);
                frame.setVisible(false);
            }
        });
    }
}

