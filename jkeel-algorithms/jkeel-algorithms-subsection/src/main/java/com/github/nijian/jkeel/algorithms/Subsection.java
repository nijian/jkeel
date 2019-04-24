package com.github.nijian.jkeel.algorithms;

import com.github.nijian.jkeel.algorithms.subsection.entity.Data;
import com.github.nijian.jkeel.algorithms.subsection.entity.Snapshot;
import com.github.nijian.jkeel.commons.ObjectHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Subsection is a kind of algorithm.
 *
 * @author nj
 * @since 0.0.6
 */
public final class Subsection extends Algorithm<String, Data, AlgorithmContext> {

    private static Logger logger = LoggerFactory.getLogger(Subsection.class);

    /**
     * Subsection singleton instance
     */
    private static Subsection instance;

    /**
     * private construction
     */
    private Subsection() {
    }

    /**
     * Get serration instance
     *
     * @return serration instance
     */
    public static Subsection getInstance() {
        if (instance == null) {
            synchronized (Subsection.class) {
                if (instance == null) {
                    instance = new Subsection();
                }
            }
        }
        return instance;
    }

    /**
     * Convert raw input to Map
     *
     * @param rawInput raw input
     * @param var      variables collection
     * @param ac       algorithm context
     * @return converted input
     */
    @Override
    protected Map<String, ?> convertInput(String rawInput, Map<String, ?> var, AlgorithmContext ac) {
        //return null, use rawInput
        return null;
    }

    @Override
    protected <T> Data calc(T input, AlgorithmContext ac) {

        String data1 = (String) input;
        Data data = null;
        try {
            data = ObjectHolder.objectMapper.readValue(data1, Data.class);
        } catch (Exception e) {
            logger.error("Failed to parse input : {}", data1);
            throw new RuntimeException("Failed to parse input", e);
        }
        //create new snapshot
        List<Snapshot> snapshots = data.getSnapshots();
        Snapshot snapshot = null;
        if (snapshots == null) {
            data.setSnapshots(new ArrayList<>());
            snapshot = new Snapshot();
            data.getSnapshots().add(snapshot);
        } else {
            Snapshot lastSnapshot = snapshots.get(snapshots.size() - 1);
            snapshot = lastSnapshot.clone();
        }

        //create new row

        //calculate amount

        //calculate delta

        return null;
    }

    /**
     * Debug
     *
     * @param data calculation result
     */
    @Override
    protected void debug(Data data) {


    }
}
