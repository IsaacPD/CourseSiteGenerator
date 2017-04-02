package csg.ta;

import csg.CSGApp;
import javafx.scene.control.*;
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
	GridPane officeHoursGrid;
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

		taHeader
	}
}
