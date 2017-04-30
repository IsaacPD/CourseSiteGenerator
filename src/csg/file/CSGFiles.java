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
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import javax.json.*;
import javax.json.stream.JsonGenerator;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
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
	static final String JSON_S_FIRST_NAME = "firstName";
	static final String JSON_S_LAST_NAME = "lastName";
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
	static final String JSON_LEFT_IMAGE = "left_image";
	static final String JSON_RIGHT_IMAGE = "right_image";
	static final String JSON_BANNER_IMAGE = "banner_image";
	static final String JSON_TEMPLATE_DIR = "template_dir";
	static final String JSON_RED = "red";
	static final String JSON_BLUE = "blue";
	static final String JSON_GREEN = "green";
	static final String JSON_STYLE = "import_style";
	static final String JSON_EXPORT_DIR = "export_dir";
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
		DetailsData details = data.getDetailsData();

		JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();

		// NOW BUILD THE TA JSON OBJCTS TO SAVE
		saveTAData(teachingAssistant, jsonBuilder);

		// NOW BUILD THE RECITATION JSON OBJECTS TO SAVE
		saveRecitationData(recitation, jsonBuilder);

		// NOW BUILD THE PROJECT OBJECTS TO SAVE
		saveProjectData(project, jsonBuilder);

		// NOW BUILD THE SCHEDULE ITEM JSON OBJECTS TO SAVE
		saveScheduleData(schedule, jsonBuilder);

		// NOW BUILD THE DETAILS JSON OBJECTS TO SAVE
		saveDetailsData(details, jsonBuilder);

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

	private ArrayList<TimeSlot> saveTAData(TAData teachingAssistant, JsonObjectBuilder builder, String startTime, String endTime) {
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
		ArrayList<TimeSlot> removedTimeSlots = new ArrayList<>();
		JsonArrayBuilder timeSlotArrayBuilder = Json.createArrayBuilder();
		ArrayList<TimeSlot> officeHours = TimeSlot.buildOfficeHoursList(teachingAssistant);
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

		builder.add(JSON_START_HOUR, startTime)
				.add(JSON_END_HOUR, endTime)
				.add(JSON_UNDERGRAD_TAS, undergradTAsArray)
				.add(JSON_GRAD_TAS, gradTAsArray)
				.add(JSON_OFFICE_HOURS, timeSlotsArray);

		return removedTimeSlots;
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
					.add(JSON_TIME, s.getTime())
					.add(JSON_SI_TITLE, s.getTitle())
					.add(JSON_SI_TOPIC, s.getTopic())
					.add(JSON_SI_LINK, s.getLink())
					.add(JSON_SI_CRITERIA, s.getCriteria()).build();
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
		String templateDir = workspace.getTemplateDirL().getText();
		String exportDir = workspace.getSelectedExportDir().getText();

		File sheet = workspace.getStyleCB().getSelectionModel().getSelectedItem();
		String css = (sheet != null) ? sheet.getPath() : "";

		File temp = details.getImages().get(workspace.getLeftFImage());
		String leftImage = (temp == null) ? "" : temp.getPath();

		temp = details.getImages().get(workspace.getRightFImage());
		String rightImage = (temp == null) ? "" : temp.getPath();

		temp = details.getImages().get(workspace.getBannerImage());
		String bannerImage = (temp == null) ? "" : temp.getPath();

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

		builder.add(JSON_SUBJECT, (subject == null) ? "" : subject)
				.add(JSON_NUMBER, (number == null) ? "" : number)
				.add(JSON_SEMESTER, (semester == null) ? "" : semester)
				.add(JSON_YEAR, (year == null) ? "" : year)
				.add(JSON_TITLE, (title == null) ? "" : title)
				.add(JSON_EXPORT_DIR, (exportDir == null) ? "" : exportDir)
				.add(JSON_TEMPLATE_DIR, (templateDir))
				.add(JSON_LEFT_IMAGE, leftImage)
				.add(JSON_RIGHT_IMAGE, rightImage)
				.add(JSON_BANNER_IMAGE, bannerImage)
				.add(JSON_STYLE, css)
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
		DetailsData details = data.getDetailsData();

		// LOAD THE JSON FILE WITH ALL THE DATA
		JsonObject json = loadJSONFile(filePath);

		// LOAD ALL NECESSARY DATA
		loadTAData(teachingAssistant, json);
		loadRecitationData(recitation, json);
		loadProjectData(project, json);
		loadScheduleData(schedule, json);
		loadDetailsData(details, json);
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

	private void loadDetailsData(DetailsData details, JsonObject json) throws FileNotFoundException {
		CourseDetailsPane workspace = ((CSGWorkspace) app.getWorkspaceComponent()).getCourseDetailsPane();

		String subject = json.getString(JSON_SUBJECT);
		workspace.getSubjectCB().getSelectionModel().select(subject);
		String number = json.getString(JSON_NUMBER);
		workspace.getNumberCB().getSelectionModel().select(number);
		String semester = json.getString(JSON_SEMESTER);
		workspace.getSemesterCB().getSelectionModel().select(semester);
		String year = json.getString(JSON_YEAR);
		workspace.getYearCB().getSelectionModel().select(year);
		String title = json.getString(JSON_TITLE);
		workspace.getTitleTF().setText(title);
		String css = json.getString(JSON_STYLE);
		workspace.getStyleCB().getSelectionModel().select(details.getStyle(css));
		String export = json.getString(JSON_EXPORT_DIR);
		workspace.getSelectedExportDir().setText(export);

		String leftImage = json.getString(JSON_LEFT_IMAGE);
		if (!leftImage.equals("")) {
			File left = new File(leftImage);
			workspace.getLeftFImage().setImage(new Image(new FileInputStream(left)));
			details.getImages().put(workspace.getLeftFImage(), left);
		}

		String rightImage = json.getString(JSON_RIGHT_IMAGE);
		if (!rightImage.equals("")) {
			File right = new File(rightImage);
			workspace.getRightFImage().setImage(new Image(new FileInputStream(right)));
			details.getImages().put(workspace.getRightFImage(), right);
		}

		String bannerImage = json.getString(JSON_BANNER_IMAGE);
		if (!bannerImage.equals("")) {
			File banner = new File(bannerImage);
			workspace.getBannerImage().setImage(new Image(new FileInputStream(banner)));
			details.getImages().put(workspace.getBannerImage(), banner);
		}

		String instructorHome = json.getString(JSON_INSTRUCTOR_HOME);
		workspace.getInstructorHome().setText(instructorHome);
		String instructorName = json.getString(JSON_INSTRUCTOR_NAME);
		workspace.getInstructorName().setText(instructorName);
		String templateDir = json.getString(JSON_TEMPLATE_DIR);
		workspace.getTemplateDirL().setText(templateDir);

		if (!templateDir.equals("")) details.addDetails(templateDir);
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

	private void loadTAData(TAData teachingAssistant, JsonObject json, boolean bool) {
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
		loadTAData(data, json, true);
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
		JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
		// NOW BUILD THE TA JSON OBJCTS TO SAVE
		ArrayList<TimeSlot> removedTimeSlots = saveTAData(dataManager, jsonBuilder, startTime, endTime);

		writeJson(jsonBuilder.build(), "temp.json");

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

		File css = ((CSGWorkspace) app.getWorkspaceComponent()).getCourseDetailsPane().getStyleCB().getSelectionModel().getSelectedItem();
		if (css != null) {
			File temp = new File("temp");

			Files.copy(css.toPath(), temp.toPath(), StandardCopyOption.REPLACE_EXISTING);
			Files.copy(temp.toPath(), new File(dir.getPath() + "/" + css.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);

			temp.delete();
		}
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

	private void exportProjectData(ProjectData project, String filePath) throws FileNotFoundException {
		JsonObjectBuilder builder = Json.createObjectBuilder();

		JsonArrayBuilder teamArrayBuilder = Json.createArrayBuilder();
		ObservableList<Team> teams = project.getTeams();
		for (Team t : teams) {
			Color c = Color.web(t.getColor());
			JsonObject tJson = Json.createObjectBuilder()
					.add(JSON_T_NAME, "" + t.getName())
					.add(JSON_RED, "" + (int) (c.getRed() * 255))
					.add(JSON_BLUE, "" + (int) (c.getBlue() * 255))
					.add(JSON_GREEN, "" + (int) (c.getGreen() * 255))
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

		File temp = new File("temp");

		File l = new File(filePath + "/" + leftImage.getName());
		File r = new File(filePath + "/" + rightImage.getName());
		File b = new File(filePath + "/" + bannerImage.getName());

		Files.copy(leftImage.toPath(), temp.toPath(), StandardCopyOption.REPLACE_EXISTING);
		Files.copy(temp.toPath(), l.toPath(), StandardCopyOption.REPLACE_EXISTING);

		Files.copy(rightImage.toPath(), temp.toPath(), StandardCopyOption.REPLACE_EXISTING);
		Files.copy(temp.toPath(), r.toPath(), StandardCopyOption.REPLACE_EXISTING);

		Files.copy(bannerImage.toPath(), temp.toPath(), StandardCopyOption.REPLACE_EXISTING);
		Files.copy(temp.toPath(), b.toPath(), StandardCopyOption.REPLACE_EXISTING);
		temp.delete();
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
