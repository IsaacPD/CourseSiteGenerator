package csg.ta.transaction;

import csg.CSGApp;
import csg.data.CSGData;
import csg.data.TAData;
import csg.ta.TeachingAssistant;

public class AddTATransaction implements jtps.jTPS_Transaction{
	TeachingAssistant ta;
	CSGApp app;

	public AddTATransaction(String name, String email, CSGApp app){
		ta = new TeachingAssistant(name, email);
		this.app = app;
	}

	@Override
	public void doTransaction() {
		TAData data = ((CSGData) app.getDataComponent()).getTAData();

		data.addTA(ta);

		app.getGUI().getFileController().markAsEdited(app.getGUI());
	}

	@Override
	public void undoTransaction() {
		TAData data = ((CSGData) app.getDataComponent()).getTAData();

		data.removeTA(ta);
	}

}