package eu.grelaud.rfdatabase.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import eu.grelaud.rfdatabase.AppKeys;
import eu.grelaud.rfdatabase.Injector;
import eu.grelaud.rfdatabase.R;
import eu.grelaud.rfdatabase.controller.MainController;
import eu.grelaud.rfdatabase.model.Frequency;

import java.util.List;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private MainController mainController;
    private SharedPreferences sharedPreferences;

    private Spinner countrySelect;
    private ArrayAdapter<String> countrySelectAdapter;
    private TextView uit_name;

    private RecyclerView frequencyList;
    private RecyclerView.Adapter frequencyListAdapter;
    private RecyclerView.LayoutManager frequencyListLayoutManager;

    public Spinner getCountrySelect() {
        return countrySelect;
    }

    public ArrayAdapter<String> getCountrySelectAdapter() {
        return countrySelectAdapter;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        countrySelectAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        countrySelect = (Spinner) findViewById(R.id.country_select);
        countrySelect.setAdapter(countrySelectAdapter);
        countrySelect.setOnItemSelectedListener(this);

        uit_name = (TextView) findViewById(R.id.uit_name);

        frequencyList = (RecyclerView) findViewById(R.id.frequency_list_recyclerview);
        frequencyList.setHasFixedSize(true);
        frequencyListLayoutManager = new LinearLayoutManager(this);
        frequencyList.setLayoutManager(frequencyListLayoutManager);

        sharedPreferences = this.getSharedPreferences("app_data", Context.MODE_PRIVATE);

        mainController = new MainController(this, Injector.getRfDatabaseRestApiInstace(), sharedPreferences,
                isNetworkAvailable());
        mainController.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        // TODO : Set the wanted menu options
        if (id == R.id.action_about) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(AppKeys.projectGithubRepositoyUrl));
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void updateCountryList(List<String> countries) {
        this.getCountrySelectAdapter().addAll(countries);
        this.getCountrySelectAdapter().notifyDataSetChanged();
    }

    public void updateFrequencyList(String uitName, List<Frequency> frequencies) {
        this.uit_name.setText(uitName);
        this.frequencyListAdapter = new FrequencyListAdpter(this, frequencies);
        this.frequencyList.setAdapter(this.frequencyListAdapter);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            return true;
        }
        else {
            createToast("No access to the internet. Will use the cached data.");
            return false;
        }
    }

    public void createToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

        mainController.updateFrequenciesList((String)parent.getItemAtPosition(pos));
    }

    public void onNothingSelected(AdapterView<?> parent) {
    }

}
