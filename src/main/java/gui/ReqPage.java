package gui;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.MaskFormatter;
import javax.swing.text.StyleContext;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import controller.*;
import controller.exception.DatabaseException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.time.DayOfWeek;
import java.util.List;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class ReqPage {
    private JFrame frame;
    private JPanel basePanel;
    private JComboBox<String> lezioneComboBox;
    private JComboBox<String> giornoComboBox;
    private JButton confermaButton;
    private JFormattedTextField startFTextField;
    private JFormattedTextField endFTextField;
    private JButton indietroButton;
    private final LectureService ls = new LectureService();
    private final ChangeOfDateReqService cs = new ChangeOfDateReqService();

    public ReqPage(JFrame callerFrame) {
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(basePanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
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
                } catch (DateTimeParseException e0) {
                    JOptionPane.showMessageDialog(frame, "Inserire un orario valido.");
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(frame, "Impossibile recuperare l'id della lezione.");
                } catch (RuntimeException re) {
                    JOptionPane.showMessageDialog(frame, re.getMessage());
                } catch (Exception e2) {
                    JOptionPane.showMessageDialog(frame, "Si é verificato un errore.");
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

    private void populateLectures() {
        List<String> lectures;

        try {
            if (!SessionManager.getInstance().isLoggedIn()) {
                JOptionPane.showMessageDialog(frame,
                        "Nessun utente autenticato.",
                        "Errore di Autenticazione",
                        JOptionPane.WARNING_MESSAGE);

                return;
            }
            lectures = ls.getAllByTeacherToString(SessionManager.getInstance().getUserId());
            lezioneComboBox.setModel(new DefaultComboBoxModel<>(lectures.toArray(new String[0])));
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(frame, "Si e' verificato un errore nel recupero delle lezioni");
        } catch (DatabaseException de) {
            JOptionPane.showMessageDialog(frame, de.getMessage());
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
        } catch (ParseException e) {
            return new JFormattedTextField();
        }
    }

}
