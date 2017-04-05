package csg.ta.transaction;

import csg.CSGApp;
import csg.data.CSGData;
import csg.data.TAData;
import csg.ta.TeachingAssistant;
import javafx.scene.layout.Pane;

public class ToggleTATransaction implements jtps.jTPS_Transaction{
	CSGApp app;
	Pane pane;
	TeachingAssistant ta;

	public ToggleTATransaction(CSGApp app, Pane pane, TeachingAssistant ta){
		this.app = app;
		this.pane = pane;
		this.ta = ta;
	}

	@Override
	public void doTransaction() {
		String taName = ta.getName();
		TAData data = ((CSGData) app.getDataComponent()).getTAData();
		String cellKey = pane.getId();

		// AND TOGGLE THE OFFICE HOURS IN THE CLICKED CELL
		data.toggleTAOfficeHours(cellKey, taName);

		app.getGUI().getFileController().markAsEdited(app.getGUI());
	}

	@Override
	public void undoTransaction() {
		doTransaction();
	}

}