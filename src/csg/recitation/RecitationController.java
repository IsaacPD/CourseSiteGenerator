package csg.recitation;

import csg.CSGApp;
import csg.CSGAppProp;
import csg.data.CSGData;
import csg.data.RecitationData;
import csg.data.TAData;
import csg.recitation.transaction.AddRecTransaction;
import csg.recitation.transaction.RemoveRecTransaction;
import csg.recitation.transaction.UpdateRecTransaction;
import csg.ta.TeachingAssistant;
import csg.workspace.CSGWorkspace;
import djf.ui.AppMessageDialogSingleton;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TableView;
import properties_manager.PropertiesManager;

public class RecitationController {
	CSGApp app;

	public RecitationController(CSGApp initApp) {
		app = initApp;
	}

	public void handleAddUp() {
		TableView table = ((CSGWorkspace) app.getWorkspaceComponent())
				.getRecitationPane().getTable();
		RecitationPane workspace = ((CSGWorkspace) app.getWorkspaceComponent()).getRecitationPane();
		RecitationData data = ((CSGData) app.getDataComponent()).getRecitationData();

		PropertiesManager props = PropertiesManager.getPropertiesManager();
		AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();

		Recitation selected = (Recitation) table.getSelectionModel().getSelectedItem();

		String section = workspace.getSectionTF().getText();
		String instructor = workspace.getInstructorTF().getText();
		String day = workspace.getDayTimeTF().getText();
		String location = workspace.getLocationTF().getText();
		TeachingAssistant taOne = workspace.getComboBox1().getSelectionModel().getSelectedItem();
		TeachingAssistant taTwo = workspace.getComboBox2().getSelectionModel().getSelectedItem();

		String ta1 = taOne == null ? null : taOne.getName();
		String ta2 = taTwo == null ? null : taTwo.getName();

		Recitation input = new Recitation(section, instructor, day, location, ta1, ta2);

		if (isNumber(section) && !instructor.equals("") && !day.equals("") && !location.equals("")) {
			if (selected != null)
				app.jtps.addTransaction(new UpdateRecTransaction(app, selected, input));
			else if (!data.containsSection(section)) {
				app.jtps.addTransaction(new AddRecTransaction(app, input));

				workspace.getInstructorTF().clear();
				workspace.getSectionTF().clear();
				workspace.getDayTimeTF().clear();
				workspace.getLocationTF().clear();
				workspace.getComboBox1().getSelectionModel().clearSelection();
				workspace.getComboBox2().getSelectionModel().clearSelection();
			} else {
				dialog.show(props.getProperty(CSGAppProp.R_INVALID_SECTION_MESSAGE_TITLE), props.getProperty(CSGAppProp.R_INVALID_SECTION_MESSAGE));
			}
		} else {
			dialog.show(props.getProperty(CSGAppProp.R_INVALID_INPUT_TITLE), props.getProperty(CSGAppProp.R_INVALID_INPUT));
		}
	}

	private boolean isNumber(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (Exception e) {
			return false;
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

	public void handleTableClick() {
		try {
			TableView table = ((CSGWorkspace) app.getWorkspaceComponent())
					.getRecitationPane().getTable();
			RecitationPane workspace = ((CSGWorkspace) app.getWorkspaceComponent()).getRecitationPane();
			Recitation selected = (Recitation) table.getSelectionModel().getSelectedItem();

			if (selected != null) {
				workspace.getSectionTF().setText(selected.getSection());
				workspace.getDayTimeTF().setText(selected.getDay());
				workspace.getLocationTF().setText(selected.getLocation());
				workspace.getInstructorTF().setText(selected.getInstructor());

				TAData data = ((CSGData) app.getDataComponent()).getTAData();
				TeachingAssistant ta1 = data.getTA(selected.getTa1());
				TeachingAssistant ta2 = data.getTA(selected.getTa2());
				workspace.getComboBox1().getSelectionModel().select(ta1);
				workspace.getComboBox2().getSelectionModel().select(ta2);
			}
		} catch (Exception e) {
		}
	}
}
