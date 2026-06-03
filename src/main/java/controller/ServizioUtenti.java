package controller;

import model.*;
import dao.*;

import java.util.NoSuchElementException;

public class ServizioUtenti {

	private StudenteRepository studRepo = StudenteRepository.getInstance();
	private DocenteRepository docRepo = DocenteRepository.getInstance();

	public Studente studenteLogin(String identifier, String pswd) throws SecurityException {
		Studente s;
		try {
			if (identifier.contains("@")) {
				s = studRepo.findByMail(identifier);
			} else {
				s = studRepo.findByLogin(identifier);
			}
		} catch (NoSuchElementException e) {
			throw new SecurityException("login/pswd incorrect");
		}
		if (s.checkPswd(pswd))
			return s;
		throw new SecurityException("login/pswd incorrect");
	}

	public Docente docenteLogin(String identifier, String pswd) throws SecurityException {
		Docente doc;
		try {
			if (identifier.contains("@"))
				doc = docRepo.findByEmail(identifier);
			else {
				doc = docRepo.findByLogin(identifier);
			}
		} catch (NoSuchElementException e) {
			throw new SecurityException("login/pswd incorrect");
		}
		if (doc.checkPswd(pswd))
			return doc;
		throw new SecurityException("login/pswd incorrect");
	}

	// TODO: da deprecare
	public Docente coordinatoreLogin(String identifier, String pswd) throws SecurityException {
		Docente d;
		try {
			d = docenteLogin(identifier, pswd);
			return d;
		} catch (SecurityException e) {
			throw new SecurityException("login/pswd incorrect");
		}
	}

	public void registerStudente(String nome, String cognome, String login, String email, String pswd) {
		if (studRepo.checkEmail(email) || studRepo.checkLogin(login))
			throw new SecurityException("email/login already in use");
		if (nome.trim().isEmpty() || cognome.trim().isEmpty() || login.trim().isEmpty() || email.trim().isEmpty()
				|| pswd.trim().isEmpty())
			throw new SecurityException("Riempire tutti i campi!");

		Studente s = new Studente(nome, cognome, login, email, pswd);
		studRepo.addStudente(s);
	}

	public void registerDocente(String nome, String cognome, String login, String email, String pswd) {
		if (docRepo.checkEmail(email) || docRepo.checkLogin(login))
			throw new SecurityException("email/login already in use");

		Docente d = new Docente(nome, cognome, login, email, pswd, false);
		docRepo.addDocente(d);
	}

	public void registerCoordinatore(String nome, String cognome, String login, String email, String pswd) {
		if (docRepo.checkEmail(email) || docRepo.checkLogin(login))
			throw new SecurityException("email/login already in use");

		Docente d = new Docente(nome, cognome, login, email, pswd, true);
		docRepo.addDocente(d);
	}
}
