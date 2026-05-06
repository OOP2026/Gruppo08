package model;

public class ServizioUniversita {
	private MateriaRepository materiaRepo = new MateriaRepository();
	private InsegnamentoRepository insegnamentoRepo = new InsegnamentoRepository();
	private AnnoAccademicoRepository annoAccademicoRepo = new AnnoAccademicoRepository();
	private AulaRepository aulaRepo = new AulaRepository();

	// TODO: Metodi di query per estrarre oggetti

	public Materia makeMateria(String nome) {
		Materia m = new Materia(nome);
		materiaRepo.addMateria(m);
		return m;
	}

	public Insegnamento makeInsegnamento(Materia materia, int numeroCfu, int annoDiCorso) {
		Insegnamento i = new Insegnamento(materia, numeroCfu, annoDiCorso);
		insegnamentoRepo.addInsegnamento(i);
		return i;
	}

	public AnnoAccademico makeAnnoAccademico(int anno) {
		AnnoAccademico a = new AnnoAccademico(anno);
		annoAccademicoRepo.addAnnoAccademico(a);
		return a;
	}

	public AnnoAccademico makeAnnoAccademico(int anno, Insegnamento... insegnamenti) {
		AnnoAccademico a = new AnnoAccademico(anno);

		for (Insegnamento insegnamento : insegnamenti) {
			a.addInsegnamento(insegnamento);
		}

		annoAccademicoRepo.addAnnoAccademico(a);
		return a;
	}

	public Aula makeAula(char lettera, int numero, int capacita) {
		Aula a = new Aula(lettera, numero, capacita);
		aulaRepo.addAula(a);
		return a;
	}

}
