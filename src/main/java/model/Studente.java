package model;

public class Studente extends Utente {
	private static int counter = 0;
	private int matricola;
	private int annoDiCorso = 1;

	public Studente(String nome, String cognome, String login, String email, String pswd) {
		super(nome, cognome, login, email, pswd);
		matricola = counter++;
	}

	// TODO: quando dovrebbe cambiare l'anno di corso?
	public void setAnnoDiCorso(int annoDiCorso) {
		this.annoDiCorso = annoDiCorso;
	}

	public int getAnnoDiCorso() {
		return annoDiCorso;
	}

	public int getMatricola() {
		return matricola;
	}

	@Override
	public boolean puoRichiedereSpostamento() {
		return false;
	}

	@Override
	public boolean puoModificareOrario() {
		return false;
	}
}
