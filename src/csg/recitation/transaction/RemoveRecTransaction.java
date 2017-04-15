package csg.recitation.transaction;

import csg.CSGApp;
import csg.data.CSGData;
import csg.data.RecitationData;
import csg.recitation.Recitation;

public class RemoveRecTransaction implements jtps.jTPS_Transaction {
	CSGApp app;
	Recitation removed;

	public RemoveRecTransaction(CSGApp initApp, Recitation r) {
		removed = r;
		app = initApp;
	}

	@Override
	public void doTransaction() {
		RecitationData data = ((CSGData)app.getDataComponent()).getRecitationData();

		data.removeRecitation(removed);
		app.getGUI().getFileController().markAsEdited(app.getGUI());
	}

	@Override
	public void undoTransaction() {
		RecitationData data = ((CSGData)app.getDataComponent()).getRecitationData();

		data.addRecitation(removed);
		app.getGUI().getFileController().markAsEdited(app.getGUI());
	}
}
