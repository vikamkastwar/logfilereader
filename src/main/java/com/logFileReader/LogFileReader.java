package com.logFileReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;
import com.logFileReader.model.Event;

/**
 * <pre>
 * 	Logic for parsing the logFile having records in given format 
 *  {id="scsmbstgrb", state="FINISHED", "timestamp":1491377495213, "host":12345, type="APPLICATION_LOG"}
 * </pre>.
 *
 * @author  : Vikram Kastwar
 * @version : 1.0
 */
public class LogFileReader {
	
	 /** logger **/
    protected Log log = LogFactory.getLog(getClass());
	
    /**Constants*/
	private static final String STATE_FINISHED = "FINISHED";
	private static final String EXCEPTION_MESSAGE = "File either not present or not accessible at given location i.e. ";
	
	/** Used for converting records in Model Object**/ 
	private Gson gson = new Gson();
	
	/** Contains Records as present in file*/
	private Map<Event,Event> recordHMap = new HashMap<>();
	
	/**
	 * Process the records as present in input file
	 * 
	 * @param filePath
	 */
	public void parseFileAndSaveRecord(String filePath){
		try {
			processRecordAndSave(filePath);
		} catch (IOException e) {
			log.info(EXCEPTION_MESSAGE + filePath);
		}
	}
	
	/**
	 * Returns File Reader Pointer pointing to File as denoted by file Path
	 * 
	 * @param filePath
	 * @return
	 * @throws FileNotFoundException
	 */
	private LineNumberReader getFileReader(String filePath) throws FileNotFoundException {
		if(StringUtils.isNotBlank(filePath)) {
			log.debug("File is ");
			return new LineNumberReader(new FileReader(filePath));
		}
		return null;
	}
	
	/**
	 * Parse records of File one by one and Save the same into respective Inventory or Storage
	 * 
	 * @throws IOException 
	 * @param filePath 
	 */
	private void processRecordAndSave(String filePath) throws IOException  {
		try(LineNumberReader logFileReader = getFileReader(filePath)) {
			if(logFileReader != null) {
				String record = logFileReader.readLine();
			    while(record != null) {
			    	log.debug("Processing Record Number : " + logFileReader.getLineNumber());
			        processAndSaveRecords(record);
			        record = logFileReader.readLine();
			    }
			}   
		} catch (IOException e) {
			throw e;
		} 
	}
	
	/**
	 * Convert file record into model object i.e. Event
	 * and check whether Map contains current record by using overriden equals method in model class
	 * If Map contains records
	 *   Calculate the durations 
	 *   Save Records 
	 *   Remove Element from Map
	 * If Not 
	 *   Record is added in Map after converting into model class  
	 * 
	 * @param record
	 */
	private void processAndSaveRecords(String record) {
		Event event = gson.fromJson(record, Event.class);
		Event eventHMpRec = recordHMap.getOrDefault(event, null);
		if(eventHMpRec != null) {
			checkandUpdateDurations(eventHMpRec,event);
			saveRecord(event);
			recordHMap.remove(eventHMpRec);
		}else {
			recordHMap.put(event, event);
		}
	}
	
	/**
	 * Calculate durations with help of current Event or Record vs Event present in
	 * Map
	 * 
	 * @param eventHMpRec
	 * @param event
	 */
	private void checkandUpdateDurations(Event eventHMpRec, Event event) {
		double duration = event.getTimestamp() - eventHMpRec.getTimestamp();
		if(STATE_FINISHED.equalsIgnoreCase(eventHMpRec.getState())) {
			duration = eventHMpRec.getTimestamp() -  event.getTimestamp();
		}
		event.setDuration(duration);
		
		if(event.isLongDuration()) {
			log.info("Alert ! Event " + event.getId() + " takes longer time for processing i.e. " + duration);
		}	
	}
	
	/** 
	 * Saves current Records Or Event model class 
	 * 
	 * @param event
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	private void saveRecord(Event event) {
		// TODO Auto-generated method stub
		
		/*try {
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
			System.out.println("HSQLDB JDBCDriver Loaded");
			Connection con = DriverManager.getConnection(
					"jdbc:hsqldb:file:/temp/db/sampledb", "SA", "");

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
			
	}
}