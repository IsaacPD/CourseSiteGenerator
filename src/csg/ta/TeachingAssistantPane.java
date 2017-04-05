package csg.ta;

import csg.CSGApp;
import csg.CSGAppProp;
import csg.data.CSGData;
import csg.data.TAData;
import csg.style.CSGStyle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;

import java.util.ArrayList;
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

		taHeader.getChildren().add(taLabel);
		taHeader.getChildren().add(remove);

		// MAKE THE TABLE AND SETUP THE DATA MODEL
		taTable = new TableView<>();
		taTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		TAData data = ((CSGData) app.getDataComponent()).getTAData();
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
		clear = new Button(props.getProperty(CSGAppProp.CLEAR_TEXT));
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

		controller = new TAController(app);

		taTable.setOnMouseClicked(e ->{
			controller.handleTableClick();
		});

		// CONTROLS FOR ADDING TAs
		clear.setOnAction(e->{
			add.setText(props.getProperty(CSGAppProp.ADD_BUTTON_TEXT.toString()));
			emailTF.clear();
			nameTF.clear();
			nameTF.requestFocus();
		});
		nameTF.setOnAction(e -> {
			controller.handleAddTA();
		});
		emailTF.setOnAction(e -> {
			controller.handleAddTA();
		});
		add.setOnAction(e -> {
			controller.handleAddTA();
		});

		remove.setOnAction(e ->{
			controller.handleRemoveTA();
		});

		// CONTROLS FOR KEY PRESSES
		this.setOnKeyPressed(e -> {
			controller.handleKeyPress(e);
		});

		// CONTROLS FOR COMBOBOX
		startTime.setOnAction(e -> {
			controller.handleComboBox();
		});

		endTime.setOnAction(e -> {
			controller.handleComboBox();
		});
	}

	public TableView getTaTable() {
		return taTable;
	}

	public Button getRemove() {
		return remove;
	}

	public Button getAdd() {
		return add;
	}

	public Button getClear() {
		return clear;
	}

	public ComboBox<String> getStartTime() {
		return startTime;
	}

	public ComboBox<String> getEndTime() {
		return endTime;
	}

	public TextField getNameTF() {
		return nameTF;
	}

	public TextField getEmailTF() {
		return emailTF;
	}

	public Label getTaLabel() {
		return taLabel;
	}

	public Label getOfficeHoursGridLabel() {
		return ohGridLabel;
	}

	public Label getStartLabel() {
		return startLabel;
	}

	public Label getEndLabel() {
		return endLabel;
	}

	public HBox getTaHeader() {
		return taHeader;
	}

	public HBox getOfficeHoursHeader() {
		return ohHeader;
	}

	public HBox getUserInput() {
		return userInput;
	}

	public VBox getTaPane() {
		return taPane;
	}

	public VBox getOfficeHoursPane() {
		return ohPane;
	}

	public GridPane getOfficeHoursGrid() {
		return ohGrid;
	}

	public HashMap<String, Pane> getOfficeHoursGridTimeHeaderPanes() {
		return ohGridTimeHeaderPanes;
	}

	public HashMap<String, Label> getOfficeHoursGridTimeHeaderLabels() {
		return ohGridTimeHeaderLabels;
	}

	public HashMap<String, Pane> getOfficeHoursGridDayHeaderPanes() {
		return ohGridDayHeaderPanes;
	}

	public HashMap<String, Label> getOfficeHoursGridDayHeaderLabels() {
		return ohGridDayHeaderLabels;
	}

	public HashMap<String, Pane> getOfficeHoursGridTimeCellPanes() {
		return ohGridTimeCellPanes;
	}

	public HashMap<String, Label> getOfficeHoursGridTimeCellLabels() {
		return ohGridTimeCellLabels;
	}

	public HashMap<String, Pane> getOfficeHoursGridTACellPanes() {
		return ohGridTACellPanes;
	}

	public HashMap<String, Label> getOfficeHoursGridTACellLabels() {
		return ohGridTACellLabels;
	}

	public String buildCellKey(int col, int row) {
		return "" + col + "_" + row;
	}

	public String buildCellText(int militaryHour, String minutes) {
		// FIRST THE START AND END CELLS
		int hour = militaryHour;
		if (hour > 12) {
			hour -= 12;
		}
		String cellText = "" + hour + ":" + minutes;
		if (militaryHour < 12) {
			cellText += "am";
		} else {
			cellText += "pm";
		}
		return cellText;
	}

	public void resetWorkspace() {
		// CLEAR OUT THE GRID PANE
		ohGrid.getChildren().clear();

		// AND THEN ALL THE GRID PANES AND LABELS
		ohGridTimeHeaderPanes.clear();
		ohGridTimeHeaderLabels.clear();
		ohGridDayHeaderPanes.clear();
		ohGridDayHeaderLabels.clear();
		ohGridTimeCellPanes.clear();
		ohGridTimeCellLabels.clear();
		ohGridTACellPanes.clear();
		ohGridTACellLabels.clear();

		// CLEAR TRANSACTIONS AND TEXT FIELD
		app.jtps.clearTransactions();
		clear.fireEvent(new ActionEvent());
	}

	public void reloadWorkspace(){
		TAData taData = ((CSGData) app.getDataComponent()).getTAData();
		reloadOfficeHoursGrid(taData);
	}

	public void reloadOfficeHoursGrid(TAData taData) {
		ArrayList<String> gridHeaders = taData.getGridHeaders();

		// ADD THE TIME HEADERS
		for (int i = 0; i < 2; i++) {
			addCellToGrid(taData, ohGridTimeHeaderPanes, ohGridTimeHeaderLabels, i, 0);
			taData.getCellTextProperty(i, 0).set(gridHeaders.get(i));
		}

		// THEN THE DAY OF WEEK HEADERS
		for (int i = 2; i < 7; i++) {
			addCellToGrid(taData, ohGridDayHeaderPanes, ohGridDayHeaderLabels, i, 0);
			taData.getCellTextProperty(i, 0).set(gridHeaders.get(i));
		}

		// THEN THE TIME AND TA CELLS
		int row = 1;
		for (int i = taData.getStartHour(); i < taData.getEndHour(); i++) {
			// START TIME COLUMN
			int col = 0;
			addCellToGrid(taData, ohGridTimeCellPanes, ohGridTimeCellLabels, col, row);
			taData.getCellTextProperty(col, row).set(buildCellText(i, "00"));
			addCellToGrid(taData, ohGridTimeCellPanes, ohGridTimeCellLabels, col, row + 1);
			taData.getCellTextProperty(col, row + 1).set(buildCellText(i, "30"));

			// END TIME COLUMN
			col++;
			int endHour = i;
			addCellToGrid(taData, ohGridTimeCellPanes, ohGridTimeCellLabels, col, row);
			taData.getCellTextProperty(col, row).set(buildCellText(endHour, "30"));
			addCellToGrid(taData, ohGridTimeCellPanes, ohGridTimeCellLabels, col, row + 1);
			taData.getCellTextProperty(col, row + 1).set(buildCellText(endHour + 1, "00"));
			col++;

			// AND NOW ALL THE TA TOGGLE CELLS
			while (col < 7) {
				addCellToGrid(taData, ohGridTACellPanes, ohGridTACellLabels, col, row);
				addCellToGrid(taData, ohGridTACellPanes, ohGridTACellLabels, col, row + 1);
				col++;
			}
			row += 2;
		}

		// CONTROLS FOR TOGGLING TA OFFICE HOURS
		for (Pane p : ohGridTACellPanes.values()) {
			p.setOnMouseClicked(e -> {
				controller.handleCellToggle((Pane) e.getSource());
			});

			p.setOnMouseEntered(e -> {
				controller.handleCellHover((Pane) e.getSource());
			});

			p.setOnMouseExited(e -> {
				controller.handleCellExit((Pane) e.getSource());
			});
		}

		// AND MAKE SURE ALL THE COMPONENTS HAVE THE PROPER STYLE
		CSGStyle taStyle = (CSGStyle) app.getStyleComponent();
		taStyle.initTAOfficeHoursGridStyle();
	}

	public void reloadTimes(TAData taData) {
		ohGrid.getChildren().clear();

		ohGridTimeCellPanes.clear();
		ohGridTimeCellLabels.clear();
		ohGridTACellPanes.clear();
		ohGridTACellLabels.clear();

		// THEN THE TIME AND TA CELLS
		int row = 1;
		for (int i = taData.getStartHour(); i < taData.getEndHour(); i++) {
			// START TIME COLUMN
			int col = 0;
			addCellToGrid(taData, ohGridTimeCellPanes, ohGridTimeCellLabels, col, row);
			taData.getCellTextProperty(col, row).set(buildCellText(i, "00"));
			addCellToGrid(taData, ohGridTimeCellPanes, ohGridTimeCellLabels, col, row + 1);
			taData.getCellTextProperty(col, row + 1).set(buildCellText(i, "30"));

			// END TIME COLUMN
			col++;
			int endHour = i;
			addCellToGrid(taData, ohGridTimeCellPanes, ohGridTimeCellLabels, col, row);
			taData.getCellTextProperty(col, row).set(buildCellText(endHour, "30"));
			addCellToGrid(taData, ohGridTimeCellPanes, ohGridTimeCellLabels, col, row + 1);
			taData.getCellTextProperty(col, row + 1).set(buildCellText(endHour + 1, "00"));
			col++;

			// AND NOW ALL THE TA TOGGLE CELLS
			while (col < 7) {
				addCellToGrid(taData, ohGridTACellPanes, ohGridTACellLabels, col, row);
				addCellToGrid(taData, ohGridTACellPanes, ohGridTACellLabels, col, row + 1);
				col++;
			}
			row += 2;
		}

		// CONTROLS FOR TOGGLING TA OFFICE HOURS
		for (Pane p : ohGridTACellPanes.values()) {
			p.setOnMouseClicked(e -> {
				controller.handleCellToggle((Pane) e.getSource());
			});

			p.setOnMouseEntered(e -> {
				controller.handleCellHover((Pane) e.getSource());
			});

			p.setOnMouseExited(e -> {
				controller.handleCellExit((Pane) e.getSource());
			});
		}

		// AND MAKE SURE ALL THE COMPONENTS HAVE THE PROPER STYLE
		CSGStyle taStyle = (CSGStyle) app.getStyleComponent();
		taStyle.initTAOfficeHoursGridStyle();
	}

	public void addCellToGrid(TAData dataComponent, HashMap<String, Pane> panes, HashMap<String, Label> labels, int col, int row) {
		// MAKE THE LABEL IN A PANE
		Label cellLabel = new Label("");
		HBox cellPane = new HBox();
		cellPane.setAlignment(Pos.CENTER);
		cellPane.getChildren().add(cellLabel);

		// BUILD A KEY TO EASILY UNIQUELY IDENTIFY THE CELL
		String cellKey = dataComponent.getCellKey(col, row);
		cellPane.setId(cellKey);
		cellLabel.setId(cellKey);

		// NOW PUT THE CELL IN THE WORKSPACE GRID
		ohGrid.add(cellPane, col, row);

		// AND ALSO KEEP IN IN CASE WE NEED TO STYLIZE IT
		panes.put(cellKey, cellPane);
		labels.put(cellKey, cellLabel);

		// AND FINALLY, GIVE THE TEXT PROPERTY TO THE DATA MANAGER
		// SO IT CAN MANAGE ALL CHANGES
		dataComponent.setCellProperty(col, row, cellLabel.textProperty());
	}
}
