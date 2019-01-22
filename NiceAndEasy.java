package local.niceandeasy;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;
import java.time.LocalDate;
import java.time.format.*;
import java.time.temporal.IsoFields;



public class NiceAndEasy {

	public static final void main (String... args) {
		
		Stats theInstance = new Stats();
		theInstance.enterCalcStore();
		theInstance.printStats();

		theInstance.serialize(theInstance);
	}
}

class Stats implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Map <String, Float> thoseWeeks = new TreeMap<>();

	Stats () {
		Path serializedStats = Paths.get("Stats.ser");
		if (!Files.exists(serializedStats)) {
			return;
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

public void enterCalcStore () {

                Console console = System.console();
                if (console == null) throw new RuntimeException ("No console on this sytem, exiting!");

		if (console.readLine("Enter \"s\" if you only want statistics\n").equals("s")) return;

		while (true) {

			String entry = console.readLine("Enter comma-separated \"ml,percentAlcohol for today or ml,percentAlcohol,Date formatted YYYYMMDD\"\n");
			String parts [] = entry.split(",");
                	float ml = (float) Double.parseDouble(parts[0]);
                	float percent = (float) Double.parseDouble(parts[1]);
			String yearWeek = null;
			LocalDate date = null;
			if (parts.length == 2) date = LocalDate.now(); else {DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyyMMdd"); date = LocalDate.parse(parts[2], f);}
                                
			int week = date.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
                        int weekYear = date.get(IsoFields.WEEK_BASED_YEAR);
                        yearWeek = "" + weekYear + "_" + week;

			if (!console.readLine("You entered: ml: " + ml + ", percent: " + percent + ", WeekOfYear: " + yearWeek + ", OK to write to DB? (y/n)\n").equals("y")) continue;
                	
			float realMl = ml * percent / 100;

			if (thoseWeeks.containsKey("" + weekYear + "_" + week)) {
				thoseWeeks.put("" + weekYear + "_" + week, realMl + thoseWeeks.get("" + weekYear + "_" + week));
			} else thoseWeeks.put("" + weekYear + "_" + week, realMl);

			if (console.readLine("One more drink? (y/n)\n").equals("y")) continue; else break;
		}

}

public void enterAndCalcItMock (String weekOfYear, Float mlAlcohol) {
	thoseWeeks.put(weekOfYear, mlAlcohol);
}

public void printStats () {
	thoseWeeks.forEach((k, v) -> System.out.println(k + " " + v));
}

public void serialize (Stats toBeSerialized) {
	try {
		ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(Paths.get("Stats.ser")));
       		out.writeObject(toBeSerialized);
       		out.close();
	} catch (IOException i) {
       		i.printStackTrace();
	}
}

}
