package model;

import java.util.NoSuchElementException;

public class ServizioUtenti {

	private StudenteRepository studRepo = new StudenteRepository();
	private DocenteRepository docRepo = new DocenteRepository();
	private CoordinatoreRepository coordinatoreRepo = new CoordinatoreRepository();

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

	public Coordinatore coordinatoreLogin(String identifier, String pswd) throws SecurityException {
		Coordinatore c;
		try {
			if (identifier.contains("@"))
				c = coordinatoreRepo.findByMail(identifier);
			else {
				c = coordinatoreRepo.findByLogin(identifier);
			}
		} catch (NoSuchElementException e) {
			throw new SecurityException("login/pswd incorrect");
		}
		if (c.checkPswd(pswd))
			return c;
		throw new SecurityException("login/pswd incorrect");
	}

	public void registerStudente(String nome, String cognome, String login, String email, String pswd) {
		if (studRepo.checkEmail(email) || studRepo.checkLogin(login))
			throw new SecurityException("email/login already in use");

		Studente s = new Studente(nome, cognome, login, email, pswd);
		studRepo.addStudente(s);
	}

	public void registerDocente(String nome, String cognome, String login, String email, String pswd) {
		if (docRepo.checkEmail(email) || docRepo.checkLogin(login))
			throw new SecurityException("email/login already in use");

		Docente d = new Docente(nome, cognome, login, email, pswd);
		docRepo.addDocente(d);
	}

	public void registerCoordinatore(String nome, String cognome, String login, String email, String pswd) {
		if (coordinatoreRepo != null && coordinatoreRepo.getCoordinatori().isEmpty()) {
			Coordinatore c = new Coordinatore(nome, cognome, login, email, pswd);
			coordinatoreRepo.addCoordinatore(c);
			return;
		}
		throw new SecurityException("coordinatore già esistente");
	}
}
