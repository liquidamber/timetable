package com.art_no_sundo.timetable.android;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button button_station = (Button) findViewById(R.id.button_search_as_station);
		button_station.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClassName(
						"com.art_no_sundo.timetable.mobile",
						"com.art_no_sundo.timetable.mobile.TrainTimetableActivity");
				startActivity(intent);
			}
		});
	}
}
