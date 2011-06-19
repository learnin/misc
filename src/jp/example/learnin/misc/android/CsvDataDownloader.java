package jp.example.learnin.misc.android;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import android.util.Log;

public class CsvDataDownloader {

	private static final String TAG = "CsvDataDownloader";

	public static CsvData downloadData(CallbackProcess callback) {
		BufferedReader reader = null;
		HttpURLConnection conn = null;
		publishProgress(callback, 0);
		try {
			URL url = new URL(
					"http://www.tepco.co.jp/forecast/html/images/juyo-j.csv");
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Host", "www.tepco.co.jp");
			conn.setDoInput(true);
			conn.connect();
			publishProgress(callback, 2);
			reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "Shift-JIS"));
			publishProgress(callback, 6);
			String line = null;
			CsvData csvData = new CsvData();
			int i = 1;
			while ((line = reader.readLine()) != null) {
				if (i == 1) {
					csvData.setLastUpdate(new Date(line.replace(" UPDATE", "")));
				} else if (i == 3) {
					String[] lineData = line.split(",");
					csvData.setSupplyPowerOnPeek(Integer.valueOf(lineData[0]));
					csvData.setHour(lineData[1]);
					SimpleDateFormat format = new SimpleDateFormat("MM/dd H:m");
					csvData.setSupplyPowerInfoUpdateDate(format
							.parse(lineData[2] + " " + lineData[3]));
				} else if (i == 6) {
					String[] lineData = line.split(",");
					csvData.setExpectedMaxPower(Integer.valueOf(lineData[0]));
					csvData.setExpectedMaxPowerTimeLine(lineData[1]);
					SimpleDateFormat format = new SimpleDateFormat("MM/dd H:m");
					csvData.setExpectedMaxPowerInfoUpdateDate(format
							.parse(lineData[2] + " " + lineData[3]));
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
			publishProgress(callback, 10);
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

	private static void publishProgress(CallbackProcess callback, int value) {
		if (callback != null) {
			callback.noticeProgress(value);
		}
	}

}
