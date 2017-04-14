package csg.project;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Team {
	private final StringProperty name, color, textColor, link;

	public Team(String name, String color, String textColor, String link) {
		this.color = new SimpleStringProperty(color);
		this.link = new SimpleStringProperty(link);
		this.name = new SimpleStringProperty(name);
		this.textColor = new SimpleStringProperty(textColor);
	}

	public String getName() {
		return name.get();
	}

	public StringProperty nameProperty() {
		return name;
	}

	public StringProperty getColor() {
		return color;
	}

	public StringProperty getTextColor() {
		return textColor;
	}

	public StringProperty getLink() {
		return link;
	}
}
