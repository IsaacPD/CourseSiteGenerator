package csg.data;

import csg.schedule.ScheduleItem;
import javafx.collections.ObservableList;

public class ScheduleData {
	private ObservableList<ScheduleItem> schedules;
	private ObservableList<String> types;

	public ObservableList getSchedules() {
		return schedules;
	}

	public ObservableList<String> getTypes() {
		return types;
	}
}
