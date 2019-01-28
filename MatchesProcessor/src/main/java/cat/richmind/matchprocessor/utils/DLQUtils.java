package cat.richmind.matchprocessor.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class DLQUtils {
	public static void saveInDLQ(String item) throws IOException {
	    BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\dlq\\" + new Date().getTime() + ".json"));
	    writer.write(item);
	     
	    writer.close();
	}
}