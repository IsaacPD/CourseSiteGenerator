package csg;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LocalePickerSingleton extends Stage{
	static LocalePickerSingleton singleton;

	VBox messagePane;
	Scene messageScene;
	Label localeLabel;
	Button okButton;
	Button cancelButton;
	String selection;
	ComboBox<String> locales;

	// CONSTANT CHOICES

	public static final String OK = "OK";
	public static final String LOCALES = "Pick Language: ";
	public static final String CANCEL = "Cancel";

	private LocalePickerSingleton(){}

	public static LocalePickerSingleton getSingleton() {
		if(singleton == null)
			singleton = new LocalePickerSingleton();
		return singleton;
	}

	public void init(Stage primaryStage){
		initModality(Modality.WINDOW_MODAL);
		initOwner(null);

		locales = new ComboBox<>(FXCollections.observableArrayList("ENGLISH", "SPANISH"));

		// LABEL TO DISPLAY THE CUSTOM MESSAGE
		localeLabel = new Label(LOCALES);

		// YES, NO, AND CANCEL BUTTONS
		okButton = new Button(OK);
		cancelButton = new Button(CANCEL);

		// MAKE THE EVENT HANDLER FOR THESE BUTTONS
		EventHandler okCancelHandler = (EventHandler<ActionEvent>) (ActionEvent ae) -> {
			Button sourceButton = (Button)ae.getSource();
			if (sourceButton.getText().equals("OK"))
				selection = locales.getSelectionModel().getSelectedItem();
			else
				selection = "ENGLISH";

			this.hide();
		};

		// AND THEN REGISTER THEM TO RESPOND TO INTERACTIONS
		okButton.setOnAction(okCancelHandler);
		cancelButton.setOnAction(okCancelHandler);

		// NOW ORGANIZE OUR BUTTONS
		HBox buttonBox = new HBox();
		buttonBox.getChildren().add(okButton);
		buttonBox.getChildren().add(cancelButton);

		// NOW ORGANIZE OUR COMBOBOX
		HBox comboPane = new HBox();
		comboPane.getChildren().add(localeLabel);
		comboPane.getChildren().add(locales);

		// WE'LL PUT EVERYTHING HERE
		messagePane = new VBox();
		messagePane.setAlignment(Pos.CENTER);
		messagePane.getChildren().add(comboPane);
		messagePane.getChildren().add(buttonBox);

		// MAKE IT LOOK NICE
		messagePane.setPadding(new Insets(10, 20, 20, 20));
		messagePane.setSpacing(10);

		// AND PUT IT IN THE WINDOW
		messageScene = new Scene(messagePane);
		this.setScene(messageScene);
		this.setTitle("Pick Language");

		showAndWait();
	}

	public String getSelection(){
		return selection;
	}
}
