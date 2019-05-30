package eu.grelaud.rfdatabase.model;

import java.util.List;

public class Region {
    private int id;
    private String name;
    private List<String> countries;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getCountries() {
        return countries;
    }
}
