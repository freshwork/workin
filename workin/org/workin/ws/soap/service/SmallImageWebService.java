package org.workin.ws.soap.service;

import java.io.File;

import javax.jws.WebService;

import org.workin.ws.result.SmallImageResult;
import org.workin.ws.constant.WSConstants;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
@WebService(name = "SmallImageService", targetNamespace = WSConstants.NS)
public interface SmallImageWebService {

	public SmallImageResult getImageWithPath(String imagePath);
	
	public SmallImageResult getImage(File imageFile);
	
}
