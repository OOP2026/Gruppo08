package controller;

import java.time.LocalTime;
import java.util.NoSuchElementException;
import java.time.DayOfWeek;

import model.*;

public class ServizioLezioni {
	private LezioneRepository lezioneRepo = LezioneRepository.getInstance();
	private RichiestaSpostamentoRepository richiesteRepo = RichiestaSpostamentoRepository.getInstance();
	private AnnoAccademicoRepository annoRepo = AnnoAccademicoRepository.getInstance();
	private DocenteRepository docenteRepo = DocenteRepository.getInstance();
	private AulaRepository aulaRepo = AulaRepository.getInstance();
	private InsegnamentoRepository insegnamentoRepo = InsegnamentoRepository.getInstance();

	public Lezione makeLezione(int idLezione, int annoAccademico, DayOfWeek giornoSett, String nomeAula,
			int idInsegnamento, LocalTime oraInizio, LocalTime oraFine) throws NoSuchElementException {

		AnnoAccademico a;
		Aula aula;
		Insegnamento insegnamento;
		try {
			a = annoRepo.findAnno(annoAccademico);
			aula = aulaRepo.findAula(nomeAula);
			insegnamento = insegnamentoRepo.findInsegnamento(idInsegnamento);
		} catch (NoSuchElementException e) {
			throw e;
		}

		Lezione l = new Lezione(idLezione, a, giornoSett, oraInizio, oraFine, aula, insegnamento);
		lezioneRepo.addLezione(l);
		return l;
	}

	public RichiestaSpostamento makeRichiestaSpostamento(int idRichiesta, int idLezioneDaSpostare, String docenteLogin,
			DayOfWeek nuovoGiorno, LocalTime nuovaOraInizio, LocalTime nuovaOraFine) throws NoSuchElementException {

		Lezione lezioneDaSpostare;
		Docente docente;
		try {
			lezioneDaSpostare = lezioneRepo.findById(idLezioneDaSpostare);
			docente = docenteRepo.findByLogin(docenteLogin);
		} catch (NoSuchElementException e) {
			throw e;
		}

		RichiestaSpostamento r = new RichiestaSpostamento(idRichiesta, lezioneDaSpostare, docente, nuovoGiorno,
				nuovaOraInizio, nuovaOraFine);
		richiesteRepo.addRichiesta(r);
		return r;
	}

	public void approvaRichiestaSpostamento(int idRichiesta, boolean approvata) throws NoSuchElementException {
		RichiestaSpostamento r;
		try {
			r = richiesteRepo.findById(idRichiesta);
		} catch (NoSuchElementException e) {
			throw e;
		}

		r.setStato(approvata);
		if (!approvata)
			return;

		Lezione l = r.getLezioneDaSpostare();

		// TODO: Spostamento solo se valori compatibili (aula non occupata quel giorno
		// da altre lezioni ecc..).
		l.setOraInizio(r.getNuovaOraInizio());
		l.setOraFine(r.getNuovaOraFine());
		l.setGiornoSett(r.getNuovoGiorno());
	}

}
