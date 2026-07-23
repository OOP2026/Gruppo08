package dao;

import dao.dto.ClassroomDTO;

public interface ClassroomDao extends GenericDao<ClassroomDTO, String> {
	public void insertClassroom(String name);
}
