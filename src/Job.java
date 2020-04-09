import java.io.Serializable;

public class Job implements Serializable{
	int cpuBurst;
	int memmoryReq;
	int arrivalTime;
	int IOBurst;
	public Job(int cpuBurst, int memmoryReq, int arrivalTime, int iOBurst) {
		super();
		this.cpuBurst = cpuBurst;
		this.memmoryReq = memmoryReq;
		this.arrivalTime = arrivalTime;
		IOBurst = iOBurst;
	}
	
}
