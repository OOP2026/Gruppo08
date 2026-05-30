package controller;

import java.time.LocalTime;
import java.time.DayOfWeek;

import model.*;

public class ServizioLezioni {
	private LezioneRepository lezioneRepo = new LezioneRepository();
	private RichiestaSpostamentoRepository richiesteRepo = new RichiestaSpostamentoRepository();
	private DocenteRepository docenteRepo = new DocenteRepository();

	public Lezione makeLezione(int idLezione, AnnoAccademico annoAccademico, DayOfWeek giornoSett, Aula aula,
			Insegnamento insegnamento, LocalTime oraInizio, LocalTime oraFine) {
		Lezione l = new Lezione(idLezione, annoAccademico, giornoSett, oraInizio, oraFine, aula, insegnamento);
		lezioneRepo.addLezione(l);
		return l;
	}

	public RichiestaSpostamento makeRichiestaSpostamento(int idRichiesta, int idLezioneDaSpostare, String docenteLogin,
			DayOfWeek nuovoGiorno, LocalTime nuovaOraInizio, LocalTime nuovaOraFine) {

		Lezione lezioneDaSpostare = lezioneRepo.findById(idLezioneDaSpostare);
		Docente docente = docenteRepo.findByLogin(docenteLogin);

		RichiestaSpostamento r = new RichiestaSpostamento(idRichiesta, lezioneDaSpostare, docente, nuovoGiorno,
				nuovaOraInizio, nuovaOraFine);
		richiesteRepo.addRichiesta(r);
		return r;
	}

	public void approvaRichiestaSpostamento(int idRichiesta, boolean approvata) {
		RichiestaSpostamento r = richiesteRepo.findById(idRichiesta);
		r.setStato(approvata);
		if (!approvata)
			return;

		Lezione l = r.getLezioneDaSpostare();

		// TODO: Spostamento solo se aula non occupata quel giorno da altre lezioni.
		l.setOraInizio(r.getNuovaOraInizio());
		l.setOraFine(r.getNuovaOraFine());
		l.setGiornoSett(r.getNuovoGiorno());
	}

}
