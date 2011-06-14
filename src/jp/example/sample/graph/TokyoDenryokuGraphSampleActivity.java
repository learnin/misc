package jp.example.sample.graph;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

//参考 http://pastebin.com/q1Vcbe9C
// TODO ボタン等のカスタマイズ
// TODO UX重要。例えばボタンを押したらちゃんとへこませて離したら戻す。
// TODO 設定画面でデータ取得間隔を設定し、その間隔で自動的にデータ取得し画面更新する。
// TODO 複数画面にして、最初の画面ではヘッダのサマリ情報を表示し、グラフは別アクティビティにする。
// TODO 複数画面等にして、過去分にも対応する。過去の電力ソースは http://tepco-usage-api.appspot.com/
// TODO WebViewでJavaScriptでも実装してみる。JavaScriptのグラフライブラリを使って。
// TODO 縦横回転時の表示切り替え
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
