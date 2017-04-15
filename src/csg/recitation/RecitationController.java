package csg.recitation;

import csg.CSGApp;
import csg.data.CSGData;
import csg.data.TAData;
import csg.recitation.transaction.AddRecTransaction;
import csg.recitation.transaction.RemoveRecTransaction;
import csg.recitation.transaction.UpdateRecTransaction;
import csg.ta.TeachingAssistant;
import csg.workspace.CSGWorkspace;
import javafx.scene.control.TableView;

public class RecitationController {
	CSGApp app;

	public RecitationController(CSGApp initApp) {
		app = initApp;
	}

	public void handleAddUp() {
		TableView table = ((CSGWorkspace) app.getWorkspaceComponent())
				.getRecitationPane().getTable();
		RecitationPane workspace = ((CSGWorkspace) app.getWorkspaceComponent()).getRecitationPane();

		Recitation selected = (Recitation) table.getSelectionModel().getSelectedItem();

		String section = workspace.getSectionTF().getText();
		String instructor = workspace.getInstructorTF().getText();
		String day = workspace.getDayTimeTF().getText();
		String location = workspace.getLocationTF().getText();
		String ta1 = workspace.getComboBox1().getSelectionModel().getSelectedItem().getName();
		String ta2 = workspace.getComboBox2().getSelectionModel().getSelectedItem().getName();

		Recitation input = new Recitation(section, instructor, day, location, ta1, ta2);

		if (section != null && instructor != null && day != null
				&& location != null && ta1 != null && ta2 != null) {
			if (selected != null)
				app.jtps.addTransaction(new UpdateRecTransaction(app, selected, input));
			else
				app.jtps.addTransaction(new AddRecTransaction(app, input));

			workspace.getInstructorTF().clear();
			workspace.getSectionTF().clear();
			workspace.getDayTimeTF().clear();
			workspace.getLocationTF().clear();
			workspace.getComboBox1().getSelectionModel().clearSelection();
			workspace.getComboBox2().getSelectionModel().clearSelection();

		} else {
			System.out.println("ENTER VALID INPUTS");
		}
	}

	public void handleRemove() {
		RecitationPane workspace = ((CSGWorkspace) app.getWorkspaceComponent()).getRecitationPane();
		TableView table = ((CSGWorkspace) app.getWorkspaceComponent())
				.getRecitationPane().getTable();

		Recitation selected = (Recitation) table.getSelectionModel().getSelectedItem();

		if (selected != null)
			app.jtps.addTransaction(new RemoveRecTransaction(app, selected));

		workspace.getInstructorTF().clear();
		workspace.getSectionTF().clear();
		workspace.getDayTimeTF().clear();
		workspace.getLocationTF().clear();
		workspace.getComboBox1().getSelectionModel().clearSelection();
		workspace.getComboBox2().getSelectionModel().clearSelection();
	}

	public void handleTableClick(){
		try{
			TableView table = ((CSGWorkspace) app.getWorkspaceComponent())
					.getRecitationPane().getTable();
			RecitationPane workspace = ((CSGWorkspace) app.getWorkspaceComponent()).getRecitationPane();
			Recitation selected = (Recitation) table.getSelectionModel().getSelectedItem();

			if (selected != null){
				workspace.getSectionTF().setText(selected.getSection());
				workspace.getDayTimeTF().setText(selected.getDay());
				workspace.getLocationTF().setText(selected.getLocation());
				workspace.getInstructorTF().setText(selected.getInstructor());

				TAData data = ((CSGData)app.getDataComponent()).getTAData();
				TeachingAssistant ta1 = data.getTA(selected.getTa1());
				TeachingAssistant ta2 = data.getTA(selected.getTa2());
				workspace.getComboBox1().getSelectionModel().select(ta1);
				workspace.getComboBox2().getSelectionModel().select(ta2);
			}
		} catch (Exception e) {}
	}
}
