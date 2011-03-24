package org.workin.web.portal.weather;

import java.util.ArrayList;
import java.util.List;

import org.workin.util.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author <a href="mailto:yao.angellin@gmail.com">Angellin Yao</a>
 */
public class LocationHandler extends DefaultHandler {
	private String readPart;/* s-search; l-loc;*/
	private String preTag = null;

	private List<Location> locations;

	public List<Location> getLocations() {
		return locations;
	}

	public Location getLastLocation() {
		return locations.get(locations.size() - 1);
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		//start search info
		if ("search".equals(qName)) {
			locations = new ArrayList<Location>();
			readPart = "s";
		}
		//start loc reading
		if ("loc".equals(qName)) {
			locations.add(new Location());
			String locId = attributes.getValue("id");
			//			String type = attributes.getValue("type");
			this.getLastLocation().setLocationId(locId);
			readPart = "l";
		}
		preTag = qName;
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		preTag = null;
		//end search info
		if ("search".equals(qName)) {
			readPart = null;
			return;
		}
		//end loc reading
		if ("loc".equals(qName)) {
			readPart = null;
			return;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String data = new String(ch, start, length);
		if (StringUtils.hasText(preTag)) {
			if ("l".equals(readPart)) {
				if ("loc".equals(preTag)) {
					this.getLastLocation().setPlace(data);
				}
			}

		}
	}
}
