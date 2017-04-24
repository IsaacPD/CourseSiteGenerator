package csg.project.transaction;

import csg.CSGApp;
import csg.project.Team;

public class RemoveTeamTransaction implements jtps.jTPS_Transaction {
	AddTeamTransaction wrapped;

	public RemoveTeamTransaction(CSGApp app, Team removed){
		wrapped = new AddTeamTransaction(app, removed);
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
