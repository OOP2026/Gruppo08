package model;

public class Classroom implements Identifiable<String> {
	private String name;

	public Classroom(String name) {
		this.name = name;
	}

	@Override
	public String getId() {
		return name;
	}

	@Deprecated
	public String getName() {
		return getId();
	}
}
