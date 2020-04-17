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
	PQ<PCB> premetidProcess;

	// Based on the arrival time , we execute first process then check the
	//
	void startCpu() {
		ram = new RAM();

		while (true) {
			
		// general way
			PCB process = readyQueue.serve();
			process.setState(ProccessState.RUNNING);
			process.CPUNumIncrement();
			int cBurst = process.getFirstCPU();
			for (int i = 0; i < cBurst && readyQueue.peek().getFirstCycle().getCpuBurst() > cBurst; i++)

			{
				++Clock.time;
				process.getFirstCycle().setCpuBurst(process.getFirstCycle().getCpuBurst() - 1);
			}
			// out of the loop because of a new short process
			if (readyQueue.peek().getFirstCycle().getCpuBurst() < cBurst) {
				premetidProcess.enqueue(process, process.getFirstCycle().getCpuBurst());
				continue;
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
			//process finished CPU and IO bursts
				// might be null poitner exception
			if(process.getFirstCPU()==0&&process.getFirstIO()==0) {
				Cycle newCycle = process.serveCycle();
				process.getFirstCycle().setMemory(process.getFirstMemory()+newCycle.getMemory());
				process.setState(ProccessState.READY);
				
				// add process back the readyQueue with(addToReadQueue)
			}
			
			//process finished (add indicator in PCB for when process has no more cycles)
		}

	}
}
