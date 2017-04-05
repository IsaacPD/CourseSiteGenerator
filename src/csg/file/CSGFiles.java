package csg.file;

import csg.ta.TimeSlot;
import djf.components.AppDataComponent;
import djf.components.AppFileComponent;

import java.io.IOException;
import java.util.ArrayList;

public class CSGFiles implements AppFileComponent{
	@Override
	public void saveData(AppDataComponent appDataComponent, String s) throws IOException {

	}

	@Override
	public void loadData(AppDataComponent appDataComponent, String s) throws IOException {

	}

	public ArrayList<TimeSlot> saveData(AppDataComponent data, String filePath, String startTime, String endTime) throws IOException {
		return new ArrayList<>();
	}

	@Override
	public void exportData(AppDataComponent appDataComponent, String s) throws IOException {

	}

	@Override
	public void importData(AppDataComponent appDataComponent, String s) throws IOException {

	}
}
