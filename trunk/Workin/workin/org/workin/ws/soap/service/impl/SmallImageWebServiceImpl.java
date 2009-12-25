package org.workin.ws.soap.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.jws.WebService;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.workin.exception.ThrowableHandle;
import org.workin.util.Assert;
import org.workin.util.FileUtils;

import org.workin.ws.constant.WSConstants;
import org.workin.ws.result.SmallImageResult;
import org.workin.ws.result.WSResult;
import org.workin.ws.soap.service.SmallImageWebService;
/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
@WebService(serviceName = "SmallImageService", portName = "SmallImageServicePort", endpointInterface = "org.workin.ws.soap.service.SmallImageWebService", targetNamespace = WSConstants.NS)
public class SmallImageWebServiceImpl implements SmallImageWebService {

	@Override
	public SmallImageResult getImageWithPath(String imagePath) {
		Assert.hasText(imagePath,
				"Image Path can not be null! when excute SmallImageWebServiceImpl.getImage(String imagePath).");
		File file = null;
		try {
			URL url = FileUtils.findAsResource(imagePath);
			if(url != null)
				file = new File(FileUtils.findAsResource(imagePath).getFile());
		} catch (Exception e) {
			ThrowableHandle.handle("Hit Exception,when call getImage(String imagePath).", e, logger);
		}
		return getImage(file);
	}

	@Override
	public SmallImageResult getImage(File imageFile) {
		Assert.notNull(imageFile,
				"Image File can not be null! when excute SmallImageWebServiceImpl.getImage(File imageFile).");
		SmallImageResult result = new SmallImageResult();
		InputStream is = null;

		try {
			is = new FileInputStream(imageFile);
			byte[] imageBytes = IOUtils.toByteArray(is);
			result.setImageData(imageBytes);
		} catch (IOException e) {
			result.setResult(WSResult.IMAGE_ERROR, "Image reading error.");
			ThrowableHandle.handle("Hit Exception,when call getImage(File imageFile).", e, logger);
		} finally {
			IOUtils.closeQuietly(is);
		}
		return result;
	}

	private static final transient Logger logger = LoggerFactory.getLogger(SmallImageWebServiceImpl.class);
}
