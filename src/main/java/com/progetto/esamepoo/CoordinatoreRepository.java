package com.progetto.esamepoo;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class CoordinatoreRepository {
    private List<Coordinatore> coordinatori = new ArrayList<>();

    public List<Coordinatore> getCoordinatori() {
        return coordinatori;
    }

    public void addCoordinatore(Coordinatore coordinatore) {
        coordinatori.add(coordinatore);
    }

    public Coordinatore findByMail(String mail) throws NoSuchElementException {
        for (Coordinatore coordinatore : coordinatori) {
            if(coordinatore.getEmail().equals(mail)) {
                return coordinatore;
            }
        }
        throw new NoSuchElementException();
    }

    public boolean checkMail(String mail) {
        for (Coordinatore coordinatore : coordinatori) {
            if(coordinatore.getEmail().equals(mail)) {
                return true;
            }
        }
        return false;
    }

    public Coordinatore findByLogin(String login) throws NoSuchElementException {
        for (Coordinatore coordinatore : coordinatori) {
            if(coordinatore.getLogin().equals(login)) {
                return coordinatore;
            }
        }
        throw new NoSuchElementException();
    }

    public boolean checkLogin(String login) {
        for (Coordinatore coordinatore : coordinatori) {
            if(coordinatore.getLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }
}
