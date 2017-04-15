package csg.schedule;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ScheduleItem {
	private final StringProperty type, date, title, topic;

	public ScheduleItem(String type, String date, String title, String topic) {
		this.type = new SimpleStringProperty(type);
		this.date = new SimpleStringProperty(date);
		this.title = new SimpleStringProperty(title);
		this.topic = new SimpleStringProperty(topic);
	}

	public String getType() {
		return type.get();
	}

	public String getDate() {
		return date.get();
	}

	public String getTitle() {
		return title.get();
	}

	public String getTopic() {
		return topic.get();
	}
}
