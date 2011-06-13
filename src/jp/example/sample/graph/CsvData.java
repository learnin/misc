package jp.example.sample.graph;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CsvData {

	private Date lastUpdate;

	/* ピーク時供給力(万kW) */
	private int supplyPowerOnPeek;
	/* 時台 */
	private String hour;
	/* 供給力情報更新日時 */
	private Date supplyPowerInfoUpdateDate;

	/* 予想最大電力(万kW) */
	private int expectedMaxPower;
	/* 時間帯 */
	private String expectedMaxPowerTimeLine;
	/* 予想最大電力情報更新日時 */
	private Date expectedMaxPowerInfoUpdateDate;

	/* 実績電力 */
	private List<ResultPower> resultPowerList = new ArrayList<ResultPower>();

	/**
	 * updateを取得します。
	 *
	 * @return lastUpdate
	 */
	public Date getLastUpdate() {
		return lastUpdate;
	}

	/**
	 * updateを設定します。
	 *
	 * @param update lastUpdate
	 */
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	/**
	 * supplyPowerOnPeekを取得します。
	 *
	 * @return supplyPowerOnPeek
	 */
	public int getSupplyPowerOnPeek() {
		return supplyPowerOnPeek;
	}

	/**
	 * supplyPowerOnPeekを設定します。
	 *
	 * @param supplyPowerOnPeek supplyPowerOnPeek
	 */
	public void setSupplyPowerOnPeek(int supplyPowerOnPeek) {
		this.supplyPowerOnPeek = supplyPowerOnPeek;
	}

	/**
	 * hourを取得します。
	 *
	 * @return hour
	 */
	public String getHour() {
		return hour;
	}

	/**
	 * hourを設定します。
	 *
	 * @param hour hour
	 */
	public void setHour(String hour) {
		this.hour = hour;
	}

	/**
	 * supplyPowerInfoUpdateDateを取得します。
	 *
	 * @return supplyPowerInfoUpdateDate
	 */
	public Date getSupplyPowerInfoUpdateDate() {
		return supplyPowerInfoUpdateDate;
	}

	/**
	 * supplyPowerInfoUpdateDateを設定します。
	 *
	 * @param supplyPowerInfoUpdateDate supplyPowerInfoUpdateDate
	 */
	public void setSupplyPowerInfoUpdateDate(Date supplyPowerInfoUpdateDate) {
		this.supplyPowerInfoUpdateDate = supplyPowerInfoUpdateDate;
	}

	/**
	 * expectedMaxPowerを取得します。
	 *
	 * @return expectedMaxPower
	 */
	public int getExpectedMaxPower() {
		return expectedMaxPower;
	}

	/**
	 * expectedMaxPowerを設定します。
	 *
	 * @param expectedMaxPower expectedMaxPower
	 */
	public void setExpectedMaxPower(int expectedMaxPower) {
		this.expectedMaxPower = expectedMaxPower;
	}

	/**
	 * expectedMaxPowerTimeLineを取得します。
	 *
	 * @return expectedMaxPowerTimeLine
	 */
	public String getExpectedMaxPowerTimeLine() {
		return expectedMaxPowerTimeLine;
	}

	/**
	 * expectedMaxPowerTimeLineを設定します。
	 *
	 * @param expectedMaxPowerTimeLine expectedMaxPowerTimeLine
	 */
	public void setExpectedMaxPowerTimeLine(String expectedMaxPowerTimeLine) {
		this.expectedMaxPowerTimeLine = expectedMaxPowerTimeLine;
	}

	/**
	 * expectedMaxPowerInfoUpdateDateを取得します。
	 *
	 * @return expectedMaxPowerInfoUpdateDate
	 */
	public Date getExpectedMaxPowerInfoUpdateDate() {
		return expectedMaxPowerInfoUpdateDate;
	}

	/**
	 * expectedMaxPowerInfoUpdateDateを設定します。
	 *
	 * @param expectedMaxPowerInfoUpdateDate expectedMaxPowerInfoUpdateDate
	 */
	public void setExpectedMaxPowerInfoUpdateDate(
			Date expectedMaxPowerInfoUpdateDate) {
		this.expectedMaxPowerInfoUpdateDate = expectedMaxPowerInfoUpdateDate;
	}

	/**
	 * resultPowerListを取得します。
	 *
	 * @return resultPowerList
	 */
	public List<ResultPower> getResultPowerList() {
		return resultPowerList;
	}

	/**
	 * resultPowerListを設定します。
	 *
	 * @param resultPowerList resultPowerList
	 */
	public void setResultPowerList(List<ResultPower> resultPowerList) {
		this.resultPowerList = resultPowerList;
	}

}
