package org.workin.web.portal.weather;

/**
 * @author <a href="mailto:yao.angellin@gmail.com">Angellin Yao</a>
 */
public class CurrentCondition {
	private String temperature;//tmp
	private String feelsLike;//flik
	private String description;//t
	private String icon;//icon
	private String humidity;//hmid
	private String visibility;//vis
	private String dewPoint;//dewp
	private Barometer bar = new Barometer();//bar
	private Wind wind = new Wind();//wind
	private Uv uv = new Uv();//uv
	private Moon moon = new Moon();//moon

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getFeelsLike() {
		return feelsLike;
	}

	public void setFeelsLike(String feelsLike) {
		this.feelsLike = feelsLike;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	public String getDewPoint() {
		return dewPoint;
	}

	public void setDewPoint(String dewPoint) {
		this.dewPoint = dewPoint;
	}

	public Barometer getBar() {
		return bar;
	}

	public void setBar(Barometer bar) {
		this.bar = bar;
	}

	public Wind getWind() {
		return wind;
	}

	public void setWind(Wind wind) {
		this.wind = wind;
	}

	public Uv getUv() {
		return uv;
	}

	public void setUv(Uv uv) {
		this.uv = uv;
	}

	public Moon getMoon() {
		return moon;
	}

	public void setMoon(Moon moon) {
		this.moon = moon;
	}

}