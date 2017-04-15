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

	public String getColor() {
		return color.get();
	}

	public String getTextColor() {
		return textColor.get();
	}

	public String getLink() {
		return link.get();
	}

	public String toString(){
		return name.get();
	}
}
