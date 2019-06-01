package eu.grelaud.rfdatabase.controller;

import eu.grelaud.rfdatabase.model.Frequency;
import eu.grelaud.rfdatabase.view.FrequencyDetailsActivity;

import java.util.Arrays;

public class FrequencyDetailsController {
    private FrequencyDetailsActivity frequencyDetailsActivity;
    private Frequency frequency;
    private String frequencyStr;
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
        StringBuilder modeStrbuilder = new StringBuilder();
        for (String str : frequency.getTransmition_mode()) {
            modeStrbuilder.append("▸ ").append(str).append("\n");
        }
        modeStr = modeStrbuilder.toString();
    }

    public void start(){
        frequencyDetailsActivity.updateView(frequencyStr, frequency.getFrequency_type(), frequency.getEIRP(),
                frequency.getWave_length(), frequency.getUse(), modeStr, frequency.isSpace(), frequency.isEmergency(),
                Arrays.asList(frequency.getSub_allocation()));
    }
}
