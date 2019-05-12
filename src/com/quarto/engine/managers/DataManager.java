package com.quarto.engine.managers;

import java.util.HashMap;

import com.quarto.engine.utilities.DataFile;

public class DataManager {
	
	private static HashMap<String, DataFile> dataFiles = new HashMap<String, DataFile>();
	
	private static void addDataFile(String dataFilePath) {
		dataFiles.put(dataFilePath, new DataFile(dataFilePath));
		dataFiles.get(dataFilePath).open();
	}
	
	public static DataFile getDataFile(String dataFilePath) {
		if(!dataFiles.containsKey(dataFilePath))
			addDataFile(dataFilePath);
		return dataFiles.get(dataFilePath);
	}
	
	public static DataFile reloadDataFile(String dataFilePath) {
		addDataFile(dataFilePath);
		return dataFiles.get(dataFilePath);
	}

}
