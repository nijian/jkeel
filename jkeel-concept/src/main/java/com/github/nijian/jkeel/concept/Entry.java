package com.github.nijian.jkeel.concept;

public class Entry {

    private String conceptName;

    private String configName;

    public String getConceptName() {
        return conceptName;
    }

    public void setConceptName(String conceptName) {
        this.conceptName = conceptName;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public static Entry parse(String entryStr) {

        Entry entry = new Entry();

        String[] segs = entryStr.split("@");
        int len = segs.length;
        if (len > 2) {
            throw new RuntimeException("xxx");
        } else if (len == 2) {
            entry.setConfigName(segs[0]);
            entry.setConceptName(segs[1]);
        } else {
            entry.setConceptName(segs[0]);
        }

        return entry;
    }
}
