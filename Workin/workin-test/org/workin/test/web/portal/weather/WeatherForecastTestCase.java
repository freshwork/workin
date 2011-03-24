package org.workin.test.web.portal.weather;

import java.util.List;

import org.workin.web.portal.weather.Location;
import org.workin.web.portal.weather.WeatherForecast;
import org.workin.web.portal.weather.WeatherInfo;

/**
 * @author <a href="mailto:yao.angellin@gmail.com">Angellin Yao</a>
 *
 */
public class WeatherForecastTestCase {
	public static void main(String[] args) {
		WeatherInfo weatherInfo = WeatherForecast.parse("CHXX0016", 5);
		List<Location> locations = WeatherForecast.getLocation("new+York");
		System.out.println(locations);
		System.out.println(weatherInfo);
	}
}
