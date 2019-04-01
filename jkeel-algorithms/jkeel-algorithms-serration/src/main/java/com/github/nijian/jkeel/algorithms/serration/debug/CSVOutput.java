package com.github.nijian.jkeel.algorithms.serration.debug;

import com.github.nijian.jkeel.algorithms.debug.Output;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.util.List;

/**
 * Debug output in csv format.
 *
 * @author nj
 * @since 0.0.1
 */
public class CSVOutput implements Output<List<List<String>>, List<String>, FileWriter> {

    private static Logger logger = LoggerFactory.getLogger(CSVOutput.class);

    /**
     * Write debug output
     *
     * @param table   data source
     * @param headers csv header
     * @param out     real output
     */
    @Override
    public void write(List<List<String>> table, List<String> headers, FileWriter out) {
        try {
            try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT
                    .withHeader(headers.toArray(new String[0])))) {
                table.forEach(row ->
                        {
                            try {
                                printer.printRecord(row);
                            } catch (Exception ex) {
                                logger.error("Failed to print record to csv : {}", row);
                                throw new RuntimeException("Failed to print record to csv", ex);
                            }
                        }
                );
            }
        } catch (Exception e) {
            logger.error("Failed to write debug into", e);
            throw new RuntimeException("Failed to write debug info", e);
        }
    }
}
