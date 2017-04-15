package csg.data;

import csg.CSGApp;
import csg.recitation.Recitation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RecitationData {
	ObservableList<Recitation> recitations;

	CSGApp app;

	public RecitationData(CSGApp initApp) {
		app = initApp;

		recitations = FXCollections.observableArrayList();
	}

	public ObservableList<Recitation> getRecitations() {
		return recitations;
	}

	public void addRecitation(Recitation r){
		recitations.add(r);
	}

	public Recitation removeRecitation(Recitation r){
		recitations.remove(r);
		return r;
	}
}
