package csg.project.transaction;

import csg.CSGApp;
import csg.data.CSGData;
import csg.data.ProjectData;
import csg.project.Team;

public class AddTeamTransaction implements jtps.jTPS_Transaction {
	CSGApp app;
	Team added;

	public AddTeamTransaction(CSGApp app, Team added) {
		this.added = added;
		this.app = app;
	}

	@Override
	public void doTransaction() {
		ProjectData data = ((CSGData) app.getDataComponent()).getProjectData();
		data.addTeam(added);
		app.getGUI().getFileController().markAsEdited(app.getGUI());
	}

	@Override
	public void undoTransaction() {
		ProjectData data = ((CSGData) app.getDataComponent()).getProjectData();
		data.removeTeam(added);
	}
}
