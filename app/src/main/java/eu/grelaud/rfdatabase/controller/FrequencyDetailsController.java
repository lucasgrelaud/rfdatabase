package eu.grelaud.rfdatabase.controller;

import android.text.TextUtils;
import eu.grelaud.rfdatabase.R;
import eu.grelaud.rfdatabase.model.Frequency;
import eu.grelaud.rfdatabase.view.FrequencyDetailsActivity;

import java.util.Arrays;

public class FrequencyDetailsController {
    private FrequencyDetailsActivity frequencyDetailsActivity;
    private Frequency frequency;
    private String frequencyStr;
    private String type;
    private String eirp;
    private String wave_length;
    private String use;
    private String modeStr;

    public FrequencyDetailsController(FrequencyDetailsActivity frequencyDetailsActivity, Frequency frequency) {
        this.frequencyDetailsActivity = frequencyDetailsActivity;
        this.frequency = frequency;

        if (frequency.getFrequency_range().length == 1) {
            frequencyStr = frequency.getFrequency_range()[0] + " " + frequency.getFrequency_base();
        }
        else {
            frequencyStr = frequency.getFrequency_range()[0] + " " + frequency.getFrequency_base()
                    + " −" + frequency.getFrequency_range()[1] + " " + frequency.getFrequency_base();
        }

        type = validateStringData(frequency.getFrequency_type());
        eirp = validateStringData(frequency.getEIRP());
        wave_length = validateStringData(frequency.getWave_length());
        use = validateStringData(frequency.getUse());

        if (frequency.getTransmition_mode().length != 0 && frequency.getTransmition_mode()[0] != "?") {
            StringBuilder modeStrbuilder = new StringBuilder();
            for (String str : frequency.getTransmition_mode()) {
                modeStrbuilder.append("▸ ").append(str).append("\n");
            }
            modeStr = modeStrbuilder.toString();
        }
        else {
            modeStr = frequencyDetailsActivity.getString(R.string.unknown_data);
        }
    }

    public void start(){
        frequencyDetailsActivity.updateView(frequencyStr,type, eirp,wave_length, use, modeStr, frequency.isSpace(),
                frequency.isEmergency(), Arrays.asList(frequency.getSub_allocation()));
    }

    private String validateStringData(String value){
        return (!TextUtils.isEmpty(value) && !value.equals("?"))?value:frequencyDetailsActivity
                .getString(R.string.unknown_data);
    }
}
