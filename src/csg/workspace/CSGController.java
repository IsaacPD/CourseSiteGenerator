package csg.workspace;

import csg.CSGApp;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class CSGController {
    CSGApp app;

    public CSGController(CSGApp initApp){
            app = initApp;
    }

    public void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.Z && event.isControlDown()){
                app.jtps.undoTransaction();
        }
        else if (event.getCode() == KeyCode.Y && event.isControlDown()){
                app.jtps.doTransaction();
        }
    }
}
