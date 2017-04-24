package csg.project;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Student {
	private final StringProperty fName, lName, team, role;

	public Student(String firstName, String lastName, String team, String role) {
		this.fName = new SimpleStringProperty(firstName);
		this.lName = new SimpleStringProperty(lastName);
		this.role = new SimpleStringProperty(role);
		this.team = new SimpleStringProperty(team);
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

	public void setAll(Student other){
		fName.set(other.getFName());
		lName.set(other.getLName());
		team.set(other.getTeam());
		role.set(other.getRole());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Student))
			return false;

		Student that = (Student) obj;

		return getFName().equals(that.getFName()) && getLName().equals(that.getLName())
				&& getRole().equals(that.getRole()) && getTeam().equals(that.getTeam());
	}
}
