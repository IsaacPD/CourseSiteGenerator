package csg.project.transaction;

import csg.CSGApp;
import csg.project.ProjectPane;
import csg.project.Student;
import csg.workspace.CSGWorkspace;

public class UpdateStudentTransaction implements jtps.jTPS_Transaction {
	Student old, newStudent;
	CSGApp app;

	public UpdateStudentTransaction(CSGApp app, Student updated, Student selected){
		this.app = app;
		newStudent = updated;
		old = selected;
	}

	@Override
	public void doTransaction() {
		ProjectPane workspace = ((CSGWorkspace) app.getWorkspaceComponent()).getProjectPane();
		Student temp = new Student(old.getFName(), old.getLName(), old.getTeam(), old.getRole());

		old.setAll(newStudent);
		newStudent.setAll(temp);

		app.getGUI().getFileController().markAsEdited(app.getGUI());
		(workspace.getStudentTable().getColumns().get(0)).setVisible(false);
		(workspace.getStudentTable().getColumns().get(0)).setVisible(true);
	}

	@Override
	public void undoTransaction() {
		doTransaction();
	}
}
