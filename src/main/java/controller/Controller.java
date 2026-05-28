package controller;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import model.*;

public class Controller {
	private ServizioUniversita sUni = new ServizioUniversita();
	private ServizioLezioni sLezioni = new ServizioLezioni();
	private ServizioUtenti sUtenti = new ServizioUtenti();

	private Utente session;

	public boolean isCoordinatore() {
		if (session.puoModificareOrario() && session.puoRichiedereSpostamento())
			return true;
		return false;
	}

	public boolean isDocente() {
		if (!session.puoModificareOrario() && session.puoRichiedereSpostamento())
			return true;
		return false;
	}

	public boolean isStudente() {
		if (!session.puoModificareOrario() && !session.puoRichiedereSpostamento())
			return true;
		return false;
	}

	// TODO: si potrebbe rendere il login piu' modulare eliminando il formato
	// ruoloLogin per usare esclusivamente isRuolo
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

	/*
	 **********************
	 * Infrastruttura Uni *
	 **********************
	 */
	public boolean makeInsegnamento(Materia materia, int numeroCfu, int annoDiCorso) {
		if (!isCoordinatore())
			return false;
		sUni.makeInsegnamento(materia, numeroCfu, annoDiCorso);
		return true;
	}

	public boolean makeAnnoAccademico(int anno) {
		if (!isCoordinatore())
			return false;
		sUni.makeAnnoAccademico(anno);
		return true;
	}

	public boolean makeAnnoAccademico(int anno, Insegnamento... insegnamenti) {
		if (!isCoordinatore())
			return false;
		sUni.makeAnnoAccademico(anno, insegnamenti);
		return true;
	}

	public boolean makeAula(char lettera, int numero, int capacita) {
		if (!isCoordinatore())
			return false;
		sUni.makeAula(lettera, numero, capacita);
		return true;
	}

	/*
	 **********************
	 * Operazioni Lezioni *
	 **********************
	 */

	public boolean makeLezione(Aula aula, Insegnamento insegnamento, LocalDateTime oraInizio, LocalDateTime oraFine) {
		if (!isCoordinatore())
			return false;
		sLezioni.makeLezione(aula, insegnamento, oraInizio, oraFine);
		return true;
	}

	public boolean makeOrarioLezione(AnnoAccademico anno, DayOfWeek giornoSett, Insegnamento insegnamento) {
		if (!isCoordinatore())
			return false;
		sLezioni.makeOrarioLezione(anno, giornoSett, insegnamento);
		return true;
	}
}
