package model;

public class Coordinatore extends Docente {

    public Coordinatore(String nome, String cognome, String login, String email, String pswd) {
        super(nome, cognome, login, email, pswd);
    }

    @Override
    public boolean puoRichiedereSpostamento() {
        return true;
    }

    @Override
    public boolean puoModificareOrario() {
        return true;
    }
}
