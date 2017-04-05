package csg.file;

import csg.CSGApp;
import csg.data.CSGData;
import csg.data.TAData;
import csg.ta.TeachingAssistant;
import csg.ta.TimeSlot;
import djf.components.AppDataComponent;
import djf.components.AppFileComponent;
import djf.ui.AppMessageDialogSingleton;
import javafx.collections.ObservableList;

import javax.json.*;
import javax.json.stream.JsonGenerator;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CSGFiles implements AppFileComponent{
	CSGApp app;

	// THESE ARE USED FOR IDENTIFYING JSON TYPES
	static final String JSON_START_HOUR = "startHour";
	static final String JSON_END_HOUR = "endHour";
	static final String JSON_OFFICE_HOURS = "officeHours";
	static final String JSON_DAY = "day";
	static final String JSON_TIME = "time";
	static final String JSON_NAME = "name";
	static final String JSON_UNDERGRAD_TAS = "undergrad_tas";
	static final String JSON_EMAIL = "email";

	public CSGFiles(CSGApp initApp){
		app = initApp;
	}

	@Override
	public void saveData(AppDataComponent appDataComponent, String s) throws IOException {

	}

	@Override
	public void loadData(AppDataComponent appDataComponent, String s) throws IOException {

	}

	public void loadTAData(TAData data, String filePath) throws IOException{
		AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();

		// CLEAR THE OLD DATA OUT
		TAData dataManager = (TAData)data;

		// LOAD THE JSON FILE WITH ALL THE DATA
		JsonObject json = loadJSONFile(filePath);

		// LOAD THE START AND END HOURS
		String startHour = json.getString(JSON_START_HOUR);
		String endHour = json.getString(JSON_END_HOUR);
		dataManager.initHours(startHour, endHour);

		// NOW RELOAD THE WORKSPACE WITH THE LOADED DATA
		app.getWorkspaceComponent().reloadWorkspace(app.getDataComponent());

		// NOW LOAD ALL THE UNDERGRAD TAs
		JsonArray jsonTAArray = json.getJsonArray(JSON_UNDERGRAD_TAS);
		for (int i = 0; i < jsonTAArray.size(); i++) {
			JsonObject jsonTA = jsonTAArray.getJsonObject(i);
			String name = jsonTA.getString(JSON_NAME);
			String email = jsonTA.getString(JSON_EMAIL);

			dataManager.addTA(name, email);
		}

		// AND THEN ALL THE OFFICE HOURS
		JsonArray jsonOfficeHoursArray = json.getJsonArray(JSON_OFFICE_HOURS);
		for (int i = 0; i < jsonOfficeHoursArray.size(); i++) {
			JsonObject jsonOfficeHours = jsonOfficeHoursArray.getJsonObject(i);
			String day = jsonOfficeHours.getString(JSON_DAY);
			String time = jsonOfficeHours.getString(JSON_TIME);
			String name = jsonOfficeHours.getString(JSON_NAME);
			dataManager.addOfficeHoursReservation(day, time, name);
		}
	}

	private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
		InputStream is = new FileInputStream(jsonFilePath);
		JsonReader jsonReader = Json.createReader(is);
		JsonObject json = jsonReader.readObject();
		jsonReader.close();
		is.close();
		return json;
	}

	public ArrayList<TimeSlot> saveData(AppDataComponent data, String filePath, String startTime, String endTime) throws IOException {
		// GET THE DATA
		TAData dataManager = ((CSGData)data).getTAData();

		// NOW BUILD THE TA JSON OBJCTS TO SAVE
		JsonArrayBuilder taArrayBuilder = Json.createArrayBuilder();
		ObservableList<TeachingAssistant> tas = dataManager.getTeachingAssistants();
		for (TeachingAssistant ta : tas) {
			JsonObject taJson = Json.createObjectBuilder()
					.add(JSON_NAME, ta.getName())
					.add(JSON_EMAIL, ta.getEmail()).build();
			taArrayBuilder.add(taJson);
		}
		JsonArray undergradTAsArray = taArrayBuilder.build();

		// NOW BUILD THE TIME SLOT JSON OBJCTS TO SAVE
		ArrayList<TimeSlot> removedTimeSlots = new ArrayList<>();
		JsonArrayBuilder timeSlotArrayBuilder = Json.createArrayBuilder();
		ArrayList<TimeSlot> officeHours = TimeSlot.buildOfficeHoursList(dataManager);
		for (TimeSlot ts : officeHours) {
			if(TimeSlot.compareTime(ts.getTime(), startTime) >= 0 &&
					TimeSlot.compareTime(ts.getTime(), endTime) < 0){
				JsonObject tsJson = Json.createObjectBuilder()
						.add(JSON_DAY, ts.getDay())
						.add(JSON_TIME, ts.getTime())
						.add(JSON_NAME, ts.getName()).build();
				timeSlotArrayBuilder.add(tsJson);
			}
			else {
				removedTimeSlots.add(ts);
			}
		}
		JsonArray timeSlotsArray = timeSlotArrayBuilder.build();

		// THEN PUT IT ALL TOGETHER IN A JsonObject
		JsonObject dataManagerJSO = Json.createObjectBuilder()
				.add(JSON_START_HOUR, startTime)
				.add(JSON_END_HOUR, endTime)
				.add(JSON_UNDERGRAD_TAS, undergradTAsArray)
				.add(JSON_OFFICE_HOURS, timeSlotsArray)
				.build();

		// AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
		Map<String, Object> properties = new HashMap<>(1);
		properties.put(JsonGenerator.PRETTY_PRINTING, true);
		JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
		StringWriter sw = new StringWriter();
		JsonWriter jsonWriter = writerFactory.createWriter(sw);
		jsonWriter.writeObject(dataManagerJSO);
		jsonWriter.close();

		// INIT THE WRITER
		OutputStream os = new FileOutputStream(filePath);
		JsonWriter jsonFileWriter = Json.createWriter(os);
		jsonFileWriter.writeObject(dataManagerJSO);
		String prettyPrinted = sw.toString();
		PrintWriter pw = new PrintWriter(filePath);
		pw.write(prettyPrinted);
		pw.close();

		return removedTimeSlots;
	}

	@Override
	public void exportData(AppDataComponent appDataComponent, String s) throws IOException {

	}

	@Override
	public void importData(AppDataComponent appDataComponent, String s) throws IOException {

	}
}
