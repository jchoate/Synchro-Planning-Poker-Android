package com.agile_coder.poker.client.android;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class EstimateActivity extends Activity {

    private class SubmitListener implements OnClickListener {

        private final String host;
        private final String user;
        private final Spinner spinner;
        private static final String LATE = "You have already submitted an estimate or estimates have already been revealed. Cannot submit an estimate at this time.";

        public SubmitListener(String host, String user, Spinner estimate) {
            this.host = host;
            this.user = user;
            this.spinner = estimate;
        }

        public void onClick(View v) {
            String estimate = spinner.getSelectedItem().toString();
            String message = host + "/poker/" + user + "/" + estimate;
            try {
                submitEstimate(message);
            } catch (IllegalStateException e) {
                Toast.makeText(v.getContext(), LATE, Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        private void submitEstimate(String message) throws IOException, IllegalStateException {
            HttpClient client = new DefaultHttpClient();
            HttpPut put = new HttpPut(message);
            HttpResponse resp = client.execute(put);
            if (resp.getStatusLine().getStatusCode() != 204) {
                throw new IllegalStateException();
            }
        }
    }

    public static final String HOST = "host";

    public static final String USER = "user";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.estimate);
        Spinner spinner = setupSpinner();
        setupButton(spinner);
    }

    private void setupButton(Spinner spinner) {
        Intent intent = getIntent();
        String host = intent.getExtras().getString(HOST);
        String user = intent.getExtras().getString(USER);
        Button button = (Button) findViewById(R.id.Submit);
        button.setOnClickListener(new SubmitListener(host, user, spinner));
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
}
