package csg.data;

import csg.project.Student;
import csg.project.Team;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class ProjectData {
	ObservableList<Team> teams;
	ObservableList<Student> students;

	public ProjectData(){
		teams = FXCollections.observableArrayList();
		students = FXCollections.observableArrayList();
	}

	public ObservableList<Team> getTeams() {
		return teams;
	}

	public ObservableList<Student> getStudents() {
		return students;
	}

	public void addTeam(Team t){
		teams.add(t);
	}

	public void addStudent(Student s){
		students.add(s);
	}

	public Team removeTeam(Team t){
		teams.remove(t);
		return t;
	}

	public Student removeStudent(Student s){
		students.remove(s);
		return s;
	}
}
