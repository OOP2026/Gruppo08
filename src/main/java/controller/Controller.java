package controller;

import model.*;

public class Controller {
	private ServizioUniversita sUni = new ServizioUniversita();
	private ServizioLezioni sLezioni = new ServizioLezioni();
	private ServizioUtenti sUtenti = new ServizioUtenti();

	private Utente session;

	public boolean isCoordinatore(Utente session) {
		if (session.puoModificareOrario() && session.puoRichiedereSpostamento())
			return true;
		return false;
	}

	public boolean isDocente(Utente session) {
		if (!session.puoModificareOrario() && session.puoRichiedereSpostamento())
			return true;
		return false;
	}

	public boolean isStudente(Utente session) {
		if (!session.puoModificareOrario() && !session.puoRichiedereSpostamento())
			return true;
		return false;
	}

	public void studenteLogin(String identifier, String pswd) throws SecurityException {
		session = sUtenti.studenteLogin(identifier, pswd);
		return;
	}

	public void docenteLogin(String identifier, String pswd) throws SecurityException {
		session = sUtenti.docenteLogin(identifier, pswd);
		return;
	}

	public void coordinatoreLogin(String identifier, String pswd) throws SecurityException {
		session = sUtenti.coordinatoreLogin(identifier, pswd);
		return;
	}

	public void registerStudente(String nome, String cognome, String login, String email, String pswd) {
		sUtenti.registerStudente(nome, cognome, login, email, pswd);
	}

	public void registerDocente(String nome, String cognome, String login, String email, String pswd) {
		sUtenti.registerDocente(nome, cognome, login, email, pswd);
	}

	public void registerCoordinatore(String nome, String cognome, String login, String email, String pswd) {
		sUtenti.registerCoordinatore(nome, cognome, login, email, pswd);
	}

	public Utente getSession() {
		return session;
	}

	/*
	 *******************************
	 * Operazioni del coordinatore *
	 *******************************
	 */
	public void makeInsegnamento(Materia materia, int numeroCfu, int annoDiCorso) {
		if (!isCoordinatore(session))
			return;
		sUni.makeInsegnamento(materia, numeroCfu, annoDiCorso);
	}

	public void makeAnnoAccademico(int anno) {
		if (!isCoordinatore(session))
			return;
		sUni.makeAnnoAccademico(anno);
	}

	public void makeAnnoAccademico(int anno, Insegnamento... insegnamenti) {
		if (!isCoordinatore(session))
			return;
		sUni.makeAnnoAccademico(anno, insegnamenti);
	}

	public void makeAula(char lettera, int numero, int capacita) {
		if (!isCoordinatore(session))
			return;
		sUni.makeAula(lettera, numero, capacita);
	}

}
