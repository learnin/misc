package jp.example.sample.graph;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

//参考 http://pastebin.com/q1Vcbe9C
// TODO 複数画面にして、最初の画面ではヘッダのサマリ情報を表示し、グラフは別アクティビティにする。
// TODO 複数画面等にして、過去分にも対応する。過去の電力ソースは http://tepco-usage-api.appspot.com/
// TODO WebViewでJavaScriptでも実装してみる。JavaScriptのグラフライブラリを使って。
// TODO 縦横回転時の表示切り替え
public class TokyoDenryokuGraphSampleActivity extends Activity {

	private static final String TAG = "TokyoDenryokuGraphSampleActivity";

	GraphicalView mChartView = null;
	XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
	XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// FIXME 非同期にする
		CsvData csvData = downloadData();
		if (csvData != null) {
			drawLineChart(csvData);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (mChartView == null) {
			LinearLayout layout = (LinearLayout) findViewById(R.id.chart_area);
			// mChartView = ChartFactory.getTimeChartView(this, mDataset,
			// mRenderer, "yyyy/MM/dd H:m");
			mChartView = ChartFactory.getLineChartView(this, mDataset,
					mRenderer);
			layout.addView(mChartView, new LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		} else {
			mChartView.repaint();
		}
	}

	private void drawLineChart(CsvData csvData) {

		// FIXME
		// 0の値がない場合に、Y軸が例えばmRenderer.setYLabels(10)で2400からなので2200ぐらいで一見、0に見えるような目盛りになるのを修正
		mRenderer.setAxisTitleTextSize(16);
		mRenderer.setChartTitleTextSize(20);
		mRenderer.setLabelsTextSize(15);
		mRenderer.setLegendTextSize(15);
		mRenderer.setPointSize(5f);
		mRenderer.setMargins(new int[] { 20, 30, 15, 0 });
		mRenderer.setXLabels(0);
		mRenderer.setYLabels(10);
		mRenderer.setShowGrid(true);
		mRenderer.setXLabelsAlign(Align.CENTER);
		mRenderer.setYLabelsAlign(Align.RIGHT);

		mRenderer.setXLabelsAngle(90);
		mRenderer.setXLabelsAlign(Align.LEFT);

		// グラフ上に値を表示するか
		mRenderer.setDisplayChartValues(true);

		// ズームボタン表示制御
		mRenderer.setZoomEnabled(false, false);

		// TimeSeries mCurrentSeries;
		XYSeries mCurrentSeries;
		String seriesTitle = "Series " + (mDataset.getSeriesCount() + 1);

		// TimeSeries series = new TimeSeries(seriesTitle);
		XYSeries series = new XYSeries(seriesTitle);
		mDataset.addSeries(series);
		mCurrentSeries = series;

		XYSeriesRenderer renderer = new XYSeriesRenderer();
		int[] colors = new int[] { Color.RED };
		PointStyle[] styles = new PointStyle[] { PointStyle.POINT };
		renderer.setColor(colors[0]);
		renderer.setPointStyle(styles[0]);
		renderer.setFillPoints(false);
		mRenderer.addSeriesRenderer(renderer);

		int i = 0;
		SimpleDateFormat format;
		Date prevDate = null;
		for (ResultPower resultPower : csvData.getResultPowerList()) {
			// mCurrentSeries.add(resultPower.getDateTime(),
			// resultPower.getPower());
			mCurrentSeries.add(resultPower.getDateTime().getTime(),
					resultPower.getPower());

			// X軸のラベル表示カスタマイズ
			if (i % 2 == 0) {
				if (i == 0) {
					format = new SimpleDateFormat("yyyy/MM/dd H:m");
				} else if (resultPower.getDateTime().getYear() != prevDate
						.getYear()) {
					format = new SimpleDateFormat("yyyy/MM/dd H:m");
				} else if (resultPower.getDateTime().getMonth() != prevDate
						.getMonth()) {
					format = new SimpleDateFormat("yyyy/MM/dd H:m");
				} else if (resultPower.getDateTime().getDate() != prevDate
						.getDate()) {
					format = new SimpleDateFormat("yyyy/MM/dd H:m");
				} else {
					format = new SimpleDateFormat("H:m");
				}
				mRenderer.addTextLabel(resultPower.getDateTime().getTime(),
						format.format(resultPower.getDateTime()));
			}
			prevDate = resultPower.getDateTime();
			i++;
		}
	}

	private CsvData downloadData() {
		BufferedReader reader = null;
		HttpURLConnection conn = null;
		try {
			URL url = new URL(
					"http://www.tepco.co.jp/forecast/html/images/juyo-j.csv");
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Host", "www.tepco.co.jp");
			conn.setDoInput(true);
			conn.connect();
			reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "Shift-JIS"));
			String line = null;
			CsvData csvData = new CsvData();
			int i = 1;
			while ((line = reader.readLine()) != null) {
				if (i == 1) {
					csvData.setLastUpdate(new Date(line.replace(" UPDATE", "")));
				} else if (i == 3) {
					// String[] lineData = line.split(",");
					// csvData.setSupplyPowerOnPeek(Integer.valueOf(lineData[0]));
					// csvData.setHour(lineData[1]);
					// csvData.setSupplyPowerInfoUpdateDate(new Date(lineData[2]
					// + " " + lineData[3]));
				} else if (i == 6) {

				} else if (i >= 9) {
					String[] lineData = line.split(",");
					ResultPower resultPower = new ResultPower();
					SimpleDateFormat format = new SimpleDateFormat(
							"yyyy/MM/dd H:m");
					Date today = format.parse(lineData[0] + " " + lineData[1]);
					resultPower.setDateTime(today);
					resultPower.setPower(Integer.valueOf(lineData[2]));
					csvData.getResultPowerList().add(resultPower);

					Calendar yestarday = Calendar.getInstance();
					String year = lineData[0].split("/")[0];
					String month = lineData[0].split("/")[1];
					String day = lineData[0].split("/")[2];
					String hour = lineData[1].split(":")[0];
					String minute = lineData[1].split(":")[1];
					yestarday.set(Integer.valueOf(year),
							Integer.valueOf(month) - 1, Integer.valueOf(day),
							Integer.valueOf(hour), Integer.valueOf(minute));
					yestarday.add(Calendar.DATE, -1);

					ResultPower resultPowerYestarDay = new ResultPower();
					resultPowerYestarDay.setDateTime(yestarday.getTime());
					resultPowerYestarDay.setPower(Integer.valueOf(lineData[3]));
					csvData.getResultPowerList().add(resultPowerYestarDay);
				}
				i++;
				Log.d(TAG, line);
			}
			Collections.sort(csvData.getResultPowerList());
			return csvData;
		} catch (Exception e) {
			Log.e(TAG, e.toString());
			return null;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e) {
				}
				if (conn != null) {
					try {
						conn.disconnect();
					} catch (Exception e) {
					}
				}
			}
		}
	}
}
