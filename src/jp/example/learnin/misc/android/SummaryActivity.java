package jp.example.learnin.misc.android;

import java.text.SimpleDateFormat;
import java.util.Date;
import jp.example.learnin.misc.android.R;

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
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class SummaryActivity extends Activity {

	private static final String TAG = "SummaryActivity";

	private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
	private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
	private GraphicalView mChartView = null;
	private ProgressBar mProgressBar = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_summary);

		mProgressBar = (ProgressBar) findViewById(R.id.progressbar_horizontal);
		mProgressBar.setMax(10);
		mProgressBar.setVisibility(View.VISIBLE);

		GetSummaryTask task = new GetSummaryTask(this);
		task.execute();
	}

	@Override
	public void onResume() {
		super.onResume();
		if (mChartView != null) {
			mChartView.repaint();
		}
	}

	public ProgressBar getProgressBar() {
		return mProgressBar;
	}

	/**
	 * 線グラフを描画します。<br>
	 *
	 * @param csvData
	 */
	public void drawLineChart(CsvData csvData) {

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

		// Y軸の最小値を0に設定
		mRenderer.setYAxisMin(0);

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

		LinearLayout layout = (LinearLayout) findViewById(R.id.chart_area);
		// mChartView = ChartFactory.getTimeChartView(this, mDataset,
		// mRenderer, "yyyy/MM/dd H:m");
		mChartView = ChartFactory.getLineChartView(this, mDataset, mRenderer);
		layout.addView(mChartView, new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
	}
}
