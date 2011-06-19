package jp.example.learnin.misc.android;

import android.app.Activity;
import android.content.Intent;
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

	private TextView mExpectedMaxPower;
	private ProgressBar mProgressBarForExpectedMaxPower;
	private TextView mExpectedMaxPowerTimeLine;
	private ProgressBar mProgressBarForExpectedMaxPowerTimeLine;
	private Button mShowRecently;

	private boolean mGotSummaryData = false;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mExpectedMaxPower = (TextView) findViewById(R.id.expectedMaxPower);
		mProgressBarForExpectedMaxPower = (ProgressBar) findViewById(R.id.progressBarForExpectedMaxPower);
		mExpectedMaxPowerTimeLine = (TextView) findViewById(R.id.expectedMaxPowerTimeLine);
		mProgressBarForExpectedMaxPowerTimeLine = (ProgressBar) findViewById(R.id.progressBarForeEpectedMaxPowerTimeLine);
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
		if (!mGotSummaryData) {
			GetSummaryTask task = new GetSummaryTask(this);
			task.execute();
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

}
