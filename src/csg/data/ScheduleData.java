package csg.data;

import csg.schedule.ScheduleItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ScheduleData {
	private ObservableList<ScheduleItem> schedules;
	private ObservableList<String> types;

	// TODO
	// ADD TYPES TO PROPERTIES
	public ScheduleData() {
		schedules = FXCollections.observableArrayList();
		types = FXCollections.observableArrayList("Holiday", "Lecture", "HW", "Midterm", "Final");
	}

	public ObservableList<ScheduleItem> getSchedules() {
		return schedules;
	}

	public ObservableList<String> getTypes() {
		return types;
	}

	public void addSchedule(ScheduleItem s) {
		schedules.add(s);
	}

	public ScheduleItem removeSchedule(ScheduleItem s){
		schedules.remove(s);
		return s;
	}
}
