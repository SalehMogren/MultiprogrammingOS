/*
 * loads info from input that is generated file to a queue
 */

  
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JobQueue {
	
	
	private static final String INPUTFILE = "src//inputFile.txt";
	private BufferedReader br;
	private FileReader fr;
	private String sCurrentLine;
	private Queue<PCB> JobQueue;
	
	public JobQueue() {
		BufferedReader br = null;
		FileReader fr = null;
		JobQueue = new Queue<PCB>();

	}
	
	
	
	// 
	
	
	private Queue<PCB> loadToJobQueue() throws FileNotFoundException {
		
		
		
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public Queue<PCB> getProcesses() {
		try {
			return this.loadToJobQueue();
			
		} catch (FileNotFoundException e) {

			System.err.println("File not found!!");
		}
		return JobQueue;
	}
	

	
}
