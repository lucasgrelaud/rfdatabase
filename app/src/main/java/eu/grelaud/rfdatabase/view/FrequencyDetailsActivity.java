package eu.grelaud.rfdatabase.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.TextView;
import eu.grelaud.rfdatabase.AppKeys;
import eu.grelaud.rfdatabase.R;
import eu.grelaud.rfdatabase.controller.FrequencyDetailsController;
import eu.grelaud.rfdatabase.model.Frequency;

import java.util.List;

public class FrequencyDetailsActivity extends AppCompatActivity {

    private FrequencyDetailsController frequencyDetailsController;

    private RecyclerView frequencyList;
    private RecyclerView.Adapter frequencyListAdapter;
    private RecyclerView.LayoutManager frequencyListLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frequency_details);

        Intent intent = getIntent();
        Frequency frequency = (Frequency) intent.getSerializableExtra(AppKeys.frequencyIntentKey);

        frequencyList = (RecyclerView) findViewById(R.id.afd_sub_frequencies);
        frequencyList.setHasFixedSize(true);
        frequencyListLayoutManager = new LinearLayoutManager(this);
        frequencyList.setLayoutManager(frequencyListLayoutManager);

        frequencyDetailsController = new FrequencyDetailsController(this, frequency);
        frequencyDetailsController.start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateView(String frequency, String type, String eirp, String waveLength, String use, String mode,
                           boolean space, boolean emergency, List<Frequency> subfrequencies) {
        TextView frequencyView = findViewById(R.id.afd_frequency_value);
        frequencyView.setText(frequency);

        TextView typeView = findViewById(R.id.afd_type_value);
        typeView.setText(type);

        TextView eirpView = findViewById(R.id.afd_eirp_value);
        eirpView.setText(eirp);

        TextView wavelengthView = findViewById(R.id.afd_wave_length_value);
        wavelengthView.setText(waveLength);

        TextView useView = findViewById(R.id.afd_use_value);
        useView.setText(use);

        TextView modeView = findViewById(R.id.afd_mode_value);
        modeView.setText(mode);

        CheckBox spaceView = findViewById(R.id.afd_space_checkbox);
        spaceView.setChecked(space);

        CheckBox emergencyView = findViewById(R.id.afd_emergency_checkbox);
        emergencyView.setChecked(emergency);

        this.frequencyListAdapter = new FrequencyListAdpter(this, subfrequencies);
        this.frequencyList.setAdapter(this.frequencyListAdapter);
    }
}
