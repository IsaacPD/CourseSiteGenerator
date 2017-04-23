package csg.data;

import csg.schedule.ScheduleItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ScheduleData {
	private ObservableList<ScheduleItem> schedules;
	private ObservableList<String> types;

	private int mondayMonth, mondayDay, fridayMonth, fridayDay;

	// TODO
	// ADD TYPES TO PROPERTIES
	public ScheduleData() {
		schedules = FXCollections.observableArrayList();
		types = FXCollections.observableArrayList("Holiday", "Lecture", "HW", "Reference", "Recitation");
	}

	public ObservableList<ScheduleItem> getSchedules() {
		return schedules;
	}

	public ObservableList<String> getTypes() {
		return types;
	}

	public int getMondayMonth() {
		return mondayMonth;
	}

	public int getMondayDay() {
		return mondayDay;
	}

	public int getFridayMonth() {
		return fridayMonth;
	}

	public int getFridayDay() {
		return fridayDay;
	}

	public void initScheduleBoundaries(String mMonth, String mDay, String fMonth, String fDay){
		mondayMonth = Integer.parseInt(mMonth);
		mondayDay = Integer.parseInt(mDay);
		fridayMonth = Integer.parseInt(fMonth);
		fridayDay = Integer.parseInt(fDay);
	}

	public void addSchedule(ScheduleItem s) {
		schedules.add(s);
	}

	public ScheduleItem removeSchedule(ScheduleItem s){
		schedules.remove(s);
		return s;
	}
}
