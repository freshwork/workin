package org.workin.ws.soap.service;

import java.io.File;

import javax.jws.WebService;

import org.workin.ws.constant.WSConstants;
import org.workin.ws.result.LargeImageResult;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
@WebService(name = "LargeImageService", targetNamespace = WSConstants.NS)
public interface LargeImageWebService {
	
	public LargeImageResult getImageWithPath(String imagePath);
	
	public LargeImageResult getImage(File imageFile);
	
}
