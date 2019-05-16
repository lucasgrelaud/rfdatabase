package eu.grelaud.rfdatabase;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Injector {
    static final String BASE_URL = "https://raw.githubusercontent.com/lucasgrelaud/rfdatabase/master/api/";
    private static RFDatabaseRestAPI RF_DATABASE_REST_API = null;

    private static RFDatabaseRestAPI createRFDatabaseAPI() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

       Retrofit retrofit = new Retrofit.Builder()
               .baseUrl(BASE_URL)
               .addConverterFactory(GsonConverterFactory.create(gson))
               .build();

       return retrofit.create(RFDatabaseRestAPI.class);
    }

    static RFDatabaseRestAPI getRfDatabaseRestApiInstace() {
        if (RF_DATABASE_REST_API == null) {
            RF_DATABASE_REST_API = createRFDatabaseAPI();
        }
        return RF_DATABASE_REST_API;
    }
}
