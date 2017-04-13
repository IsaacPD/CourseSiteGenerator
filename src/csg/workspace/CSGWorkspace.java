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
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;

import static csg.CSGAppProp.*;

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
		PropertiesManager properties = PropertiesManager.getPropertiesManager();
		app = initApp;

		taPane = new TeachingAssistantPane(initApp);
		cdPane = new CourseDetailsPane(initApp);
		rPane = new RecitationPane(initApp);
		sPane = new SchedulePane(initApp);
		pPane = new ProjectPane(initApp);

		tabSpace = new TabPane();
		tabSpace.setMaxHeight(800);
		tabSpace.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
		tabSpace.getTabs().add(new Tab(properties.getProperty(TAB_COURSE_DETAILS), cdPane));
		tabSpace.getTabs().add(new Tab(properties.getProperty(TAB_TA_DATA), taPane));
		tabSpace.getTabs().add(new Tab(properties.getProperty(TAB_RECITATION_DATA), rPane));
		tabSpace.getTabs().add(new Tab(properties.getProperty(TAB_SCHEDULE_DATA), sPane));
		tabSpace.getTabs().add(new Tab(properties.getProperty(TAB_PROJECT_DATA), pPane));

		controller = new CSGController(initApp);
		workspace = new VBox();
		workspace.getChildren().add(tabSpace);

		tabSpace.tabMinWidthProperty().bind(workspace.widthProperty().divide(6));

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

	public RecitationPane getRecitationPane() {
		return (RecitationPane) tabSpace.getTabs().get(2).getContent();
	}
}
