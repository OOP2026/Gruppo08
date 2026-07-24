package gui;

import controller.*;
import javax.security.sasl.AuthenticationException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage {
    private JFrame frame;
    private JPanel basePanel;
    private JTextField loginTextField;
    private JPasswordField pswdField;
    private JButton loginButton;
    private JButton regButton;
    private final UserService ua = new UserService();

    public static void main(String[] args) {
        new LoginPage().display();
    }

    public void display() {
        frame = new JFrame("LoginPage");
        frame.setContentPane(this.basePanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    public LoginPage() {
        regButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegPage(frame);
                frame.setVisible(false);
            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String login = loginTextField.getText();
                String pswd = String.valueOf(pswdField.getPassword());
                boolean success = true;
                try {
                    ua.login(login, pswd);
                } catch (AuthenticationException ae) {
                    JOptionPane.showMessageDialog(basePanel, ae.getMessage());
                    success = false;
                }

                if (success) {
                    JOptionPane.showMessageDialog(basePanel, "Login Success");
                    new HomePage();
                    frame.dispose();
                }
            }
        });
    }

}
