package csg.schedule.transaction;

import csg.CSGApp;
import csg.data.CSGData;
import csg.data.ScheduleData;
import csg.schedule.ScheduleItem;

public class RemoveScheduleTransaction implements jtps.jTPS_Transaction{
	CSGApp app;
	ScheduleItem removed;

	public RemoveScheduleTransaction(CSGApp app, ScheduleItem item) {
		this.app = app;
		removed = item;
	}

	@Override
	public void doTransaction() {
		ScheduleData data = ((CSGData) app.getDataComponent()).getScheduleData();
		data.removeSchedule(removed);
		app.getGUI().getFileController().markAsEdited(app.getGUI());
	}

	@Override
	public void undoTransaction() {
		ScheduleData data = ((CSGData) app.getDataComponent()).getScheduleData();
		data.addSchedule(removed);
	}
}
