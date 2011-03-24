package org.workin.web.portal.weather;

/**
 * @author <a href="mailto:yao.angellin@gmail.com">Angellin Yao</a>
 */
public class Forecast {
	private String weekDay;//t
	private String date;//dt
	private String high;//hi
	private String low;//low
	private String sunrise;//sunr
	private String sunset;//suns
	private ForecastPart day = new ForecastPart();//part[p=d]
	private ForecastPart night = new ForecastPart();//part[p=n]

	public String getWeekDay() {
		return weekDay;
	}

	public void setWeekDay(String weekDay) {
		this.weekDay = weekDay;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getHigh() {
		return high;
	}

	public void setHigh(String high) {
		this.high = high;
	}

	public String getLow() {
		return low;
	}

	public void setLow(String low) {
		this.low = low;
	}

	public String getSunrise() {
		return sunrise;
	}

	public void setSunrise(String sunrise) {
		this.sunrise = sunrise;
	}

	public String getSunset() {
		return sunset;
	}

	public void setSunset(String sunset) {
		this.sunset = sunset;
	}

	public ForecastPart getDay() {
		return day;
	}

	public void setDay(ForecastPart day) {
		this.day = day;
	}

	public ForecastPart getNight() {
		return night;
	}

	public void setNight(ForecastPart night) {
		this.night = night;
	}

}