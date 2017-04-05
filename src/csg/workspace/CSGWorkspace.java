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

	TeachingAssistantPane taPane;
	CourseDetailsPane cdPane;
	SchedulePane sPane;
	ProjectPane pPane;
	RecitationPane rPane;

	public CSGWorkspace(CSGApp initApp) {
		app = initApp;

		taPane = new TeachingAssistantPane(initApp);
		cdPane = new CourseDetailsPane(initApp);
		rPane = new RecitationPane(initApp);
		sPane = new SchedulePane(initApp);
		pPane = new ProjectPane(initApp);

		tabSpace = new TabPane();
		tabSpace.getTabs().add(new Tab("Course Details", cdPane));
		tabSpace.getTabs().add(new Tab("TA Data", taPane));
		tabSpace.getTabs().add(new Tab("Recitation Data", rPane));
		tabSpace.getTabs().add(new Tab("Schedule Data", sPane));
		tabSpace.getTabs().add(new Tab("Project Data", pPane));

		controller = new CSGController(initApp);
		workspace = new Pane();
		workspace.getChildren().add(tabSpace);

		workspace.setOnKeyPressed(e -> {
			controller.handleKeyPress(e);
		});
	}

	public TeachingAssistantPane getTeachingAssistantPane() {
		return (TeachingAssistantPane) tabSpace.getTabs().get(1).getContent();
	}

	public TabPane getTabSpace() {
		return tabSpace;
	}

	@Override
	public void resetWorkspace() {
		taPane.resetWorkspace();
	}

	@Override
	public void reloadWorkspace(AppDataComponent appDataComponent) {
		taPane.reloadWorkspace();
	}
}
