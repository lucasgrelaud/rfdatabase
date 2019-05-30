package eu.grelaud.rfdatabase.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import eu.grelaud.rfdatabase.Injector;
import eu.grelaud.rfdatabase.R;
import eu.grelaud.rfdatabase.controller.MainController;
import eu.grelaud.rfdatabase.model.Frequency;

import java.util.List;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private MainController mainController;

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

        mainController = new MainController(this, Injector.getRfDatabaseRestApiInstace());
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
        if (id == R.id.action_settings) {
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
        this.frequencyListAdapter = new FrequencyListAdpter(frequencies);
        this.frequencyList.setAdapter(this.frequencyListAdapter);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        mainController.updateFrequenciesList((String)parent.getItemAtPosition(pos));
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }




}