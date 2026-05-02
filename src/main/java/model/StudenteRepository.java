package model;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class StudenteRepository {
    private List<Studente> studenti = new ArrayList<>();

    public List<Studente> getStudenti() {
        return studenti;
    }

    public void addStudente(Studente studente) {
        studenti.add(studente);
    }

    public Studente findByMail(String mail) throws NoSuchElementException {
        for (Studente studente : studenti) {
            if (studente.getEmail().equals(mail)) {
                return studente;
            }
        }
        throw new NoSuchElementException();
    }

    public boolean checkEmail(String email) {
        for (Studente studente : studenti) {
            if (studente.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public Studente findByLogin(String login) throws NoSuchElementException {
        for (Studente studente : studenti) {
            if (studente.getLogin().equals(login)) {
                return studente;
            }
        }
        throw new NoSuchElementException();
    }

    public boolean checkLogin(String login) {
        for (Studente studente : studenti) {
            if (studente.getLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }



}
