package csg.schedule.transaction;

import csg.CSGApp;
import csg.schedule.ScheduleItem;
import csg.schedule.SchedulePane;
import csg.workspace.CSGWorkspace;

public class UpdateScheduleTransaction implements jtps.jTPS_Transaction {
	ScheduleItem old, newItem;
	CSGApp app;

	public UpdateScheduleTransaction(CSGApp app, ScheduleItem scheduleItem, ScheduleItem selected) {
		this.app = app;
		old = selected;
		newItem = scheduleItem;
	}

	@Override
	public void doTransaction() {
		SchedulePane workspace = ((CSGWorkspace) app.getWorkspaceComponent()).getSchedulePane();
		ScheduleItem temp = new ScheduleItem(old.getType(), old.getDate(), old.getTime(),
				old.getTitle(), old.getTopic(), old.getLink(), old.getCriteria());

		old.setAll(newItem);
		newItem.setAll(temp);

		app.getGUI().getFileController().markAsEdited(app.getGUI());
		(workspace.getTable().getColumns().get(0)).setVisible(false);
		(workspace.getTable().getColumns().get(0)).setVisible(true);
	}

	@Override
	public void undoTransaction() {
		doTransaction();
	}
}
