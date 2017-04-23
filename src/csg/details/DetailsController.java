package csg.details;

import csg.CSGApp;
import csg.data.CSGData;
import csg.workspace.CSGWorkspace;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static djf.settings.AppStartupConstants.PATH_IMAGES;
import static djf.settings.AppStartupConstants.PATH_WORK;

public class DetailsController {
	CSGApp app;

	public DetailsController(CSGApp initApp) {
		app = initApp;
	}

	public void handleExportDirButton() {
		DirectoryChooser dc = new DirectoryChooser();
		dc.setInitialDirectory(new File(PATH_WORK));
		File selection = dc.showDialog(app.getGUI().getWindow());

		CourseDetailsPane workspace = ((CSGWorkspace) app.getWorkspaceComponent()).getCourseDetailsPane();
		if (selection != null) {
			workspace.getSelectedExportDir().setText(selection.getPath());
		}
	}


	public void handleTemplateButton() {
		DirectoryChooser dc = new DirectoryChooser();
		dc.setInitialDirectory(new File("./templates"));
		File selection = dc.showDialog(app.getGUI().getWindow());

		CourseDetailsPane workspace = ((CSGWorkspace) app.getWorkspaceComponent()).getCourseDetailsPane();
		if (selection != null) {
			workspace.getTemplateDirL().setText(selection.getPath());
			((CSGData) app.getDataComponent()).getDetailsData().addDetails(selection.getPath());
		}
	}

	public void handleBannerChange() {
		CourseDetailsPane workspace = ((CSGWorkspace) app.getWorkspaceComponent()).getCourseDetailsPane();
		ImageView pane = workspace.getBannerImage();
		try {
			addImage(pane);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void handleLeftImageChange() {
		CourseDetailsPane workspace = ((CSGWorkspace) app.getWorkspaceComponent()).getCourseDetailsPane();
		ImageView pane = workspace.getLeftFImage();
		try {
			addImage(pane);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void handleRightImageChange() {
		CourseDetailsPane workspace = ((CSGWorkspace) app.getWorkspaceComponent()).getCourseDetailsPane();
		ImageView pane = workspace.getRightFImage();
		try {
			addImage(pane);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void addImage(ImageView pane) throws FileNotFoundException {
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File(PATH_IMAGES));
		File selection = fc.showOpenDialog(app.getGUI().getWindow());
		pane.setImage(new Image(new FileInputStream(selection)));
		((CSGData) app.getDataComponent()).getDetailsData().getImages().replace(pane, selection);
	}
}
