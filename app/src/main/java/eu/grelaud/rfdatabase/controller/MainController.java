package eu.grelaud.rfdatabase.controller;


import android.content.SharedPreferences;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import eu.grelaud.rfdatabase.R;
import eu.grelaud.rfdatabase.RFDatabaseRestAPI;

import eu.grelaud.rfdatabase.AppKeys;
import eu.grelaud.rfdatabase.model.Frequency;
import eu.grelaud.rfdatabase.model.Region;
import eu.grelaud.rfdatabase.model.RestFrequencyResponse;
import eu.grelaud.rfdatabase.view.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.lang.reflect.Type;
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
    private boolean networkOK;


    public MainController(MainActivity mainActivity, RFDatabaseRestAPI rfDatabaseRestAPI,
                          SharedPreferences sharedPreferences, boolean networkOK) {
        this.mainActivity = mainActivity;
        this.rfDatabaseRestAPI = rfDatabaseRestAPI;
        this.sharedPreferences = sharedPreferences;
        this.gson = new Gson();
        this.networkOK = networkOK;
    }

    public void start() {
        if (this.networkOK) {
            Call<RestFrequencyResponse> regionsCall = rfDatabaseRestAPI.getRegions();
            regionsCall.enqueue(new Callback<RestFrequencyResponse>() {
                @Override
                public void onResponse(Call<RestFrequencyResponse> call, Response<RestFrequencyResponse> response) {
                    RestFrequencyResponse restFrequencyResponse = response.body();
                    regions = restFrequencyResponse.getRegions();
                    frequencies = restFrequencyResponse.getFrequencies();
                    cacheData(regions, frequencies);
                    updateCountryList();
                }

                @Override
                public void onFailure(Call<RestFrequencyResponse> call, Throwable t) {
                    getCachedData();
                    mainActivity.createToast(mainActivity.getString(R.string.no_internet_loading));
                    if (regions != null) {
                        updateCountryList();
                    }
                }
            });
        }
        else {
            getCachedData();
            if (this.regions != null) {
                updateCountryList();
            }
        }


    }

    private void cacheData(List<Region> regions, List<Frequency> frequencies) {
        String regionCache = this.gson.toJson(regions);
        String frequenciesCache = this.gson.toJson(frequencies);
        this.sharedPreferences
                .edit()
                .putString(AppKeys.regionCacheKey, regionCache)
                .putString(AppKeys.frequenciesCacheKey, frequenciesCache)
                .apply();
    }

    private void getCachedData(){
        String regionsStr = this.sharedPreferences.getString(AppKeys.regionCacheKey, "");
        String frequenciesStr = this.sharedPreferences.getString(AppKeys.frequenciesCacheKey, "");

        if(regionsStr != null && !TextUtils.isEmpty(regionsStr)){
            Type regionListType = new TypeToken<List<Region>>(){}.getType();
            this.regions = new Gson().fromJson(regionsStr, regionListType);
        }

        if(frequenciesStr != null && !TextUtils.isEmpty(frequenciesStr)){
            Type frequencyListType = new TypeToken<List<Frequency>>(){}.getType();
            this.frequencies = new Gson().fromJson(frequenciesStr, frequencyListType);
        }
    }

    private void updateCountryList() {
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
