package csg.data;

import djf.components.AppDataComponent;

public class CSGData implements AppDataComponent {
	private ScheduleData scheduleData;
	private TAData taData;
	private RecitationData recitationData;
	private ProjectData projectData;
	private DetailsData detailsData;

	public CSGData(){
		scheduleData = new ScheduleData();
		taData = new TAData();
		recitationData = new RecitationData();
		projectData = new ProjectData();
		detailsData = new DetailsData();
	}

	public RecitationData getRecitationData() {
		return recitationData;
	}

	public ProjectData getProjectData() {
		return projectData;
	}

	public DetailsData getDetailsData() {
		return detailsData;
	}

	public TAData getTAData(){
		return taData;
	}

	public ScheduleData getScheduleData() {
		return scheduleData;
	}

	@Override
	public void resetData() {

	}
}
