package csg.schedule;

import csg.CSGApp;
import csg.data.CSGData;
import csg.data.ScheduleData;
import csg.schedule.transaction.AddScheduleTransaction;
import csg.schedule.transaction.RemoveScheduleTransaction;
import csg.schedule.transaction.UpdateScheduleTransaction;
import csg.workspace.CSGWorkspace;

import java.time.LocalDate;

public class ScheduleController {
	private CSGApp app;

	public ScheduleController(CSGApp app) {
		this.app = app;
	}

	public void handleAdd() {
		SchedulePane workspace = ((CSGWorkspace) app.getWorkspaceComponent()).getSchedulePane();
		LocalDate localDate = workspace.getInDate().getValue();

		ScheduleItem selected = workspace.getTable().getSelectionModel().getSelectedItem();

		String type = workspace.getTypeCB().getSelectionModel().getSelectedItem();
		String date = localDate.getMonthValue() + "/" + localDate.getDayOfMonth() + "/" + localDate.getYear();
		String time = workspace.getTimeTF().getText();
		String title = workspace.getTitleTF().getText();
		String topic = workspace.getTopicTF().getText();
		String link = workspace.getLinkTF().getText();
		String criteria = workspace.getCriteriaTF().getText();

		if (selected == null)
			app.jtps.addTransaction(new AddScheduleTransaction(app, new ScheduleItem(type, date, time, title, topic, link, criteria)));
		else
			app.jtps.addTransaction(new UpdateScheduleTransaction(app, new ScheduleItem(type, date, time, title, topic, link, criteria), selected));

		workspace.resetWorkspace();
	}

	public void handleRemove() {
		SchedulePane workspace = ((CSGWorkspace) app.getWorkspaceComponent()).getSchedulePane();
		ScheduleItem item = workspace.getTable().getSelectionModel().getSelectedItem();
		if (item != null)
			app.jtps.addTransaction(new RemoveScheduleTransaction(app, item));
	}

	public void handleStartDayChange() {
		SchedulePane workspace = ((CSGWorkspace) app.getWorkspaceComponent()).getSchedulePane();
		ScheduleData data = ((CSGData) app.getDataComponent()).getScheduleData();
		LocalDate localDate = workspace.getStartDate().getValue();
		int month = localDate.getMonthValue();
		int day = localDate.getDayOfMonth();
		data.setStartingMonday(month, day);
	}

	public void handleEndDayChange() {
		SchedulePane workspace = ((CSGWorkspace) app.getWorkspaceComponent()).getSchedulePane();
		ScheduleData data = ((CSGData) app.getDataComponent()).getScheduleData();
		LocalDate localDate = workspace.getEndDate().getValue();
		int month = localDate.getMonthValue();
		int day = localDate.getDayOfMonth();
		data.setEndingFriday(month, day);
	}

	public void handleTableClick() {
		SchedulePane workspace = ((CSGWorkspace) app.getWorkspaceComponent()).getSchedulePane();
		ScheduleItem selected = workspace.getTable().getSelectionModel().getSelectedItem();

		if (selected != null) {
			String[] date = selected.getDate().split("/");

			workspace.getTypeCB().getSelectionModel().select(selected.getType());
			workspace.getInDate().setValue(
					LocalDate.of(Integer.parseInt(date[2]), Integer.parseInt(date[0]), Integer.parseInt(date[1])));
			workspace.getTimeTF().setText(selected.getTime());
			workspace.getTitleTF().setText(selected.getTitle());
			workspace.getTopicTF().setText(selected.getTopic());
			workspace.getLinkTF().setText(selected.getLink());
			workspace.getCriteriaTF().setText(selected.getCriteria());
		}
	}
}
