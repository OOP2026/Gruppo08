package model;

import java.time.DayOfWeek;
import java.util.HashMap;

// TODO: Trasforma in OrarioLezione e creare OrarioLezioneRepository?
public class OrarioLezioni {
	private AnnoAccademico anno;
	private HashMap<DayOfWeek, Insegnamento> orari = new HashMap<>();

	public OrarioLezioni(AnnoAccademico anno) {
		this.anno = anno;
	}

	public void addOrario(DayOfWeek day, Insegnamento insegnamento) {
		orari.put(day, insegnamento);
	}

	public HashMap<DayOfWeek, Insegnamento> getOrari() {
		return orari;
	}

	public AnnoAccademico getAnno() {
		return anno;
	}

}
