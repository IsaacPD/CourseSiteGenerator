package csg.data;

import csg.CSGApp;
import csg.recitation.Recitation;
import csg.recitation.RecitationPane;
import csg.ta.TeachingAssistant;
import csg.workspace.CSGWorkspace;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;

import java.util.ArrayList;

public class RecitationData {
	ObservableList<Recitation> recitations;
	CSGApp app;

	public RecitationData(CSGApp app) {
		recitations = FXCollections.observableArrayList();
		this.app = app;
	}

	public ObservableList<Recitation> getRecitations() {
		return recitations;
	}

	public void addRecitation(Recitation r) {
		recitations.add(r);
	}

	public Recitation removeRecitation(Recitation r) {
		recitations.remove(r);
		return r;
	}

	public void resetData() {
		recitations.clear();
	}

	public ArrayList<Recitation> removeTA(TeachingAssistant ta) {
		RecitationPane workspace = ((CSGWorkspace) app.getWorkspaceComponent()).getRecitationPane();
		ArrayList<Recitation> removed = new ArrayList<>();
		for (Recitation r : recitations) {
			if (r.getTa1().equals(ta.getName())) {
				r.setTa1("");
				removed.add(r);
			} else if (r.getTa2().equals(ta.getName())) {
				r.setTa2("");
				removed.add(r);
			}
		}

		((workspace.getTable().getColumns().get(0))).setVisible(false);
		((workspace.getTable().getColumns().get(0))).setVisible(true);

		return removed;
	}

	public void updateTA(TeachingAssistant ta, String name) {
		RecitationPane workspace = ((CSGWorkspace) app.getWorkspaceComponent()).getRecitationPane();
		for (Recitation r : recitations) {
			if (r.getTa2().equals(ta.getName()))
				r.setTa2(name);
			else if (r.getTa1().equals(ta.getName()))
				r.setTa1(name);
		}

		((workspace.getTable().getColumns().get(0))).setVisible(false);
		((workspace.getTable().getColumns().get(0))).setVisible(true);
	}

	public boolean containsSection(String section) {
		for (Recitation r: recitations){
			if (r.getSection().equals(section))
				return true;
		}

		return false;
	}
}
