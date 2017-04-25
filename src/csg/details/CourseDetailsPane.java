package csg.details;

import csg.CSGApp;
import csg.data.CSGData;
import csg.data.DetailsData;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;

import static csg.CSGAppProp.*;

import javafx.scene.control.cell.CheckBoxTableCell;

import java.io.File;

public class CourseDetailsPane extends VBox {
	VBox courseInfo, siteTemplate, pageStyle;
	Label infoL, subjectL, numberL, semesterL, yearL, titleL, instructorNameL, instructorHomeL, exportDirL, selectedExportDir;
	Label siteL, descriptionL, templateDirL, sitePagesL;
	Label pageStyleL, bannerSchoolImageL, leftFImageL, rightFImageL, styleL, noteL;
	ComboBox<String> subjectCB, numberCB, semesterCB, yearCB;
	TextField titleTF, instructorName, instructorHome;
	Button exportChangeButton, selectTemplateButton;
	TableView<Details> pages;
	TableColumn<Details, String> navbarCol, fileCol, scriptCol;
	TableColumn<Details, Boolean> useCol;
	ImageView bannerImage, leftFImage, rightFImage;
	ComboBox<File> styleCB;
	GridPane imageIn, comboIn, tfIn;
	Button bannerChange, leftChange, rightChange;
	HBox style;
	DetailsController controller;

	CSGApp app;

	public CourseDetailsPane(CSGApp initApp) {
		app = initApp;

		PropertiesManager props = PropertiesManager.getPropertiesManager();
		DetailsData data = ((CSGData) app.getDataComponent()).getDetailsData();

		infoL = new Label(props.getProperty(CD_INFO_TEXT));
		subjectL = new Label(props.getProperty(CD_SUBJECT_TEXT) + ":");
		subjectCB = new ComboBox<>(data.getSubjects());
		numberL = new Label(props.getProperty(CD_NUMBER_TEXT) + ":");
		numberCB = new ComboBox<>(data.getNumbers());
		semesterL = new Label(props.getProperty(CD_SEMESTER_TEXT) + ":");
		semesterCB = new ComboBox<>(data.getSemesters());
		yearL = new Label(props.getProperty(CD_YEAR_TEXT) + ":");
		yearCB = new ComboBox<>(data.getYears());
		titleL = new Label(props.getProperty(CD_TITLE_TEXT) + ":");
		titleTF = new TextField();
		instructorNameL = new Label(props.getProperty(CD_INSTRUCTOR_NAME_TEXT) + ":");
		instructorName = new TextField();
		instructorHomeL = new Label(props.getProperty(CD_INSTRUCTOR_HOME_TEXT) + ":");
		instructorHome = new TextField();
		exportDirL = new Label(props.getProperty(CD_EXPORT_TEXT) + ":");
		selectedExportDir = new Label();
		exportChangeButton = new Button(props.getProperty(CD_CHANGE));

		comboIn = new GridPane();
		comboIn.addRow(0, subjectL, subjectCB, numberL, numberCB);
		comboIn.addRow(1, semesterL, semesterCB, yearL, yearCB);
		comboIn.addRow(2, titleL, titleTF);
		comboIn.addRow(3, instructorNameL, instructorName);
		comboIn.addRow(4, instructorHomeL, instructorHome);
		comboIn.addRow(5, exportDirL, selectedExportDir, exportChangeButton);

		courseInfo = new VBox();
		courseInfo.getChildren().addAll(infoL, comboIn);

		siteL = new Label(props.getProperty(CD_SITE_TEXT) + ":");
		descriptionL = new Label(props.getProperty(CD_DESCRIPTION_TEXT));
		templateDirL = new Label();
		selectTemplateButton = new Button(props.getProperty(CD_TEMPLATE_DIR));
		sitePagesL = new Label(props.getProperty(CD_SITE_PAGES_TEXT) + ":");

		pages = new TableView<>(data.getDetails());
		pages.setEditable(true);

		navbarCol = new TableColumn<>(props.getProperty(CD_NAVBAR_TITLE));
		navbarCol.setCellValueFactory(
				new PropertyValueFactory<>("navbarTitle")
		);
		fileCol = new TableColumn<>(props.getProperty(CD_FILE_NAME));
		fileCol.setCellValueFactory(
				new PropertyValueFactory<>("fileName")
		);
		scriptCol = new TableColumn<>(props.getProperty(CD_SCRIPT));
		scriptCol.setCellValueFactory(
				new PropertyValueFactory<>("script")
		);
		useCol = new TableColumn<>(props.getProperty(CD_USE_COL_TEXT));
		useCol.setCellValueFactory(
				(TableColumn.CellDataFeatures<Details, Boolean> param) -> param.getValue().useProperty()
		);
		useCol.setCellFactory(CheckBoxTableCell.forTableColumn(useCol));
		pages.getColumns().addAll(useCol, navbarCol, fileCol, scriptCol);

		siteTemplate = new VBox();
		siteTemplate.getChildren().addAll(siteL, descriptionL, templateDirL, selectTemplateButton, sitePagesL, pages);

		pageStyleL = new Label(props.getProperty(CD_PAGE_STYLE_TEXT));
		bannerSchoolImageL = new Label(props.getProperty(CD_BANNER_SCHOOL_IMAGE_TEXT) + ":");
		bannerChange = new Button(props.getProperty(CD_CHANGE));
		bannerImage = new ImageView();
		leftFImageL = new Label(props.getProperty(CD_LEFT_FOOTER_IMAGE_TEXT) + ":");
		leftFImage = new ImageView();
		leftChange = new Button(props.getProperty(CD_CHANGE));
		rightFImageL = new Label(props.getProperty(CD_RIGHT_FOOTER_IMAGE_TEXT) + ":");
		rightFImage = new ImageView();
		rightChange = new Button(props.getProperty(CD_CHANGE));
		styleL = new Label(props.getProperty(CD_STYLESHEET_TEXT) + ":");
		styleCB = new ComboBox<>(data.getStylesheets());
		noteL = new Label(props.getProperty(CD_NOTE_TEXT));

		imageIn = new GridPane();
		imageIn.addRow(0, bannerSchoolImageL, bannerImage, bannerChange);
		imageIn.addRow(1, leftFImageL, leftFImage, leftChange);
		imageIn.addRow(2, rightFImageL, rightFImage, rightChange);
		style = new HBox();
		style.getChildren().addAll(styleL, styleCB);

		pageStyle = new VBox();
		pageStyle.getChildren().addAll(pageStyleL, imageIn, style, noteL);

		this.getChildren().add(courseInfo);
		this.getChildren().add(siteTemplate);
		this.getChildren().add(pageStyle);

		data.getImages().put(bannerImage, null);
		data.getImages().put(leftFImage, null);
		data.getImages().put(rightFImage, null);

		controller = new DetailsController(initApp);
		exportChangeButton.setOnAction(e -> controller.handleExportDirButton());
		selectTemplateButton.setOnAction(e -> controller.handleTemplateButton());
		bannerChange.setOnAction(e -> controller.handleBannerChange());
		leftChange.setOnAction(e -> controller.handleLeftImageChange());
		rightChange.setOnAction(e -> controller.handleRightImageChange());
		semesterCB.setOnAction(e -> change());
		subjectCB.setOnAction(e -> change());
		yearCB.setOnAction(e -> change());
		numberCB.setOnAction(e -> change());
		titleTF.setOnAction(e -> change());
		instructorName.setOnKeyTyped(e -> change());
		instructorHome.setOnKeyTyped(e -> change());
	}

