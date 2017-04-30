import csg.CSGApp;
import csg.data.CSGData;
import csg.project.Student;
import csg.project.Team;
import csg.recitation.Recitation;
import csg.schedule.ScheduleItem;
import csg.ta.TeachingAssistant;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;

import java.io.IOException;

public class TestSave {
	static Recitation testR = new Recitation("23", "Prof. Duarte", "Tuesday", "OLD CS", "Isaac", "Duarte");
	static TeachingAssistant testTA = new TeachingAssistant("Isaac Duarte", "i@you.com", true);
	static Team testTeam = new Team("ROBOBOTS", "#9980E6", "#990000", "www.youtube.com");
	static Student testStudent = new Student("Isaac", "Pablo", testTeam.getName(), "Lead Designer");
	static ScheduleItem testSchedule = new ScheduleItem("Holiday", "4/18/18","12:00am", "HallowBEEN", "SCARY", "FUN", "https://www.google.com");

	public static void main(String... args) throws InterruptedException {
		CSGApp app = new CSGApp();

		Thread thread = new Thread(() -> {
			new JFXPanel(); // Initializes the JavaFx Platform
			Platform.runLater(() -> {
				app.start(new Stage());
				CSGData dataComponent = (CSGData) app.getDataComponent();
				dataComponent.getRecitationData().addRecitation(testR);
				dataComponent.getTAData().addTA(testTA);
				dataComponent.getTAData().addOfficeHoursReservation("MONDAY", "10_00amm", "Isaac");
				dataComponent.getProjectData().addTeam(testTeam);
				dataComponent.getProjectData().addStudent(testStudent);
				dataComponent.getScheduleData().addSchedule(testSchedule);

				try {
					app.getFileComponent().saveData(dataComponent, "./work/SiteSaveTest.json");
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		});
		thread.start();
		Thread.sleep(10000);
	}
}
