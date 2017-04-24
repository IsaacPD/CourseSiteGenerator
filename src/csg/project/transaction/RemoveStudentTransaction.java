package csg.project.transaction;

import csg.CSGApp;
import csg.project.Student;

public class RemoveStudentTransaction implements jtps.jTPS_Transaction {
	AddStudentTransaction wrapped;

	public RemoveStudentTransaction(CSGApp app, Student removed) {
		wrapped = new AddStudentTransaction(app, removed);
	}

	@Override
	public void doTransaction() {
		wrapped.undoTransaction();
	}

	@Override
	public void undoTransaction() {
		wrapped.doTransaction();
	}
}
