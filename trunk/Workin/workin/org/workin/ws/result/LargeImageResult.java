package org.workin.ws.result;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.bind.annotation.XmlType;

import org.workin.ws.constant.WSConstants;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
@XmlType(name = "LargeImageResult", namespace = WSConstants.NS)
public class LargeImageResult extends WSResult {

	private static final long serialVersionUID = -1253622232457191420L;

	private DataHandler imageData;

	@XmlMimeType("application/octet-stream")
	public DataHandler getImageData() {
		return imageData;
	}

	public void setImageData(DataHandler imageData) {
		this.imageData = imageData;
	}
}
