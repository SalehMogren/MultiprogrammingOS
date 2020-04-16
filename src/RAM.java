import java.io.FileNotFoundException;

import com.sun.org.apache.xalan.internal.xsltc.compiler.Template;

/*This Class Represent The RAM
 * Its Operations will be :
 * Load the jobQueue to ready Queue or to waiting queue if it can not be added .
 * Clear The Largest Process in  the memory when there is a deadlock 
 * 
 */

public class RAM {
	public final static int SIZE = 680;
	private int availbleSIZE;
	private Queue<PCB> jobQueue;
	private PQ<PCB> readyQueue;
	private Queue<PCB> waitingQueue;
	private Queue<PCB> finishedPCB;
	private JobQueue startingObj;

	// Constructor
	RAM() {
		startingObj = new JobQueue();
		try {
			jobQueue = startingObj.getProcesses();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		readyQueue = new PQ<PCB>();
		waitingQueue=new Queue<PCB>();
		finishedPCB = new Queue<PCB>();
		
		this.availbleSIZE=578;
		

	}
	// Method To Load from the file to Queue
	public PQ<PCB> loadToReady() {

		if (jobQueue.length() == 0 && waitingQueue.length() == 0)
			return this.readyQueue;

		checkWaitingQueue();

		while (jobQueue.length() != 0 && availbleSIZE != 0) {
			PCB proccess = jobQueue.serve();

			// if it can fit in the ready queue
			if (proccess.getFirstMemory() <= availbleSIZE) {
				availbleSIZE -= proccess.getFirstMemory();
				if (proccess.getReadyQueueTime() == 0)
					proccess.setReadyQueueTime(Clock.time);
				proccess.setState(ProccessState.READY);
				readyQueue.enqueue(proccess, proccess.getFirstCPU());
			}
			// if its not fitting in the ready queue add to waiting queue
			else {
				proccess.setState(ProccessState.WAITING);
				waitingQueue.enqueue(proccess);
				proccess.waitNumIncrement();
			}
			++Clock.time;
		}

		return this.readyQueue;
	}

	private void checkWaitingQueue() {
		// TODO Auto-generated method stub
		Queue<PCB> copy = new Queue<PCB>();
		int n = waitingQueue.length();

		while (waitingQueue.length() > 0) {
			++Clock.time;
			PCB proccess = waitingQueue.serve();

			if (proccess.getFirstMemory() < availbleSIZE) {
				availbleSIZE -= proccess.getFirstMemory();
				proccess.setState(ProccessState.READY);
				if (proccess.getReadyQueueTime() == 0)
					proccess.setReadyQueueTime(Clock.time);
				readyQueue.enqueue(proccess, proccess.getFirstCPU());
			} else {
				copy.enqueue(proccess);
			}
		}
		if (n == copy.length())
			removeMaxPCB(copy);
		else {
			while (copy.length() != 0) {
				PCB temp = copy.serve();
				waitingQueue.enqueue(temp);
			++Clock.time;
			}
		}
	}

	private void removeMaxPCB(Queue<PCB> copy) {
		// TODO Auto-generated method stub
		
		if (copy.length() == 0) {
			return;
		}
		Queue<PCB> temp = new Queue<PCB>();
		PCB Max = copy.serve(); 
		PCB deletedPCB = null; // the max PCB from the WaitingQueue 
		temp.enqueue(Max);
		int id = Max.getPid();

		while (copy.length() != 0) {//  find the max memory process in the waiting queue
											 
			if (copy.peek().getFirstMemory() > Max.getFirstMemory()) {
				Max = copy.serve(); //  change the max if condition True 
				id = Max.getPid(); // save the id of the max memory process
				temp.enqueue(Max);
			} 
			else {
				temp.enqueue(copy.serve());
			}
			++Clock.time;
		}//
		
           // return the PCB's form temp to WaitingQueue Except the MAX
		while (temp.length() != 0) {
			if (temp.peek().getPid() == id) { 
				deletedPCB = temp.serve();
			} 
			else {
				PCB tempPcb = temp.serve();
				this.waitingQueue.enqueue(tempPcb);
			}
			++Clock.time;
		}//
		this.availbleSIZE+=deletedPCB.getMemorySum();
		deletedPCB.setState(ProccessState.KILLED);
		finishedPCB.enqueue(deletedPCB);
		
	}
	
	// checks if the waiting queue and job queue and ready queue is empty
	public boolean isEmpty() {

			if (jobQueue.length() == 0 && waitingQueue.length() == 0 && readyQueue.length() == 0) {
				return true;
			}

			return false;
		}
	
	// after IO burst this method is called to put back a process into the ready queue or waiting queue
	public void addToReadyQueue(PCB process) {
			
			if(process.getCycles().length()==0) {
				return;
			}
			
				if (process.getFirstMemory() <= this.availbleSIZE) {

					this.availbleSIZE -= (process.getFirstMemory());
					process.setState(ProccessState.READY);
					if(process.getReadyQueueTime()==0) {
						
						process.setReadyQueueTime(Clock.time);
						}
					readyQueue.enqueue(process, process.getFirstCPU());
				

				} else {
					process.setState(ProccessState.WAITING);
					process.waitNumIncrement();
					waitingQueue.enqueue(process);
				}

			

		}

	public void addToFinshedQueue(PCB process) {
			this.availbleSIZE+= process.getMemorySum();
			this.finishedPCB.enqueue(process);
		}
		
	
	
	
	
	

	// getters and setters
	public int getAvailbleSIZE() {
		return availbleSIZE;
	}

	public void setAvailbleSIZE(int availbleSIZE) {
		this.availbleSIZE = availbleSIZE;
	}

	public Queue<PCB> getJobQueue() {
		return jobQueue;
	}

	public void setJobQueue(Queue<PCB> jobQueue) {
		this.jobQueue = jobQueue;
	}

	public PQ<PCB> getReadyQueue() {
		return readyQueue;
	}

	public void setReadyQueue(PQ<PCB> readyQueue) {
		this.readyQueue = readyQueue;
	}

	public Queue<PCB> getFinishedPCB() {
		return finishedPCB;
	}

	public void setFinishedPCB(Queue<PCB> finishedPCB) {
		this.finishedPCB = finishedPCB;
	}

	public Queue<PCB> getWaitingQueue() {
		return waitingQueue;
	}

	public void setWaitingQueue(Queue<PCB> waitingQueue) {
		this.waitingQueue = waitingQueue;
	}

}
