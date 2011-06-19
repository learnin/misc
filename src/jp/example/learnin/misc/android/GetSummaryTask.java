package jp.example.learnin.misc.android;

import android.os.AsyncTask;

public class GetSummaryTask extends AsyncTask<Void, Integer, CsvData> {

	private static final String TAG = "GetSummaryTask";
	private TokyoDenryokuGraphSampleActivity mActivity = null;

	public GetSummaryTask(TokyoDenryokuGraphSampleActivity activity) {
		mActivity = activity;
	}

	/*
	 * バックグラウンドで電力データを取得します。<br>
	 *
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected CsvData doInBackground(Void... params) {
		return CsvDataDownloader.downloadData();
	}

	/*
	 * 電力データ取得後、表示を行います。<br>
	 *
	 * @see android.os.AsyncTask#onPostExecute(Result)
	 */
	@Override
	protected void onPostExecute(CsvData csvData) {
		if (csvData != null) {
			try {
				mActivity.showSummaryData(csvData);
			} catch (Exception e) {
			}
		}
	}

}
