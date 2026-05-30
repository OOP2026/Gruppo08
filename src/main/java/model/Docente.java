package model;

public class Docente extends Utente {
	private boolean isCoordinatore;

	public Docente(String nome, String cognome, String login, String email, String pswd, boolean isCoordinatore) {
		super(nome, cognome, login, email, pswd);
		this.isCoordinatore = isCoordinatore;
	}

	@Override
	public boolean puoRichiedereSpostamento() {
		return true;
	}

	@Override
	public boolean puoModificareOrario() {
		return isCoordinatore;
	}

	@Override
	public Integer getMatricola() {
		return null;
	}
}
