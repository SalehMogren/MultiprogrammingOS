import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.BufferedReader;


/*
 * Output 
 * Process ID 
 * Time when it was loaded into the ready queue ( First time entered the ready queue )
 * Total spent time in the cpu ( counter every time entered cpu)
 * Number of times Reformed an IO ( Counter every time there is an IO)
 * Total spent time performing an IO ( collect all time doing IO)
 * Number of times was in the Waiting queue ( Counter for waiting)
 * Number of times was preempted( counter for permeated)
 * Time it terminated or was Killed ( When ? Using Clock)
 * Its Final State : Killed or Terminated 
 * CPU Utilization
 */

public class OS {
	public static void main(String[] args) {

		System.out.println("This program will simulate single core processer.");

		RAM r1 = new RAM();
		
		CPU cpu1 = new CPU();
		cpu1.startCpu();
		System.out.println("Cpu started");
		Queue<PCB> jobs = cpu1.getRam().getFinishedPCB();
		
		
		
		
		
		
		double CPUProcessing = cpu1.getTotalCpuTime();
		double CPUTotalTime = Clock.time;
		double Utilization = CPUProcessing / CPUTotalTime;
		int i = 0;
		
		try {
			WriteToFile(jobs, CPUProcessing, Utilization, CPUTotalTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Executeing finished, Check the file 'SimulationOutput' For the results!");
		
		
		
		
	}
	

	//This method is used to write all the info of the finished processes to the file "SimulatorOutput"
	public static void WriteToFile(Queue<PCB> jobs, double CPUProcessing, double Utilization, double CPUTotalTime)
			throws IOException {
		try {

			// Sample 01: Open the FileWriter, Buffered Writer
			FileWriter fw = new FileWriter("SimulationOutput.Txt");
			BufferedWriter WriteFileBuffer = new BufferedWriter(fw);
			WriteFileBuffer.write("/////////////////////////////// " + jobs.length() + " Process were executed");
			WriteFileBuffer.newLine();
			int i = 0;
			while (jobs.length() != 0) {

				PCB b = jobs.serve();
				
				WriteFileBuffer
						.write("-------------------------------------------------------------------------------");
				WriteFileBuffer.newLine();
				WriteFileBuffer.write("The Process ID: " + b.getPid());
				WriteFileBuffer.newLine();
				WriteFileBuffer.write("Time it was loaded into the ready queue: " + b.getReadyQueueTime());
				WriteFileBuffer.newLine();
				WriteFileBuffer.write("Number of times it was in the CPU: " + b.getCPUNum()  );
				WriteFileBuffer.newLine();
				WriteFileBuffer.write("Total time spent in the CPU: " + b.getCPUSum()  );
				WriteFileBuffer.newLine();
				WriteFileBuffer.write("Number of times it was in the IO: " + b.getIONum()  );
				WriteFileBuffer.newLine();
				WriteFileBuffer.write("Total time spent in the IO: " + b.getIOSum()  );
				WriteFileBuffer.newLine();
				WriteFileBuffer.write("Number of times it was waiting for memory: " + b.getWaitNum()  );
				WriteFileBuffer.newLine();
				WriteFileBuffer.write("Number of times it was preempted: " + b.getNumOfPrem()  );
				WriteFileBuffer.newLine();
				WriteFileBuffer.write("Time it terminated or was killed: " + b.getEndTime()  );
				WriteFileBuffer.newLine();
				WriteFileBuffer.write("Final state: " + b.getState()  );
				WriteFileBuffer.newLine();
				i++;

			}
			WriteFileBuffer.newLine();
			WriteFileBuffer.newLine();

			WriteFileBuffer.write("-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --");
			WriteFileBuffer.newLine();
			WriteFileBuffer.write("CPU Utilization is:");
			String utilRes = String.format("%.2f", Utilization * 100);
			WriteFileBuffer.write(utilRes);
			WriteFileBuffer.write("%");
			WriteFileBuffer.newLine();
			WriteFileBuffer.write("-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- ");
			// WriteFileBuffer.write(i);

			// Sample 02: Write Some Text to File
			// Using Buffered Writer)
			// WriteFileBuffer.write("First Line \n Second line!");

			// Sample 03: Close both the Writers
			WriteFileBuffer.close();
			//System.out.println("I think it worked!");

		} catch (IOException Ex) {
			System.out.println(Ex.getMessage());
		}

	}

	
	
}
