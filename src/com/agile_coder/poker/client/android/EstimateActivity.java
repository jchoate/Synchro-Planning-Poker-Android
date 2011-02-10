package com.agile_coder.poker.client.android;

import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class EstimateActivity extends Activity {

    public static final String HOST = "host";
    public static final String USER = "user";
    private static final String FAILED_REVEAL = "Unable to reveal estimates.";
    private static final String FAILED_RESET = "Unable to reset estimates.";
    private MessageSender sender = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.estimate);
        Spinner spinner = setupSpinner();
        setupButton(spinner);
        sender = new MessageSender(getIntent().getExtras().getString(HOST), getIntent().getExtras()
                .getString(USER));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.estimate_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.reveal_estimates:
                try {
                    sender.revealEstimates();
                } catch (IllegalStateException e) {

                    Toast.makeText(this.getBaseContext(), FAILED_REVEAL, Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.reset_estimates:
                try {
                    sender.resetEstimates();
                } catch (IllegalStateException e) {
                    Toast.makeText(this.getBaseContext(), FAILED_RESET, Toast.LENGTH_LONG).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void setupButton(Spinner spinner) {
        Button button = (Button) findViewById(R.id.Submit);
        button.setOnClickListener(new SubmitListener(spinner));
    }

    private Spinner setupSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.Spinner01);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.point_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        return spinner;
    }

    private class SubmitListener implements OnClickListener {

        private final Spinner spinner;
        private static final String LATE = "You have already submitted an estimate or estimates have already been revealed. Cannot submit an estimate at this time.";

        public SubmitListener(Spinner estimate) {
            this.spinner = estimate;
        }

        public void onClick(View v) {
            String estimate = spinner.getSelectedItem().toString();
            try {
                sender.submitEstimate(estimate);
            } catch (IllegalStateException e) {
                Toast.makeText(v.getContext(), LATE, Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

    }

}
