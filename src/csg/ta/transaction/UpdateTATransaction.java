package csg.ta.transaction;

import csg.CSGApp;
import csg.data.CSGData;
import csg.data.RecitationData;
import csg.data.TAData;
import csg.ta.TeachingAssistant;

public class UpdateTATransaction implements jtps.jTPS_Transaction{
	CSGApp app;
	TeachingAssistant update;
	TeachingAssistant ta;

	public UpdateTATransaction(TeachingAssistant ta, String name, String email, CSGApp app){
		this.app = app;
		this.ta = ta;
		this.update = new TeachingAssistant(name, email, ta.isUndergrad());
	}

	@Override
	public void doTransaction() {
		TAData data = ((CSGData) app.getDataComponent()).getTAData();
		RecitationData recitationData = ((CSGData) app.getDataComponent()).getRecitationData();
		TeachingAssistant temp = new TeachingAssistant(ta.getName(), ta.getEmail(), ta.isUndergrad());

		recitationData.updateTA(ta, update.getName());
		data.updateTA(ta, update.getName(), update.getEmail());

		update.setName(temp.getName());
		update.setEmail(temp.getEmail());

		app.getGUI().getFileController().markAsEdited(app.getGUI());
	}

	@Override
	public void undoTransaction() {
		doTransaction();
	}

}