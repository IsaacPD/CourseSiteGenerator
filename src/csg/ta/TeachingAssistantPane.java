package csg.ta;

import csg.CSGApp;
import csg.CSGAppProp;
import csg.data.CSGData;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;

import java.util.HashMap;

public class TeachingAssistantPane extends HBox{
	CSGApp app;
	TAController controller;
	TableView<TeachingAssistant> taTable;
	TableColumn<TeachingAssistant, String> nameColumn, emailColumn;
	TableColumn<TeachingAssistant, CheckBox>  ugColumn;
	Button remove, add, clear;
	ComboBox<String> startTime, endTime;
	TextField nameTF, emailTF;
	Label taLabel, ohGridLabel, startLabel, endLabel;
	HBox taHeader, ohHeader, userInput;
	VBox taPane, ohPane;
	GridPane ohGrid;
	HashMap<String, Pane> ohGridTimeHeaderPanes;
	HashMap<String, Label> ohGridTimeHeaderLabels;
	HashMap<String, Pane> ohGridDayHeaderPanes;
	HashMap<String, Label> ohGridDayHeaderLabels;
	HashMap<String, Pane> ohGridTimeCellPanes;
	HashMap<String, Label> ohGridTimeCellLabels;
	HashMap<String, Pane> ohGridTACellPanes;
	HashMap<String, Label> ohGridTACellLabels;

	public TeachingAssistantPane(CSGApp initApp){
		app = initApp;

		PropertiesManager props = PropertiesManager.getPropertiesManager();

		taPane = new VBox();
		ohPane = new VBox();

		taHeader = new HBox();
		String taHeaderText = props.getProperty(CSGAppProp.TAS_HEADER_TEXT.toString());
		taLabel = new Label(taHeaderText);
		remove = new Button();
		Image buttonImage = new Image(props.getProperty(CSGAppProp.REMOVE_ICON));
		remove.setGraphic(new ImageView(buttonImage));
		Tooltip tooltip = new  Tooltip(props.getProperty(CSGAppProp.REMOVE_TOOLTIP));
		remove.setTooltip(tooltip);

		taHeader.getChildren().add(remove);
		taHeader.getChildren().add(taLabel);

		// MAKE THE TABLE AND SETUP THE DATA MODEL
		taTable = new TableView<>();
		taTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		CSGData data = (CSGData) app.getDataComponent();
		ObservableList<TeachingAssistant> tableData = data.getTeachingAssistants();
		taTable.setItems(tableData);
		String nameColumnText = props.getProperty(CSGAppProp.NAME_COLUMN_TEXT.toString());
		String emailColumnText = props.getProperty(CSGAppProp.EMAIL_COLUMN_TEXT.toString());
		nameColumn = new TableColumn<>(nameColumnText);
		emailColumn = new TableColumn<>(emailColumnText);
		nameColumn.setCellValueFactory(
				new PropertyValueFactory<>("name")
		);
		emailColumn.setCellValueFactory(
				new PropertyValueFactory<>("email")
		);
		taTable.getColumns().add(nameColumn);
		taTable.getColumns().add(emailColumn);

		// ADD BOX FOR ADDING A TA
		String namePromptText = props.getProperty(CSGAppProp.NAME_PROMPT_TEXT.toString());
		String emailPromptText = props.getProperty(CSGAppProp.EMAIL_PROMPT_TEXT.toString());
		String addButtonText = props.getProperty(CSGAppProp.ADD_BUTTON_TEXT.toString());
		emailTF = new TextField();
		emailTF.setPromptText(emailPromptText);
		nameTF = new TextField();
		nameTF.setPromptText(namePromptText);
		add = new Button(addButtonText);
		clear = new Button("CLEAR");
		userInput = new HBox();
		emailTF.prefWidthProperty().bind(userInput.widthProperty().multiply(.4));
		nameTF.prefWidthProperty().bind(userInput.widthProperty().multiply(.4));
		add.prefWidthProperty().bind(userInput.widthProperty().multiply(.1));
		clear.prefWidthProperty().bind(userInput.widthProperty().multiply(.1));
		userInput.getChildren().add(nameTF);
		userInput.getChildren().add(emailTF);
		userInput.getChildren().add(add);
		userInput.getChildren().add(clear);

		// INIT THE HEADER ON THE RIGHT
		ohHeader = new HBox();
		String ohGridText = props.getProperty(CSGAppProp.OFFICE_HOURS_SUBHEADER.toString());
		ohGridLabel = new Label(ohGridText);
		ohHeader.getChildren().add(ohGridLabel);

		// THESE WILL STORE PANES AND LABELS FOR OUR OFFICE HOURS GRID
		ohGrid = new GridPane();
		ohGridTimeHeaderPanes = new HashMap<>();
		ohGridTimeHeaderLabels = new HashMap<>();
		ohGridDayHeaderPanes = new HashMap<>();
		ohGridDayHeaderLabels = new HashMap<>();
		ohGridTimeCellPanes = new HashMap<>();
		ohGridTimeCellLabels = new HashMap<>();
		ohGridTACellPanes = new HashMap<>();
		ohGridTACellLabels = new HashMap<>();

		// ORGANIZE THE COMBO BOXES
		startTime = new ComboBox<>(data.getTimes());
		endTime = new ComboBox<>(data.getTimes());
		startLabel = new Label("Start Time:");
		endLabel = new Label("End Time:  ");
		ohHeader.getChildren().add(startLabel);
		ohHeader.getChildren().add(startTime);
		ohHeader.getChildren().add(endLabel);
		ohHeader.getChildren().add(endTime);

		// FILL THE OFFICE HOURS AND TEACHING ASSISTANT PANES
		taPane.getChildren().add(taHeader);
		taPane.getChildren().add(taTable);
		taPane.getChildren().add(userInput);
		ohPane.getChildren().add(ohHeader);
		ohPane.getChildren().add(ohGrid);

		this.getChildren().add(taPane);
		this.getChildren().add(ohPane);
	}
}
