package csg.ta.transaction;

import csg.CSGApp;
import csg.data.CSGData;
import csg.data.TAData;
import csg.ta.TimeSlot;

import java.util.ArrayList;

public class ComboTimeTransaction implements jtps.jTPS_Transaction{
	CSGApp app;
	ArrayList<TimeSlot> removed;
	int startTime;
	int endTime;
	int prevStart;
	int prevEnd;

	public ComboTimeTransaction(CSGApp app, int start, int end){
		this.app = app;
		this.startTime = start;
		this.endTime = end;
	}

	@Override
	public void doTransaction() {
		TAData data = ((CSGData) app.getDataComponent()).getTAData();
		prevStart = data.getStartHour();
		prevEnd = data.getEndHour();

		removed = data.changeHours(startTime, endTime);

		int temp = startTime;
		startTime = prevStart;
		prevStart = temp;

		temp = endTime;
		endTime = prevEnd;
		prevEnd = temp;

		app.getGUI().getFileController().markAsEdited(app.getGUI());
	}

	@Override
	public void undoTransaction() {
		TAData data = ((CSGData) app.getDataComponent()).getTAData();
		prevStart = data.getStartHour();
		prevEnd = data.getEndHour();

		data.changeHours(startTime, endTime);

		int temp = startTime;
		startTime = prevStart;
		prevStart = temp;

		temp = endTime;
		endTime = prevEnd;
		prevEnd = temp;

		for (TimeSlot ts: removed){
			data.addOfficeHoursReservation(ts.getDay(), ts.getTime(), ts.getName());
		}
	}
}

