
public class Cycle {
	private int cpuBurst;
	private int memory;
	private int IOBurst;
	
	
	public Cycle() {
		this.cpuBurst = 0;
		this.IOBurst = 0 ;
		this.memory = 0 ;
		
	}
	public Cycle(int cpuBurst , int memory , int IOBurst){
		this.cpuBurst = cpuBurst;
		this.memory = memory;
		this.IOBurst = IOBurst;
		
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
	
	
	


}
