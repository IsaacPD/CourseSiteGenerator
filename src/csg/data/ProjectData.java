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
	}

	public ObservableList<Team> getTeams() {
		return teams;
	}

	public ObservableList<String> getTeamNames(){
		ArrayList<String> names = new ArrayList<>();
		for(Team t: teams){
			names.add(t.getName());
		}
		return FXCollections.observableArrayList(names);
	}

	public ObservableList<Student> getStudents() {
		return students;
	}
}
