package implementazioneDao.entity;

public class ClassroomEntity implements IdentifiableEntity<String> {
	public ClassroomEntity(String name) {
		this.name = name;
	}

	private String name;

	@Override
	public String getId() {
		return getName();
	}

	public String getName() {
		return name;
	}
}
