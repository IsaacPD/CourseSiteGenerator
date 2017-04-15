package csg.project;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Student {
	private final StringProperty fName, lName, team, role;

	public Student(String firstName, String lastName, Team team, String role) {
		this.fName = new SimpleStringProperty(firstName);
		this.lName = new SimpleStringProperty(lastName);
		this.role = new SimpleStringProperty(role);
		this.team = new SimpleStringProperty(team.getName());
	}

	public String getFName() {
		return fName.get();
	}

	public String getLName() {
		return lName.get();
	}

	public String getTeam() {
		return team.get();
	}

	public String getRole() {
		return role.get();
	}
}
