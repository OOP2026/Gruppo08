package gui;

import controller.CourseService;
import controller.UserService;
import controller.exception.DatabaseException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.text.NumberFormat;
import javax.swing.text.NumberFormatter;

public class AdminPage {
    private JFrame frame;
    private JPanel basePanel;
    private JButton indietroButton;
    private JComboBox<String> teacherComboBox;
    private JTextField courseNameTextField;
    private JComboBox<Integer> ayComboBox;
    private JComboBox<String> isActivecomboBox;
    private JButton creaCorsoButton;
    private JFormattedTextField cfuFormattedTextField;
    private final UserService us = new UserService();
    private final CourseService cs = new CourseService();

    public AdminPage(JFrame callerFrame) {
        frame = new JFrame("Admin Page");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(basePanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        populateTeachers();
        creaCorsoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int teacheruid = Integer.parseInt(teacherComboBox.getSelectedItem().toString().split(" ")[0]);
                    String courseName = courseNameTextField.getText();
                    int academicYear = Integer.parseInt(ayComboBox.getSelectedItem().toString());
                    if (cfuFormattedTextField.getValue() == null) {
                         JOptionPane.showMessageDialog(frame, "Inserire un valore cfu");
                         return;
                    }
                    int cfu = ((Number)cfuFormattedTextField.getValue()).intValue();
                    boolean active = isActivecomboBox.getSelectedIndex() == 0;
                    cs.makeCourse(teacheruid, courseName, academicYear, cfu, active);
                    JOptionPane.showMessageDialog(frame, "Corso creato con successo");
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(frame, "Errore nell'inserimento di uno o più dati");
                } catch (DatabaseException de) {
                    JOptionPane.showMessageDialog(frame, de.getMessage());
                }
            }
        });
        indietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                callerFrame.setVisible(true);
                frame.dispose();
            }
        });
    }

    private void populateTeachers() {
         try {
             List<String> teachersInfo = us.getAllTeachersInfo();
             teacherComboBox.setModel(new DefaultComboBoxModel<>(teachersInfo.toArray(new String[0])));
         } catch (DatabaseException e) {
             JOptionPane.showMessageDialog(frame, e.getMessage());
         }
    }

    private JFormattedTextField makeCfuField() {
        NumberFormat format = NumberFormat.getIntegerInstance();
        format.setGroupingUsed(false);
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);
        return new JFormattedTextField(formatter);
    }

    private void createUIComponents() {
        cfuFormattedTextField = makeCfuField();
    }
}
