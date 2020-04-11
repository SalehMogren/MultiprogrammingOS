/*
 * loads info from input that is generated file to a queue
 */

  
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JobQueue {
	
	
	private static final String INPUTFILE = "inputFile.txt";
	private BufferedReader br;
	private FileReader fr;
	private String sCurrentLine;
	private PQ<PCB> JobQueue;
	
	public JobQueue() {
		BufferedReader br = null;
		FileReader fr = null;
		JobQueue = new PQ<PCB>();

	}
	
	
	
	// will talk about this
	
	
	private PQ<PCB> loadToJobQueue() throws FileNotFoundException {
		
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public PQ<PCB> getProcesses() {
		try {
			return this.loadToJobQueue();
			
		} catch (FileNotFoundException e) {

			System.err.println("File not found!!");
		}
		return JobQueue;
	}
	

	
}
