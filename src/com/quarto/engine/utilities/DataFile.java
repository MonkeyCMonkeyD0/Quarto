package com.quarto.engine.utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class DataFile {
	
	private String path;
	private HashMap<String, String> data = new HashMap<String, String>();
	
	public DataFile(String path) {
		this.path = path;
	}
	
	public void open() {
		String line;
		String[] lineSplit;
		try {
			FileReader fileReader = new FileReader("data/" + path + ".txt");
			BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {
            	lineSplit = line.split(":");
            	data.put(lineSplit[0], lineSplit[1]);
            }
            bufferedReader.close();
        } catch(FileNotFoundException ex) {
            System.out.println("Unable to open file 'data/" + path + ".txt'");                
        } catch(IOException ex) {
            System.out.println("Error reading file 'data/" + path + ".txt'");
        }
	}
	
	public void write() {
        try {
            FileWriter fileWriter = new FileWriter("data/" + path + ".txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            
            Iterator<Entry<String, String>> it = data.entrySet().iterator();
            Map.Entry<String, String> pair;
            while (it.hasNext()) {
                pair = (Map.Entry<String, String>) it.next();
            	bufferedWriter.write(pair.getKey() + ":" + pair.getValue());
                bufferedWriter.newLine();
            }
            
            bufferedWriter.close();
        } catch(IOException ex) {
            System.out.println("Error writing to file 'data/" + path + ".txt'");
        }
	}
	
	public boolean exists(String property) {
		return data.containsKey(property);
	}
	
	public void add(String property, Object value) {
		data.put(property, value.toString());
		this.write();
	}
	
	public void def(String property, Object value) {
		if(!exists(property))
			data.put(property, value.toString());
	}
	
	public String get(String property) {
		if(exists(property))
			return data.get(property);
		return "0";
	}
	
	public void remove(String property) {
		data.remove(property);
	}
	
	public HashMap<String, String> getData(){
		return data;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
