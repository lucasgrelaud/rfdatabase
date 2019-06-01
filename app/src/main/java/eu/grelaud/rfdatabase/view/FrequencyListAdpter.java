package eu.grelaud.rfdatabase.view;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import eu.grelaud.rfdatabase.AppKeys;
import eu.grelaud.rfdatabase.R;
import eu.grelaud.rfdatabase.model.Frequency;

import java.util.List;

public class FrequencyListAdpter extends RecyclerView.Adapter<FrequencyListAdpter.FrequencyViewHolder> {

    public static class FrequencyViewHolder extends RecyclerView.ViewHolder {

        public TextView frequency;
        public TextView span;
        public TextView type;
        public TextView eirp;
        public ImageButton button;
        public FrequencyViewHolder(View frequencyRow) {
           super(frequencyRow);
           this.frequency = (TextView) frequencyRow.findViewById(R.id.flr_frequency);
           this.span = (TextView) frequencyRow.findViewById(R.id.flr_span_value);
           this.type = (TextView) frequencyRow.findViewById(R.id.flr_type_value);
           this.eirp = (TextView) frequencyRow.findViewById(R.id.flr_eirp_value);
           this.button = (ImageButton) frequencyRow.findViewById(R.id.flr_button);
        }

    }

    private Activity parent;
    private List<Frequency> frequencies;

    public FrequencyListAdpter(Activity parent, List<Frequency> frequencies) {
        this.parent = parent;
        this.frequencies = frequencies;
    }

    @Override
    public FrequencyListAdpter.FrequencyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View frequencyView = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.frequency_listing_row,
                parent, false);
        return new FrequencyViewHolder(frequencyView);
    }

    @Override
    public void onBindViewHolder(final FrequencyViewHolder frequencyViewHolder, int position) {
        final Frequency frequencyObj = frequencies.get(position);
        String frequencyStr;

        if (frequencyObj.getFrequency_range().length == 1) {
            frequencyStr = frequencyObj.getFrequency_range()[0] + " " + frequencyObj.getFrequency_base();
        }
        else {
            frequencyStr = frequencyObj.getFrequency_range()[0] + " " + frequencyObj.getFrequency_base()
                    + " −" + frequencyObj.getFrequency_range()[1] + " " + frequencyObj.getFrequency_base();
        }

        frequencyViewHolder.frequency.setText(frequencyStr);
        frequencyViewHolder.span.setText(frequencyObj.getFrequency_span());
        frequencyViewHolder.type.setText(frequencyObj.getFrequency_type());
        frequencyViewHolder.eirp.setText(frequencyObj.getEIRP());
        frequencyViewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent, FrequencyDetailsActivity.class);
                intent.putExtra(AppKeys.frequencyIntentKey, frequencyObj);
                parent.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return frequencies.size();
    }

}
