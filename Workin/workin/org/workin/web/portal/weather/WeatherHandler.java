package org.workin.web.portal.weather;

import org.workin.util.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author <a href="mailto:yao.angellin@gmail.com">Angellin Yao</a>
 */
public class WeatherHandler extends DefaultHandler {

	private WeatherInfo weatherInfo;

	private String readPart;/* h-head; l-loc; c-cc; f-dayf */
	private String preTag = null;

	public WeatherInfo getWeatherInfo() {
		return weatherInfo;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		//start weather info
		if ("weather".equals(qName)) {
			weatherInfo = new WeatherInfo();
		}
		//start head reading
		if ("head".equals(qName)) {
			weatherInfo.setHead(new Head());
			readPart = "h";
		}
		//start location reading
		if ("loc".equals(qName)) {
			weatherInfo.setLocation(new Location());
			readPart = "l";
		}
		//start currentCondition reading
		if ("cc".equals(qName)) {
			weatherInfo.setCurrentCondition(new CurrentCondition());
			readPart = "c";
		}
		//start forecast reading
		if ("dayf".equals(qName)) {
			//			weatherInfo.setForecast(new ArrayList());
			readPart = "f";
		}
		if ("l".equals(readPart)) {
			String locId = attributes.getValue("id");
			weatherInfo.getLocation().setLocationId(locId);
		} else if ("c".equals(readPart)) {
			if ("bar".equals(qName)) {
				weatherInfo.getCurrentCondition().setBar(new Barometer());
				readPart = "cb";
			} else if ("wind".equals(qName)) {
				weatherInfo.getCurrentCondition().setWind(new Wind());
				readPart = "cw";
			} else if ("uv".equals(qName)) {
				weatherInfo.getCurrentCondition().setUv(new Uv());
				readPart = "cu";
			} else if ("moon".equals(qName)) {
				weatherInfo.getCurrentCondition().setMoon(new Moon());
				readPart = "cm";
			}
		} else if ("f".equals(readPart)) {
			if ("day".equals(qName)) {
				weatherInfo.getForecasts().add(new Forecast());
				String day = attributes.getValue("d");
				String weekDay = attributes.getValue("t");
				String date = attributes.getValue("dt");
				weatherInfo.getLastForecast().setDate(day);
				weatherInfo.getLastForecast().setWeekDay(weekDay);
				weatherInfo.getLastForecast().setDate(date);
				readPart = "fd";
			}
		} else if ("fd".equals(readPart)) {
			if ("part".equals(qName)) {
				String dayOrNight = attributes.getValue("p");
				readPart = "fd" + dayOrNight;
				if ("d".equals(dayOrNight)) {
					weatherInfo.getLastForecast().setDay(new ForecastPart());
				} else if ("n".equals(dayOrNight)) {
					weatherInfo.getLastForecast().setNight(new ForecastPart());
				}
			}
		} else if ("fdd".equals(readPart)) {
			if ("wind".equals(qName)) {
				readPart = "fddw";
				weatherInfo.getLastForecast().getDay().setWind(new Wind());
			}
		}
		preTag = qName;
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String data = new String(ch, start, length);
		if (StringUtils.hasText(preTag)) {
			if ("h".equals(readPart)) {
				if ("ut".equals(preTag)) {
					weatherInfo.getHead().setUnitTemperature(data);
				} else if ("ud".equals(preTag)) {
					weatherInfo.getHead().setUnitDistance(data);
				} else if ("us".equals(preTag)) {
					weatherInfo.getHead().setUnitSpeed(data);
				} else if ("up".equals(preTag)) {
					weatherInfo.getHead().setUnitPressure(data);
				}
			} else if ("l".equals(readPart)) {
				if ("dnam".equals(preTag)) {
					weatherInfo.getLocation().setPlace(data);
				} else if ("tm".equals(preTag)) {
					weatherInfo.getLocation().setLocalTime(data);
				} else if ("lat".equals(preTag)) {
					weatherInfo.getLocation().setLatitude(data);
				} else if ("lon".equals(preTag)) {
					weatherInfo.getLocation().setLongitude(data);
				} else if ("sunr".equals(preTag)) {
					weatherInfo.getLocation().setSunrise(data);
				} else if ("suns".equals(preTag)) {
					weatherInfo.getLocation().setSunset(data);
				} else if ("zone".equals(preTag)) {
					weatherInfo.getLocation().setZone(data);
				}
			} else if ("c".equals(readPart)) {
				if ("tmp".equals(preTag)) {
					weatherInfo.getCurrentCondition().setTemperature(data);
				} else if ("flik".equals(preTag)) {
					weatherInfo.getCurrentCondition().setFeelsLike(data);
				} else if ("t".equals(preTag)) {
					weatherInfo.getCurrentCondition().setDescription(data);
				} else if ("icon".equals(preTag)) {
					weatherInfo.getCurrentCondition().setIcon(data);
				} else if ("hmid".equals(preTag)) {
					weatherInfo.getCurrentCondition().setHumidity(data);
				} else if ("vis".equals(preTag)) {
					weatherInfo.getCurrentCondition().setVisibility(data);
				} else if ("dewp".equals(preTag)) {
					weatherInfo.getCurrentCondition().setDewPoint(data);
				} else if ("bar".equals(preTag)) {
					weatherInfo.getCurrentCondition().setBar(new Barometer());
					readPart = "cb";
				} else if ("wind".equals(preTag)) {
					weatherInfo.getCurrentCondition().setWind(new Wind());
					readPart = "cw";
				} else if ("uv".equals(preTag)) {
					weatherInfo.getCurrentCondition().setUv(new Uv());
					readPart = "cu";
				} else if ("moon".equals(preTag)) {
					weatherInfo.getCurrentCondition().setMoon(new Moon());
					readPart = "cm";
				}
			} else if ("f".equals(readPart)) {
				if ("day".equals(preTag)) {
					weatherInfo.getForecasts().add(new Forecast());
					readPart = "fd";
				}
			} else if ("cb".equals(readPart)) {
				if ("r".equals(preTag)) {
					weatherInfo.getCurrentCondition().getBar().setReading(data);
				} else if ("d".equals(preTag)) {
					weatherInfo.getCurrentCondition().getBar().setDescription(data);
				}
			} else if ("cw".equals(readPart)) {
				if ("s".equals(preTag)) {
					weatherInfo.getCurrentCondition().getWind().setSpeed(data);
				} else if ("gust".equals(preTag)) {
					weatherInfo.getCurrentCondition().getWind().setGust(data);
				} else if ("d".equals(preTag)) {
					weatherInfo.getCurrentCondition().getWind().setDegrees(data);
				} else if ("t".equals(preTag)) {
					weatherInfo.getCurrentCondition().getWind().setDirection(data);
				}
			} else if ("cu".equals(readPart)) {
				if ("i".equals(preTag)) {
					weatherInfo.getCurrentCondition().getUv().setIcon(data);
				} else if ("t".equals(preTag)) {
					weatherInfo.getCurrentCondition().getUv().setDescription(data);
				}
			} else if ("cm".equals(readPart)) {
				if ("icon".equals(preTag)) {
					weatherInfo.getCurrentCondition().getMoon().setIcon(data);
				} else if ("t".equals(preTag)) {
					weatherInfo.getCurrentCondition().getMoon().setDescription(data);
				}
			} else if ("fd".equals(readPart)) {
				if ("hi".equals(preTag)) {
					weatherInfo.getLastForecast().setHigh(data);
				} else if ("low".equals(preTag)) {
					weatherInfo.getLastForecast().setLow(data);
				} else if ("sunr".equals(preTag)) {
					weatherInfo.getLastForecast().setSunrise(data);
				} else if ("suns".equals(preTag)) {
					weatherInfo.getLastForecast().setSunset(data);
				} else if ("part".equals(preTag)) {
				}
			} else if ("fdd".equals(readPart)) {
				if ("icon".equals(preTag)) {
					weatherInfo.getLastForecast().getDay().setIcon(data);
				} else if ("t".equals(preTag)) {
					weatherInfo.getLastForecast().getDay().setDescription(data);
				} else if ("wind".equals(preTag)) {
					readPart = "fddw";
					weatherInfo.getLastForecast().getDay().setWind(new Wind());
				} else if ("ppcp".equals(preTag)) {
					weatherInfo.getLastForecast().getDay().setChancePercipitation(data);
				} else if ("hmid".equals(preTag)) {
					weatherInfo.getLastForecast().getDay().setHumidity(data);
				}
			} else if ("fdn".equals(readPart)) {
				if ("icon".equals(preTag)) {
					weatherInfo.getLastForecast().getNight().setIcon(data);
				} else if ("t".equals(preTag)) {
					weatherInfo.getLastForecast().getNight().setDescription(data);
				} else if ("wind".equals(preTag)) {
					readPart = "fdnw";
					weatherInfo.getLastForecast().getNight().setWind(new Wind());
				} else if ("ppcp".equals(preTag)) {
					weatherInfo.getLastForecast().getNight().setChancePercipitation(data);
				} else if ("hmid".equals(preTag)) {
					weatherInfo.getLastForecast().getNight().setHumidity(data);
				}
			} else if ("fddw".equals(readPart)) {
				if ("s".equals(preTag)) {
					weatherInfo.getLastForecast().getDay().getWind().setSpeed(data);
				} else if ("gust".equals(preTag)) {
					weatherInfo.getLastForecast().getDay().getWind().setGust(data);
				} else if ("d".equals(preTag)) {
					weatherInfo.getLastForecast().getDay().getWind().setDegrees(data);
				} else if ("t".equals(preTag)) {
					weatherInfo.getLastForecast().getDay().getWind().setDirection(data);
				}
			} else if ("fdnw".equals(readPart)) {
				if ("s".equals(preTag)) {
					weatherInfo.getLastForecast().getNight().getWind().setSpeed(data);
				} else if ("gust".equals(preTag)) {
					weatherInfo.getLastForecast().getNight().getWind().setGust(data);
				} else if ("d".equals(preTag)) {
					weatherInfo.getLastForecast().getNight().getWind().setDegrees(data);
				} else if ("t".equals(preTag)) {
					weatherInfo.getLastForecast().getNight().getWind().setDirection(data);
				}
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		preTag = null;
		//end weather info
		if ("weather".equals(qName)) {
			readPart = "ew";
			return;
		}
		//end head reading
		if ("head".equals(qName)) {
			readPart = "eh";
			return;
		}
		//end location reading
		if ("loc".equals(qName)) {
			readPart = "el";
			return;
		}
		//end currentCondition reading
		if ("cc".equals(qName)) {
			readPart = "ec";
			return;
		}
		//end forecast reading
		if ("dayf".equals(qName)) {
			readPart = "ef";
			return;
		}
		// end current condition bar part
		if (readPart == "cb" && "bar".equals(qName)) {
			readPart = "c";
			return;
		}
		// end current condition bar part
		if (readPart == "cw" && "wind".equals(qName)) {
			readPart = "c";
			return;
		}
		// end current condition bar part
		if (readPart == "cu" && "uv".equals(qName)) {
			readPart = "c";
			return;
		}
		// end current condition bar part
		if (readPart == "cm" && "moon".equals(qName)) {
			readPart = "c";
			return;
		}
		// end forecast day part
		if ("fd".equals(readPart) && "day".equals(qName)) {
			readPart = "f";
			return;
		}
		// end forecast day part of day
		if ("fdd".equals(readPart) && "part".equals(qName)) {
			readPart = "fd";
			return;
		}
		// end forecast day part of night
		if ("fdn".equals(readPart) && "part".equals(qName)) {
			readPart = "fd";
			return;
		}
		// end forecast day day part of wind
		if ("fddw".equals(readPart) && "wind".equals(qName)) {
			readPart = "fdd";
			return;
		}
		// end forecast day night part of wind
		if ("fdnw".equals(readPart) && "wind".equals(qName)) {
			readPart = "fdn";
			return;
		}
	}

}
