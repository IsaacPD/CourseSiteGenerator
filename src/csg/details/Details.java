package csg.details;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

public class Details {
	private final BooleanProperty use;
	private final StringProperty navbarTitle, fileName, script;

	private Details(String navbarTitle, String fileName, String script) {
		this.navbarTitle = new SimpleStringProperty(navbarTitle);
		this.fileName = new SimpleStringProperty(fileName);
		this.script = new SimpleStringProperty(script);
		use = new SimpleBooleanProperty(false);
	}

	public boolean isUse() {
		return use.get();
	}

	public String getNavbarTitle() {
		return navbarTitle.get();
	}

	public String getFileName() {
		return fileName.get();
	}

	public String getScript() {
		return script.get();
	}

	public BooleanProperty useProperty() {
		return use;
	}

	public static class DetailsBuilder {
		private String navbarTitle, fileName, script;

		public DetailsBuilder() {
			navbarTitle = "";
			fileName = "";
			script = "";
		}

		public void addNavbarTitle(String title) {
			navbarTitle = title;
		}

		public void addFileName(String name) {
			fileName = name;
		}

		public void addScript(String script) {
			this.script = script;
		}

		public Details build() {
			return new Details(navbarTitle, fileName, script);
		}
	}
}
