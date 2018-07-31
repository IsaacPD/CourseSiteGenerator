package csg.data;

import csg.schedule.ScheduleItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class ScheduleData {
	private ObservableList<ScheduleItem> schedules;
	private ObservableList<String> types;

	private int mondayMonth, mondayDay, fridayMonth, fridayDay, mondayYear, fridayYear;

	// TODO
	// ADD TYPES TO PROPERTIES
	public ScheduleData() {
		LocalDate now = LocalDate.now();
		int daysToMonday = 1 - now.getDayOfWeek().getValue();
		int daysToFriday = 5 - now.getDayOfWeek().getValue();

		LocalDate friday = now.minusDays(-daysToFriday);
		fridayDay = friday.getDayOfMonth();
		fridayMonth = friday.getMonthValue();
		fridayYear = friday.getYear();

		LocalDate monday = now.minusDays(-daysToMonday);
		mondayDay = monday.getDayOfMonth();
		mondayMonth = monday.getMonthValue();
		mondayYear = monday.getYear();

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

	public int getMondayYear() {
		return mondayYear;
	}

	public int getFridayYear() {
		return fridayYear;
	}

	public void initScheduleBoundaries(String mMonth, String mDay, String mYear, String fMonth, String fDay, String fYear){
		mondayMonth = Integer.parseInt(mMonth);
		mondayDay = Integer.parseInt(mDay);
		mondayYear = Integer.parseInt(mYear);
		fridayMonth = Integer.parseInt(fMonth);
		fridayDay = Integer.parseInt(fDay);
		fridayYear = Integer.parseInt(fYear);
	}

	public void addSchedule(ScheduleItem s) {
		schedules.add(s);
	}

	public ScheduleItem removeSchedule(ScheduleItem s){
		schedules.remove(s);
		return s;
	}

	public void resetData() {
		schedules.clear();
		mondayDay = 0;
		mondayMonth = 0;
		fridayDay = 0;
		fridayMonth = 0;
	}

	public void setStartingMonday(int month, int day, int year) {
		mondayMonth = month;
		mondayDay = day;
		mondayYear = year;
	}

	public void setEndingFriday(int month, int day, int year){
		fridayMonth = month;
		fridayDay = day;
		fridayYear = year;
	}
}
