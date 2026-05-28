package model;

public class Docente extends Utente {
	public Docente(String nome, String cognome, String login, String email, String pswd) {
		super(nome, cognome, login, email, pswd);
	}

	@Override
	public boolean puoRichiedereSpostamento() {
		return true;
	}

	@Override
	public boolean puoModificareOrario() {
		return false;
	}

	@Override
	public Integer getMatricola() {
		return null;
	}
}
