package csg.ta;

import csg.CSGApp;
import csg.data.CSGData;
import csg.data.TAData;
import csg.ta.transaction.*;
import csg.workspace.CSGWorkspace;
import djf.ui.AppMessageDialogSingleton;
import djf.ui.AppYesNoCancelDialogSingleton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import properties_manager.PropertiesManager;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static csg.CSGAppProp.*;

public class TAController {
	// THE APP PROVIDES ACCESS TO OTHER COMPONENTS AS NEEDED
	CSGApp app;

	/**
	 * Constructor, note that the app must already be constructed.
	 */
	public TAController(CSGApp initApp) {
		// KEEP THIS FOR LATER
		app = initApp;
	}

	/**
	 * This method responds to when the user requests to add
	 * a new TA via the UI. Note that it must first do some
	 * validation to make sure a unique name and email address
	 * has been provided.
	 */
	public void handleAddTA() {
		// WE'LL NEED THE WORKSPACE TO RETRIEVE THE USER INPUT VALUES
		TeachingAssistantPane workspace = ((CSGWorkspace) app.getWorkspaceComponent()).getTeachingAssistantPane();
		TextField nameTextField = workspace.getNameTF();
		TextField emailTextField = workspace.getEmailTF();
		String name = nameTextField.getText();
		String email = emailTextField.getText();

		EmailValidator boss = new EmailValidator();

		// WE'LL NEED TO ASK THE DATA SOME QUESTIONS TOO
		TAData data = ((CSGData) app.getDataComponent()).getTAData();

		// WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
		PropertiesManager props = PropertiesManager.getPropertiesManager();

		TeachingAssistant ta = workspace.taTable.getSelectionModel().getSelectedItem();

		// DID THE USER NEGLECT TO PROVIDE A TA NAME OR EMAIL?
		if (name.isEmpty()) {
			AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
			dialog.show(props.getProperty(MISSING_TA_NAME_TITLE), props.getProperty(MISSING_TA_NAME_MESSAGE));
		} else if (email.isEmpty()) {
			AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
			dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));
		}

		// IS IT A VALID EMAIL?
		else if (!boss.validate(email)) {
			AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
			dialog.show(props.getProperty(TA_INVALID_EMAIL_TITLE), props.getProperty(TA_INVALID_EMAIL_MESSAGE));
		}

		// DOES A TA ALREADY HAVE THE SAME NAME OR EMAIL?
		else if (data.containsTA(name, email)) {
			AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
			dialog.show(props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_TITLE), props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_MESSAGE));
		}

		// UPDATE TA IF BUTTON IS SET TO DO SO
		else if (ta != null) {

			app.jtps.addTransaction(new UpdateTATransaction(ta, name, email, app));

			// CLEAR THE TEXT FIELDS
			nameTextField.setText("");
			emailTextField.setText("");

			// AND SEND THE CARET BACK TO THE NAME TEXT FIELD FOR EASY DATA ENTRY
			nameTextField.requestFocus();
		}

		// EVERYTHING IS FINE, ADD A NEW TA
		else {
			// ADD THE NEW TA TO THE DATA
			app.jtps.addTransaction(new AddTATransaction(name, email, app));

			// CLEAR THE TEXT FIELDS
			nameTextField.setText("");
			emailTextField.setText("");

			// AND SEND THE CARET BACK TO THE NAME TEXT FIELD FOR EASY DATA ENTRY
			nameTextField.requestFocus();
		}
	}

	/**
	 * This function provides a response for when the user presses
	 * a key.
	 *
	 * @param event The key that was pressed.
	 */
	public void handleKeyPress(KeyEvent event) {
		if (event.getCode() == KeyCode.DELETE) {
			handleRemoveTA();
		}
	}

	public void handleRemoveTA() {
		TeachingAssistantPane workspace = ((CSGWorkspace) app.getWorkspaceComponent()).getTeachingAssistantPane();
		TableView taTable = workspace.getTaTable();

		Object selectedItem = taTable.getSelectionModel().getSelectedItem();

		TeachingAssistant ta = (TeachingAssistant) selectedItem;

		if (ta != null)
			app.jtps.addTransaction(new DeleteTATransaction(ta, app));
	}

	/**
	 * This function provides a response for when the user clicks
	 * on the office hours grid to add or remove a TA to a time slot.
	 *
	 * @param pane The pane that was toggled.
	 */
	public void handleCellToggle(Pane pane) {
		// GET THE TABLE
		TeachingAssistantPane workspace = ((CSGWorkspace) app.getWorkspaceComponent()).getTeachingAssistantPane();
		TableView taTable = workspace.getTaTable();

		// IS A TA SELECTED IN THE TABLE?
		Object selectedItem = taTable.getSelectionModel().getSelectedItem();

		// GET THE TA
		TeachingAssistant ta = (TeachingAssistant) selectedItem;

		if (ta != null)
			app.jtps.addTransaction(new ToggleTATransaction(app, pane, ta));
	}

	public void handleCellHover(Pane pane) {
		TeachingAssistantPane workspace = ((CSGWorkspace) app.getWorkspaceComponent()).getTeachingAssistantPane();
		HashMap<String, Pane> tas = workspace.getOfficeHoursGridTACellPanes();
		HashMap<String, Pane> times = workspace.getOfficeHoursGridTimeCellPanes();
		HashMap<String, Pane> days = workspace.getOfficeHoursGridDayHeaderPanes();

		int row = GridPane.getRowIndex(pane);
		int col = GridPane.getColumnIndex(pane);

		for (Pane p : tas.values()) {
			if (GridPane.getRowIndex(p) == row && GridPane.getColumnIndex(p) < col) {
				p.setStyle("-fx-border-color: #364e87;");
			}
			if (GridPane.getRowIndex(p) < row && GridPane.getColumnIndex(p) == col) {
				p.setStyle("-fx-border-color: #364e87;");
			}
		}

		for (Pane p : times.values()) {
			if (GridPane.getRowIndex(p) == row) {
				p.setStyle("-fx-border-color: yellow; -fx-border-style: solid;");
			}
		}

		for (Pane p : days.values()) {
			if (GridPane.getColumnIndex(p) == col) {
				p.setStyle("-fx-border-color: yellow;");
			}
		}

		pane.setStyle("-fx-border-color: yellow;");
	}

	public void handleCellExit(Pane pane) {
		TeachingAssistantPane workspace = ((CSGWorkspace) app.getWorkspaceComponent()).getTeachingAssistantPane();
		HashMap<String, Pane> cells = workspace.getOfficeHoursGridTACellPanes();
		HashMap<String, Pane> times = workspace.getOfficeHoursGridTimeCellPanes();
		HashMap<String, Pane> days = workspace.getOfficeHoursGridDayHeaderPanes();

		int row = GridPane.getRowIndex(pane);
		int col = GridPane.getColumnIndex(pane);

		for (Pane p : cells.values()) {
			if (GridPane.getRowIndex(p) == row || GridPane.getColumnIndex(pane) == col) {
				p.setStyle("-fx-border-color: black;");
			}
		}

		for (Pane p : times.values()) {
			if (GridPane.getRowIndex(p) == row) {
				p.setStyle("-fx-border-color: black;");
			}
		}

		for (Pane p : days.values()) {
			if (GridPane.getColumnIndex(p) == col) {
				p.setStyle("-fx-border-color: black;");
			}
		}
	}

	public void handleTableClick() {
		try {
			// GET THE TABLE
			TeachingAssistantPane workspace = ((CSGWorkspace) app.getWorkspaceComponent()).getTeachingAssistantPane();
			TableView taTable = workspace.getTaTable();

			// GET SELECTED ITEM
			Object selectedItem = taTable.getSelectionModel().getSelectedItem();

			// GET TA
			TeachingAssistant ta = (TeachingAssistant) selectedItem;

			if (ta != null) {
				workspace.getNameTF().setText(ta.getName());
				workspace.getEmailTF().setText(ta.getEmail());
			}
		} catch (Exception e) {
		}
	}

	public void handleComboBox() {
		TeachingAssistantPane workspace = ((CSGWorkspace) app.getWorkspaceComponent()).getTeachingAssistantPane();
		TAData dataSpace = ((CSGData) app.getDataComponent()).getTAData();
		PropertiesManager props = PropertiesManager.getPropertiesManager();

		String start = workspace.getStartTime().getSelectionModel().getSelectedItem();
		String end = workspace.getEndTime().getSelectionModel().getSelectedItem();

		int startTime = (start != null) ? getTime(start) : dataSpace.getStartHour();
		int endTime = (end != null) ? getTime(end) : dataSpace.getEndHour();

		if (startTime >= endTime) {
			AppMessageDialogSingleton single = AppMessageDialogSingleton.getSingleton();
			single.show(props.getProperty(TA_INVALID_TIMES_TITLE), props.getProperty(TA_INVALID_TIMES_MESSAGE));
		} else if (start != null || end != null) {
			boolean verify = true;

			if (startTime > dataSpace.getStartHour() || endTime < dataSpace.getEndHour()) {
				verify = false;

				AppYesNoCancelDialogSingleton dialog = AppYesNoCancelDialogSingleton.getSingleton();
				dialog.show(props.getProperty(TA_VERIFY_TITLE), props.getProperty(TA_VERIFY_MESSAGE));
				String selection = dialog.getSelection();

				if (selection.equals(AppYesNoCancelDialogSingleton.YES))
					verify = true;
			}

			if (verify) app.jtps.addTransaction(new ComboTimeTransaction(app, startTime, endTime));
		}
	}

	private static int getTime(String time) {
		String[] t = time.split(":");
		int hour = Integer.parseInt(t[0]);

		if (t[1].charAt(t[1].indexOf("m") - 1) == 'p') {
			if (hour != 12) hour += 12;
		} else if (hour == 12)
			hour = 0;

		return hour;
	}

	private class EmailValidator {
		private final Pattern pattern;
		private Matcher matcher;

		private static final String EMAIL_PATTERN =
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
						+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		public EmailValidator() {
			pattern = Pattern.compile(EMAIL_PATTERN);
		}

		/**
		 * Validate hex with regular expression
		 *
		 * @param hex hex for validation
		 * @return true valid hex, false invalid hex
		 */
		public boolean validate(final String hex) {

			matcher = pattern.matcher(hex);
			return matcher.matches();

		}
	}
}