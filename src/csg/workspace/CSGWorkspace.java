package csg.workspace;

import csg.CSGApp;
import csg.ta.TeachingAssistantPane;
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
		tabSpace.getTabs().add(new Tab("Teaching Assistant", new TeachingAssistantPane(initApp)));

		controller = new CSGController(initApp);
		workspace = new Pane();
		workspace.getChildren().add(tabSpace);

		workspace.setOnKeyPressed(e ->{
			controller.handleKeyPress(e);
		});
	}

	@Override
	public void resetWorkspace() {

	}

	@Override
	public void reloadWorkspace(AppDataComponent appDataComponent) {

	}
}
