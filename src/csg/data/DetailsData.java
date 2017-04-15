package csg.data;

import csg.details.Details;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;

public class DetailsData {
	ObservableList<String> subjects, years, semesters, numbers;
	ObservableList<File> stylesheets;
	ObservableList<Details> details;

	public DetailsData(){
		File style = new File("./work/css");
		File[] sheets = style.listFiles();

		stylesheets = FXCollections.observableArrayList(sheets);
		subjects = FXCollections.observableArrayList("CSE");
		years = FXCollections.observableArrayList();
		semesters = FXCollections.observableArrayList("Fall", "Spring", "Winter", "Summer");
		numbers = FXCollections.observableArrayList("219", "308", "380");
	}

	public ObservableList<String> getYears() {
		return years;
	}

	public ObservableList<String> getSemesters() {
		return semesters;
	}

	public ObservableList<String> getNumbers() {
		return numbers;
	}

	public ObservableList<String> getSubjects() {
		return subjects;
	}

	public ObservableList<Details> getDetails() {
		return details;
	}

	public ObservableList<File> getStylesheets() {
		return stylesheets;
	}
}
