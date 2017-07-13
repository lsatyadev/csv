import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CSVUtils {

    private static final char DEFAULT_SEPARATOR = ',';
    private static final char DEFAULT_QUOTE = '"';
    private static final int COLUMN_COUNT = 3;

    public static void main(String[] args) throws Exception {

        String csvFile = "C:/Temp/csvfile.csv";
        String csvFile2 = "C:/Temp/test123.csv";
        try(Scanner scanner = new Scanner(new File(csvFile));
            FileWriter fw = new FileWriter(csvFile2);
            BufferedWriter bw = new BufferedWriter(fw)) {
            while (scanner.hasNext()) {
                String line = parseLine(scanner.nextLine());
                bw.write(line);
                bw.newLine();
            }
        }

        System.out.println("Completed !!");
    }

    public static String parseLine(String cvsLine) {

        StringBuffer result = new StringBuffer();

        //if empty, return!
        if (cvsLine == null && cvsLine.isEmpty()) {
            return result.toString();
        }

        StringBuffer curVal = new StringBuffer();
        boolean inQuotes = false;
        boolean startCollectChar = false;
        boolean doubleQuotesInColumn = false;
        int columnCount = 0;
        char[] chars = cvsLine.toCharArray();

        for (char ch : chars) {
            if(columnCount == COLUMN_COUNT) {
                break;
            }
            if (inQuotes) {
                startCollectChar = true;
                if (ch == DEFAULT_QUOTE) {
                    inQuotes = false;
                    doubleQuotesInColumn = false;
                } else {

                    //Fixed : allow "" in custom quote enclosed
                    if (ch == '\"') {
                        if (!doubleQuotesInColumn) {
                            curVal.append(ch);
                            doubleQuotesInColumn = true;
                        }
                    } else {
                        curVal.append(ch);
                    }

                }
            } else {
                if (ch == DEFAULT_QUOTE) {

                    inQuotes = true;

                    //Fixed : allow "" in empty quote enclosed
                    if (chars[0] != '"') {
                        curVal.append('"');
                    }

                    //double quotes in column will hit this!
                    if (startCollectChar) {
                        curVal.append('"');
                    }

                } else if (ch == DEFAULT_SEPARATOR) {
                    result.append(curVal.toString());
                    columnCount++;
                    if(columnCount < COLUMN_COUNT) {
                        result.append(DEFAULT_SEPARATOR);
                    }

                    curVal = new StringBuffer();
                    startCollectChar = false;
                } /*else if (ch == '\r') {
                    //ignore LF characters
                    continue;
                } else if (ch == '\n') {
                    //the end, break!
                    break;
                }*/ else {
                    curVal.append(ch);
                }
            }

        }

        result.append(curVal.toString());
        return result.toString();
    }

}