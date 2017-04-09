package csg;

import csg.data.CSGData;
import csg.file.CSGFiles;
import csg.workspace.CSGWorkspace;
import csg.style.CSGStyle;
import djf.AppTemplate;

import java.util.Locale;

public class CSGApp extends AppTemplate {
	@Override
	public void buildAppComponentsHook() {
		dataComponent = new CSGData(this);
		workspaceComponent = new CSGWorkspace(this);
		fileComponent = new CSGFiles(this);
		styleComponent = new  CSGStyle(this);
	}

	public static void main(String... args){
		Locale.setDefault(Locale.US);
		launch(args);
	}
}
