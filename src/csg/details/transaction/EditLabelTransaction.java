package csg.details.transaction;

import csg.CSGApp;
import javafx.scene.control.Label;

public class EditLabelTransaction implements jtps.jTPS_Transaction{
	Label label;
	String oldPath, newPath;
	CSGApp app;

	public EditLabelTransaction(Label label, String oldPath, String newPath, CSGApp app) {
		this.label = label;
		this.oldPath = oldPath;
		this.newPath = newPath;
		this.app = app;
	}

	@Override
	public void doTransaction() {
		label.setText(newPath);

		String temp = oldPath;
		oldPath = newPath;
		newPath = temp;

		app.getGUI().getFileController().markAsEdited(app.getGUI());
	}

	@Override
	public void undoTransaction() {
		doTransaction();
	}
}
