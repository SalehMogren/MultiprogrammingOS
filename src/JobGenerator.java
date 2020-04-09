/*This class will generate a text file that has jobs generated randomly
 * 1- CPU-Burst range: 10-100
2- Memory size: 5-200
3- I/O Burst: 20-60
4- Arrival time: 1-80
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.Buffer;
import java.io.FileWriter;  
import java.util.concurrent.ThreadLocalRandom;

public class JobGenerator {

	public static void main(String[] args) {

		 try {
		      File myObj = new File("InputFile.txt");
		      if (myObj.createNewFile()) {
		        System.out.println("File created: " + myObj.getName());
		      } else {
		        System.out.println("File already exists.");
		      }
				 createJobs(myObj);

		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		 
		  }
	//this method will write on the file the random jobs
	static	void createJobs(File f){
			try {
				BufferedWriter fw =new BufferedWriter ( new FileWriter(f));
//				ObjectOutputStream ofw = new ObjectOutputStream(fw);
				fw.write("id\tcpu\tmemory\tio\tarTime\t");

				for(int i = 0;i<5;i++) {
					fw.write("cpu\tmemory\tio\t");
				}
				fw.newLine();
				for(int i=0;i<100;i++) {
					fw.write(i+1+"\t"); //pid
					fw.write(ThreadLocalRandom.current().nextInt(10, 101)+"\t");	//CPU burst
					fw.write(ThreadLocalRandom.current().nextInt(5, 201)+"\t");     //Memory burst
					fw.write(ThreadLocalRandom.current().nextInt(20, 61)+"\t");		//io burst

					fw.write(ThreadLocalRandom.current().nextInt(1, 81)+"\t");   	//arrival time 
					
					int n =ThreadLocalRandom.current().nextInt(2, 4) ;
					for (int j=0;j<n;j++) {
						fw.write(ThreadLocalRandom.current().nextInt(10, 101)+"\t");	//CPU burst
						fw.write(ThreadLocalRandom.current().nextInt(5, 201)+"\t");     //Memory burst
						fw.write(ThreadLocalRandom.current().nextInt(20, 61)+"\t");		//io burset 
					}
					fw.newLine();
				}
				
				
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
			      System.out.println("An error occurred.");
			      
				e.printStackTrace();
			}
			
		}
	
	}
	

