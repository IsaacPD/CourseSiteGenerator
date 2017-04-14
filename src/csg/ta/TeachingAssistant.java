package csg.ta;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TeachingAssistant<E extends Comparable<E>> implements Comparable<E> {
	// THE TABLE WILL STORE TA NAMES AND EMAILS
	private final StringProperty name;
	private final StringProperty email;
	private final BooleanProperty undergrad;

	/**
	 * Constructor initializes the TA name
	 */
	public TeachingAssistant(String initName, String initEmail) {
		name = new SimpleStringProperty(initName);
		email = new SimpleStringProperty(initEmail);
		undergrad = new SimpleBooleanProperty(false);
	}

	// ACCESSORS AND MUTATORS FOR THE PROPERTIES

	public String getName() {
		return name.get();
	}

	public String getEmail() {
		return email.get();
	}

	public void setName(String initName) {
		name.set(initName);
	}

	public void setEmail(String initEmail) {
		email.set(initEmail);
	}

	public BooleanProperty undergradProperty() {
		return undergrad;
	}

	public boolean getUndergrad() {
		return undergrad.getValue();
	}

	public void setUndergrad(boolean isUG) {
		undergrad.set(isUG);
	}

	@Override
	public int compareTo(E otherTA) {
		return getName().compareTo(((TeachingAssistant) otherTA).getName());
	}

	@Override
	public String toString() {
		return name.getValue() + ", " + email.getValue();
	}
}
