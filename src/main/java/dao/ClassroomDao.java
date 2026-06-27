package dao;

import java.util.List;
import java.util.NoSuchElementException;

import daoImplementation.ClassroomPostgresDao;

import java.sql.SQLException;
import java.util.ArrayList;

import model.Classroom;

public class ClassroomDao {
	private static ClassroomDao instance;

	private List<Classroom> classroomInMem = new ArrayList<>();

	private ClassroomDao() {
	}

	public static ClassroomDao getInstance() {
		if (instance == null)
			instance = new ClassroomDao();
		return instance;
	}

	public List<Classroom> getClassroomInMem() {
		return new ArrayList<Classroom>(classroomInMem);
	}

	private boolean isNameInMem(String name) {
		for (Classroom c : classroomInMem) {
			if (c.getName().equals(name))
				return true;
		}
		return false;
	}

	private Classroom getByNameInMem(String name) throws NoSuchElementException {
		for (Classroom c : classroomInMem) {
			if (c.getName().equals(name))
				return c;
		}

		throw new NoSuchElementException(name + " not found");
	}

	public Classroom getByName(String name) throws NoSuchElementException {
		if (isNameInMem(name)) {
			try {
				return getByNameInMem(name);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}

		ClassroomPostgresDao psqldao = new ClassroomPostgresDao();
		Classroom c = psqldao.getByName(name);
		classroomInMem.add(c);
		return c;
	}

	public void insertClassroom(String name) throws SQLException {
		ClassroomPostgresDao psqldao = new ClassroomPostgresDao();
		Classroom c = psqldao.insertClassroom(name);
		classroomInMem.add(c);
	}
}
