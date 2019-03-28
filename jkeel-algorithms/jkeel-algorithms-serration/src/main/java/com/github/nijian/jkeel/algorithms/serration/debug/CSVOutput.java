package com.github.nijian.jkeel.algorithms.serration.debug;

import com.github.nijian.jkeel.algorithms.debug.Output;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.util.List;

public class CSVOutput implements Output<List<List<String>>, List<String>> {

    @Override
    public void write(List<List<String>> table, List<String> headers) {
        try {
            FileWriter out = new FileWriter("book_new.csv");
            try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT
                    .withHeader(headers.toArray(new String[0])))) {
                table.stream().forEach(row ->
                        {
                            try {
                                printer.printRecord(row);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
