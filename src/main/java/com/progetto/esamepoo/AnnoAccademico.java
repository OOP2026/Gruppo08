package com.progetto.esamepoo;

import java.util.ArrayList;
import java.util.List;

public class AnnoAccademico {
	private int anno;
	private List<Insegnamento> insegnamenti = new ArrayList<>();

	public AnnoAccademico(int anno) {
		this.anno = anno;
	}

	public int getAnno() {
		return anno;
	}

	public List<Insegnamento> getInsegnamenti() {
		return insegnamenti;
	}
}
