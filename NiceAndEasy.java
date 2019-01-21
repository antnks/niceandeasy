package local.niceandeasy;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.HashMap;
import java.time.LocalDate;
import java.time.format.*;
import java.time.temporal.IsoFields;



public class NiceAndEasy {

	public static final void main (String... args) {
		

		Console console = System.console();
		if (console == null) throw new RuntimeException ("No console on this sytem, exiting!");

		String dateToTest = console.readLine("Enter a date YYYYMMDD\n");
		float ml = (float) Double.parseDouble((console.readLine("Enter ml\n")));
		float percent = (float) Double.parseDouble((console.readLine("Enter percent\n")));
		float realMl = ml * percent / 100;
		DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyyMMdd");
		LocalDate date = LocalDate.parse(dateToTest, f);

		int week = date.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
		int weekYear = date.get(IsoFields.WEEK_BASED_YEAR);

		//System.out.println("Date: " + date + ", week: " + week + ", year: " + weekYear + ", ml: " + ml + ", percent: " + percent + ", realMl: " + realMl);


		Stats theInstance = new Stats();
		theInstance.printStats();
		theInstance.enterAndCalcItMock("" + weekYear + "_" + week, realMl);
		theInstance.printStats();
		





		theInstance.serialize(theInstance);
	}
}

class Stats implements Serializable {
	
	
	private static final long serialVersionUID = 1L;

	private Map <String, Float> thoseWeeks = new HashMap<>();

	Stats () {
		Path serializedStats = Paths.get("Stats.ser");
		if (!Files.exists(serializedStats)) {
			LocalDate date = LocalDate.now();
			thoseWeeks.put(Integer.toString(date.getYear()), null);

		} else {
			try {
         			ObjectInputStream in = new ObjectInputStream(Files.newInputStream(Paths.get("Stats.ser")));
				this.thoseWeeks = ((Stats) in.readObject()).thoseWeeks;
         			in.close();
      			} catch (IOException i) {
				i.printStackTrace();
			} catch (ClassNotFoundException c) {
				System.out.println("Stats class not found");
         			c.printStackTrace();
			}
		}
	}

public void enterCalcStore () {}

public void enterAndCalcItMock (String year, Float promille) {
	thoseWeeks.put(year, promille);
}

public void printStats () {
	thoseWeeks.forEach((k, v) -> System.out.println(k + " " + v));
}

public void serialize (Stats toBeSerialized) {
	try {
		ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(Paths.get("Stats.ser")));
       		out.writeObject(toBeSerialized);
       		out.close();
       		System.out.println("Serialized data is saved in Stats.ser");
	} catch (IOException i) {
       		i.printStackTrace();
	}
}

}
