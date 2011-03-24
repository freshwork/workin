package org.workin.web.portal.weather;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.springframework.util.StringUtils;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 * Fetch weather information from xopa.weather.com, which can be used for portal page on your web sites.
 * 
 * @author <a href="mailto:yao.angellin@gmail.com">Angellin Yao</a>
 */
public class WeatherForecast {
	public final static String WEATHER_URL = "http://xoap.weather.com/weather/local/%s?cc=*&dayf=%s&par=1244749937&key=f454b5314341acbb&ut=%s";
	public final static String LOCATION_URL = "http://xoap.weather.com/search/search?where=%s";

	public enum UnitTemperature {
		C {
			@Override
			public String getValue() {
				return "C";
			}
		},
		F {
			@Override
			public String getValue() {
				return "F";
			}
		};
		public abstract String getValue();
	}

	public WeatherInfo parse(String place) {
		return parse(place, 3, UnitTemperature.C.getValue());
	}

	public WeatherInfo parse(String place, String unitTemperature) {
		return parse(place, 3, unitTemperature);
	}

	public static WeatherInfo parse(String place, int forecastDays) {
		return parse(place, forecastDays, UnitTemperature.C.getValue());
	}

	public static WeatherInfo parse(String place, int forecastDays, String unitTemperature) {
		String currentPlace = place.replace(" ", "%20");
		int fds = forecastDays > 0 ? forecastDays : 3;
		String ut = StringUtils.hasText(unitTemperature) ? unitTemperature : UnitTemperature.C.getValue();

		StringBuilder weatherURL = new StringBuilder();
		weatherURL.append(String.format(WEATHER_URL, currentPlace, fds, ut));
		InputStream in = null;
		try {
			URL url = new URL(weatherURL.toString());
			in = url.openStream();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return parse(in);
	}

	public static WeatherInfo parse(InputStream in) {
		if (in == null) {
			return null;
		}
		SAXParserFactory spf = SAXParserFactory.newInstance();

		try {
			SAXParser parser = spf.newSAXParser();

			XMLReader reader = parser.getXMLReader();
			WeatherHandler handler = new WeatherHandler();
			reader.setContentHandler(handler);
			reader.parse(new InputSource(in));
			return handler.getWeatherInfo();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<Location> getLocation(String place) {
		String currentPlace = place.replace(" ", "%20");
		StringBuilder locationURL = new StringBuilder();
		locationURL.append(String.format(LOCATION_URL, currentPlace));
		InputStream in = null;
		try {
			URL url = new URL(locationURL.toString());
			in = url.openStream();
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser parser = spf.newSAXParser();
			XMLReader reader = parser.getXMLReader();
			LocationHandler handler = new LocationHandler();
			reader.setContentHandler(handler);
			reader.parse(new InputSource(in));
			return handler.getLocations();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		return null;
	}

}
