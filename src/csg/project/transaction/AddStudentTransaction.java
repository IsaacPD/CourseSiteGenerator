package csg.project.transaction;

import csg.CSGApp;
import csg.data.CSGData;
import csg.data.ProjectData;
import csg.project.Student;

public class AddStudentTransaction implements jtps.jTPS_Transaction {
	Student added;
	CSGApp app;

	public AddStudentTransaction(CSGApp app, Student added){
		this.app = app;
		this.added = added;
	}

	@Override
	public void doTransaction() {
		ProjectData data = ((CSGData) app.getDataComponent()).getProjectData();
		data.addStudent(added);
		app.getGUI().getFileController().markAsEdited(app.getGUI());
	}

	@Override
	public void undoTransaction() {
		ProjectData data = ((CSGData) app.getDataComponent()).getProjectData();
		data.removeStudent(added);
	}
}
