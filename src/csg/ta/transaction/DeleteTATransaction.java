package csg.ta.transaction;

import csg.CSGApp;
import csg.data.CSGData;
import csg.data.TAData;
import csg.ta.TeachingAssistant;

import java.util.ArrayList;

public class DeleteTATransaction implements jtps.jTPS_Transaction{
	TeachingAssistant ta;
	CSGApp app;
	ArrayList<String> removed;

	public DeleteTATransaction(TeachingAssistant ta, CSGApp app){
		this.ta = ta;
		this.app = app;
	}

	@Override
	public void doTransaction() {
		TAData data = ((CSGData) app.getDataComponent()).getTAData();

		removed = data.removeTA(ta);

		app.getGUI().getFileController().markAsEdited(app.getGUI());
	}

	@Override
	public void undoTransaction() {
		TAData data = ((CSGData) app.getDataComponent()).getTAData();

		for(String key: removed){
			data.toggleTAOfficeHours(key, ta.getName());
		}

		data.addTA(ta);
	}

}