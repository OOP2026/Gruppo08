package gui;

import controller.ClassroomService;
import controller.CourseService;
import controller.LectureService;
import controller.UserService;
import controller.exception.DatabaseException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.text.NumberFormat;
import javax.swing.text.MaskFormatter;
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
    private JTextField classNameTextField;
    private JButton creaAulaButton;
    private JComboBox<String> courseComboBox;
    private JComboBox<String> classComboBox;
    private JFormattedTextField startTimeFormattedTextField;
    private JFormattedTextField endTimeFormattedTextField;
    private JButton creaLezioneButton;
    private JComboBox<String> dowComboBox;
    private final UserService us = new UserService();
    private final CourseService cs = new CourseService();
    private final ClassroomService cls = new ClassroomService();
    private final LectureService ls = new LectureService();

    public AdminPage(JFrame callerFrame) {
        frame = new JFrame("Admin Page");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(basePanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
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
                    int cfu = ((Number) cfuFormattedTextField.getValue()).intValue();
                    boolean active = isActivecomboBox.getSelectedIndex() == 0;
                    cs.makeCourse(teacheruid, courseName, cfu, academicYear, active);
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

        creaAulaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               try {
                   cls.makeClassroom(classNameTextField.getText());
                   JOptionPane.showMessageDialog(frame, "Aula creata con successo");
               } catch (DatabaseException de) {
                   JOptionPane.showMessageDialog(frame, de.getMessage());
               }
            }
        });

        creaLezioneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (courseComboBox.getSelectedItem() == null ||  classComboBox.getSelectedItem() == null) {
                        JOptionPane.showMessageDialog(frame, "Riempire tutti i campi");
                        return;
                    }
                    int courseId = Integer.parseInt(courseComboBox.getSelectedItem().toString().split(" ")[0]);
                    String className = classComboBox.getSelectedItem().toString();
                    DayOfWeek dow = DayOfWeek.of(dowComboBox.getSelectedIndex() + 1);
                    LocalTime start = LocalTime.parse(startTimeFormattedTextField.getText(), DateTimeFormatter.ofPattern("HH:mm"));
                    LocalTime end = LocalTime.parse(endTimeFormattedTextField.getText(), DateTimeFormatter.ofPattern("HH:mm"));
                    ls.makeLecture(courseId, className, dow, start, end);
                    JOptionPane.showMessageDialog(frame, "Lezione creata con successo");
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(frame, "Impossibile recuperare l'id del corso");
                } catch (DateTimeParseException de) {
                    JOptionPane.showMessageDialog(frame, "Inserire degli orari validi");
                } catch (DatabaseException de) {
                    JOptionPane.showMessageDialog(frame, de.getMessage());
                }
            }
        });
        populateTeachers();
        populateClassrooms();
        populateCourses();
    }

    private void populateTeachers() {
        try {
            List<String> teachersInfo = us.getAllTeachersInfo();
            if (teachersInfo == null)
                return;
            teacherComboBox.setModel(new DefaultComboBoxModel<>(teachersInfo.toArray(new String[0])));
        } catch (DatabaseException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage());
        }
    }

    private void populateCourses() {
        List<String> courses;
        try {
            courses = cs.getAllCoursesInfo();
            if (courses == null) {
                return;
            }
            courseComboBox.setModel(new DefaultComboBoxModel<>(courses.toArray(new String[0])));
        } catch (DatabaseException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage());
        }
    }

    private void populateClassrooms() {
        List<String> classrooms;

        try {
            classrooms = cls.getAllClassroomInfo();
            if (classrooms == null)
                return;
            classComboBox.setModel(new DefaultComboBoxModel<>(classrooms.toArray(new String[0])));
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

    private JFormattedTextField makeTimeFields() {
        try {
            MaskFormatter mask = new MaskFormatter("##:##");
            mask.setPlaceholderCharacter('_');
            return new JFormattedTextField(mask);
        } catch (ParseException e) {
            return new JFormattedTextField();
        }
    }

    private void createUIComponents() {
        cfuFormattedTextField = makeCfuField();
        startTimeFormattedTextField = makeTimeFields();
        endTimeFormattedTextField = makeTimeFields();
    }

}
