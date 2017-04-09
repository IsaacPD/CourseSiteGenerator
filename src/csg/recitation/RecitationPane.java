package csg.recitation;

import csg.CSGApp;
import csg.CSGAppProp;
import csg.data.CSGData;
import csg.data.RecitationData;
import csg.ta.TeachingAssistant;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;

public class RecitationPane extends VBox {
	CSGApp app;
	RecitationController controller;

	Label header, addEditL, sectionL, instructor, dayTime, location, supervisingLabel1, supervisingLabel2;
	Button remove, addUp, clear;
	TableView<Recitation> table;
	TextField sectionTF, instructorTF, dayTimeTF, locationTF;
	ComboBox<TeachingAssistant> comboBox1, comboBox2;
	VBox inputBox;
	HBox headerBox, section, instrBox, dayBox, locationBox, supTA1Box, supTA2Box, buttons;
	TableColumn<Recitation, String> sectionCol, instructorCol,
			dayTimeCol, locationCol, ta1Col, ta2Col;


	public RecitationPane(CSGApp initApp) {
		app = initApp;

		PropertiesManager props = PropertiesManager.getPropertiesManager();
		RecitationData data = ((CSGData) app.getDataComponent()).getRecitationData();

		header = new Label(props.getProperty(CSGAppProp.R_HEADER_TEXT));
		headerBox = new HBox();
		headerBox.getChildren().add(header);
		remove = app.getGUI().initChildButton(headerBox, CSGAppProp.REMOVE_ICON.toString(),
				props.getProperty(CSGAppProp.REMOVE_TOOLTIP), false);

		table = new TableView<>(data.getRecitations());
		sectionCol = new TableColumn<>(props.getProperty(CSGAppProp.R_SECTION_TEXT));
		sectionCol.setCellValueFactory(
				new PropertyValueFactory<>("section")
		);
		instructorCol = new TableColumn<>(props.getProperty(CSGAppProp.R_INSTRUCTOR_TEXT));
		instructorCol.setCellValueFactory(
				new PropertyValueFactory<>("instructor")
		);
		dayTimeCol = new TableColumn<>(props.getProperty(CSGAppProp.R_DAY_TIME_TEXT));
		dayTimeCol.setCellValueFactory(
				new PropertyValueFactory<>("day")
		);
		locationCol = new TableColumn<>(props.getProperty(CSGAppProp.R_LOCATION_TEXT));
		locationCol.setCellValueFactory(
				new PropertyValueFactory<>("location")
		);
		ta1Col = new TableColumn<>(props.getProperty(CSGAppProp.R_TA_TEXT));
		ta1Col.setCellValueFactory(
				new PropertyValueFactory<>("ta1")
		);
		ta2Col = new TableColumn<>(props.getProperty(CSGAppProp.R_TA_TEXT));
		ta2Col.setCellValueFactory(
				new PropertyValueFactory<>("ta2")
		);
		table.getColumns().addAll(sectionCol, instructorCol, dayTimeCol, locationCol, ta1Col, ta2Col);

		inputBox = new VBox();
		addEditL = new Label(props.getProperty(CSGAppProp.ADD_EDIT_TEXT));
		sectionL = new Label(props.getProperty(CSGAppProp.R_SECTION_TEXT) + ":");
		sectionTF = new TextField();
		instructor = new Label(props.getProperty(CSGAppProp.R_INSTRUCTOR_TEXT) + ":");
		instructorTF = new TextField();
		dayTime = new Label(props.getProperty(CSGAppProp.R_DAY_TIME_TEXT) + ":");
		dayTimeTF = new TextField();
		location = new Label(props.getProperty(CSGAppProp.R_LOCATION_TEXT) + ":");
		locationTF = new TextField();
		supervisingLabel1 = new Label(props.getProperty(CSGAppProp.R_SUP_TA_TEXT) + ":");
		supervisingLabel2 = new Label(props.getProperty(CSGAppProp.R_SUP_TA_TEXT) + ":");
		comboBox1 = new ComboBox<>(((CSGData) app.getDataComponent()).getTAData().getTeachingAssistants());
		comboBox2 = new ComboBox<>(((CSGData) app.getDataComponent()).getTAData().getTeachingAssistants());
		addUp = new Button(props.getProperty(CSGAppProp.ADD_UP_TEXT));
		clear = new Button(props.getProperty(CSGAppProp.CLEAR_TEXT));

		section = new HBox();
		section.getChildren().addAll(sectionL, sectionTF);
		instrBox = new HBox();
		instrBox.getChildren().addAll(instructor, instructorTF);
		dayBox = new HBox();
		dayBox.getChildren().addAll(dayTime, dayTimeTF);
		locationBox = new HBox();
		locationBox.getChildren().addAll(location, locationTF);
		supTA1Box = new HBox();
		supTA1Box.getChildren().addAll(supervisingLabel1, comboBox1);
		supTA2Box = new HBox();
		supTA2Box.getChildren().addAll(supervisingLabel2, comboBox2);
		buttons = new HBox();
		buttons.getChildren().addAll(addUp, clear);
		inputBox.getChildren().addAll(addEditL, section, instrBox, dayBox, locationBox,
				supTA1Box, supTA2Box, buttons);

		this.getChildren().addAll(headerBox, table, inputBox);
	}

	public Label getHeader() {
		return header;
	}

	public Label getAddEditL() {
		return addEditL;
	}

	public Label getSectionL() {
		return sectionL;
	}

	public Label getInstructor() {
		return instructor;
	}

	public Label getDayTime() {
		return dayTime;
	}

	public Label getLocation() {
		return location;
	}

	public Label getSupervisingLabel1() {
		return supervisingLabel1;
	}

	public Label getSupervisingLabel2() {
		return supervisingLabel2;
	}

	public Button getRemove() {
		return remove;
	}

	public Button getAddUp() {
		return addUp;
	}

	public Button getClear() {
		return clear;
	}

	public TableView<Recitation> getTable() {
		return table;
	}

	public TextField getSectionTF() {
		return sectionTF;
	}

	public TextField getInstructorTF() {
		return instructorTF;
	}

	public TextField getDayTimeTF() {
		return dayTimeTF;
	}

	public TextField getLocationTF() {
		return locationTF;
	}

	public ComboBox<TeachingAssistant> getComboBox1() {
		return comboBox1;
	}

	public ComboBox<TeachingAssistant> getComboBox2() {
		return comboBox2;
	}

	public VBox getInputBox() {
		return inputBox;
	}

	public HBox getHeaderBox() {
		return headerBox;
	}

	public HBox getSection() {
		return section;
	}

	public HBox getInstrBox() {
		return instrBox;
	}

	public HBox getDayBox() {
		return dayBox;
	}

	public HBox getLocationBox() {
		return locationBox;
	}

	public HBox getSupTA1Box() {
		return supTA1Box;
	}

	public HBox getSupTA2Box() {
		return supTA2Box;
	}

	public HBox getButtons() {
		return buttons;
	}

	public TableColumn<Recitation, String> getSectionCol() {
		return sectionCol;
	}

	public TableColumn<Recitation, String> getInstructorCol() {
		return instructorCol;
	}

	public TableColumn<Recitation, String> getDayTimeCol() {
		return dayTimeCol;
	}

	public TableColumn<Recitation, String> getLocationCol() {
		return locationCol;
	}

	public TableColumn<Recitation, String> getTa1Col() {
		return ta1Col;
	}

	public TableColumn<Recitation, String> getTa2Col() {
		return ta2Col;
	}
}
