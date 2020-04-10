
public class Cycle {
	private int cpuBurst;
	private int memory;
	private int IOBurst;
	private int arrivalTime;
	
	public Cycle() {
		this.cpuBurst = 0;
		this.IOBurst = 0 ;
		this.memory = 0 ;
		this.arrivalTime=0;
	}
	public Cycle(int cpuBurst , int memory , int IOBurst , int arrivalTime){
		this.cpuBurst = cpuBurst;
		this.memory = memory;
		this.IOBurst = IOBurst;
		this.arrivalTime=arrivalTime;
	}
	// setters & getters
	
	public int getCpuBurst() {
		return cpuBurst;
	}
	public void setCpuBurst(int cpuBurst) {
		this.cpuBurst = cpuBurst;
	}
	
	public int getMemory() {
		return memory;
	}
	public void setMemory(int memory) {
		this.memory = memory;
	}
	
	public int getIOBurst() {
		return IOBurst;
	}
	public void setIOBurst(int iOBurst) {
		IOBurst = iOBurst;
	}
	
	public int getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	
	


}
