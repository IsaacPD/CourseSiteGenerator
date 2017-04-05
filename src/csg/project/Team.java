package csg.project;

import javafx.beans.property.StringProperty;

public class Team {
	StringProperty name, color, textColor, link;

	public String getName() {
		return name.get();
	}

	public StringProperty nameProperty() {
		return name;
	}
}
