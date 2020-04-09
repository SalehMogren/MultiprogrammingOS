/*This class will generate a text file that has jobs generated randomly
 * 1- CPU-Burst range: 10-100
2- Memory size: 5-200
3- I/O Burst: 20-60
4- Arrival time: 1-80
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
				FileOutputStream fw = new FileOutputStream(f);
				ObjectOutputStream ofw = new ObjectOutputStream(fw);
				for (int i = 0; i <10; i++) {
					Job temp= new Job(
							ThreadLocalRandom.current().nextInt(10, 101),	//cpu burst
							ThreadLocalRandom.current().nextInt(5, 201),     //memmory burst
							ThreadLocalRandom.current().nextInt(1, 81),     //arrival time 
							ThreadLocalRandom.current().nextInt(20, 61)      //io burst
							);
					ofw.writeObject(temp);
				}
				ofw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
			      System.out.println("An error occurred.");
			      
				e.printStackTrace();
			}
			
		}
	
	}
	

