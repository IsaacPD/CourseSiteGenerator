package csg.schedule;


import java.time.LocalDate;

import csg.CSGApp;
import csg.CSGAppProp;
import csg.data.CSGData;
import csg.data.ScheduleData;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;

public class SchedulePane extends VBox{
	Label header, calendarL, start, end, itemL;
	HBox calendarSelection, inputHead;
	VBox calendar, input;
	TableView table;
	Label addL, typeL, dateL, timeL, titleL, topicL, linkL, criteriaL;
	HBox add, type, date, time, inTitle, topic, link, criteria, buttons;
	ComboBox<String> typeCB;
	DatePicker startDate, endDate, inDate;
	TextField timeTF, titleTF, linkTF, criteriaTF, topicTF;
	Button addButton, clearButton, removeButton;
	TableColumn<ScheduleItem, String> typeCol, dateCol, titleCol, topicCol;
	ScheduleController controller;

	CSGApp app;

	public SchedulePane(CSGApp initApp){
		app = initApp;

		PropertiesManager props = PropertiesManager.getPropertiesManager();

		startDate = new DatePicker(LocalDate.now());
		endDate = new DatePicker(LocalDate.now());
		inDate = new DatePicker(LocalDate.now());

		header = new Label(props.getProperty(CSGAppProp.S_TITLE));
		calendarL = new Label(props.getProperty(CSGAppProp.S_CALENDAR_BOUNDS));
		start = new Label(props.getProperty(CSGAppProp.S_CAL_START_TEXT));
		end = new Label(props.getProperty(CSGAppProp.S_CAL_END_TEXT));

		calendar = new VBox();
		calendarSelection = new HBox();
		calendarSelection.getChildren().addAll(start, startDate, end, endDate);
		calendar.getChildren().addAll(calendarL, calendarSelection);

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
		add = new HBox();
		type = new HBox();
		date = new HBox();
		time = new HBox();
		inTitle = new HBox();
		topic = new HBox();
		link = new HBox();
		criteria = new HBox();
		buttons = new HBox();

		inputHead.getChildren().add(itemL);
                removeButton = app.getGUI().initChildButton(inputHead, CSGAppProp.REMOVE_ICON.toString(),
                        props.getProperty(CSGAppProp.REMOVE_TOOLTIP), false);
		add.getChildren().add(addL);
		type.getChildren().addAll(typeL, typeCB);
		date.getChildren().addAll(dateL, inDate);
		time.getChildren().addAll(timeL, timeTF);
		inTitle.getChildren().addAll(titleL, titleTF);
		topic.getChildren().addAll(topicL, topicTF);
		link.getChildren().addAll(linkL, linkTF);
		criteria.getChildren().addAll(criteriaL, criteriaTF);

		addButton = new Button(props.getProperty(CSGAppProp.ADD_UP_TEXT));
		clearButton = new Button(props.getProperty(CSGAppProp.CLEAR_TEXT));
		buttons.getChildren().addAll(addButton, clearButton);

		input = new VBox();
		input.getChildren().addAll(inputHead, table, add, type,
				date, time, inTitle, topic, link, criteria, buttons);

		this.getChildren().addAll(header, calendar, input);
	}
}

