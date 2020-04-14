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
		setWaitingQueue(new Queue<PCB>());
		finishedPCB = new Queue<PCB>();

	}

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
			while (copy.length() != 0)
				waitingQueue.enqueue(copy.serve());
			++Clock.time;

		}
	}

	private void removeMaxPCB(Queue<PCB> copy) {
		// TODO Auto-generated method stub

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
