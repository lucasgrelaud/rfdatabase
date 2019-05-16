package eu.grelaud.rfdatabase;

import eu.grelaud.rfdatabase.model.RestFrequencyResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RFDatabaseRestAPI {
    @GET("frequencies.json")
    Call<RestFrequencyResponse> getRegions();
    Call<RestFrequencyResponse> getFrequencies();

}
