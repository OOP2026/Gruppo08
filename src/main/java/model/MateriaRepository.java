package model;

import java.util.ArrayList;
import java.util.List;

public class MateriaRepository {
	private List<Materia> materie = new ArrayList<>();

	public void addMateria(Materia m) {
		materie.add(m);
	}

	public List<Materia> getMaterie() {
		return materie;
	}

}
