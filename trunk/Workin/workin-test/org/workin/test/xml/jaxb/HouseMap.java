package org.workin.test.xml.jaxb;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.XmlAdapter;


/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
@XmlType(name = "houses")
public class HouseMap {
	@XmlElement(name = "house")
	List<HouseEntry> entries = new ArrayList<HouseEntry>();
	
	static class HouseEntry {
		@XmlAttribute
		public String key;

		@XmlValue
		public String value;

		public HouseEntry() {
		}

		public HouseEntry(Map.Entry<String, String> e) {
			key = e.getKey();
			value = e.getValue();
		}
	}
	
	public static class HouseMapAdapter extends XmlAdapter<HouseMap, Map<String, String>> {

		@Override
		public HouseMap marshal(Map<String, String> map) throws Exception {
			HouseMap houseMap = new HouseMap();
			for (Map.Entry<String, String> e : map.entrySet()) {
				houseMap.entries.add(new HouseEntry(e));
			}
			return houseMap;
		}

		@Override
		public Map<String, String> unmarshal(HouseMap houseMap) throws Exception {
			Map<String, String> map = new LinkedHashMap<String, String>();
			for (HouseEntry e : houseMap.entries) {
				map.put(e.key, e.value);
			}
			return map;
		}
	}
}
