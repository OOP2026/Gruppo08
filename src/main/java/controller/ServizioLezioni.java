package controller;

import java.time.LocalDateTime;

import model.*;

public class ServizioLezioni {
	private LezioneRepository lezioneRepo = new LezioneRepository();
	private OrarioLezioneRepository orarioRepo = new OrarioLezioneRepository();

	/*
	 * TODO:
	 * - richiediSpostamento: interagisce con Docente e OrarioLezioni e crea uno
	 * spostamento temporaneo per una determinata settimana della lezione
	 * - rispondiSpostamento: cambia lo StatoSpostamento di una richiesta e modifica
	 * l'orario di una lezione
	 */

	public Lezione makeLezione(Aula aula, Insegnamento insegnamento, LocalDateTime oraInizio, LocalDateTime oraFine) {
		Lezione l = new Lezione(aula, insegnamento, oraInizio, oraFine);
		lezioneRepo.addLezione(l);
		return l;
	}

	public OrarioLezione makeOrarioLezione(AnnoAccademico anno, String giornoSett, Insegnamento insegnamento) {
		OrarioLezione o = new OrarioLezione(anno, giornoSett, insegnamento);
		orarioRepo.addOrarioLezione(o);
		return o;
	}

}
