package csg.project.transaction;

import csg.CSGApp;
import csg.project.ProjectPane;
import csg.project.Team;
import csg.workspace.CSGWorkspace;

public class UpdateTeamTransaction implements jtps.jTPS_Transaction {
	Team old, updated;
	CSGApp app;

	public UpdateTeamTransaction(CSGApp initApp, Team updated, Team selected){
		app = initApp;
		old = selected;
		this.updated = updated;
	}

	@Override
	public void doTransaction() {
		ProjectPane workspace = ((CSGWorkspace) app.getWorkspaceComponent()).getProjectPane();
		Team temp = new Team(old.getName(), old.getColor(), old.getTColor(), old.getLink());

		old.setAll(updated);
		updated.setAll(temp);

		app.getGUI().getFileController().markAsEdited(app.getGUI());
		(workspace.getTeamTable().getColumns().get(0)).setVisible(false);
		(workspace.getTeamTable().getColumns().get(0)).setVisible(true);
	}

	@Override
	public void undoTransaction() {
		doTransaction();
	}
}
