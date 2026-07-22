package dao;

import model.Classroom;
import dao.impl.ClassroomPostgresDao;

public class ClassroomDao extends AbstractDao<Classroom, ClassroomPostgresDao, String> {
	private static ClassroomDao instance;

	private ClassroomDao() {
		super(new ClassroomPostgresDao());
	}

	public static ClassroomDao getInstance() {
		if (instance == null)
			instance = new ClassroomDao();
		return instance;
	}

	public void insertClassroom(String name) {
		Classroom c = sqldao.insertClassroom(name);
		inMem.add(c);
	}
}
