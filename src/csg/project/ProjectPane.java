package csg.project;

import csg.CSGApp;
import csg.data.CSGData;
import csg.data.ProjectData;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;

import static csg.CSGAppProp.*;

public class ProjectPane extends VBox {
	Label header, teamsL, addEditL, nameL, colorL, textColorL, linkL;
	TableView<Team> teamTable;
	TableView<Student> studentTable;
	Button addButton, clearButton, removeTeam, studentRemove, addStudent, clearStudent;
	TextField nameTF, linkTF, fNameTF, lNameTF, roleTF;
	ColorPicker colorPicker, textColor;
	VBox team, students;
	HBox teamHead, studentHead, nameRow, colorRow, linkRow, teamButtonRow;
	HBox fNameRow, lNameRow, teamRow, roleRow, studentButtonsRow;
	Label sAddEdit, fNameL, lNameL, student, teamL, roleL;
	ComboBox<String> teamCB;
	TableColumn<Team, String> nameCol, colorCol, textColorCol, linkCol;
	TableColumn<Student, String> fNameCol, lNameCol, roleCol, teamCol;

	CSGApp app;
	ProjectController controller;

	public ProjectPane(CSGApp initApp) {
		app = initApp;

		ProjectData data = ((CSGData) app.getDataComponent()).getProjectData();
		PropertiesManager props = PropertiesManager.getPropertiesManager();

		header = new Label(props.getProperty(P_HEADER_TEXT));
		teamsL = new Label(props.getProperty(P_TEAMS_TEXT));
		addEditL = new Label(props.getProperty(ADD_EDIT_TEXT));
		nameL = new Label(props.getProperty(P_NAME_TEXT) + ":");
		nameTF = new TextField();
		colorL = new Label(props.getProperty(P_COLOR_TEXT) + ":");
		colorPicker = new ColorPicker();
		textColorL = new Label(props.getProperty(P_TEXT_COLOR_TEXT) + ":");
		textColor = new ColorPicker();
		linkL = new Label(props.getProperty(LINK_TEXT) + ":");
		linkTF = new TextField();
		addButton = new Button(props.getProperty(ADD_UP_TEXT));
		clearButton = new Button(props.getProperty(CLEAR_TEXT));

		teamTable = new TableView<>(data.getTeams());
		nameCol = new TableColumn<>(props.getProperty(P_NAME_TEXT));
		nameCol.setCellValueFactory(
				new PropertyValueFactory<>("name")
		);
		colorCol = new TableColumn<>(props.getProperty(P_COLOR_HEX_TEXT));
		colorCol.setCellValueFactory(
				new PropertyValueFactory<>("color")
		);
		textColorCol = new TableColumn<>(props.getProperty(P_TEXT_COLOR_HEX_TEXT));
		textColorCol.setCellValueFactory(
				new PropertyValueFactory<>("tColor")
		);
		linkCol = new TableColumn<>(props.getProperty(LINK_TEXT));
		linkCol.setCellValueFactory(
				new PropertyValueFactory<>("link")
		);
		teamTable.getColumns().addAll(nameCol, colorCol, textColorCol, linkCol);

		teamHead = new HBox();
		teamHead.getChildren().add(teamsL);
		removeTeam = app.getGUI().initChildButton(teamHead, REMOVE_ICON.toString(),
				REMOVE_TOOLTIP.toString(), false);
		nameRow = new HBox();
		nameRow.getChildren().addAll(nameL, nameTF);
		colorRow = new HBox();
		colorRow.getChildren().addAll(colorL, colorPicker, textColorL, textColor);
		linkRow = new HBox();
		linkRow.getChildren().addAll(linkL, linkTF);
		teamButtonRow = new HBox();
		teamButtonRow.getChildren().addAll(addButton, clearButton);

		team = new VBox();
		team.getChildren().addAll(teamHead, teamTable, addEditL, nameRow, colorRow, linkRow, teamButtonRow);

		student = new Label(props.getProperty(P_STUDENTS_TEXT));
		studentTable = new TableView<>(data.getStudents());
		fNameCol = new TableColumn<>(props.getProperty(P_FIRST_NAME_TEXT));
		fNameCol.setCellValueFactory(
				new PropertyValueFactory<>("fName")
		);
		lNameCol = new TableColumn<>(props.getProperty(P_LAST_NAME_TEXT));
		lNameCol.setCellValueFactory(
				new PropertyValueFactory<>("lName")
		);
		teamCol = new TableColumn<>(props.getProperty(P_TEAM_TEXT));
		teamCol.setCellValueFactory(
				new PropertyValueFactory<>("team")
		);
		roleCol = new TableColumn<>(props.getProperty(P_ROLE_TEXT));
		roleCol.setCellValueFactory(
				new PropertyValueFactory<>("role")
		);
		studentTable.getColumns().addAll(fNameCol, lNameCol, teamCol, roleCol);

		sAddEdit = new Label(props.getProperty(ADD_EDIT_TEXT));
		fNameL = new Label(props.getProperty(P_FIRST_NAME_TEXT) + ":");
		fNameTF = new TextField();
		lNameL = new Label(props.getProperty(P_LAST_NAME_TEXT) + ":");
		lNameTF = new TextField();
		teamL = new Label(props.getProperty(P_TEAM_TEXT) + ":");
		teamCB = new ComboBox<>(data.getTeamNames());
		roleL = new Label(props.getProperty(P_ROLE_TEXT) + ":");
		roleTF = new TextField();
		addStudent = new Button(props.getProperty(ADD_UP_TEXT));
		clearStudent = new Button(props.getProperty(CLEAR_TEXT));
		studentButtonsRow = new HBox();
		studentButtonsRow.getChildren().addAll(addStudent, clearStudent);

		studentHead = new HBox();
		studentHead.getChildren().add(student);
		studentRemove = app.getGUI().initChildButton(studentHead, REMOVE_ICON.toString(),
				REMOVE_TOOLTIP.toString(), false);
		fNameRow = new HBox();
		fNameRow.getChildren().addAll(fNameL, fNameTF);
		lNameRow = new HBox();
		lNameRow.getChildren().addAll(lNameL, lNameTF);
		teamRow = new HBox();
		teamRow.getChildren().addAll(teamL, teamCB);
		roleRow = new HBox();
		roleRow.getChildren().addAll(roleL, roleTF);

		students = new VBox();
		students.getChildren().addAll(studentHead, studentTable, sAddEdit,
				fNameRow, lNameRow, teamRow, roleRow, studentButtonsRow);

		this.getChildren().addAll(header, team, students);
	}

	public VBox getTeam() {
		return team;
	}

	public VBox getStudents() {
		return students;
	}

	public Label getAddEditL() {
		return addEditL;
	}

	public Label getSAddEdit() {
		return sAddEdit;
	}

	public Label getHeader() {
		return header;
	}

	public Label getTeamsL() {
		return teamsL;
	}

	public Label getStudent() {
		return student;
	}

	public HBox getTeamHead() {
		return teamHead;
	}

	public HBox getStudentHead() {
		return studentHead;
	}

	public HBox getNameRow() {
		return nameRow;
	}

	public HBox getColorRow() {
		return colorRow;
	}

	public HBox getLinkRow() {
		return linkRow;
	}

	public HBox getTeamButtonRow() {
		return teamButtonRow;
	}

	public HBox getfNameRow() {
		return fNameRow;
	}

	public HBox getlNameRow() {
		return lNameRow;
	}

	public HBox getTeamRow() {
		return teamRow;
	}

	public HBox getRoleRow() {
		return roleRow;
	}

	public HBox getStudentButtonsRow() {
		return studentButtonsRow;
	}
}
