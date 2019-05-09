package eu.grelaud.rfdatabase.model;

public class Frequency {
    private Float[] frequency_range;
    private String frequency_base;
    private String frequency_span;
    private String frequency_type;
    private String wave_length;
    private String EIRP;
    private String[] transmition_mode;
    private String use;
    private boolean emergency;
    private Frequency[] sub_allocation;
    private boolean space;
    private Integer[] regions;

    public Float[] getFrequency_range() {
        return frequency_range;
    }

    public String getFrequency_base() {
        return frequency_base;
    }

    public String getFrequency_span() {
        return frequency_span;
    }

    public String getFrequency_type() {
        return frequency_type;
    }

    public String getWave_length() {
        return wave_length;
    }

    public String getEIRP() {
        return EIRP;
    }

    public String[] getTransmition_mode() {
        return transmition_mode;
    }

    public String getUse() {
        return use;
    }

    public boolean isEmergency() {
        return emergency;
    }

    public Frequency[] getSub_allocation() {
        return sub_allocation;
    }

    public boolean isSpace() {
        return space;
    }

    public Integer[] getRegions() {
        return regions;
    }
}
