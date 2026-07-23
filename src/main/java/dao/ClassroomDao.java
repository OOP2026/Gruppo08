package dao;

import java.util.List;

import implementazioneDao.entity.ClassroomEntity;

public interface ClassroomDao extends GenericDao<ClassroomEntity, String> {
	public void insertClassroom(String name);

	public List<ClassroomEntity> getAllClassrooms();
}
