package csg.style;

import csg.recitation.RecitationPane;
import csg.ta.TeachingAssistant;
import csg.ta.TeachingAssistantPane;
import csg.workspace.CSGWorkspace;
import djf.components.AppStyleComponent;
import djf.AppTemplate;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.HashMap;
import javafx.scene.control.ScrollPane;

public class CSGStyle extends AppStyleComponent {
    // WE'LL USE THIS FOR ORGANIZING LEFT AND RIGHT CONTROLS
    public static String CLASS_PLAIN_PANE = "plain_pane";

    // THESE ARE THE HEADERS FOR EACH SIDE
    public static String CLASS_HEADER_PANE = "header_pane";
    public static String CLASS_HEADER_LABEL = "header_label";

    // ON THE LEFT WE HAVE THE TA ENTRY
    public static String CLASS_TABLE = "table";
    public static String CLASS_TABLE_COLUMN_HEADER = "table_column_header";
    public static String CLASS_ADD_TA_PANE = "add_ta_pane";
    public static String CLASS_ADD_TA_TEXT_FIELD = "add_ta_text_field";
    public static String CLASS_ADD_TA_BUTTON = "add_ta_button";
    public static String CLASS_CLEAR_TA_BUTTON = "clear_ta_button";

    // ON THE RIGHT WE HAVE THE OFFICE HOURS GRID
    public static String CLASS_OFFICE_HOURS_GRID = "office_hours_grid";
    public static String CLASS_OFFICE_HOURS_GRID_TIME_COLUMN_HEADER_PANE = "office_hours_grid_time_column_header_pane";
    public static String CLASS_OFFICE_HOURS_GRID_TIME_COLUMN_HEADER_LABEL = "office_hours_grid_time_column_header_label";
    public static String CLASS_OFFICE_HOURS_GRID_DAY_COLUMN_HEADER_PANE = "office_hours_grid_day_column_header_pane";
    public static String CLASS_OFFICE_HOURS_GRID_DAY_COLUMN_HEADER_LABEL = "office_hours_grid_day_column_header_label";
    public static String CLASS_OFFICE_HOURS_GRID_TIME_CELL_PANE = "office_hours_grid_time_cell_pane";
    public static String CLASS_OFFICE_HOURS_GRID_TIME_CELL_LABEL = "office_hours_grid_time_cell_label";
    public static String CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE = "office_hours_grid_ta_cell_pane";
    public static String CLASS_OFFICE_HOURS_GRID_TA_CELL_LABEL = "office_hours_grid_ta_cell_label";

    public static String CLASS_TAB = "tab";
    public static String CLASS_VBOX = "vbox";
    public static String CLASS_RPANE = "rpane";
    public static String CLASS_LABEL = "label";
    public static String CLASS_SCROLL = "scroll_pane";

    // THIS PROVIDES ACCESS TO OTHER COMPONENTS
    private AppTemplate app;

    public CSGStyle(AppTemplate initApp) {
            // KEEP THIS FOR LATER
            app = initApp;

            // LET'S USE THE DEFAULT STYLESHEET SETUP
            super.initStylesheet(app);

            // INIT THE STYLE FOR THE FILE TOOLBAR
            app.getGUI().initFileToolbarStyle();

            // AND NOW OUR WORKSPACE STYLE
            initCSGWorkspaceStyle();
    }

    private void initCSGWorkspaceStyle(){
            CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
            workspace.getTabSpace().getStyleClass().add(CLASS_TAB);
            workspace.getWorkspace().getStyleClass().add(CLASS_BORDERED_PANE);

            initRecitationStyle();
            initProjectStyle();
            initDetailsStyle();
            initScheduleStyle();
            initTAStyle();
    }

    public void initRecitationStyle(){
            RecitationPane workspace = ((CSGWorkspace) app.getWorkspaceComponent()).getRecitationPane();

            workspace.getStyleClass().add(CLASS_RPANE);
            workspace.getInputBox().getStyleClass().add(CLASS_VBOX);
            workspace.getSectionL().getStyleClass().add(CLASS_LABEL);
    }

    public void initProjectStyle(){

    }

    public void initDetailsStyle(){

    }

    public void initScheduleStyle(){

    }

    public void initTAStyle(){
            TeachingAssistantPane workspace = ((CSGWorkspace) app.getWorkspaceComponent()).getTeachingAssistantPane();
            workspace.getTaHeader().getStyleClass().add(CLASS_HEADER_PANE);
            workspace.getTaLabel().getStyleClass().add(CLASS_HEADER_LABEL);

            TableView<TeachingAssistant> fuckmeaidiniwantyoutoputitinmypussie = workspace.getTaTable();
            fuckmeaidiniwantyoutoputitinmypussie.getStyleClass().add(CLASS_TABLE);
            for(TableColumn tableColumn: fuckmeaidiniwantyoutoputitinmypussie.getColumns()){
                    tableColumn.getStyleClass().add(CLASS_TABLE_COLUMN_HEADER);
            }

            workspace.getUserInput().getStyleClass().add(CLASS_ADD_TA_PANE);
            workspace.getNameTF().getStyleClass().add(CLASS_ADD_TA_TEXT_FIELD);
            workspace.getAdd().getStyleClass().add(CLASS_ADD_TA_BUTTON);
            workspace.getClear().getStyleClass().add(CLASS_CLEAR_TA_BUTTON);

            // RIGHT SIDE - THE HEADER
            workspace.getOfficeHoursHeader().getStyleClass().add(CLASS_HEADER_PANE);
            workspace.getOfficeHoursGridLabel().getStyleClass().add(CLASS_HEADER_LABEL);

            initTAOfficeHoursGridStyle();
    }

    public void initTAOfficeHoursGridStyle(){
            TeachingAssistantPane taPane = ((CSGWorkspace) app.getWorkspaceComponent()).getTeachingAssistantPane();
            taPane.getOfficeHoursGrid().getStyleClass().add(CLASS_OFFICE_HOURS_GRID);
            setStyleClassOnAll(taPane.getOfficeHoursGridTimeHeaderPanes(), CLASS_OFFICE_HOURS_GRID_TIME_COLUMN_HEADER_PANE);
            setStyleClassOnAll(taPane.getOfficeHoursGridTimeHeaderLabels(), CLASS_OFFICE_HOURS_GRID_TIME_COLUMN_HEADER_LABEL);
            setStyleClassOnAll(taPane.getOfficeHoursGridDayHeaderPanes(), CLASS_OFFICE_HOURS_GRID_DAY_COLUMN_HEADER_PANE);
            setStyleClassOnAll(taPane.getOfficeHoursGridDayHeaderLabels(), CLASS_OFFICE_HOURS_GRID_DAY_COLUMN_HEADER_LABEL);
            setStyleClassOnAll(taPane.getOfficeHoursGridTimeCellPanes(), CLASS_OFFICE_HOURS_GRID_TIME_CELL_PANE);
            setStyleClassOnAll(taPane.getOfficeHoursGridTimeCellLabels(), CLASS_OFFICE_HOURS_GRID_TIME_CELL_LABEL);
            setStyleClassOnAll(taPane.getOfficeHoursGridTACellPanes(), CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE);
            setStyleClassOnAll(taPane.getOfficeHoursGridTACellLabels(), CLASS_OFFICE_HOURS_GRID_TA_CELL_LABEL);
    }

    private void setStyleClassOnAll(HashMap nodes, String styleClass) {
            for (Object nodeObject : nodes.values()) {
                    Node n = (Node)nodeObject;
                    n.getStyleClass().add(styleClass);
            }
    }
}
