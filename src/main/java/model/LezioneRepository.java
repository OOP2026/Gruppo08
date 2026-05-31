package model;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;

public class LezioneRepository {
	private LezioneRepository() {
	}

	private static LezioneRepository instance;

	public static LezioneRepository getInstance() {
		if (instance == null)
			instance = new LezioneRepository();

		return instance;
	}

	List<Lezione> lezioni = new ArrayList<>();

	public void addLezione(Lezione l) {
		lezioni.add(l);
	}

	public Lezione findById(int idLezione) throws NoSuchElementException {
		for (Lezione l : lezioni) {
			if (l.getIdLezione() == idLezione)
				return l;
		}
		throw new NoSuchElementException();
	}
}
