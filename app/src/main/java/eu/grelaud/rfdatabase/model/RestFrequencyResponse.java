package eu.grelaud.rfdatabase.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestFrequencyResponse {
    private List<Region> regions;
    private List<Frequency> frequencies;

    public List<Region> getRegions() {
        return regions;
    }

    public List<Frequency> getFrequencies() {
        return frequencies;
    }
}
