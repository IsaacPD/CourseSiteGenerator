package csg.schedule.transaction;

import csg.CSGApp;
import csg.data.CSGData;
import csg.data.ScheduleData;
import csg.schedule.ScheduleItem;

public class AddScheduleTransaction implements jtps.jTPS_Transaction{
	ScheduleItem added;
	CSGApp app;

	public AddScheduleTransaction(CSGApp app, ScheduleItem scheduleItem) {
		this.app = app;
		added = scheduleItem;
	}

	@Override
	public void doTransaction() {
		ScheduleData data = ((CSGData) app.getDataComponent()).getScheduleData();
		data.addSchedule(added);
		app.getGUI().getFileController().markAsEdited(app.getGUI());
	}

	@Override
	public void undoTransaction() {
		ScheduleData data = ((CSGData) app.getDataComponent()).getScheduleData();
		data.removeSchedule(added);
	}
}
