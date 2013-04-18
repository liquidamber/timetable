package com.art_no_sundo.timetable.android;

import java.util.concurrent.ExecutionException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button buttonStation = (Button) findViewById(R.id.button_search_as_station);
		buttonStation.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						TrainTimetableActivity.class);
				startActivity(intent);
			}
		});
		Button buttonSearchLine = (Button) findViewById(R.id.button_search_as_line);
		buttonSearchLine.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
//				SimpleAsyncTask task = new SimpleAsyncTask(getApplicationContext());
				SimpleAsyncTask task = new SimpleAsyncTask(v.getContext());
				task.execute();
			}
		});
	}

	private static final class SimpleAsyncTask extends
	AsyncTask<Void, Void, Void> {

		private Context context;
		private ProgressDialog dialog;

		SimpleAsyncTask(Context context){
			Log.d("async-test", "task constructor enter");
			this.context = context;
			Log.d("async-test", "task constructor exit");
		}

		@Override
		protected void onPostExecute(Void result) {
			Log.d("async-test", "onPostExecute enter");
			if(dialog != null && dialog.isShowing()) {
				dialog.dismiss();
			}
			Log.d("async-test", "onPostExecute exit");
			Intent intent = new Intent(context,
					SearchResultActivity.class);
			context.startActivity(intent);
		}

		@Override
		protected void onPreExecute() {
			Log.d("async-test", "onPreExecute enter");
			dialog = new ProgressDialog(context);
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog.setTitle("Sleep...");
			dialog.setMessage("Sleeping...");
			dialog.setCancelable(false);
			dialog.show();
			Log.d("async-test", "onPreExecute exit");
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				Log.d("async-test", "wait beginning");
				Thread.sleep(1000);
				Log.d("async-test", "wait 1s");
				Thread.sleep(2000);
				Log.d("async-test", "wait 3s");
				Thread.sleep(3000);
				Log.d("async-test", "wait 6s, complete");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	}
}
