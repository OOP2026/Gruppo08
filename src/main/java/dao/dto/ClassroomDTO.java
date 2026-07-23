package dao.dto;

public class ClassroomDTO implements IdentifiableDTO<String> {
	public ClassroomDTO(String name) {
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
