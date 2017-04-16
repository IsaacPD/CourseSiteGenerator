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
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class LoadTest {
	@Test
	public void testLoad() throws InterruptedException {
		CSGApp app = new CSGApp();
		Thread thread = new Thread(() -> {
			new JFXPanel(); // Initializes the JavaFx Platform
			Platform.runLater(() -> {
				app.start(new Stage());
				try {
					app.getFileComponent().loadData(app.getDataComponent(), "./work/SiteSaveTest.json");
				} catch (IOException e) {
					System.out.println("Hello");
				}

				Recitation r = ((CSGData) app.getDataComponent()).getRecitationData().getRecitations().get(0);
				Team t = ((CSGData) app.getDataComponent()).getProjectData().getTeams().get(0);
				Student s = ((CSGData) app.getDataComponent()).getProjectData().getStudents().get(0);
				ScheduleItem si = ((CSGData) app.getDataComponent()).getScheduleData().getSchedules().get(0);
				TeachingAssistant ta = ((CSGData) app.getDataComponent()).getTAData().getTeachingAssistants().get(0);

				assertEquals(TestSaveAndLoad.testR, r);
				assertEquals(TestSaveAndLoad.testSchedule, si);
				assertEquals(TestSaveAndLoad.testStudent, s);
				assertEquals(TestSaveAndLoad.testTA, ta);
				assertEquals(TestSaveAndLoad.testTeam, t);
			});
		});
		thread.start();
		Thread.sleep(10000);
	}
}