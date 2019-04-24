package com.github.nijian.jkeel.algorithms;

import com.github.nijian.jkeel.algorithms.subsection.entity.Data;
import com.github.nijian.jkeel.algorithms.subsection.entity.Input;
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

        Input<?> in;
        try {
            in = ObjectHolder.objectMapper.readValue((String) input, Input.class);
        } catch (Exception e) {
            logger.error("Failed to parse input : {}", input);
            throw new RuntimeException("Failed to parse input", e);
        }

        Data data = in.getData();
        if (data == null) {
            data = new Data();
        }

        List<Snapshot> snapshots = data.getSnapshots();
        Snapshot snapshot;
        if (snapshots == null) {
            data.setSnapshots(new ArrayList<>());
            snapshot = new Snapshot();
        } else {
            Snapshot lastSnapshot = snapshots.get(snapshots.size() - 1);
            snapshot = lastSnapshot.clone();
        }
        data.getSnapshots().add(snapshot);

        //create new row

        //calculate amount
        snapshot.calc(in);

        //calculate delta

        return data;
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
