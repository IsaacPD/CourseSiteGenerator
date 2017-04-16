package csg.project;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Team {
	private final StringProperty name, color, tColor, link;

	public Team(String name, String color, String textColor, String link) {
		this.color = new SimpleStringProperty(color);
		this.link = new SimpleStringProperty(link);
		this.name = new SimpleStringProperty(name);
		this.tColor = new SimpleStringProperty(textColor);
	}

	public String getName() {
		return name.get();
	}

	public String getColor() {
		return color.get();
	}

	public String getTColor() {
		return tColor.get();
	}

	public String getLink() {
		return link.get();
	}

	public String toString(){
		return name.get();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Team))
			return false;

		Team that = (Team) obj;

		return getColor().equals(that.getColor()) && getLink().equals(that.getLink())
				&& getName().equals(that.getName()) && getTColor().equals(that.getTColor());
	}
}
