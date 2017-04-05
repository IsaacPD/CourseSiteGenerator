package csg.details;

import csg.CSGApp;
import csg.data.CSGData;
import csg.data.DetailsData;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;
import static csg.CSGAppProp.*;

import java.io.File;

public class CourseDetailsPane extends VBox{
	VBox courseInfo, siteTemplate, pageStyle;
	Label infoL, subjectL, numberL, semesterL, yearL, titleL, instructorNameL, instructorHomeL, exportDirL;
	Label siteL, descriptionL, templateDirL, sitePagesL;
	Label pageStyleL, bannerSchoolImageL, leftFImageL, rightFImageL, styleL, noteL;
	ComboBox<String> subjectCB, numberCB, semesterCB, yearCB;
	TextField titleTF, instructorName, instructorHome;
	Button exportChangeButton, selectTemplateButton;
	TableView<Details> pages;
	TableColumn<Details, String> navbarCol, fileCol, scriptCol;
	ImageView bannerImage, leftFImage, rightFImage;
	ComboBox<String> styleCB;
	Button bannerChange, leftChange, rightChange;
	HBox subNumBox, semYearBox, titleBox, instrNameBox, instrHomeBox, exporBox;
	HBox banner, leftFoot, rightFoot, style;

	CSGApp app;

	public CourseDetailsPane(CSGApp initApp){
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
		exportChangeButton = new Button("Change");

		subNumBox = new HBox();
		subNumBox.getChildren().addAll(subjectL, subjectCB, numberL, numberCB);
		semYearBox = new HBox();
		semYearBox.getChildren().addAll(semesterL, semesterCB, yearL, yearCB);
		titleBox = new HBox();
		titleBox.getChildren().addAll(titleL, titleTF);
		instrNameBox = new HBox();
		instrNameBox.getChildren().addAll(instructorNameL, instructorName);
		instrHomeBox = new HBox();
		instrHomeBox.getChildren().addAll(instructorHomeL, instructorHome);
		exporBox = new HBox();
		exporBox.getChildren().addAll(exportDirL, exportChangeButton);

		courseInfo = new VBox();
		courseInfo.getChildren().addAll(infoL, subNumBox, semYearBox,
				titleBox, instrNameBox, instrHomeBox, exporBox);

		siteL = new Label(props.getProperty(CD_SITE_TEXT) + ":");
		descriptionL = new Label(props.getProperty(CD_DESCRIPTION_TEXT));
		templateDirL = new Label(props.getProperty(CD_TEMPLATE_TEXT));
		selectTemplateButton = new Button("Select Template Directory");
		sitePagesL = new Label(props.getProperty(CD_SITE_PAGES_TEXT) + ":");

		pages = new TableView<>(data.getDetails());

		navbarCol = new TableColumn<>(props.getProperty(CD_NAVBAR_TITLE));
		navbarCol.setCellValueFactory(
				new PropertyValueFactory<>("navbarTitle")
		);
		fileCol = new TableColumn<>(props.getProperty(CD_FILE_NAME));
		fileCol.setCellValueFactory(
				new PropertyValueFactory<>("file")
		);
		scriptCol = new TableColumn<>(props.getProperty(CD_SCRIPT));
		scriptCol.setCellValueFactory(
				new PropertyValueFactory<>("script")
		);
		pages.getColumns().addAll(navbarCol, fileCol, scriptCol);

		siteTemplate = new VBox();
		siteTemplate.getChildren().addAll(siteL, descriptionL, templateDirL, selectTemplateButton, sitePagesL, pages);

		pageStyleL = new Label(props.getProperty(CD_PAGE_STYLE_TEXT));
		bannerSchoolImageL = new Label(props.getProperty(CD_BANNER_SCHOOL_IMAGE_TEXT) + ":");
		bannerChange = new Button("Change");
		bannerImage = new ImageView();
		leftFImageL = new Label(props.getProperty(CD_LEFT_FOOTER_IMAGE_TEXT) + ":");
		leftFImage = new ImageView();
		leftChange = new Button("Change");
		rightFImageL = new Label(props.getProperty(CD_RIGHT_FOOTER_IMAGE_TEXT) + ":");
		rightFImage = new ImageView();
		rightChange = new Button("Change");
		styleL = new Label(props.getProperty(CD_STYLESHEET_TEXT) + ":");
		styleCB = new ComboBox<>(data.getStylesheets());
		noteL = new Label(props.getProperty(CD_NOTE_TEXT));

		banner = new HBox();
		banner.getChildren().addAll(bannerSchoolImageL, bannerImage, bannerChange);
		leftFoot = new HBox();
		leftFoot.getChildren().addAll(leftFImageL, leftFImage, leftChange);
		rightFoot = new HBox();
		rightFoot.getChildren().addAll(rightFImageL, rightFImage, rightChange);
		style = new HBox();
		style.getChildren().addAll(styleL, styleCB);

		pageStyle = new VBox();
		pageStyle.getChildren().addAll(pageStyleL, banner, leftFoot, rightFoot, style, noteL);

		this.getChildren().add(courseInfo);
		this.getChildren().add(siteTemplate);
		this.getChildren().add(pageStyle);
	}
}
