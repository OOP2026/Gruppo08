package model;

import java.util.List;
import java.util.ArrayList;

public class AulaRepository {
	private List<Aula> aule = new ArrayList<>();

	public List<Aula> getAule() {
		return aule;
	}

	public void addAula(Aula a) {
		aule.add(a);
	}
}
