package csg.project;

import csg.CSGApp;
import csg.CSGAppProp;
import csg.data.CSGData;
import csg.data.ProjectData;
import csg.project.transaction.*;
import csg.workspace.CSGWorkspace;

import djf.ui.AppMessageDialogSingleton;
import javafx.scene.paint.Color;
import properties_manager.PropertiesManager;

public class ProjectController {
	private CSGApp app;

	public ProjectController(CSGApp app) {
		this.app = app;
	}


	public void handleRemoveTeam() {
		ProjectPane workspace = ((CSGWorkspace) app.getWorkspaceComponent()).getProjectPane();
		Team selected = workspace.getTeamTable().getSelectionModel().getSelectedItem();

		if (selected != null)
			app.jtps.addTransaction(new RemoveTeamTransaction(app, selected));
	}

	public void handleRemoveStudent() {
		ProjectPane workspace = ((CSGWorkspace) app.getWorkspaceComponent()).getProjectPane();
		Student selected = workspace.getStudentTable().getSelectionModel().getSelectedItem();

		if (selected != null)
			app.jtps.addTransaction(new RemoveStudentTransaction(app, selected));
	}

	public void handleAddStudent() {
		ProjectPane workspace = ((CSGWorkspace) app.getWorkspaceComponent()).getProjectPane();
		Student selected = workspace.getStudentTable().getSelectionModel().getSelectedItem();

		String fName = workspace.getfNameTF().getText();
		String lName = workspace.getlNameTF().getText();
		String team = workspace.getTeamCB().getSelectionModel().getSelectedItem().getName();
		String role = workspace.getRoles().getSelectionModel().getSelectedItem();

		if (selected == null)
			app.jtps.addTransaction(new AddStudentTransaction(app, new Student(fName, lName, team, role)));
		else
			app.jtps.addTransaction(new UpdateStudentTransaction(app, new Student(fName, lName, team, role), selected));
		workspace.getClearStudent().fire();
	}

	public void handleAddTeam() {
		ProjectPane workspace = ((CSGWorkspace) app.getWorkspaceComponent()).getProjectPane();
		ProjectData data = ((CSGData) app.getDataComponent()).getProjectData();
		Team selected = workspace.getTeamTable().getSelectionModel().getSelectedItem();

		String name = workspace.getNameTF().getText();
		String color = toRGBCode(workspace.getColorPicker().getValue());
		String tColor = toRGBCode(workspace.getTextColor().getValue());
		String link = workspace.getLinkTF().getText();

		if (!data.containsTeam(name)) {
			if (selected == null)
				app.jtps.addTransaction(new AddTeamTransaction(app, new Team(name, color, tColor, link)));
			else
				app.jtps.addTransaction(new UpdateTeamTransaction(app, new Team(name, color, tColor, link), selected));
			workspace.getClearButton().fire();
		} else{
			PropertiesManager props = PropertiesManager.getPropertiesManager();
			AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
			dialog.show(props.getProperty(CSGAppProp.P_UNIQUE_TEAM_TITLE), props.getProperty(CSGAppProp.P_UNIQUE_TEAM));
		}
	}

	public void handleStudentTableClick() {
		ProjectPane workspace = ((CSGWorkspace) app.getWorkspaceComponent()).getProjectPane();
		ProjectData data = ((CSGData) app.getDataComponent()).getProjectData();
		Student selected = workspace.getStudentTable().getSelectionModel().getSelectedItem();

		if (selected != null) {
			workspace.getfNameTF().setText(selected.getFName());
			workspace.getlNameTF().setText(selected.getLName());
			workspace.getTeamCB().getSelectionModel().select(data.getTeam(selected.getTeam()));
			workspace.getRoles().getSelectionModel().select(selected.getRole());
		}
	}

	public void handleTeamTableClick() {
		ProjectPane workspace = ((CSGWorkspace) app.getWorkspaceComponent()).getProjectPane();
		Team selected = workspace.getTeamTable().getSelectionModel().getSelectedItem();

		if (selected != null) {
			workspace.getNameTF().setText(selected.getName());
			workspace.getLinkTF().setText(selected.getLink());
			workspace.getColorPicker().setValue(Color.web(selected.getColor()));
			workspace.getTextColor().setValue(Color.web(selected.getTColor()));
		}
	}

	public static String toRGBCode(Color color) {
		return String.format("#%02X%02X%02X",
				(int) (color.getRed() * 255),
				(int) (color.getGreen() * 255),
				(int) (color.getBlue() * 255));
	}
}
