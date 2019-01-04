package local.spiritkill;
import java.io.Serializable;


public class SpiritKill {

	public static final void main (String... args) {
		
		System.out.println("Test");
	}
}

class Stats implements Serializable {
	
	
	private Map <String, Float> drunkWeeks = new HashMap<>();

	Stats () {
		File serializedStats = new File("Stats.ser");
		if (!tmpDir.exists()) {
			LocalDate date = LocalDate.now();
			drunkWeeks.put(date.getYear(), null);

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
				System.out.println("Employee class not found");
         			c.printStackTrace();
			}
		}
	}

	public enterAndCalcAlco () {}

}
