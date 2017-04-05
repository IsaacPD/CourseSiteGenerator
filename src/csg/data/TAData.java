package csg.data;

import csg.CSGApp;
import csg.CSGAppProp;
import csg.file.CSGFiles;
import csg.ta.TeachingAssistant;
import csg.ta.TeachingAssistantPane;
import csg.ta.TimeSlot;
import csg.workspace.CSGWorkspace;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.Pane;
import properties_manager.PropertiesManager;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class TAData {
	CSGApp app;

	ObservableList<TeachingAssistant> teachingAssistants;
	ObservableList<String> times;

	HashMap<String, StringProperty> officeHours;
	ArrayList<String> gridHeaders;

	int startHour, endHour;

	public static final int MIN_START_HOUR = 9;
	public static final int MAX_END_HOUR = 20;

	public TAData(CSGApp initApp){
		app = initApp;

		// CONSTRUCT THE LIST OF TAs FOR THE TABLE
		teachingAssistants = FXCollections.observableArrayList();

		times = FXCollections.observableArrayList();

		// THESE ARE THE DEFAULT OFFICE HOURS
		startHour = MIN_START_HOUR;
		endHour = MAX_END_HOUR;

		//THIS WILL STORE OUR OFFICE HOURS
		officeHours = new HashMap();

		// THESE ARE THE LANGUAGE-DEPENDENT OFFICE HOURS GRID HEADERS
		PropertiesManager props = PropertiesManager.getPropertiesManager();
		ArrayList<String> timeHeaders = props.getPropertyOptionsList(CSGAppProp.OFFICE_HOURS_TABLE_HEADERS);
		ArrayList<String> dowHeaders = props.getPropertyOptionsList(CSGAppProp.DAYS_OF_WEEK);
		gridHeaders = new ArrayList();
		gridHeaders.addAll(timeHeaders);
		gridHeaders.addAll(dowHeaders);

		for (int i = 9; i < 20; i++) {
			times.add(getTimeString(i, true));
		}
	}

	public void resetData() {
		startHour = MIN_START_HOUR;
		endHour = MAX_END_HOUR;
		teachingAssistants.clear();
		officeHours.clear();
	}

	public HashMap<String, StringProperty> getOfficeHours() {
		return officeHours;
	}

	public ArrayList<String> getGridHeaders() {
		return gridHeaders;
	}

	public int getStartHour() {
		return startHour;
	}

	public int getEndHour() {
		return endHour;
	}

	public ObservableList<TeachingAssistant> getTeachingAssistants() {
		return teachingAssistants;
	}

	public ObservableList<String> getTimes() {
		return times;
	}

	public String getCellKey(int col, int row){
		return col + "_" + row;
	}

	public StringProperty getCellTextProperty(int col, int row){
		String cellKey = getCellKey(col, row);
		return officeHours.get(cellKey);
	}

	public int getNumRows() {
		return ((endHour - startHour) * 2) + 1;
	}

	public String getTimeString(int militaryHour, boolean onHour) {
		String minutesText = "00";
		if (!onHour) {
			minutesText = "30";
		}

		// FIRST THE START AND END CELLS
		int hour = militaryHour;
		if (hour > 12) {
			hour -= 12;
		}
		if (hour == 0){
			hour = 12;
		}
		String cellText = "" + hour + ":" + minutesText;
		if (militaryHour < 12) {
			cellText += "am";
		} else {
			cellText += "pm";
		}
		return cellText;
	}

	public String getCellKey(String day, String time) {
		int col = gridHeaders.indexOf(day);
		int row = 1;
		int hour = Integer.parseInt(time.substring(0, time.indexOf("_")));
		int milHour = hour;
		if (hour < startHour) {
			milHour += 12;
		}
		row += (milHour - startHour) * 2;
		if (time.contains("_30")) {
			row += 1;
		}
		return getCellKey(col, row);
	}

	public TeachingAssistant getTA(String testName) {
		for (TeachingAssistant ta : teachingAssistants) {
			if (ta.getName().equals(testName)) {
				return ta;
			}
		}
		return null;
	}

	public void setCellProperty(int col, int row, StringProperty prop) {
		String cellKey = getCellKey(col, row);
		officeHours.put(cellKey, prop);
	}

	public void setGridProperty(ArrayList<ArrayList<StringProperty>> grid,
	                            int column, int row, StringProperty prop) {
		grid.get(row).set(column, prop);
	}

	private void initOfficeHours(int initStartHour, int initEndHour) {
		// NOTE THAT THESE VALUES MUST BE PRE-VERIFIED
		startHour = initStartHour;
		endHour = initEndHour;

		// EMPTY THE CURRENT OFFICE HOURS VALUES
		officeHours.clear();

		// WE'LL BUILD THE USER INTERFACE COMPONENT FOR THE
		// OFFICE HOURS GRID AND FEED THEM TO OUR DATA
		// STRUCTURE AS WE GO
		TeachingAssistantPane workspaceComponent = ((CSGWorkspace) app.getWorkspaceComponent()).getTeachingAssistantPane();
		workspaceComponent.reloadOfficeHoursGrid(this);
	}

	public void initHours(String startHourText, String endHourText) {
		int initStartHour = Integer.parseInt(startHourText);
		int initEndHour = Integer.parseInt(endHourText);

		if ((initStartHour >= MIN_START_HOUR)
				&& (initEndHour <= MAX_END_HOUR)
				&& (initStartHour <= initEndHour)) {
			// THESE ARE VALID HOURS SO KEEP THEM
			initOfficeHours(initStartHour, initEndHour);
		}
	}

	public boolean containsTA(String testName, String testEmail) {
		for (TeachingAssistant ta : teachingAssistants) {

			if (ta.getName().equals(testName) && ta.getEmail().equals(testEmail)) {
				return true;
			}

		}
		return false;
	}

	public void addTA(String initName, String email) {
		// MAKE THE TA
		TeachingAssistant ta = new TeachingAssistant(initName, email);

		// ADD THE TA
		if (!containsTA(initName, email)) {
			teachingAssistants.add(ta);
		}

		// SORT THE TAS
		Collections.sort(teachingAssistants);
	}

	public void addTA(TeachingAssistant ta) {
		if(!containsTA(ta.getName(), ta.getEmail())){
			teachingAssistants.add(ta);
		}

		Collections.sort(teachingAssistants);
	}

	public ArrayList<String> removeTA(TeachingAssistant ta) {
		ArrayList<String> removed = new ArrayList<>();
		String name = ta.getName();
		teachingAssistants.remove(ta);

		TeachingAssistantPane workspace = ((CSGWorkspace) app.getWorkspaceComponent()).getTeachingAssistantPane();
		HashMap<String, Pane> panes = workspace.getOfficeHoursGridTACellPanes();

		for (Pane p : panes.values()) {
			StringProperty cellProp = officeHours.get(p.getId());
			String cellText = cellProp.getValue();

			if (cellText.contains(name)) {
				removeTAFromCell(cellProp, name);
				removed.add(p.getId());
			}
		}

		return removed;
	}

	public void addOfficeHoursReservation(String day, String time, String taName) {
		String cellKey = getCellKey(day, time);
		toggleTAOfficeHours(cellKey, taName);
	}

	public void toggleTAOfficeHours(String cellKey, String taName) {
		StringProperty cellProp = officeHours.get(cellKey);
		String cellText = cellProp.getValue();
		if (cellText.contains(taName)) {
			removeTAFromCell(cellProp, taName);
		} else {
			if (cellText.equals("")) {
				cellProp.setValue(taName);
			} else {
				cellProp.setValue(cellText + "\n" + taName);
			}
		}
	}

	public void removeTAFromCell(StringProperty cellProp, String taName) {
		// GET THE CELL TEXT
		String cellText = cellProp.getValue();
		// IS IT THE ONLY TA IN THE CELL?
		if (cellText.equals(taName)) {
			cellProp.setValue("");
		} // IS IT THE FIRST TA IN A CELL WITH MULTIPLE TA'S?
		else if (cellText.indexOf(taName) == 0) {
			int startIndex = cellText.indexOf("\n") + 1;
			cellText = cellText.substring(startIndex);
			cellProp.setValue(cellText);
		} // IT MUST BE ANOTHER TA IN THE CELL
		else {
			int startIndex = cellText.indexOf("\n" + taName);
			cellText = cellText.substring(0, startIndex)
					+ cellText.substring(startIndex + taName.length() + 1);
			cellProp.setValue(cellText);
		}
	}

	public void updateTA(TeachingAssistant ta, String newName, String newEmail) {
		String name = ta.getName();

		//workspace.nameColumn;
		ta.setName(newName);
		ta.setEmail(newEmail);

		TeachingAssistantPane workspace = ((CSGWorkspace) app.getWorkspaceComponent()).getTeachingAssistantPane();

		if (!newName.equals(name)){
			HashMap<String, Pane> panes = workspace.getOfficeHoursGridTACellPanes();

			for (Pane p : panes.values()) {
				StringProperty cellProp = officeHours.get(p.getId());
				String cellText = cellProp.getValue();

				if (cellText.contains(name)) {
					removeTAFromCell(cellProp, name);

					if (cellProp.getValue().equals("")) {
						cellProp.setValue(newName);
					} else {
						cellProp.setValue(cellProp.getValue() + "\n" + newName);
					}
				}
			}
		}
		((TableColumn) (workspace.getTaTable().getColumns().get(0))).setVisible(false);
		((TableColumn) (workspace.getTaTable().getColumns().get(0))).setVisible(true);
	}

	public ArrayList<TimeSlot> changeHours(int start, int end){
		try {
			CSGFiles fileComponent = (CSGFiles) app.getFileComponent();
			ArrayList<TimeSlot> removed;
			removed = fileComponent.saveData(app.getDataComponent(), "temp.json", ""+start, ""+end);

			startHour = start;
			endHour = end;

			(((CSGWorkspace) app.getWorkspaceComponent()).getTeachingAssistantPane()).reloadTimes(this);
			fileComponent.loadTAData(this, "temp.json");

			File temp = new File("temp.json");
			temp.delete();

			return removed;
		}
		catch(IOException e) {
			return null;
		}
	}
}
