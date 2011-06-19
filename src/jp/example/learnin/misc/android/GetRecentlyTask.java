package jp.example.learnin.misc.android;

import android.os.AsyncTask;
import android.view.View;

public class GetRecentlyTask extends AsyncTask<Void, Integer, CsvData> {

	private static final String TAG = "GetRecentlyTask";
	private ShowRecentlyActivity mActivity = null;

	// private ProgressDialog mProgressDialog = null;

	public GetRecentlyTask(ShowRecentlyActivity activity) {
		mActivity = activity;
	}

	@Override
	protected void onPreExecute() {
		// 進捗ダイアログ表示
		// mProgressDialog = new ProgressDialog(mActivity);
		// mProgressDialog.setTitle("電力データを取得しています...");
		// mProgressDialog.setIndeterminate(false);
		// mProgressDialog.setMax(10);
		// mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		// try {
		// mProgressDialog.show();
		// } catch (Exception e) {
		// }
	}

	/*
	 * バックグラウンドで電力データを取得します。<br>
	 *
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected CsvData doInBackground(Void... params) {
		return CsvDataDownloader.downloadData(new CallbackProcess() {
			public void noticeProgress(int value) {
				publishProgress(value);
			}
		});
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		// mProgressDialog.setProgress(values[0]);
		mActivity.getProgressBar().setProgress(values[0]);
	}

	/*
	 * 電力データ取得後、グラフ描画を行います。<br>
	 *
	 * @see android.os.AsyncTask#onPostExecute(Result)
	 */
	@Override
	protected void onPostExecute(CsvData csvData) {
		if (csvData != null) {
			mActivity.drawLineChart(csvData);
		}
		try {
			// mProgressDialog.dismiss();
			mActivity.getProgressBar().setVisibility(View.GONE);
		} catch (Exception e) {
		}
	}

}
