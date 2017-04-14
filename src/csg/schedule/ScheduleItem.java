package csg.schedule;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ScheduleItem {
	private final StringProperty type, date, title, topic;

	public ScheduleItem(String t, String d, String ti, String to) {
		type = new SimpleStringProperty(t);
		date = new SimpleStringProperty(d);
		title = new SimpleStringProperty(ti);
		topic = new SimpleStringProperty(to);
	}

	public StringProperty getType() {
		return type;
	}

	public StringProperty getDate() {
		return date;
	}

	public StringProperty getTitle() {
		return title;
	}

	public StringProperty getTopic() {
		return topic;
	}
}
