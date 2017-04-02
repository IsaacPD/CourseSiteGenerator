package csg;

import csg.data.CSGData;
import csg.file.CSGFiles;
import csg.workspace.CSGWorkspace;
import csg.style.CSGStyle;
import djf.AppTemplate;
import jtps.jTPS;

import java.util.Locale;

public class CSGApp extends AppTemplate {
	jTPS jTPS = new jTPS();

	@Override
	public void buildAppComponentsHook() {
		dataComponent = new CSGData();
		workspaceComponent = new CSGWorkspace(this);
		fileComponent = new CSGFiles();
		styleComponent = new  CSGStyle();
	}

	public static void main(String... args){
		Locale.setDefault(Locale.US);
		launch(args);
	}
}
