package csg.schedule;


import java.time.LocalDate;

import csg.CSGApp;
import csg.CSGAppProp;
import csg.data.CSGData;
import csg.data.ScheduleData;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;

public class SchedulePane extends VBox {
	Label header, calendarL, start, end, itemL;
	HBox inputHead;
	VBox calendar, input;
	TableView table;
	Label addL, typeL, dateL, timeL, titleL, topicL, linkL, criteriaL;
	HBox add;
	GridPane calendarIn, scheduleIn;
	ComboBox<String> typeCB;
	DatePicker startDate, endDate, inDate;
	TextField timeTF, titleTF, linkTF, criteriaTF, topicTF;
	Button addButton, clearButton, removeButton;
	TableColumn<ScheduleItem, String> typeCol, dateCol, titleCol, topicCol;
	ScheduleController controller;

	CSGApp app;

	public SchedulePane(CSGApp initApp) {
		app = initApp;

		PropertiesManager props = PropertiesManager.getPropertiesManager();

		startDate = new DatePicker(LocalDate.now());
		endDate = new DatePicker(LocalDate.now());
		inDate = new DatePicker(LocalDate.now());

		header = new Label(props.getProperty(CSGAppProp.S_TITLE));
		calendarL = new Label(props.getProperty(CSGAppProp.S_CALENDAR_BOUNDS));
		start = new Label(props.getProperty(CSGAppProp.S_CAL_START_TEXT) + ":");
		end = new Label(props.getProperty(CSGAppProp.S_CAL_END_TEXT) + ":");

		calendar = new VBox();
		calendarIn = new GridPane();
		calendarIn.addRow(0, start, startDate, new Pane(), end, endDate);
		calendar.getChildren().addAll(calendarL, calendarIn);

		ScheduleData sData = ((CSGData) app.getDataComponent()).getScheduleData();
		itemL = new Label(props.getProperty(CSGAppProp.S_ITEMS_TEXT));
		table = new TableView(sData.getSchedules());
		addL = new Label(props.getProperty(CSGAppProp.S_ADD_TEXT));
		typeL = new Label(props.getProperty(CSGAppProp.S_TYPE_TEXT) + ":");
		typeCB = new ComboBox<>(sData.getTypes());
		dateL = new Label(props.getProperty(CSGAppProp.S_DATE_TEXT) + ":");
		timeL = new Label(props.getProperty(CSGAppProp.S_TIME_TEXT) + ":");
		timeTF = new TextField();
		titleL = new Label(props.getProperty(CSGAppProp.S_TITLE_TEXT) + ":");
		titleTF = new TextField();
		topicL = new Label(props.getProperty(CSGAppProp.S_TOPIC_TEXT) + ":");
		topicTF = new TextField();
		linkL = new Label(props.getProperty(CSGAppProp.LINK_TEXT) + ":");
		linkTF = new TextField();
		criteriaL = new Label(props.getProperty(CSGAppProp.S_CRITERIA_TEXT) + ":");
		criteriaTF = new TextField();

		dateCol = new TableColumn<>(props.getProperty(CSGAppProp.S_DATE_TEXT));
		dateCol.setCellValueFactory(
				new PropertyValueFactory<>("date")
		);

		typeCol = new TableColumn<>(props.getProperty(CSGAppProp.S_TYPE_TEXT));
		typeCol.setCellValueFactory(
				new PropertyValueFactory<>("type")
		);

		titleCol = new TableColumn<>(props.getProperty(CSGAppProp.S_TITLE_TEXT));
		titleCol.setCellValueFactory(
				new PropertyValueFactory<>("title")
		);

		topicCol = new TableColumn<>(props.getProperty(CSGAppProp.S_TOPIC_TEXT));
		topicCol.setCellValueFactory(
				new PropertyValueFactory<>("topic")
		);
		table.getColumns().addAll(typeCol, dateCol, titleCol, topicCol);

		inputHead = new HBox();
		scheduleIn = new GridPane();
		add = new HBox();
		inputHead.getChildren().add(itemL);
		removeButton = app.getGUI().initChildButton(inputHead, CSGAppProp.REMOVE_ICON.toString(),
				CSGAppProp.REMOVE_TOOLTIP.toString(), false);
		add.getChildren().add(addL);

		addButton = new Button(props.getProperty(CSGAppProp.ADD_UP_TEXT));
		clearButton = new Button(props.getProperty(CSGAppProp.CLEAR_TEXT));
		scheduleIn.addColumn(0, typeL, dateL, timeL, titleL, topicL, linkL, criteriaL, addButton);
		scheduleIn.addColumn(1, typeCB, inDate, timeTF, titleTF, topicTF, linkTF, criteriaTF, clearButton);

		input = new VBox();
		input.getChildren().addAll(inputHead, table, add, scheduleIn);

		this.getChildren().addAll(header, calendar, input);
	}

	public VBox getCalendar() {
		return calendar;
	}

	public VBox getInput() {
		return input;
	}

	public Label getHeader() {
		return header;
	}

	public Label getCalendarL() {
		return calendarL;
	}

	public Label getItemL() {
		return itemL;
	}

	public Label getAddL() {
		return addL;
	}

	public GridPane getCalendarIn() {
		return calendarIn;
	}

	public GridPane getScheduleIn() {
		return scheduleIn;
	}

	public HBox getInputHead(){
		return inputHead;
	}
}

