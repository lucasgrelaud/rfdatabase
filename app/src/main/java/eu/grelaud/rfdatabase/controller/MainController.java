package eu.grelaud.rfdatabase.controller;


import android.content.SharedPreferences;
import android.util.Log;
import com.google.gson.Gson;
import eu.grelaud.rfdatabase.RFDatabaseRestAPI;

import eu.grelaud.rfdatabase.SharedPreferencesKeys;
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

    private SharedPreferences sharedPreferences;
    private Gson gson;


    public MainController(MainActivity mainActivity, RFDatabaseRestAPI rfDatabaseRestAPI, SharedPreferences sharedPreferences) {
        this.mainActivity = mainActivity;
        this.rfDatabaseRestAPI = rfDatabaseRestAPI;
        this.sharedPreferences = sharedPreferences;
        this.gson = new Gson();
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
                cacheData(regions, frequencies);
            }

            @Override
            public void onFailure(Call<RestFrequencyResponse> call, Throwable t) {
                Log.d("The API failed", "onFailure");
            }
        });

    }

    private void cacheData(List<Region> regions, List<Frequency> frequencies) {
        String regionCache = this.gson.toJson(regions);
        String frequenciesCache = this.gson.toJson(frequencies);
        this.sharedPreferences
                .edit()
                .putString(SharedPreferencesKeys.regionCacheKey, regionCache)
                .putString(SharedPreferencesKeys.frequenciesCacheKey, frequenciesCache)
                .apply();
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
