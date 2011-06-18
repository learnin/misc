package jp.example.learnin.misc.android;

import java.util.Date;
import jp.example.learnin.misc.android.R;

public class ResultPower implements Comparable<ResultPower> {

	/* 日時 */
	private Date dateTime;

	/* 電力 */
	private int power;

	/**
	 * dateTimeを取得します。
	 *
	 * @return dateTime
	 */
	public Date getDateTime() {
		return dateTime;
	}

	/**
	 * dateTimeを設定します。
	 *
	 * @param dateTime dateTime
	 */
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	/**
	 * powerを取得します。
	 *
	 * @return power
	 */
	public int getPower() {
		return power;
	}

	/**
	 * powerを設定します。
	 *
	 * @param power power
	 */
	public void setPower(int power) {
		this.power = power;
	}

	@Override
	public int compareTo(ResultPower another) {
		return this.dateTime.compareTo(another.getDateTime());
	}
}
