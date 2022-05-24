package logFileReader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.logFileReader.LogFileReader;

import junit.framework.TestCase;

/**
 * <pre>
 *  Junit Test Class File for checking logic as Written in LogFileReader
 * </pre>.
 *
 * @author  : Vikram Kastwar
 * @version : 1.0
 */
public class LogFileReaderTest extends TestCase{
	
	File logFile;
	
	private void setup() throws IOException {
		logFile = File.createTempFile("logFile", ".txt");
		FileWriter fileWrite = new FileWriter(logFile);
		fileWrite.write("{id=\"scsmbstgrb\", state=\"STARTED\", \"timestamp\":1491377495213, \"host\":12345, type=\"APPLICATION_LOG\"}\r\n" + 
				"{id=\"scsmbstgrc\", state=\"FINISHED\", \"timestamp\":1491377495218}\r\n" + 
				"{id=\"scsmbstgrc\", state=\"STARTED\", \"timestamp\":1491377495210}\r\n" + 
				"{id=\"scsmbstgrb\", state=\"FINISHED\", \"timestamp\":1491377495213, \"host\":12345, type=\"APPLICATION_LOG\"}");
		fileWrite.close();
	}
	
	/**
	 * Main method for testing ParseFileAndSaveRecord
	 * 
	 * @throws IOException
	 */
	public void testParseFileAndSaveRecord() throws IOException
    {	
		setup();
		new LogFileReader().parseFileAndSaveRecord(logFile.getAbsolutePath());
        assertTrue( true );
        /** Delete File after Execution*/
        logFile.delete();
    }
}