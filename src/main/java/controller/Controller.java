package controller;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.NoSuchElementException;

import model.*;

// TODO: check sui vincoli di chiave (unique) nella creazione di nuovi oggetti

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

	// NOTE: si potrebbe rendere il login piu' modulare eliminando il formato
	// ruoloLogin per usare esclusivamente isRuolo
	public void studenteLogin(String identifier, String pswd) throws SecurityException {
		try {
			session = sUtenti.studenteLogin(identifier, pswd);
		} catch (SecurityException e) {
			throw e;
		}
	}

	public void docenteLogin(String identifier, String pswd) throws SecurityException {
		try {
			session = sUtenti.docenteLogin(identifier, pswd);
		} catch (SecurityException e) {
			throw e;
		}
	}

	public void coordinatoreLogin(String identifier, String pswd) throws SecurityException {
		try {
			session = sUtenti.coordinatoreLogin(identifier, pswd);
		} catch (SecurityException e) {
			throw e;
		}
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

	public boolean makeMateria(String nome) {
		if (!isCoordinatore())
			return false;
		sUni.makeMateria(nome);
		return true;
	}

	public boolean makeInsegnamento(int idInsegnamento, String nomeMateria, String loginDocente, int numeroCfu,
			int annoDiCorso) {

		if (!isCoordinatore())
			return false;

		try {
			sUni.makeInsegnamento(idInsegnamento, nomeMateria, loginDocente, numeroCfu, annoDiCorso);
		} catch (NoSuchElementException e) {
			throw e;
		}

		return true;
	}

	public boolean makeAnnoAccademico(int anno) {
		if (!isCoordinatore())
			return false;
		sUni.makeAnnoAccademico(anno);
		return true;
	}

	public boolean makeAula(String nome) {
		if (!isCoordinatore())
			return false;
		sUni.makeAula(nome);
		return true;
	}

	/*
	 **********************
	 * Operazioni Lezioni *
	 **********************
	 */

	public boolean makeLezione(int idLezione, int annoAccademico, DayOfWeek giornoSett, String nomeAula,
			int idInsegnamento, LocalTime oraInizio, LocalTime oraFine) throws NoSuchElementException {

		if (!isCoordinatore())
			return false;

		try {
			sLezioni.makeLezione(idLezione, annoAccademico, giornoSett, nomeAula, idInsegnamento, oraInizio, oraFine);
		} catch (NoSuchElementException e) {
			throw e;
		}

		return true;
	}

	public boolean approvaRichiestaSpostamento(int idRichiesta, boolean approvata) throws NoSuchElementException {

		if (!isCoordinatore())
			return false;

		try {
			sLezioni.approvaRichiestaSpostamento(idRichiesta, approvata);
		} catch (NoSuchElementException e) {
			throw e;
		}

		return true;
	}

	public boolean makeRichiestaSpostamento(int idRichiesta, int idLezioneDaSpostare, String docenteLogin,
			DayOfWeek nuovoGiorno, LocalTime nuovaOraInizio, LocalTime nuovaOraFine) throws NoSuchElementException {

		if (!session.puoRichiedereSpostamento())
			return false;

		try {
			sLezioni.makeRichiestaSpostamento(idRichiesta, idLezioneDaSpostare, docenteLogin, nuovoGiorno,
					nuovaOraInizio, nuovaOraFine);
		} catch (NoSuchElementException e) {
			throw e;
		}

		return true;
	}
}
