package com.art_no_sundo.timetable.android;

import java.io.IOException;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.content.Context;

import com.art_no_sundo.timetable.Train;
import com.art_no_sundo.timetable.Train.Timetable;

public final class AsyncTrainTimetableQueryTask extends
		AsyncTask<Train, Void, Train.Timetable> {

	Context context;
	ProgressDialog dialog;

	public AsyncTrainTimetableQueryTask(Context context) {
		this.context = context;
		dialog = null;
	}

	@Override
	protected Train.Timetable doInBackground(Train... params) {
		assert (params.length != 0);
		Train.Timetable timetable = null;
		try {
			timetable = params[0].getTimetable();
		} catch (IOException e) {
			// TODO error on getting timetable
			e.printStackTrace();
		}
		return timetable;
	}

	@Override
	protected void onPreExecute() {
		dialog = new ProgressDialog(context);
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dialog.setTitle("Get Timetable");
		dialog.setMessage("Loading timetable");
		dialog.setCancelable(false);
		dialog.show();
	}

	@Override
	protected void onCancelled() {
		dialog.dismiss();
	}

	@Override
	protected void onPostExecute(Timetable result) {
		dialog.dismiss();
	}

}
