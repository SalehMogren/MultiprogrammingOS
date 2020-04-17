/*
 * 1- ReadyQueue - Converting to queue sorted on ( arr,cpuBurst) WaitingQueue - converting to Queue
2- general way is process will be executed each cpuBurst (time unit) when it reach 0 we will then perform the IO burst that will stop everything until its finished
	add it again to the ready queue update cpuBurst,IOBurst and add Memory
3 - Special case when new shortest process enters the ready , create PQ premetidProcess 
4- Each time unit we compare with the shortest process min(premetidProcess ,next new Process)
5- Until ram is empty
 */
public class CPU {
	private RAM ram;
	int totoaCPUTime, totalIOtime = 0;
	Queue<PCB> readyQueue;
	private Queue<PCB> waitingQueue;
	PQ<PCB> premetidProcess;

	// Based on the arrival time , we execute first process then check the
	//
	void startCpu() {
		ram = new RAM();
		readyQueue = ram.loadToReady();
		waitingQueue = ram.getWaitingQueue();
	
		int m = 0; //Number of waiting processes in waiting queue

		while (true) {
			m = waitingQueue.length();
			
		// general way
			
			
			if (Clock.time % 100 == 0 || readyQueue.length() == 0) {
				// you should reactivate job scheduler
				readyQueue = ram.loadToReady(); // new processes from the Job queue
														

			}
			if (readyQueue.length() == 0 && m != 0) {
				Clock.time++;
				continue;
			}
			
			if (readyQueue.length() == 0 && m == 0) {
				return;
			}
			
			
			
			PCB process = readyQueue.serve();
			
			process.setState(ProccessState.RUNNING);
			process.CPUNumIncrement();
			int cBurst = process.getFirstCPU();
			
			for (int i = 0; i < cBurst ; i++)

			{
				if(	Clock.time<readyQueue.peek().getarrtime()) 
				{
				
				++Clock.time;
				process.getFirstCycle().setCpuBurst(process.getFirstCycle().getCpuBurst() - 1);
				
				}
				else
					break;
			}
		
			// out of the loop because of a new short process
 			if(process.getFirstCPU()!=0) {
				
			if (readyQueue.peek().getFirstCycle().getCpuBurst() < cBurst) {
				premetidProcess.enqueue(process, process.getFirstCycle().getCpuBurst());
				continue;
			}
			
			//process finished CPU and IO bursts
				// might be null poitner exception
		
			
			//process finished (add indicator in PCB for when process has no more cycles)
 			}
 		// waiting IO
 					if (process.getFirstCycle().getCpuBurst() == 0) {
 						int ioBurst = process.getFirstCycle().getIOBurst();
 						for (int p = 0; p < ioBurst; p++) {
 							process.setState(ProccessState.WAITING);
 							++Clock.time;
 							process.getFirstCycle().setIOBurst(process.getFirstCycle().getIOBurst() - 1);
 						}
 						process.IONumIncrement();
 					}
 					
 					if(process.getFirstCPU()==0&&process.getFirstIO()==0) {
 						Cycle newCycle = process.serveCycle();
 						process.getFirstCycle().setMemory(process.getFirstMemory()+newCycle.getMemory());
 						process.setState(ProccessState.READY);
 						
 						
 						// add process back the readyQueue with(addToReadQueue)
 						ram.addToReadyQueue(process);
 					}
 					
 					if (ram.isEmpty()) {
 						break;
 					}


	}
}
	
	public Queue<PCB> getReadyQueue() {
		return readyQueue;
	}

	public void setReadyQueue(Queue<PCB> readyQueue) {
		this.readyQueue = readyQueue;
	}

	public RAM getRam() {
		return ram;
	}

	public void setRam(RAM ram) {
		this.ram = ram;
	}

	public int getTotalCpuTime() {
		return totoaCPUTime;
	}

	public void setTotalCpuTime(int totalCpuTime) {
		this.totoaCPUTime = totalCpuTime;
	}

	public int getTotalIoTime() {
		return totalIOtime;
	}

	public void setTotalIoTime(int totalIoTime) {
		this.totalIOtime = totalIoTime;
	}

}
	
	

