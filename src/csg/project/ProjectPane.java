package csg.project;

import csg.CSGApp;
import csg.data.CSGData;
import csg.data.ProjectData;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
	HBox teamHead, studentHead;
	GridPane studentIn, teamIn;
	Label sAddEdit, fNameL, lNameL, student, teamL, roleL;
	ComboBox<Team> teamCB;
	ComboBox<String> roles;
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
		colorPicker.setMinHeight(25);
		textColorL = new Label(props.getProperty(P_TEXT_COLOR_TEXT) + ":");
		textColor = new ColorPicker();
		textColor.setMinHeight(25);
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

		teamIn = new GridPane();
		teamIn.addColumn(0, nameL, colorL, linkL, addButton);
		teamIn.addColumn(1, nameTF, colorPicker, linkTF, clearButton);
		teamIn.add(textColorL, 2, 1);
		teamIn.add(textColor, 3, 1);

		teamHead = new HBox();
		teamHead.getChildren().add(teamsL);
		removeTeam = app.getGUI().initChildButton(teamHead, REMOVE_ICON.toString(),
				REMOVE_TOOLTIP.toString(), false);

		team = new VBox();
		team.getChildren().addAll(teamHead, teamTable, addEditL, teamIn);

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
		teamCB = new ComboBox<>(data.getTeams());
		roleL = new Label(props.getProperty(P_ROLE_TEXT) + ":");
		roleTF = new TextField();
		roles = new ComboBox<>(data.getRole());
		addStudent = new Button(props.getProperty(ADD_UP_TEXT));
		clearStudent = new Button(props.getProperty(CLEAR_TEXT));

		studentHead = new HBox();
		studentHead.getChildren().add(student);
		studentRemove = app.getGUI().initChildButton(studentHead, REMOVE_ICON.toString(),
				REMOVE_TOOLTIP.toString(), false);

		studentIn = new GridPane();
		studentIn.addColumn(0, fNameL, lNameL, teamL, roleL, addStudent);
		studentIn.addColumn(1, fNameTF, lNameTF, teamCB, roleTF, clearStudent);
		studentIn.add(roles, 2, 3);

		students = new VBox();
		students.getChildren().addAll(studentHead, studentTable, sAddEdit,
				studentIn);

		this.getChildren().addAll(header, team, students);

		controller = new ProjectController(app);

		removeTeam.setOnAction(e -> controller.handleRemoveTeam());
		studentRemove.setOnAction(e -> controller.handleRemoveStudent());
		addStudent.setOnAction(e -> controller.handleAddStudent());
		addButton.setOnAction(e -> controller.handleAddTeam());
		studentTable.setOnMouseClicked(e -> controller.handleStudentTableClick());
		teamTable.setOnMouseClicked(e -> controller.handleTeamTableClick());
		clearStudent.setOnAction(e -> {
			fNameTF.clear();
			lNameTF.clear();
			teamCB.getSelectionModel().clearSelection();
			roleTF.clear();
			roles.getSelectionModel().clearSelection();
			studentTable.getSelectionModel().clearSelection();
		});
		clearButton.setOnAction(e -> {
			nameTF.clear();
			colorPicker.setValue(Color.WHITE);
			textColor.setValue(Color.WHITE);
			linkTF.clear();
			teamTable.getSelectionModel().clearSelection();
		});
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

	public GridPane getStudentIn() {
		return studentIn;
	}

	public GridPane getTeamIn() {
		return teamIn;
	}

	public TableView<Team> getTeamTable() {
		return teamTable;
	}

	public TableView<Student> getStudentTable() {
		return studentTable;
	}

	public TextField getNameTF() {
		return nameTF;
	}

	public TextField getLinkTF() {
		return linkTF;
	}

	public TextField getfNameTF() {
		return fNameTF;
	}

	public TextField getlNameTF() {
		return lNameTF;
	}

	public TextField getRoleTF() {
		return roleTF;
	}

	public ColorPicker getColorPicker() {
		return colorPicker;
	}

	public ColorPicker getTextColor() {
		return textColor;
	}

	public ComboBox<Team> getTeamCB() {
		return teamCB;
	}

	public Button getClearButton() {
		return clearButton;
	}

	public Button getClearStudent() {
		return clearStudent;
	}

	public void resetWorkspace() {
		clearButton.fire();
		clearStudent.fire();
	}

	public void reloadWorkspace() {
	}

	public ComboBox<String> getRoles() {
		return roles;
	}
}
