package local.niceandeasy;
import java.io.*;
import java.util.Map;
import java.util.HashMap;
import java.time.LocalDate;

/it's a comment

public class NiceAndEasy {

	public static final void main (String... args) {
		
		System.out.println("Test");

		Stats theInstance = new Stats();
		theInstance.printStats();
		theInstance.enterAndCalcAlcoMock("2018", 2000.12f);
		theInstance.printStats();
		theInstance.serialize(theInstance);
	}
}

class Stats implements Serializable {
	
	
	private Map <String, Float> drunkWeeks = new HashMap<>();

	Stats () {
		File serializedStats = new File("Stats.ser");
		if (!serializedStats.exists()) {
			LocalDate date = LocalDate.now();
			drunkWeeks.put(Integer.toString(date.getYear()), null);

		} else {
			Stats deserialized = null;
			try {
				FileInputStream fileIn = new FileInputStream("Stats.ser");
         			ObjectInputStream in = new ObjectInputStream(fileIn);
         			deserialized = (Stats) in.readObject();
         			in.close();
         			fileIn.close();
				this.drunkWeeks = deserialized.drunkWeeks;
				deserialized = null;
				System.gc();
      			} catch (IOException i) {
				i.printStackTrace();
			} catch (ClassNotFoundException c) {
				System.out.println("Stats class not found");
         			c.printStackTrace();
			}
		}
	}

	public void enterAndCalcAlco () {}

	public void enterAndCalcAlcoMock (String year, Float promille) {
		drunkWeeks.put(year, promille);
	}

	public void printStats () {
		drunkWeeks.forEach((k, v) -> System.out.println(k + " " + v));
	}

	public void serialize (Stats toBeSerialized) {
		try {
         		FileOutputStream fileOut = new FileOutputStream("Stats.ser");
         		ObjectOutputStream out = new ObjectOutputStream(fileOut);
         		out.writeObject(toBeSerialized);
         		out.close();
         		fileOut.close();
         		System.out.printf("Serialized data is saved in Stats.ser");
      		} catch (IOException i) {
         		i.printStackTrace();
      		}
	}

}
