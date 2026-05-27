package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JFrame {
    private static JFrame frame;
    private JPanel basePanel;
    private JLabel logLable;
    private JPanel inputsPanel;
    private JComboBox roleComboBox;
    private JTextField loginTextField;
    private JPasswordField pswdField;
    private JButton loginButton;
    private JButton regButton;
    private Controller controller = new Controller();

    public static void main(String[] args) {
        frame = new JFrame("LoginPage");
        frame.setContentPane(new LoginPage().basePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public LoginPage() {
        loginButton.addActionListener(e -> {
            String role = (String) roleComboBox.getSelectedItem();
            String login = loginTextField.getText();
            String pswd = pswdField.getText();
            boolean success = true;

            if (role.equals("Studente"))
            {
                try {
                    controller.studenteLogin(login, pswd);
                }
                catch (SecurityException se) {
                    JOptionPane.showMessageDialog(basePanel, se.getMessage());
                    success = false;
                }
            } else if (role.equals("Docente")) {
                try {
                    controller.docenteLogin(login, pswd);
                }
                catch (SecurityException se) {
                    JOptionPane.showMessageDialog(basePanel, se.getMessage());
                    success = false;
                }
            } else if (role.equals("Coordinatore")) {
                try {
                    controller.coordinatoreLogin(login, pswd);
                }
                catch (SecurityException se) {
                    JOptionPane.showMessageDialog(basePanel, se.getMessage());
                    success = false;
                }
            } else {
                JOptionPane.showMessageDialog(basePanel, "Ruolo non valido");
                success = false;
            }
            if(success)
                JOptionPane.showMessageDialog(basePanel, "Login Success");
        });
        regButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegPage regPage = new RegPage(frame, controller);
                frame.setVisible(false);
            }
        });
    }
}

