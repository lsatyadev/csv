import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import java.io.*;
import java.util.Arrays;

/**
 *
 */
public class TestDate {
    public static void main(String[] args) throws Exception {
        String csvFile = "c:/temp/csvfile.csv";
        String csvFile2 = "c:/temp/csvfile123.csv";

        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(csvFile));
            String[] line;
            CSVWriter writer = new CSVWriter(new FileWriter(csvFile2));
            while ((line = reader.readNext()) != null) {

                writer.writeNext(Arrays.copyOf(line, line.length -1));
                System.out.println("Country [id= " + line[0] + ", code= " + line[1] + " , name=" + line[2] + ", id="+ line[3] +"]");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
