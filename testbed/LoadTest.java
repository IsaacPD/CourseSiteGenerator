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
		final Recitation[] r = new Recitation[1];
		final Team[] t = new Team[1];
		final Student[] s = new Student[1];
		final ScheduleItem[] si = new ScheduleItem[1];
		final TeachingAssistant[] ta = new TeachingAssistant[1];

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

				r[0] = ((CSGData) app.getDataComponent()).getRecitationData().getRecitations().get(0);
				t[0] = ((CSGData) app.getDataComponent()).getProjectData().getTeams().get(0);
				s[0] = ((CSGData) app.getDataComponent()).getProjectData().getStudents().get(0);
				si[0] = ((CSGData) app.getDataComponent()).getScheduleData().getSchedules().get(0);
				ta[0] = ((CSGData) app.getDataComponent()).getTAData().getTeachingAssistants().get(0);
			});
		});
		thread.start();
		Thread.sleep(10000);
		assertEquals(TestSave.testR, r[0]);
		assertEquals(TestSave.testSchedule, si[0]);
		assertEquals(TestSave.testStudent, s[0]);
		assertEquals(TestSave.testTA, ta[0]);
		assertEquals(TestSave.testTeam, t[0]);
	}
}