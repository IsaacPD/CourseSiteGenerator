package csg.data;

import csg.details.Details;
import javafx.collections.ObservableList;

import java.io.File;

public class DetailsData {
	ObservableList<String> subjects, years, semesters, numbers, stylesheets;
	ObservableList<Details> details;

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

	public ObservableList<String> getStylesheets() {
		return stylesheets;
	}
}
