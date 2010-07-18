package org.workin.ws.result;

import javax.xml.bind.annotation.XmlType;
import org.workin.ws.constant.WSConstants;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
@XmlType(name = "SmallImageResult", namespace = WSConstants.NS)
public class SmallImageResult extends WSResult {
	
	private static final long serialVersionUID = 8694843038503955090L;
	
	private byte[] imageData;

	public byte[] getImageData() {
		return imageData;
	}

	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}
}
