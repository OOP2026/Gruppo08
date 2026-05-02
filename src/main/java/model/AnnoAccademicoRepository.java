package model;

import java.util.ArrayList;
import java.util.List;

public class AnnoAccademicoRepository {
	private List<AnnoAccademico> anniAccademici = new ArrayList<>();

	public List<AnnoAccademico> getAnniAccademici() {
		return anniAccademici;
	}

	public void addAnnoAccademico(AnnoAccademico a) {
		anniAccademici.add(a);
	}
}
