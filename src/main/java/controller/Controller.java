package controller;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;

import model.*;

// TODO: check sui vincoli di chiave (unique) nella creazione di nuovi oggetti

public class Controller {
	private ServizioUniversita sUni = new ServizioUniversita();
	private ServizioLezioni sLezioni = new ServizioLezioni();
	private ServizioUtenti sUtenti = new ServizioUtenti();

	private Utente session;

	public boolean isCoordinatore() {
		return (session.puoModificareOrario() && session.puoRichiedereSpostamento());
	}

	public boolean isDocente() {
		return (!session.puoModificareOrario() && session.puoRichiedereSpostamento());
	}

	public boolean isStudente() {
		return (!session.puoModificareOrario() && !session.puoRichiedereSpostamento());
	}

	// NOTE: si potrebbe rendere il login piu' modulare eliminando il formato
	// ruoloLogin per usare esclusivamente isRuolo
	public void studenteLogin(String identifier, String pswd) throws SecurityException {
		session = sUtenti.studenteLogin(identifier, pswd);
	}

	public void docenteLogin(String identifier, String pswd) throws SecurityException {
		session = sUtenti.docenteLogin(identifier, pswd);
	}

	public void coordinatoreLogin(String identifier, String pswd) throws SecurityException {
		session = sUtenti.coordinatoreLogin(identifier, pswd);
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

	public int getAnno() throws IllegalStateException {
		if (session instanceof Studente)
			return ((Studente) session).getAnnoDiCorso();

		throw new IllegalStateException("Non è uno studente");
	}

	public int getMatricola() throws IllegalStateException {
		if (session instanceof Studente)
			return ((Studente) session).getMatricola();

		throw new IllegalStateException("Non è uno studente");
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
			int annoDiCorso) throws NoSuchElementException {

		if (!isCoordinatore())
			return false;

		sUni.makeInsegnamento(idInsegnamento, nomeMateria, loginDocente, numeroCfu, annoDiCorso);

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

		sLezioni.makeLezione(idLezione, annoAccademico, giornoSett, nomeAula, idInsegnamento, oraInizio, oraFine);

		return true;
	}

	public boolean approvaRichiestaSpostamento(int idRichiesta, boolean approvata) throws NoSuchElementException {

		if (!isCoordinatore())
			return false;

		sLezioni.approvaRichiestaSpostamento(idRichiesta, approvata);

		return true;
	}

	public boolean makeRichiestaSpostamento(int idRichiesta, int idLezioneDaSpostare, String docenteLogin,
			DayOfWeek nuovoGiorno, LocalTime nuovaOraInizio, LocalTime nuovaOraFine) throws NoSuchElementException {

		if (!session.puoRichiedereSpostamento())
			return false;

		sLezioni.makeRichiestaSpostamento(idRichiesta, idLezioneDaSpostare, docenteLogin, nuovoGiorno,
				nuovaOraInizio, nuovaOraFine);

		return true;
	}

	public Object[][] getOrarioMtx(int annoDiCorso) {
		return sLezioni.getOrarioMtx(annoDiCorso);
	}

	public String[] getCols() {
		return sLezioni.getCols();
	}

	public List<Integer> getIdLezioniDocente() throws IllegalStateException {
		if (isStudente())
			throw new IllegalStateException();
		return sLezioni.getIdLezioniDocente(session.getLogin());
	}

	public String getNomeMateriaLezione(int idLezione) throws NoSuchElementException {
		return sLezioni.getNomeMateriaLezione(idLezione);
	}

	public String getIntervalloOrarioLezione(int idLezione) throws NoSuchElementException {
		return sLezioni.getIntervalloOrarioLezione(idLezione);
	}

	public String getGiornoSettLezione(int idLezione) throws NoSuchElementException {
		return sLezioni.getGiornoSettLezione(idLezione);
	}

}
