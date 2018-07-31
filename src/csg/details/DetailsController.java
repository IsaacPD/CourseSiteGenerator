package csg.details;

import csg.CSGApp;
import csg.data.CSGData;
import csg.details.transaction.EditLabelTransaction;
import csg.details.transaction.EditTemplateTransaction;
import csg.details.transaction.ImageSelectTransaction;
import csg.workspace.CSGWorkspace;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;

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
			String newPath = selection.getPath();
			String oldPath = workspace.getSelectedExportDir().getText();
			app.jtps.addTransaction(new EditLabelTransaction(workspace.getSelectedExportDir(), oldPath, newPath, app));
		}
	}

	public void handleTemplateButton() {
		DirectoryChooser dc = new DirectoryChooser();
		dc.setInitialDirectory(new File("./templates"));
		File selection = dc.showDialog(app.getGUI().getWindow());

		CourseDetailsPane workspace = ((CSGWorkspace) app.getWorkspaceComponent()).getCourseDetailsPane();
		if (selection != null) {
			String newPath = selection.getPath();
			String oldPath = workspace.getTemplateDirL().getText();
			app.jtps.addTransaction(new EditTemplateTransaction(workspace.getTemplateDirL(), oldPath, newPath, app));
		}
	}

	public void handleBannerChange() {
		CourseDetailsPane workspace = ((CSGWorkspace) app.getWorkspaceComponent()).getCourseDetailsPane();
		ImageView pane = workspace.getBannerImage();
		addImage(pane);
	}

	public void handleLeftImageChange() {
		CourseDetailsPane workspace = ((CSGWorkspace) app.getWorkspaceComponent()).getCourseDetailsPane();
		ImageView pane = workspace.getLeftFImage();
		addImage(pane);
	}

	public void handleRightImageChange() {
		CourseDetailsPane workspace = ((CSGWorkspace) app.getWorkspaceComponent()).getCourseDetailsPane();
		ImageView pane = workspace.getRightFImage();
		addImage(pane);
	}

	private void addImage(ImageView pane) {
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File(PATH_IMAGES));
		File selection = fc.showOpenDialog(app.getGUI().getWindow());
		if (selection != null) {
			File prev = ((CSGData) app.getDataComponent()).getDetailsData().getImages().get(pane);
			app.jtps.addTransaction(new ImageSelectTransaction(pane, selection, prev, app));
		}
	}
}
