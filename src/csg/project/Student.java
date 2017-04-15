package csg.project;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Student {
	private final StringProperty firstName, lastName, team, role;

	public Student(String firstName, String lastName, Team team, String role) {
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
		this.role = new SimpleStringProperty(role);
		this.team = new SimpleStringProperty(team.getName());
	}
}
