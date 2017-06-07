package model;

/**
 * Created by michal on 07.06.17.
 */
public class TransactionType {
	private Integer id;
	private String name;
	private String description;

	public TransactionType(Integer id, String name, String description) {
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
