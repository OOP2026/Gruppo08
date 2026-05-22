package controller;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import model.*;

public class ServizioLezioni {
	private LezioneRepository lezioneRepo = new LezioneRepository();
	private OrarioLezioneRepository orarioRepo = new OrarioLezioneRepository();

	/*
	 * TODO:
	 * - getOrario: deve essere chiamabile da tutti gli utenti e restituisce la hash
	 * table.
	 * - richiediSpostamento: interagisce con Docente e OrarioLezioni e crea uno
	 * spostamento temporaneo per una determinata settimana della lezione
	 * - confermaSpostamento: prende un Coordinatore e cambia lo StatoSpostamento
	 */

	public Lezione makeLezione(Aula aula, Insegnamento insegnamento, LocalDateTime oraInizio, LocalDateTime oraFine) {
		Lezione l = new Lezione(aula, insegnamento, oraInizio, oraFine);
		lezioneRepo.addLezione(l);
		return l;
	}

	public OrarioLezione makeOrarioLezione(AnnoAccademico anno, DayOfWeek giornoSett, Insegnamento insegnamento) {
		OrarioLezione o = new OrarioLezione(anno, giornoSett, insegnamento);
		orarioRepo.addOrarioLezione(o);
		return o;
	}

}
