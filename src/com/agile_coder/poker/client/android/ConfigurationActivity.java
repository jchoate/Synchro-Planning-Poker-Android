package com.agile_coder.poker.client.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ConfigurationActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button button = (Button) findViewById(R.id.ok);
        button.setOnClickListener(new SetupListener());
    }
    
    private class SetupListener implements OnClickListener {

    	public void onClick(View v) {
        	Intent intent = new Intent("com.agile_coder.poker.ESTIMATE");
        	setHostValue(intent);
        	setUserValue(intent);
        	startActivity(intent);
        }

		private void setUserValue(Intent intent) {
			TextView user = (TextView) findViewById(R.id.user);
        	String userStr = user.getText().toString();
        	intent.putExtra(EstimateActivity.USER, userStr);
		}

		private void setHostValue(Intent intent) {
			TextView host = (TextView) findViewById(R.id.host);
        	String hostStr = host.getText().toString();
        	intent.putExtra(EstimateActivity.HOST, hostStr);
		}
    }
}