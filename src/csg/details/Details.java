package csg.details;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Details {
	private final BooleanProperty use;

	public Details() {
		use = new SimpleBooleanProperty(false);
	}

	public BooleanProperty useProperty() {
		return use;
	}
}
