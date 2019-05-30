package eu.grelaud.rfdatabase.controller;


import android.util.Log;
import eu.grelaud.rfdatabase.RFDatabaseRestAPI;

import eu.grelaud.rfdatabase.model.Frequency;
import eu.grelaud.rfdatabase.model.Region;
import eu.grelaud.rfdatabase.model.RestFrequencyResponse;
import eu.grelaud.rfdatabase.view.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class MainController {
    private MainActivity mainActivity;

    private RFDatabaseRestAPI rfDatabaseRestAPI;
    private List<Region> regions;
    private List<Frequency> frequencies;


    public MainController(MainActivity mainActivity, RFDatabaseRestAPI rfDatabaseRestAPI) {
        this.mainActivity = mainActivity;
        this.rfDatabaseRestAPI = rfDatabaseRestAPI;
    }

    public void start() {
        Call<RestFrequencyResponse> regionsCall = rfDatabaseRestAPI.getRegions();
        regionsCall.enqueue(new Callback<RestFrequencyResponse>() {
            @Override
            public void onResponse(Call<RestFrequencyResponse> call, Response<RestFrequencyResponse> response) {
                RestFrequencyResponse restFrequencyResponse = response.body();
                regions = restFrequencyResponse.getRegions();
                updateCountryList();
                frequencies = restFrequencyResponse.getFrequencies();

            }

            @Override
            public void onFailure(Call<RestFrequencyResponse> call, Throwable t) {
                Log.d("The API failed", "onFailure");
            }
        });

    }

    private void updateCountryList() {
        Log.d("debug", regions.toString());
        ArrayList<String> countries = new ArrayList<>();
        for (Region region : regions) {
            countries.addAll(region.getCountries());
        }
        Collections.sort(countries);

        this.mainActivity.updateCountryList(countries);
    }

    public void updateFrequenciesList(String country){
        if (this.frequencies == null || this.frequencies.size() == 0) {
            return;
        }

        Region selectedRegion = null;
        for (Region region : regions) {
            int result = region.getCountries().indexOf(country);
            if (result > 0) {
                selectedRegion = region;
                break;
            }
        }

        if(selectedRegion == null) {
            return;
        }

        List<Frequency> selectedFrequencies = new ArrayList<>();
        for (Frequency freq : this.frequencies) {
            if (freq.getRegions() != null && Arrays.asList(freq.getRegions()).contains(selectedRegion.getId())) {
                selectedFrequencies.add(freq);
            }
        }

        this.mainActivity.updateFrequencyList(selectedRegion.getName(), selectedFrequencies);
    }
}