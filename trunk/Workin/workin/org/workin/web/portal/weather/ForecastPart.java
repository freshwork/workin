package org.workin.web.portal.weather;

/**
 * @author <a href="mailto:yao.angellin@gmail.com">Angellin Yao</a>
 */
public class ForecastPart {
	private String icon;//icon
	private String description;//t
	private String chancePercipitation;//ppcp
	private String humidity;//hmid
	private Wind wind = new Wind();

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getChancePercipitation() {
		return chancePercipitation;
	}

	public void setChancePercipitation(String chancePercipitation) {
		this.chancePercipitation = chancePercipitation;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public Wind getWind() {
		return wind;
	}

	public void setWind(Wind wind) {
		this.wind = wind;
	}

}