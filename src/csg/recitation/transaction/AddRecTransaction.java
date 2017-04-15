package csg.recitation.transaction;

import csg.CSGApp;
import csg.recitation.Recitation;

public class AddRecTransaction implements jtps.jTPS_Transaction {
	RemoveRecTransaction wrapped;

	public AddRecTransaction(CSGApp initApp, Recitation add) {
		wrapped = new RemoveRecTransaction(initApp, add);
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
