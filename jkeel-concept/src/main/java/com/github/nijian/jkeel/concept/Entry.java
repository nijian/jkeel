package com.github.nijian.jkeel.concept;

public class Entry {

    private String behaviorName;

    private String configName;

    public String getBehaviorName() {
        return behaviorName;
    }

    public void setBehaviorName(String behaviorName) {
        this.behaviorName = behaviorName;
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
            entry.setBehaviorName(segs[1]);
        } else {
            entry.setBehaviorName(segs[0]);
        }

        return entry;
    }
}
