package csg.ta.transaction;

import csg.CSGApp;
import csg.data.CSGData;
import csg.data.RecitationData;
import csg.data.TAData;
import csg.recitation.Recitation;
import csg.recitation.RecitationPane;
import csg.ta.TeachingAssistant;
import csg.workspace.CSGWorkspace;

import java.util.ArrayList;

public class DeleteTATransaction implements jtps.jTPS_Transaction {
	TeachingAssistant ta;
	CSGApp app;
	ArrayList<String> removed;
	ArrayList<Recitation> deleted;

	public DeleteTATransaction(TeachingAssistant ta, CSGApp app) {
		this.ta = ta;
		this.app = app;
	}

	@Override
	public void doTransaction() {
		TAData data = ((CSGData) app.getDataComponent()).getTAData();
		RecitationData recitationData = ((CSGData) app.getDataComponent()).getRecitationData();

		removed = data.removeTA(ta);
		deleted = recitationData.removeTA(ta);

		app.getGUI().getFileController().markAsEdited(app.getGUI());
	}

	@Override
	public void undoTransaction() {
		TAData data = ((CSGData) app.getDataComponent()).getTAData();
		RecitationPane workspace = ((CSGWorkspace) app.getWorkspaceComponent()).getRecitationPane();

		for (String key : removed)
			data.toggleTAOfficeHours(key, ta.getName());

		for (Recitation r : deleted) {
			if (r.getTa1().equals(""))
				r.setTa1(ta.getName());
			else
				r.setTa2(ta.getName());
		}

		((workspace.getTable().getColumns().get(0))).setVisible(false);
		((workspace.getTable().getColumns().get(0))).setVisible(true);

		data.addTA(ta);
	}

}