/*
 * 1- ReadyQueue - Converting to queue sorted on ( arr,cpuBurst) WaitingQueue - converting to Queue
2- general way is process will be executed each cpuBurst (time unit) when it reach 0 we will then preform the IO burts that will stop everything until its finished
	add it again to the ready queue update cpuBurst,IOBurst and add Memory
3 - Special case when new shortest process enters the ready , create PQ premetidProcess 
4- Each time unit we compare with the shortest process min(premetidProcess ,next new Process)
5- Until ram is empty
 */
public class CPU {
	int totoaCPUTime,totalIOtime;
	PQ<PCB> waitingQueue,readyQueue;
	
	
	
	
	//Based on the arrival time , we execute first process then check the 
	//
	
}
