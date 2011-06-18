package jp.example.learnin.misc.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 東京電力の電力使用状況グラフ表示Androidサンプル<br>
 */
public class TokyoDenryokuGraphSampleActivity extends Activity {

	private static final String TAG = "TokyoDenryokuGraphSampleActivity";

	private Button mShowRecently;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mShowRecently = (Button) findViewById(R.id.show_recently);
		mShowRecently.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				showRecently();
			}
		});
	}

	private void showRecently() {
		Intent intent = new Intent(this, ShowRecentlyActivity.class);
		startActivity(intent);
	}

	@Override
	public void onResume() {
		super.onResume();
	}

}
