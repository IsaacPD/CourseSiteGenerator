package csg.data;

import csg.ta.TeachingAssistant;
import djf.components.AppDataComponent;
import javafx.collections.ObservableList;

public class CSGData implements AppDataComponent {
	private ObservableList<TeachingAssistant> teachingAssistants;
	private ObservableList<String> times;

	@Override
	public void resetData() {

	}

	public ObservableList<TeachingAssistant> getTeachingAssistants() {
		return teachingAssistants;
	}

	public ObservableList<String> getTimes() {
		return times;
	}
}
