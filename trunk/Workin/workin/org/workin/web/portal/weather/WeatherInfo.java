package org.workin.web.portal.weather;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:yao.angellin@gmail.com">Angellin Yao</a>
 */
public class WeatherInfo implements Serializable {

	private static final long serialVersionUID = -9052802853922922940L;

	private Head head;
	private Location location;
	private CurrentCondition currentCondition;
	private List<Forecast> forecasts = Lists.newArrayList();

	public WeatherInfo() {

	}

	public Head getHead() {
		return head;
	}

	public void setHead(Head head) {
		this.head = head;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public CurrentCondition getCurrentCondition() {
		return currentCondition;
	}

	public void setCurrentCondition(CurrentCondition currentCondition) {
		this.currentCondition = currentCondition;
	}

	public List<Forecast> getForecasts() {
		return forecasts;
	}

	public void setForecasts(List<Forecast> forecasts) {
		this.forecasts = forecasts;
	}

	/**
	 * @return last forecast condition
	 */
	public Forecast getLastForecast() {
		return forecasts.get(forecasts.size() - 1);
	}
}
