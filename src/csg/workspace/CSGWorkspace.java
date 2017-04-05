package csg.workspace;

import csg.CSGApp;
import csg.details.CourseDetailsPane;
import csg.project.ProjectPane;
import csg.recitation.RecitationPane;
import csg.ta.TeachingAssistantPane;
import csg.schedule.SchedulePane;
import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;

public class CSGWorkspace extends AppWorkspaceComponent {
	TabPane tabSpace;
	CSGController controller;
	CSGApp app;

	public CSGWorkspace(CSGApp initApp){
		app = initApp;
		tabSpace = new TabPane();
		tabSpace.getTabs().add(new Tab("Course Details", new CourseDetailsPane(initApp)));
		tabSpace.getTabs().add(new Tab("TA Data", new TeachingAssistantPane(initApp)));
		tabSpace.getTabs().add(new Tab("Recitation Data", new RecitationPane(initApp)));
		tabSpace.getTabs().add(new Tab("Schedule Data", new SchedulePane(initApp)));
		tabSpace.getTabs().add(new Tab("Project Data", new ProjectPane(initApp)));

		controller = new CSGController(initApp);
		workspace = new Pane();
		workspace.getChildren().add(tabSpace);

		workspace.setOnKeyPressed(e ->{
			controller.handleKeyPress(e);
		});
	}

	public TeachingAssistantPane getTeachingAssistantPane(){
		return (TeachingAssistantPane) tabSpace.getTabs().get(1).getContent();
	}

	public TabPane getTabSpace() {
		return tabSpace;
	}

	@Override
	public void resetWorkspace() {

	}

	@Override
	public void reloadWorkspace(AppDataComponent appDataComponent) {

	}
}
