package com.sleep;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class Sleep extends Activity implements OnClickListener {
	private int screenOffTimeout;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		View offButton = findViewById(R.id.off_button);
		offButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		reduceDisplayTimeout();
	}

	private void reduceDisplayTimeout() {
		try {
			screenOffTimeout = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT);

			Log.d("Sleep", String.format("Current display timeout is %s", screenOffTimeout));
			
			Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, 2000);
		} catch (SettingNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		resetDisplayTimeout();
	}

	private void resetDisplayTimeout() {
		PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
		Log.d("Sleep", String.format("Is screee on: %s", powerManager.isScreenOn()));

		Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, screenOffTimeout);
	}
}