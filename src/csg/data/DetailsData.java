package csg.data;

import csg.details.Details;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.HashMap;

public class DetailsData {
	ObservableList<String> subjects, years, semesters, numbers;
	ObservableList<File> stylesheets;
	ObservableList<Details> details;
	HashMap<ImageView, File> images;

	public DetailsData() {
		File style = new File("./work/css");
		File[] sheets = style.listFiles();

		images = new HashMap<>();
		details = FXCollections.observableArrayList();
		stylesheets = FXCollections.observableArrayList(sheets);
		subjects = FXCollections.observableArrayList("CSE");
		years = FXCollections.observableArrayList("2017", "2018");
		semesters = FXCollections.observableArrayList("Fall", "Spring", "Winter", "Summer");
		numbers = FXCollections.observableArrayList("219", "308", "380");
	}

	public ObservableList<String> getYears() {
		return years;
	}

	public ObservableList<String> getSemesters() {
		return semesters;
	}

	public ObservableList<String> getNumbers() {
		return numbers;
	}

	public ObservableList<String> getSubjects() {
		return subjects;
	}

	public ObservableList<Details> getDetails() {
		return details;
	}

	public ObservableList<File> getStylesheets() {
		return stylesheets;
	}

	public HashMap<ImageView, File> getImages() {
		return images;
	}

	public void addDetails(String filePath) {
		File dir = new File(filePath);

		for (File f : dir.listFiles()) {
			if (f.getName().contains(".html")) {
				Details.DetailsBuilder detailsBuilder = new Details.DetailsBuilder();
				String name = f.getName().toLowerCase().substring(0, f.getName().indexOf(".html"));

				detailsBuilder.addFileName(f.getName());
				detailsBuilder.addNavbarTitle(name);

				String path = filePath + "/" + name + "_files";
				File files = new File(path);
				for (File data : files.listFiles()) {
					if (data.getName().contains(".js") && data.getName().contains("Builder")) {
						detailsBuilder.addScript(data.getName());
						break;
					}
				}
				details.add(detailsBuilder.build());
			}
		}
	}

	public void resetData() {
		images.clear();
		details.clear();
	}

	public File getStyle(String css) {
		String[] n = css.replace('\\', '/').split("/");
		String name = n[n.length - 1];
		for (File f : stylesheets) {
			if (f.getName().equals(name))
				return f;
		}
		return null;
	}
}
