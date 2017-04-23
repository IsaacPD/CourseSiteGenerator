package csg.file;

import csg.CSGApp;
import csg.data.*;
import csg.details.CourseDetailsPane;
import csg.details.Details;
import csg.project.Student;
import csg.project.Team;
import csg.recitation.Recitation;
import csg.schedule.ScheduleItem;
import csg.ta.TeachingAssistant;
import csg.ta.TimeSlot;
import csg.workspace.CSGWorkspace;
import djf.components.AppDataComponent;
import djf.components.AppFileComponent;
import javafx.collections.ObservableList;

import javax.json.*;
import javax.json.stream.JsonGenerator;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CSGFiles implements AppFileComponent {
	// THESE ARE USED FOR IDENTIFYING JSON TYPES
	static final String JSON_START_HOUR = "startHour";
	static final String JSON_END_HOUR = "endHour";
	static final String JSON_OFFICE_HOURS = "officeHours";
	static final String JSON_STARTING_MONDAY_MONTH = "startingMondayMonth";
	static final String JSON_STARTING_MONDAY_DAY = "startingMondayDay";
	static final String JSON_ENDING_FRIDAY_MONTH = "endingFridayMonth";
	static final String JSON_ENDING_FRIDAY_DAY = "endingFridayDay";
	static final String JSON_DAY = "day";
	static final String JSON_TIME = "time";
	static final String JSON_NAME = "name";
	static final String JSON_UNDERGRAD_TAS = "undergrad_tas";
	static final String JSON_GRAD_TAS = "grad_tas";
	static final String JSON_EMAIL = "email";
	static final String JSON_R_SECTION = "section";
	static final String JSON_R_INSTRUCTOR = "instructor";
	static final String JSON_R_DAY = "day_time";
	static final String JSON_R_LOCATION = "location";
	static final String JSON_R_TA1 = "ta_1";
	static final String JSON_R_TA2 = "ta_2";
	static final String JSON_T_NAME = "name";
	static final String JSON_T_COLOR = "color";
	static final String JSON_T_TEXT_COLOR = "text_color";
	static final String JSON_T_LINK = "link";
	static final String JSON_S_FIRST_NAME = "first_name";
	static final String JSON_S_LAST_NAME = "last_name";
	static final String JSON_S_TEAM = "team";
	static final String JSON_S_ROLE = "role";
	static final String JSON_SI_TYPE = "type";
	static final String JSON_SI_DATE = "date";
	static final String JSON_SI_TITLE = "title";
	static final String JSON_SI_TOPIC = "topic";
	static final String JSON_STUDENT = "students";
	static final String JSON_TEAM = "teams";
	static final String JSON_RECITATION = "recitations";
	static final String JSON_SCHEDULE_ITEM = "schedule_items";
	static final String JSON_SI_LINK = "link";
	static final String JSON_SI_HOLIDAYS = "holidays";
	static final String JSON_SI_LECTURES = "lectures";
	static final String JSON_SI_REFERENCES = "references";
	static final String JSON_SI_HWS = "hws";
	static final String JSON_SI_CRITERIA = "criteria";
	static final String JSON_SUBJECT = "subject";
	static final String JSON_NUMBER = "number";
	static final String JSON_SEMESTER = "semester";
	static final String JSON_YEAR = "year";
	static final String JSON_TITLE = "title";
	static final String JSON_INSTRUCTOR_NAME = "instructor_name";
	static final String JSON_INSTRUCTOR_HOME = "instructor_home";
	static final String JSON_DETAILS = "details";
	static final String JSON_FILE_NAME = "file_name";
	static final String JSON_NAVBAR_TITLE = "navbar_title";
	CSGApp app;

	public CSGFiles(CSGApp initApp) {
		app = initApp;
	}

	@Override
	public void saveData(AppDataComponent appDataComponent, String filePath) throws IOException {
		CSGData data = (CSGData) appDataComponent;
		RecitationData recitation = data.getRecitationData();
		ProjectData project = data.getProjectData();
		ScheduleData schedule = data.getScheduleData();
		TAData teachingAssistant = data.getTAData();

		JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();

		// NOW BUILD THE TA JSON OBJCTS TO SAVE
		saveTAData(teachingAssistant, jsonBuilder);

		// NOW BUILD THE RECITATION JSON OBJECTS TO SAVE
		saveRecitationData(recitation, jsonBuilder);

		// NOW BUILD THE PROJECT OBJECTS TO SAVE
		saveProjectData(project, jsonBuilder);

		// NOW BUILD THE SCHEDULE ITEM JSON OBJECTS TO SAVE
		saveScheduleData(schedule, jsonBuilder);

		JsonObject dataManagerJSO = jsonBuilder.build();

		writeJson(dataManagerJSO, filePath);
	}

	private void saveTAData(TAData teachingAssistant, JsonObjectBuilder builder) {
		JsonArrayBuilder taArrayBuilder = Json.createArrayBuilder();
		JsonArrayBuilder gradTABuilder = Json.createArrayBuilder();
		ObservableList<TeachingAssistant> tas = teachingAssistant.getTeachingAssistants();
		for (TeachingAssistant ta : tas) {
			JsonObject taJson = Json.createObjectBuilder()
					.add(JSON_NAME, ta.getName())
					.add(JSON_EMAIL, ta.getEmail()).build();
			if (ta.isUndergrad()) taArrayBuilder.add(taJson);
			else gradTABuilder.add(taJson);
		}
		JsonArray undergradTAsArray = taArrayBuilder.build();
		JsonArray gradTAsArray = gradTABuilder.build();

		// NOW BUILD THE TIME SLOT JSON OBJCTS TO SAVE
		JsonArrayBuilder timeSlotArrayBuilder = Json.createArrayBuilder();
		ArrayList<TimeSlot> officeHours = TimeSlot.buildOfficeHoursList(teachingAssistant);
		for (TimeSlot ts : officeHours) {
			JsonObject tsJson = Json.createObjectBuilder()
					.add(JSON_DAY, ts.getDay())
					.add(JSON_TIME, ts.getTime())
					.add(JSON_NAME, ts.getName()).build();
			timeSlotArrayBuilder.add(tsJson);
		}
		JsonArray timeSlotsArray = timeSlotArrayBuilder.build();

		builder.add(JSON_START_HOUR, "" + teachingAssistant.getStartHour())
				.add(JSON_END_HOUR, "" + teachingAssistant.getEndHour())
				.add(JSON_UNDERGRAD_TAS, undergradTAsArray)
				.add(JSON_GRAD_TAS, gradTAsArray)
				.add(JSON_OFFICE_HOURS, timeSlotsArray);
	}

	private void saveRecitationData(RecitationData recitation, JsonObjectBuilder builder) {
		JsonArrayBuilder recitationArrayBuilder = Json.createArrayBuilder();
		ObservableList<Recitation> recitations = recitation.getRecitations();
		for (Recitation r : recitations) {
			JsonObject rJson = Json.createObjectBuilder()
					.add(JSON_R_SECTION, r.getSection())
					.add(JSON_R_INSTRUCTOR, r.getInstructor())
					.add(JSON_R_DAY, r.getDay())
					.add(JSON_R_LOCATION, r.getLocation())
					.add(JSON_R_TA1, r.getTa1())
					.add(JSON_R_TA2, r.getTa2()).build();
			recitationArrayBuilder.add(rJson);
		}
		builder.add(JSON_RECITATION, recitationArrayBuilder.build());
	}

	private void saveProjectData(ProjectData project, JsonObjectBuilder builder) {
		JsonArrayBuilder teamArrayBuilder = Json.createArrayBuilder();
		ObservableList<Team> teams = project.getTeams();
		for (Team t : teams) {
			JsonObject tJson = Json.createObjectBuilder()
					.add(JSON_T_NAME, t.getName())
					.add(JSON_T_COLOR, t.getColor())
					.add(JSON_T_TEXT_COLOR, t.getTColor())
					.add(JSON_T_LINK, t.getLink()).build();
			teamArrayBuilder.add(tJson);
		}
		JsonArray teamArray = teamArrayBuilder.build();

		// NOW BUILD THE STUDENT JSON OBJECTS TO SAVE
		JsonArrayBuilder studentArrayBuilder = Json.createArrayBuilder();
		ObservableList<Student> students = project.getStudents();
		for (Student s : students) {
			JsonObject sJson = Json.createObjectBuilder()
					.add(JSON_S_FIRST_NAME, s.getFName())
					.add(JSON_S_LAST_NAME, s.getLName())
					.add(JSON_S_TEAM, s.getTeam())
					.add(JSON_S_ROLE, s.getRole()).build();
			studentArrayBuilder.add(sJson);
		}
		JsonArray studentArray = studentArrayBuilder.build();

		builder.add(JSON_TEAM, teamArray)
				.add(JSON_STUDENT, studentArray);
	}

	private void saveScheduleData(ScheduleData schedule, JsonObjectBuilder builder) {
		builder.add(JSON_STARTING_MONDAY_MONTH, "" + schedule.getMondayMonth())
				.add(JSON_STARTING_MONDAY_DAY, "" + schedule.getMondayDay())
				.add(JSON_ENDING_FRIDAY_MONTH, "" + schedule.getFridayMonth())
				.add(JSON_ENDING_FRIDAY_DAY, "" + schedule.getFridayDay());

		JsonArrayBuilder scheduleItemArrayBuilder = Json.createArrayBuilder();
		ObservableList<ScheduleItem> scheduleItems = schedule.getSchedules();
		for (ScheduleItem s : scheduleItems) {
			JsonObject sJson = Json.createObjectBuilder()
					.add(JSON_SI_TYPE, s.getType())
					.add(JSON_SI_DATE, s.getDate())
					.add(JSON_SI_TITLE, s.getTitle())
					.add(JSON_SI_TOPIC, s.getTopic())
					.add(JSON_SI_LINK, s.getLink()).build();
			scheduleItemArrayBuilder.add(sJson);
		}
		builder.add(JSON_SCHEDULE_ITEM, scheduleItemArrayBuilder.build());
	}

	private void saveDetailsData(DetailsData details, JsonObjectBuilder builder) {
		CourseDetailsPane workspace = ((CSGWorkspace) app.getWorkspaceComponent()).getCourseDetailsPane();

		String subject = workspace.getSubjectCB().getSelectionModel().getSelectedItem();
		String number = workspace.getNumberCB().getSelectionModel().getSelectedItem();
		String semester = workspace.getSemesterCB().getSelectionModel().getSelectedItem();
		String year = workspace.getYearCB().getSelectionModel().getSelectedItem();
		String title = workspace.getTitleTF().getText();
		String instructorName = workspace.getInstructorName().getText();
		String instructorHome = workspace.getInstructorHome().getText();

		JsonArrayBuilder detailsArrayBuilder = Json.createArrayBuilder();
		for (Details d : details.getDetails()) {
			if (d.isUse()) {
				JsonObject dJson = Json.createObjectBuilder()
						.add(JSON_FILE_NAME, d.getFileName())
						.add(JSON_NAVBAR_TITLE, d.getNavbarTitle()).build();
				detailsArrayBuilder.add(dJson);
			}
		}
		JsonArray detailsArray = detailsArrayBuilder.build();

		builder.add(JSON_SUBJECT, subject)
				.add(JSON_NUMBER, number)
				.add(JSON_SEMESTER, semester)
				.add(JSON_YEAR, year)
				.add(JSON_TITLE, title)
				.add(JSON_INSTRUCTOR_NAME, instructorName)
				.add(JSON_INSTRUCTOR_HOME, instructorHome)
				.add(JSON_DETAILS, detailsArray);
	}

	@Override
	public void loadData(AppDataComponent appDataComponent, String filePath) throws IOException {
		// CLEAR THE OLD DATA OUT
		CSGData data = (CSGData) appDataComponent;
		RecitationData recitation = data.getRecitationData();
		ProjectData project = data.getProjectData();
		ScheduleData schedule = data.getScheduleData();
		TAData teachingAssistant = data.getTAData();

		// LOAD THE JSON FILE WITH ALL THE DATA
		JsonObject json = loadJSONFile(filePath);

		// LOAD ALL NECESSARY DATA
		loadTAData(teachingAssistant, json);
		loadRecitationData(recitation, json);
		loadProjectData(project, json);
		loadScheduleData(schedule, json);
	}

	private void loadRecitationData(RecitationData recitation, JsonObject json) {
		JsonArray jsonRecitationArray = json.getJsonArray(JSON_RECITATION);
		for (int i = 0; i < jsonRecitationArray.size(); i++) {
			JsonObject jsonRecitation = jsonRecitationArray.getJsonObject(i);
			String section = jsonRecitation.getString(JSON_R_SECTION);
			String instructor = jsonRecitation.getString(JSON_R_INSTRUCTOR);
			String day = jsonRecitation.getString(JSON_R_DAY);
			String location = jsonRecitation.getString(JSON_R_LOCATION);
			String ta1 = jsonRecitation.getString(JSON_R_TA1);
			String ta2 = jsonRecitation.getString(JSON_R_TA2);
			recitation.addRecitation(new Recitation(section, instructor, day, location, ta1, ta2));
		}
	}

	private void loadProjectData(ProjectData project, JsonObject json) {
		JsonArray jsonTeamsArray = json.getJsonArray(JSON_TEAM);
		for (int i = 0; i < jsonTeamsArray.size(); i++) {
			JsonObject jsonTeam = jsonTeamsArray.getJsonObject(i);
			String name = jsonTeam.getString(JSON_T_NAME);
			String color = jsonTeam.getString(JSON_T_COLOR);
			String tColor = jsonTeam.getString(JSON_T_TEXT_COLOR);
			String link = jsonTeam.getString(JSON_T_LINK);
			project.addTeam(new Team(name, color, tColor, link));
		}

		JsonArray jsonStudentArray = json.getJsonArray(JSON_STUDENT);
		for (int i = 0; i < jsonStudentArray.size(); i++) {
			JsonObject jsonStudent = jsonStudentArray.getJsonObject(i);
			String fName = jsonStudent.getString(JSON_S_FIRST_NAME);
			String lName = jsonStudent.getString(JSON_S_LAST_NAME);
			String team = jsonStudent.getString(JSON_S_TEAM);
			String role = jsonStudent.getString(JSON_S_ROLE);
			project.addStudent(new Student(fName, lName, team, role));
		}
	}

	private void loadScheduleData(ScheduleData schedule, JsonObject json) {
		String mMonth = json.getString(JSON_STARTING_MONDAY_MONTH);
		String mDay = json.getString(JSON_STARTING_MONDAY_DAY);
		String fMonth = json.getString(JSON_ENDING_FRIDAY_MONTH);
		String fDay = json.getString(JSON_ENDING_FRIDAY_DAY);
		schedule.initScheduleBoundaries(mMonth, mDay, fMonth, fDay);

		JsonArray jsonScheduleArray = json.getJsonArray(JSON_SCHEDULE_ITEM);
		for (int i = 0; i < jsonScheduleArray.size(); i++) {
			JsonObject jsonScheduleItem = jsonScheduleArray.getJsonObject(i);
			String type = jsonScheduleItem.getString(JSON_SI_TYPE);
			String date = jsonScheduleItem.getString(JSON_SI_DATE);
			String title = jsonScheduleItem.getString(JSON_SI_TITLE);
			String topic = jsonScheduleItem.getString(JSON_SI_TOPIC);
			String link = jsonScheduleItem.getString(JSON_SI_LINK);
			String time = jsonScheduleItem.getString(JSON_TIME);
			String criteria = jsonScheduleItem.getString(JSON_SI_CRITERIA);
			schedule.addSchedule(new ScheduleItem(type, date, time, title, topic, link, criteria));
		}
	}

	private void loadTAData(TAData teachingAssistant, JsonObject json) {
		// LOAD THE START AND END HOURS
		String startHour = json.getString(JSON_START_HOUR);
		String endHour = json.getString(JSON_END_HOUR);
		teachingAssistant.initHours(startHour, endHour);

		// NOW RELOAD THE WORKSPACE WITH THE LOADED DATA
		app.getWorkspaceComponent().reloadWorkspace(app.getDataComponent());

		// NOW LOAD ALL THE UNDERGRAD TAs
		JsonArray jsonTAArray = json.getJsonArray(JSON_UNDERGRAD_TAS);
		for (int i = 0; i < jsonTAArray.size(); i++) {
			JsonObject jsonTA = jsonTAArray.getJsonObject(i);
			String name = jsonTA.getString(JSON_NAME);
			String email = jsonTA.getString(JSON_EMAIL);

			teachingAssistant.addTA(name, email, true);
		}

		JsonArray gradTAArray = json.getJsonArray(JSON_GRAD_TAS);
		for (int i = 0; i < gradTAArray.size(); i++) {
			JsonObject jsonTA = gradTAArray.getJsonObject(i);
			String name = jsonTA.getString(JSON_NAME);
			String email = jsonTA.getString(JSON_EMAIL);

			teachingAssistant.addTA(name, email, false);
		}

		// AND THEN ALL THE OFFICE HOURS
		JsonArray jsonOfficeHoursArray = json.getJsonArray(JSON_OFFICE_HOURS);
		for (int i = 0; i < jsonOfficeHoursArray.size(); i++) {
			JsonObject jsonOfficeHours = jsonOfficeHoursArray.getJsonObject(i);
			String day = jsonOfficeHours.getString(JSON_DAY);
			String time = jsonOfficeHours.getString(JSON_TIME);
			String name = jsonOfficeHours.getString(JSON_NAME);
			teachingAssistant.addOfficeHoursReservation(day, time, name);
		}
	}

	public void loadChangedTimes(TAData data) throws IOException {
		JsonObject json = loadJSONFile("temp.json");
		loadTAData(data, json);
	}

	private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
		InputStream is = new FileInputStream(jsonFilePath);
		JsonReader jsonReader = Json.createReader(is);
		JsonObject json = jsonReader.readObject();
		jsonReader.close();
		is.close();
		return json;
	}

	public ArrayList<TimeSlot> saveChangedTimes(TAData dataManager, String startTime, String endTime) throws IOException {
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
			if (TimeSlot.compareTime(ts.getTime(), startTime) >= 0 &&
					TimeSlot.compareTime(ts.getTime(), endTime) < 0) {
				JsonObject tsJson = Json.createObjectBuilder()
						.add(JSON_DAY, ts.getDay())
						.add(JSON_TIME, ts.getTime())
						.add(JSON_NAME, ts.getName()).build();
				timeSlotArrayBuilder.add(tsJson);
			} else {
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
		OutputStream os = new FileOutputStream("temp.json");
		JsonWriter jsonFileWriter = Json.createWriter(os);
		jsonFileWriter.writeObject(dataManagerJSO);
		String prettyPrinted = sw.toString();
		PrintWriter pw = new PrintWriter("temp.json");
		pw.write(prettyPrinted);
		pw.close();

		return removedTimeSlots;
	}

	@Override
	public void exportData(AppDataComponent appDataComponent, String filePath) throws IOException {
		CSGData data = (CSGData) appDataComponent;
		RecitationData recitation = data.getRecitationData();
		ProjectData project = data.getProjectData();
		ScheduleData schedule = data.getScheduleData();
		TAData teachingAssistant = data.getTAData();
		DetailsData detailsData = ((CSGData) appDataComponent).getDetailsData();
		CourseDetailsPane workspace = ((CSGWorkspace) app.getWorkspaceComponent()).getCourseDetailsPane();

		File dir = new File(workspace.getSelectedExportDir().getText() + "/");
		File exportDir = new File(workspace.getTemplateDirL().getText());

		dir.mkdir();
		export(dir, exportDir);
		File js = new File(dir.getPath() + "/js/");
		js.mkdir();

		exportScheduleData(schedule, js.getPath() + "/ScheduleData.json");
		exportDetailsData(detailsData, js.getPath() + "/DetailsData.json");
		exportTAData(teachingAssistant, js.getPath() + "/OfficeHoursGridData.json");
		exportProjectData(project, js.getPath() + "/ProjectsData.json");
		exportRecitationData(recitation, js.getPath() + "/RecitationsData.json");

		File images = new File(dir.getPath() + "/images/");
		images.mkdir();
		exportImages(detailsData, images.getPath());
	}


	@Override
	public void importData(AppDataComponent appDataComponent, String filePath) throws IOException {

	}

	private void export(File directory, File exportDir) throws IOException {
		String path = directory.getPath();
		File to;

		for (File export : exportDir.listFiles()) {
			String name = export.getName();
			if (export.isDirectory() && !export.getName().equals("js")) {
				to = new File(path + "/" + name);
				to.mkdir();
				export(to, export);
			} else {
				to = new File(path + "/" + name);

				if (!to.createNewFile() && !name.equals("OfficeHoursGridData.json")) {
					continue;
				}

				to.delete();

				File copy = (name.equals("OfficeHoursGridData.json"))
						? app.getGUI().getFileController().getCurrentWorkFile() : export;
				File tempCopy = new File(path + "/temp");

				Files.copy(copy.toPath(), tempCopy.toPath());
				Files.copy(tempCopy.toPath(), to.toPath());

				tempCopy.delete();
			}
		}
	}

	private void exportTAData(TAData teachingAssistant, String filePath) throws FileNotFoundException {
		JsonObjectBuilder builder = Json.createObjectBuilder();
		saveTAData(teachingAssistant, builder);
		JsonObject dataManagerJSO = builder.build();
		writeJson(dataManagerJSO, filePath);
	}

	private void exportRecitationData(RecitationData recitations, String filePath) throws FileNotFoundException {
		JsonObjectBuilder builder = Json.createObjectBuilder();
		saveRecitationData(recitations, builder);
		JsonObject dataManagerJSO = builder.build();
		writeJson(dataManagerJSO, filePath);
	}

	private void exportScheduleData(ScheduleData schedule, String filePath) throws FileNotFoundException {
		JsonObjectBuilder builder = Json.createObjectBuilder()
				.add(JSON_STARTING_MONDAY_MONTH, "" + schedule.getMondayMonth())
				.add(JSON_STARTING_MONDAY_DAY, "" + schedule.getMondayDay())
				.add(JSON_ENDING_FRIDAY_MONTH, "" + schedule.getFridayMonth())
				.add(JSON_ENDING_FRIDAY_DAY, "" + schedule.getFridayDay());

		JsonArrayBuilder holidaysArrayBuilder = Json.createArrayBuilder();
		JsonArrayBuilder lecturesArrayBuilder = Json.createArrayBuilder();
		JsonArrayBuilder referencesArrayBuilder = Json.createArrayBuilder();
		JsonArrayBuilder recitationsArrayBuilder = Json.createArrayBuilder();
		JsonArrayBuilder hwsArrayBuilder = Json.createArrayBuilder();
		ObservableList<ScheduleItem> scheduleItems = schedule.getSchedules();
		for (ScheduleItem s : scheduleItems) {
			JsonObjectBuilder sJsonBuilder = Json.createObjectBuilder()
					.add(JSON_SI_DATE, s.getDate())
					.add(JSON_SI_TITLE, s.getTitle())
					.add(JSON_SI_TOPIC, s.getTopic())
					.add(JSON_SI_LINK, s.getLink());

			JsonObject sJson = sJsonBuilder.build();
			switch (s.getType()) {
				case "Holiday":
					holidaysArrayBuilder.add(sJson);
					break;
				case "Lecture":
					lecturesArrayBuilder.add(sJson);
					break;
				case "Reference":
					referencesArrayBuilder.add(sJson);
					break;
				case "Recitation":
					recitationsArrayBuilder.add(sJson);
					break;
				case "HW":
					JsonObject hJson = sJsonBuilder.add(JSON_TIME, s.getTime())
							.add(JSON_SI_CRITERIA, s.getCriteria()).build();
					hwsArrayBuilder.add(hJson);
			}
		}
		builder.add(JSON_SI_HOLIDAYS, holidaysArrayBuilder.build())
				.add(JSON_SI_LECTURES, lecturesArrayBuilder.build())
				.add(JSON_SI_REFERENCES, referencesArrayBuilder.build())
				.add(JSON_RECITATION, recitationsArrayBuilder.build())
				.add(JSON_SI_HWS, hwsArrayBuilder.build());
		JsonObject dataManagerJSO = builder.build();
		writeJson(dataManagerJSO, filePath);
	}

	private void exportProjectData(ProjectData projects, String filePath) throws FileNotFoundException {
		JsonObjectBuilder builder = Json.createObjectBuilder();
		saveProjectData(projects, builder);
		JsonObject dataManagerJSO = builder.build();
		writeJson(dataManagerJSO, filePath);
	}

	private void exportDetailsData(DetailsData details, String filePath) throws FileNotFoundException {
		JsonObjectBuilder builder = Json.createObjectBuilder();
		saveDetailsData(details, builder);
		JsonObject dataManagerJSO = builder.build();
		writeJson(dataManagerJSO, filePath);
	}

	private void exportImages(DetailsData data, String filePath) throws IOException {
		CourseDetailsPane workspace = ((CSGWorkspace) app.getWorkspaceComponent()).getCourseDetailsPane();
		File leftImage = data.getImages().get(workspace.getLeftFImage());
		File rightImage = data.getImages().get(workspace.getRightFImage());
		File bannerImage = data.getImages().get(workspace.getBannerImage());

		File l = new File(filePath + "/left_image.png");
		File r = new File(filePath + "/right_image.png");
		File b = new File(filePath + "/banner_image.png");

		Files.copy(leftImage.toPath(), l.toPath());
		Files.copy(rightImage.toPath(), r.toPath());
		Files.copy(bannerImage.toPath(), b.toPath());
	}

	private void writeJson(JsonObject dataManagerJSO, String filePath) throws FileNotFoundException {
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
	}
}
