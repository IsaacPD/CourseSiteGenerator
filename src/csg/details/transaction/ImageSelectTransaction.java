package csg.details.transaction;

import csg.CSGApp;
import csg.data.CSGData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ImageSelectTransaction implements jtps.jTPS_Transaction {
	ImageView pane;
	File selected, prev;
	CSGApp app;

	public ImageSelectTransaction(ImageView pane, File selected, File prev, CSGApp app) {
		this.pane = pane;
		this.selected = selected;
		this.prev = prev;
		this.app = app;
	}

	@Override
	public void doTransaction() {
		try {
			pane.setImage(new Image(new FileInputStream(selected)));
		} catch (Exception e) {
			pane.setImage(null);
		}
		((CSGData) app.getDataComponent()).getDetailsData().getImages().put(pane, selected);

		File temp = selected;
		selected = prev;
		prev = temp;

		app.getGUI().getFileController().markAsEdited(app.getGUI());
	}

	@Override
	public void undoTransaction() {
		doTransaction();
	}
}
