package com.progetto.esamepoo;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class DocenteRepository {
    private List<Docente> docenti = new ArrayList<>();

    public List<Docente> getDocenti() {
        return docenti;
    }

    public void addDocente(Docente docente) {
        docenti.add(docente);
    }

    public Docente findByLogin(String login) throws NoSuchElementException {
        for (Docente docente : docenti) {
            if (docente.getLogin().equals(login))
                return docente;
        }
        throw new NoSuchElementException();
    }

    public boolean checkLogin(String login) {
        for (Docente docente : docenti) {
            if (docente.getLogin().equals(login))
                return true;
        }
        return false;
    }

    public Docente findByEmail(String email) throws NoSuchElementException {
        for (Docente docente : docenti) {
            if (docente.getEmail().equals(email))
                return docente;
        }
        throw new NoSuchElementException();
    }

    public boolean checkEmail(String email) {
        for (Docente docente : docenti) {
            if (docente.getEmail().equals(email))
                return true;
        }
        return false;
    }
}