	private void change() {
		app.getGUI().getFileController().markAsEdited(app.getGUI());
	}

	public VBox getCourseInfo() {
		return courseInfo;
	}

	public VBox getSiteTemplate() {
		return siteTemplate;
	}

	public VBox getPageStyle() {
		return pageStyle;
	}

	public Label getInfoL() {
		return infoL;
	}

	public Label getSiteL() {
		return siteL;
	}

	public Label getPageStyleL() {
		return pageStyleL;
	}

	public HBox getStylePane() {
		return style;
	}

	public Label getSelectedExportDir() {
		return selectedExportDir;
	}

	public Label getTemplateDirL() {
		return templateDirL;
	}

	public ImageView getBannerImage() {
		return bannerImage;
	}

	public ImageView getLeftFImage() {
		return leftFImage;
	}

	public ImageView getRightFImage() {
		return rightFImage;
	}

	public GridPane getImageIn() {
		return imageIn;
	}

	public GridPane getComboIn() {
		return comboIn;
	}

	public ComboBox<File> getStyleCB() {
		return styleCB;
	}

	public GridPane getTfIn() {
		return tfIn;
	}

	public Button getBannerChange() {
		return bannerChange;
	}

	public ComboBox<String> getSubjectCB() {
		return subjectCB;
	}

	public ComboBox<String> getNumberCB() {
		return numberCB;
	}

	public ComboBox<String> getSemesterCB() {
		return semesterCB;
	}

	public ComboBox<String> getYearCB() {
		return yearCB;
	}

	public TextField getTitleTF() {
		return titleTF;
	}

	public TextField getInstructorName() {
		return instructorName;
	}

	public TextField getInstructorHome() {
		return instructorHome;
	}

	public CSGApp getApp() {
		return app;
	}

	public void resetWorkspace() {
		selectedExportDir.setText("");
		templateDirL.setText("");
		titleTF.clear();
		instructorHome.clear();
		instructorName.clear();
		subjectCB.getSelectionModel().clearSelection();
		numberCB.getSelectionModel().clearSelection();
		yearCB.getSelectionModel().clearSelection();
		semesterCB.getSelectionModel().clearSelection();
		leftFImage.setImage(null);
		rightFImage.setImage(null);
		bannerImage.setImage(null);
	}

	public void reloadWorkspace() {
	}
}
