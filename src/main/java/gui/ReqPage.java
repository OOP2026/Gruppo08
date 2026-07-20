package gui;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import controller.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.util.List;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ReqPage {
    private JFrame frame;
    private JPanel basePanel;
    private JComboBox<String> lezioneComboBox;
    private JComboBox<String> giornoComboBox;
    private JButton confermaButton;
    private JFormattedTextField startFTextField;
    private JFormattedTextField endFTextField;
    private final LectureService ls = new LectureService();
    private final ChangeOfDateReqService cs = new ChangeOfDateReqService();

    public ReqPage(JFrame callerFrame) {
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setContentPane(basePanel);
        frame.pack();
        frame.setVisible(true);
        populateLectures();
        confermaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (lezioneComboBox.getSelectedItem() == null) {
                        JOptionPane.showMessageDialog(frame, "Selezionare una lezione");
                        return;
                    }
                    int lectureId = Integer.parseInt(lezioneComboBox.getSelectedItem().toString().split(" ")[0]);
                    LocalTime start = LocalTime.parse(startFTextField.getText(), DateTimeFormatter.ofPattern("HH:mm"));
                    LocalTime end = LocalTime.parse(endFTextField.getText(), DateTimeFormatter.ofPattern("HH:mm"));
                    DayOfWeek dow = DayOfWeek.of(giornoComboBox.getSelectedIndex() + 1);
                    cs.makeChangeOfDateReq(lectureId, dow, start, end);
                    JOptionPane.showMessageDialog(frame, "Richiesta effettuata con successo");
                    callerFrame.setVisible(true);
                    frame.dispose();
                } catch (DateTimeParseException _) {
                    JOptionPane.showMessageDialog(frame, "Inserire un orario valido.");
                } catch (NumberFormatException _) {
                    JOptionPane.showMessageDialog(frame, "Impossibile recuperare l'id della lezione.");
                } catch (RuntimeException re) {
                    JOptionPane.showMessageDialog(frame, re.getMessage());
                } catch (Exception _) {
                    JOptionPane.showMessageDialog(frame, "Si é verificato un errore.");
                }
            }
        });
    }

    private void populateLectures() {
        List<String> lectures;

        try {
            lectures = ls.getAllByTeacherToString(SessionManager.getInstance().getUserId());
            lezioneComboBox.setModel(new DefaultComboBoxModel<>(lectures.toArray(new String[0])));
        } catch (NullPointerException _) {
            JOptionPane.showMessageDialog(frame, "Si e' verificato un errore nel recupero delle lezioni");
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage());
        }
    }

    private void createUIComponents() {
        startFTextField = makeTimeFields();
        endFTextField = makeTimeFields();
    }

    private JFormattedTextField makeTimeFields() {
        try {
            MaskFormatter mask = new MaskFormatter("##:##");
            mask.setPlaceholderCharacter('_');
            return new JFormattedTextField(mask);
        } catch (java.text.ParseException _) {
            return new JFormattedTextField();
        }
    }
}
