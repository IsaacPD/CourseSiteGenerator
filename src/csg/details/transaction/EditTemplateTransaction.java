package csg.details.transaction;

import csg.CSGApp;
import csg.data.CSGData;
import csg.data.DetailsData;
import javafx.scene.control.Label;

public class EditTemplateTransaction implements jtps.jTPS_Transaction {
	EditLabelTransaction wrapper;
	CSGApp app;

	public EditTemplateTransaction(Label selectedExportDir, String oldPath, String newPath, CSGApp app) {
		wrapper = new EditLabelTransaction(selectedExportDir, oldPath, newPath, app);
		this.app = app;
	}


	@Override
	public void doTransaction() {
		DetailsData data = ((CSGData) app.getDataComponent()).getDetailsData();
		data.getDetails().clear();
		data.addDetails(wrapper.newPath);
		wrapper.doTransaction();
	}

	@Override
	public void undoTransaction() {
		doTransaction();
	}
}
