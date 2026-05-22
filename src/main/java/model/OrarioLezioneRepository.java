package model;

import java.util.ArrayList;
import java.util.List;

public class OrarioLezioneRepository {
	private List<OrarioLezione> orarioLezioni = new ArrayList<>();

	public void addOrarioLezione(OrarioLezione o) {
		orarioLezioni.add(o);
	}

	public List<OrarioLezione> getOrarioLezioni() {
		return orarioLezioni;
	}

}
