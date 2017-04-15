import csg.CSGApp;
import csg.data.CSGData;
import csg.project.Student;
import csg.project.Team;
import csg.recitation.Recitation;
import csg.schedule.ScheduleItem;
import csg.ta.TeachingAssistant;

import java.io.IOException;

public class TestSave extends CSGApp{

	@Override
	public void buildAppComponentsHook() {
		super.buildAppComponentsHook();
		gui.getFileController().handleNewRequest();

		((CSGData) dataComponent).getRecitationData().addRecitation(new Recitation("23", "Prof. Duarte",
				"Tuesday", "OLD CS", "Isaac", "Duarte"));
		((CSGData) dataComponent).getTAData().addTA(new TeachingAssistant("Isaac Duarte", "i@you.com"));
		((CSGData) dataComponent).getTAData().addOfficeHoursReservation("MONDAY", "10_00amm", "Isaac");
		Team t = new Team("ROBOBOTS", "FFFFFF", "000000", "www.youtube.com");
		((CSGData) dataComponent).getProjectData().addTeam(t);
		((CSGData) dataComponent).getProjectData().addStudent(new Student("Isaac", "Pablo", t, "Cool Guy"));
		((CSGData) dataComponent).getScheduleData().addSchedule(new ScheduleItem("Holiday", "10/31/17", "HallowBEEN", "SCARY"));

		try {
			getFileComponent().saveData(getDataComponent(), "./work/SiteSaveTest.json");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String... args){
		launch();
	}
}
