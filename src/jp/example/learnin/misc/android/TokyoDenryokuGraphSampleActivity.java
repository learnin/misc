package jp.example.learnin.misc.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import jp.example.learnin.misc.android.R;


/**
 * 東京電力の電力使用状況グラフ表示サンプル<br>
 */
public class TokyoDenryokuGraphSampleActivity extends Activity {

	private static final String TAG = "TokyoDenryokuGraphSampleActivity";

	private Button mShowSummary;
	private Button mShowRecently;
	private Button mShowPast;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mShowSummary = (Button) findViewById(R.id.show_summary);
		mShowSummary.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				showSummary();
			}
		});

		mShowRecently = (Button) findViewById(R.id.show_recently);
		mShowPast = (Button) findViewById(R.id.show_past);
	}

	private void showSummary() {
		Intent intent = new Intent(this, SummaryActivity.class);
		startActivity(intent);
	}

	@Override
	public void onResume() {
		super.onResume();
	}

}
