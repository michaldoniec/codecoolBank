package model;

public class CardStatus {
	private Integer id;
	private String name;
	private String description;

	public CardStatus(Integer id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
}
