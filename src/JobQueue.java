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
		
		try {

			br = new BufferedReader(new FileReader(INPUTFILE));
			fr = new FileReader(INPUTFILE);
			br = new BufferedReader(fr);
			int pid = 0;
			int cpuBurst = 0;
			int memory = 0;
			int IOBurst = 0;
			int Artime=0;
			int counter = 0;

			String sCurrentLine;
			br.readLine(); // first Line "Name CPU Memory IO " etc..
			// Cycle contains:
			// cpuBurst, memory, IOBurst
			while ((sCurrentLine = br.readLine()) != null ) {

				
				String[] PCBInfo = sCurrentLine.split("	");		
				pid = Integer.parseInt(PCBInfo[0]); // Name of Process
				Artime = Math.abs(Integer.parseInt(PCBInfo[4]))*-1; //artime in (-)
				PCB pcb1 = new PCB(pid,Artime);
				cpuBurst = Integer.parseInt(PCBInfo[1]);
				memory = Math.abs(Integer.parseInt(PCBInfo[2])); //First memory should be positive
				IOBurst = Integer.parseInt(PCBInfo[3]);
				
				pcb1.addCycle(cpuBurst, memory, IOBurst);// Cycle 1
				cpuBurst = Integer.parseInt(PCBInfo[5]);
				memory = Integer.parseInt(PCBInfo[6]);
				IOBurst = Integer.parseInt(PCBInfo[7]);
				pcb1.addCycle(cpuBurst, memory, IOBurst);// Cycle 2
				cpuBurst = Integer.parseInt(PCBInfo[8]);
				memory = Integer.parseInt(PCBInfo[9]);
				IOBurst = Integer.parseInt(PCBInfo[10]);
				pcb1.addCycle(cpuBurst, memory, IOBurst);// Cycle 3
				if (PCBInfo.length == 11) {// if 3 cycles
					JobQueue.enqueue(pcb1, Artime);
					continue;
				}

				cpuBurst = Integer.parseInt(PCBInfo[11]);
				memory = Integer.parseInt(PCBInfo[12]);
				IOBurst = Integer.parseInt(PCBInfo[13]);
				pcb1.addCycle(cpuBurst, memory, IOBurst);// Cycle 4

				if (PCBInfo.length == 15) { // Has only 5 Cycles
					
					cpuBurst = Integer.parseInt(PCBInfo[14]);
					memory = 0;												//this part is when there is extra cpuburst but with no io and memory
					IOBurst = 0;
					pcb1.addCycle(cpuBurst, memory, IOBurst);// Cycle 5

				}
				if (PCBInfo.length == 18) { // if it has a 6th cycles
					
					cpuBurst = Integer.parseInt(PCBInfo[14]);
					memory = Integer.parseInt(PCBInfo[15]);
					IOBurst = Integer.parseInt(PCBInfo[16]);
					pcb1.addCycle(cpuBurst, memory, IOBurst);// Cycle 5          //this part is when there is extra cpuburst but with no io and memory
					cpuBurst = Integer.parseInt(PCBInfo[17]);
					memory = 0;
					IOBurst = 0;
					pcb1.addCycle(cpuBurst, memory, IOBurst);// Cycle 6

				}

				JobQueue.enqueue(pcb1,Artime);
				counter++;
			}

		} catch (

		IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}
		}

		return JobQueue;

	}
	
	
	
	
	
	
	
	//test
	
	
	
	
	
	public Queue<PCB> getProcesses() throws FileNotFoundException {
		PQ<PCB> copy = JobQueue;
		Queue<PCB> proccessQueeu = new Queue<PCB>();
		
		int n = copy.length();
		for(int i = 0; i<n; i++)
			proccessQueeu.enqueue(copy.serve().data);
		
		return proccessQueeu;
		
	}
	

	
}
