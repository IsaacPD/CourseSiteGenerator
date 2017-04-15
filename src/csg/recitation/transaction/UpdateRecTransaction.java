package csg.recitation.transaction;

import csg.CSGApp;
import csg.recitation.Recitation;
import csg.recitation.RecitationPane;
import csg.workspace.CSGWorkspace;

public class UpdateRecTransaction implements jtps.jTPS_Transaction {
	Recitation rec;
	Recitation updated;
	CSGApp app;

	public UpdateRecTransaction(CSGApp initApp, Recitation old, Recitation newR) {
		app = initApp;
		rec = old;
		updated = newR;
	}

	@Override
	public void doTransaction() {
		RecitationPane workspace = ((CSGWorkspace)app.getWorkspaceComponent()).getRecitationPane();
		Recitation temp = new Recitation(rec.getSection(), rec.getInstructor(),
				rec.getDay(), rec.getLocation(), rec.getTa1(), rec.getTa2());

		rec.setAll(updated);
		updated.setAll(temp);

		app.getGUI().getFileController().markAsEdited(app.getGUI());
		(workspace.getTable().getColumns().get(0)).setVisible(false);
		(workspace.getTable().getColumns().get(0)).setVisible(true);
	}

	@Override
	public void undoTransaction() {
		doTransaction();
	}
}
