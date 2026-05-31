package controller;

import java.time.LocalTime;
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
			int idInsegnamento, LocalTime oraInizio, LocalTime oraFine) {
		AnnoAccademico a = annoRepo.findAnno(annoAccademico);
		Aula aula = aulaRepo.findAula(nomeAula);
		Insegnamento insegnamento = insegnamentoRepo.findInsegnamento(idInsegnamento);
		Lezione l = new Lezione(idLezione, a, giornoSett, oraInizio, oraFine, aula, insegnamento);
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

		// TODO: Spostamento solo se valori compatibili (aula non occupata quel giorno
		// da altre lezioni ecc..).
		l.setOraInizio(r.getNuovaOraInizio());
		l.setOraFine(r.getNuovaOraFine());
		l.setGiornoSett(r.getNuovoGiorno());
	}

}
