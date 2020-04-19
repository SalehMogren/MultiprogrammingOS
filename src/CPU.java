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
	int totoalCPUTime, totalIOtime = 0;
	PQ<PCB> readyQueue;
	private Queue<PCB> waitingQueue;
	PQ<PCB> premetidProcess;

	// Based on the arrival time , we execute first process then check the
	//
	void startCpu() {
		ram = new RAM();
		readyQueue = ram.loadToReady();
		waitingQueue = ram.getWaitingQueue();
		premetidProcess = new PQ<PCB>();
//		int m = 0; // Number of waiting processes in waiting queue

		while (true) {
//			m = waitingQueue.length();
			int currentTime = Clock.time;
			// general way

			if (Clock.time % 100 == 0 || readyQueue.length() == 0) {
				// you should reactivate job scheduler
				readyQueue = ram.loadToReady(); // new processes from the Job queue

			}
//			if (readyQueue.length() == 0 && m != 0) {
//				Clock.time++;
//				continue;
//			}

//			if (readyQueue.length() == 0 && m == 0) {
//				return;
//			}

//			PCB process1= readyQueue.peek();

			PCB process = minProcess();
//			if(process1==process)
//				returnToReadyQueue(process1);
			if (process != null) {
				int cBurst = process.getFirstCPU();
				int i = 0;
				for (i = 0; i < cBurst && checkArr_Burst(process); i++) {

					process.setState(ProccessState.RUNNING);
					process.CPUNumIncrement();

					++Clock.time;
					// null Pointer here
					process.getCycles().peek().setCpuBurst(process.getCycles().peek().getCpuBurst() - 1);
//				process.getFirstCycle().setCpuBurst(process.getFirstCycle().getCpuBurst() - 1);

				}
				if (Clock.time >= process.getarrtime()) {
					totoalCPUTime += i;
					process.increaseCPUSum(i);
				}
				// out of the loop because of a new short process
				if (process.getFirstCPU() != 0) {
					if (Clock.time >= process.getarrtime())
						if (readyQueue.peek().data.getFirstCPU() < cBurst) {
							process.setState(ProccessState.WAITING);

//						premetidProcess.enqueue(process, process.getFirstCycle().getCpuBurst());
							continue;
						}
				}
				Cycle newCycle = new Cycle();
				int memCycle = 0;
				int cpuBurst = process.getFirstCPU();
				int ioBurst = -1;
				// waiting IO
				if (process.getFirstCPU() == 0) {
					int ioB = process.getCycles().peek().getIOBurst();
					for (int p = 0; p < ioB; p++) {
						process.setState(ProccessState.WAITING);
						++Clock.time;
						process.getCycles().peek().setIOBurst(process.getCycles().peek().getIOBurst() - 1);
					}
					totalIOtime += ioB;
					process.increaseIOSum(ioB);
					process.increaseMemorySum(process.getFirstMemory());
					ioBurst = process.getFirstIO();
					memCycle = process.getFirstMemory();
					process.getFirstCycle();
					process.IONumIncrement();

				}

				// process finished
				if (process.getIndicator() == 0) {
					process.setState(ProccessState.TERMINATED);
					process.setEndTime(Clock.time);
					ram.addToFinshedQueue(process);
					continue;
				}

				// if the cycle is finished new cycle
				if (process.getIndicator() > 0) {
					if (cpuBurst == 0 && ioBurst == 0) {
						process.getCycles().peek().setMemory(process.getFirstMemory() + memCycle);

						process.setState(ProccessState.READY);

						// add process back the readyQueue with(addToReadQueue)
						ram.addToReadyQueue(process);
					}
				}

			}
			if (currentTime == Clock.time)
				++Clock.time;

			if (ram.isEmpty()&&premetidProcess.length()==0) {
				break;
			}
		}

	}

//	private void returnToReadyQueue(PCB process1) {
//		// TODO Auto-generated method stub
//		Queue<PCB> q1 = new Queue<PCB>();
//
//		q1.enqueue(process1);
//		int x = readyQueue.length();
//		for (int i = 0; i < x; i++)
//			q1.enqueue(readyQueue.serve());
//
//		x = q1.length();
//		for (int i = 0; i < x; i++)
//			readyQueue.enqueue(q1.serve());
//
//	}

	// this method checks if the next Process meet the arrival condition and min
	//
	// process
	// if it meat the arrival but not the min process then its add to PQ
	// premitedProcess
	private boolean checkArr_Burst(PCB currentP) {

		if (Clock.time >= currentP.getarrtime()) {
			if (readyQueue.length() > 0) {
				PCB next = readyQueue.peek().data;

				if (Clock.time >= next.getarrtime()) {
//			if(currentP.getIndicator()>0)
					if (currentP.getFirstCPU() > next.getFirstCPU()) {

						premetidProcess.enqueue(currentP, currentP.getFirstCPU());
						return false;
					} else if (currentP.getFirstCPU() <= next.getFirstCPU()) {
						next = readyQueue.serve().data;
						premetidProcess.enqueue(next, next.getFirstCPU());

						return true;
					}
				}

				return true;
			}
			return true;

		}
		return false;
	}

	// this method compare between the next Process in the readyQueu and PQ and
	// returns the minimum Process
	// compare between premitedProcess and readyQueue process
	// Special cases:
	/*
	 * - last elm in the readyQueue or Empty - not in the Clock Range -
	 */
	private PCB minProcess() {
		PCB min = null;
		if (readyQueue.length() > 0) {
			min = readyQueue.peek().data;
//
			if (Clock.time >= min.getarrtime())
				min = readyQueue.serve().data;

			if (premetidProcess.length() > 0) {
				if (min.getFirstCPU() > premetidProcess.peek().priority) {
					premetidProcess.enqueue(min, min.getFirstCPU());
					min = premetidProcess.serve().getData();
				}

				return min;
			}

		}
		if (premetidProcess.length() > 0) {
			min = premetidProcess.serve().data;

		}
		return min;
	}

	

//	public Queue<PCB> getReadyQueue() {
//		return readyQueue;
//	}
//
//	public void setReadyQueue(Queue<PCB> readyQueue) {
//		this.readyQueue = readyQueue;
//	}

	public RAM getRam() {
		return ram;
	}

	public void setRam(RAM ram) {
		this.ram = ram;
	}

	public int getTotalCpuTime() {
		return totoalCPUTime;
	}

	public void setTotalCpuTime(int totalCpuTime) {
		this.totoalCPUTime = totalCpuTime;
	}

	public int getTotalIoTime() {
		return totalIOtime;
	}

	public void setTotalIoTime(int totalIoTime) {
		this.totalIOtime = totalIoTime;
	}

}
