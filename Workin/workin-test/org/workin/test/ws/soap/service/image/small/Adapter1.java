package org.workin.test.ws.soap.service.image.small;

import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class Adapter1 extends XmlAdapter<String, Date> {

	@Override
	public Date unmarshal(String value) {
		return (org.apache.cxf.tools.common.DataTypeAdapter.parseDateTime(value));
	}

	@Override
	public String marshal(Date value) {
		return (org.apache.cxf.tools.common.DataTypeAdapter.printDateTime(value));
	}

}
