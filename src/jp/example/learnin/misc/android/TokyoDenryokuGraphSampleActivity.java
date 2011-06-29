package jp.example.learnin.misc.android;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 東京電力の電力使用状況表示Androidサンプル<br>
 */
public class TokyoDenryokuGraphSampleActivity extends Activity {

	private static final String TAG = "TokyoDenryokuGraphSampleActivity";

	private static final String GET_SUMMARY_TASK_STATUS_NOT_FINISHED = "jp.example.learnin.misc.android.GET_SUMMARY_TASK_STATUS_NOT_FINISHED";
	private static final String GET_SUMMARY_TASK_STATUS_FINISHED = "jp.example.learnin.misc.android.GET_SUMMARY_TASK_STATUS_FINISHED";

	private static final String EXPECTED_MAX_POWER = "jp.example.learnin.misc.android.EXPECTED_MAX_POWER";
	private static final String EXPECTED_MAX_POWER_TIME_LINE = "jp.example.learnin.misc.android.EXPECTED_MAX_POWER_TIME_LINE";

	private TextView mExpectedMaxPower;
	private ProgressBar mProgressBarForExpectedMaxPower;
	private TextView mExpectedMaxPowerTimeLine;
	private ProgressBar mProgressBarForExpectedMaxPowerTimeLine;
	private Button mUpdate;
	private Button mShowRecently;

	private GetSummaryTask mGetSummaryTask;
	private boolean mGotSummaryData = false;

	private Bundle mSavedState;
	private boolean mRestoredState;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		setupViews();
	}

	private void setupViews() {
		mExpectedMaxPower = (TextView) findViewById(R.id.expectedMaxPower);
		mProgressBarForExpectedMaxPower = (ProgressBar) findViewById(R.id.progressBarForExpectedMaxPower);
		mExpectedMaxPowerTimeLine = (TextView) findViewById(R.id.expectedMaxPowerTimeLine);
		mProgressBarForExpectedMaxPowerTimeLine = (ProgressBar) findViewById(R.id.progressBarForeEpectedMaxPowerTimeLine);
		mUpdate = (Button) findViewById(R.id.update);
		mUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mExpectedMaxPower.setText("");
				mProgressBarForExpectedMaxPower.setVisibility(View.VISIBLE);
				mExpectedMaxPowerTimeLine.setText("");
				mProgressBarForExpectedMaxPowerTimeLine
						.setVisibility(View.VISIBLE);
				mGetSummaryTask = new GetSummaryTask();
				mGetSummaryTask.execute();
			}
		});
		mShowRecently = (Button) findViewById(R.id.show_recently);
		mShowRecently.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				showRecently();
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		if (mSavedState != null) {
			restoreInstanceState(mSavedState);
		}
		if (!mRestoredState) {
			mGetSummaryTask = new GetSummaryTask();
			mGetSummaryTask.execute();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		cancelGetSummaryTask();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		saveInstanceState(outState);
		mSavedState = outState;
	}

	private void saveInstanceState(Bundle outState) {
		if (mGetSummaryTask != null
				&& mGetSummaryTask.getStatus() != AsyncTask.Status.FINISHED) {
			mGetSummaryTask.cancel(true);
			outState.putBoolean(GET_SUMMARY_TASK_STATUS_NOT_FINISHED, true);
			mGetSummaryTask = null;
		} else if (mGotSummaryData) {
			outState.putCharSequence(EXPECTED_MAX_POWER,
					mExpectedMaxPower.getText());
			outState.putCharSequence(EXPECTED_MAX_POWER_TIME_LINE,
					mExpectedMaxPowerTimeLine.getText());
			outState.putBoolean(GET_SUMMARY_TASK_STATUS_FINISHED, true);
			mGetSummaryTask = null;
		}
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		restoreInstanceState(savedInstanceState);
		mSavedState = null;
	}

	private void restoreInstanceState(Bundle savedInstanceState) {
		if (savedInstanceState.getBoolean(GET_SUMMARY_TASK_STATUS_NOT_FINISHED)) {
			mGetSummaryTask = new GetSummaryTask();
			mGetSummaryTask.execute();
			mRestoredState = true;
		} else if (savedInstanceState
				.getBoolean(GET_SUMMARY_TASK_STATUS_FINISHED)) {
			mExpectedMaxPower.setText(String.valueOf(savedInstanceState
					.getCharSequence(EXPECTED_MAX_POWER)));
			mProgressBarForExpectedMaxPower.setVisibility(View.GONE);

			mExpectedMaxPowerTimeLine.setText(savedInstanceState
					.getCharSequence(EXPECTED_MAX_POWER_TIME_LINE));
			mProgressBarForExpectedMaxPowerTimeLine.setVisibility(View.GONE);
			mRestoredState = true;
			mGotSummaryData = true;
		}
	}

	private void cancelGetSummaryTask() {
		if (mGetSummaryTask != null
				&& mGetSummaryTask.getStatus() == AsyncTask.Status.RUNNING) {
			mGetSummaryTask.cancel(true);
			mGetSummaryTask = null;
		}
	}

	private void showRecently() {
		Intent intent = new Intent(this, ShowRecentlyActivity.class);
		startActivity(intent);
	}

	public void showSummaryData(CsvData csvData) {
		mExpectedMaxPower
				.setText(String.valueOf(csvData.getExpectedMaxPower()));
		mProgressBarForExpectedMaxPower.setVisibility(View.GONE);

		mExpectedMaxPowerTimeLine
				.setText(csvData.getExpectedMaxPowerTimeLine());
		mProgressBarForExpectedMaxPowerTimeLine.setVisibility(View.GONE);

		mGotSummaryData = true;
	}

	private class GetSummaryTask extends AsyncTask<Void, Integer, CsvData> {

		/*
		 * バックグラウンドで電力データを取得します。<br>
		 */
		@Override
		protected CsvData doInBackground(Void... params) {
			return CsvDataDownloader.downloadData();
		}

		/*
		 * 電力データ取得後、表示を行います。<br>
		 */
		@Override
		protected void onPostExecute(CsvData csvData) {
			if (csvData != null) {
				try {
					showSummaryData(csvData);
				} catch (Exception e) {
				}
			}
		}

		@Override
		protected void onCancelled() {
			mGetSummaryTask = null;
		}
	}

}
