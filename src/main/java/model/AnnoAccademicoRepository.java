package model;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class AnnoAccademicoRepository {
	private AnnoAccademicoRepository() {
	}

	private static AnnoAccademicoRepository instance;

	public static AnnoAccademicoRepository getInstance() {
		if (instance == null)
			instance = new AnnoAccademicoRepository();

		return instance;
	}

	private List<AnnoAccademico> anniAccademici = new ArrayList<>();

	public List<AnnoAccademico> getAnniAccademici() {
		return anniAccademici;
	}

	public AnnoAccademico findAnno(int anno) throws NoSuchElementException {
		for (AnnoAccademico a : anniAccademici) {
			if (a.getAnno() == anno)
				return a;
		}
		throw new NoSuchElementException();
	}

	public void addAnnoAccademico(AnnoAccademico a) {
		anniAccademici.add(a);
	}
}
