package com.art_no_sundo.timetable.android;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.art_no_sundo.timetable.DataSourceID;
import com.art_no_sundo.timetable.Train;
import com.art_no_sundo.timetable.MockDataSource;

import android.os.Bundle;
import android.app.Activity;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class TrainTimetableActivity extends Activity {

	static final int MP = LinearLayout.LayoutParams.MATCH_PARENT;
	static final int WC = LinearLayout.LayoutParams.WRAP_CONTENT;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_train_time_table);
//		getActionBar().setDisplayHomeAsUpEnabled(true);

		URL url;
		try {
			url = new URL("http://www.ekikara.jp/newdata/detail/4001011/36911.htm");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			url = null;
		}
		Train train = new Train(new DataSourceID(new MockDataSource(), url),
				"普通", "1001M", "普通", "113系", "予約不要", "毎日運転", "特記事項なし");
		Train.Timetable timetable = null;
		try {
			timetable = train.getTimetable();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		((TextView)findViewById(R.id.trainName)).setText(train.getName());
		((TextView)findViewById(R.id.trainId)).setText(train.getTrainId());
		((TextView)findViewById(R.id.trainType)).setText(train.getType());

		TableLayout table = (TableLayout) findViewById(R.id.TrainTimeableTableLayout);
		TableRow row;
		TableRow.LayoutParams row_layout_params = new TableRow.LayoutParams();
		int margin_row = getResources().getDimensionPixelSize(R.dimen.timetable_margin_size);
		row_layout_params.setMargins(margin_row, margin_row, margin_row, margin_row);
//		int margin_col = getResources().getDimensionPixelSize(R.dimen.timetable_border_size);
//		LinearLayout.LayoutParams container_params = new LinearLayout.LayoutParams(WC, WC);
//		container_params.setMargins(margin_col, margin_col, margin_col, margin_col);
		for (int i = 0; i < timetable.segments.size(); ++i) {
			row = new TableRow(this);
			row.setLayoutParams(row_layout_params);

//			LinearLayout container_st, container_tm;
//			container_st = new LinearLayout(this);
//			container_tm = new LinearLayout(this);
//			container_st.setLayoutParams(container_params);
//			container_tm.setLayoutParams(container_params);

			TextView text_st, text_tm;
			text_st = new TextView(this);
			text_tm = new TextView(this);
			text_tm.setTextSize(getResources().getDimension(R.dimen.timetable_station_font_size));
			text_st.setTextSize(getResources().getDimension(R.dimen.timetable_station_font_size));
			text_st.setText(timetable.segments.get(i).getStation().getName());
			text_tm.setText(timetable.segments.get(i).getTimeString());

//			container_st.addView(text_st);
//			container_tm.addView(text_tm);
//			row.addView(container_st);
//			row.addView(container_tm);
			row.addView(text_st);
			row.addView(text_tm);
			table.addView(row);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
