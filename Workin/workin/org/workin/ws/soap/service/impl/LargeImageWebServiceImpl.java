package org.workin.ws.soap.service.impl;

import java.io.File;
import java.net.URL;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.workin.exception.ThrowableHandle;
import org.workin.util.Assert;
import org.workin.util.FileUtils;
import org.workin.ws.constant.WSConstants;
import org.workin.ws.result.LargeImageResult;
import org.workin.ws.result.WSResult;
import org.workin.ws.soap.service.LargeImageWebService;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
@WebService(serviceName = "LargeImageService", portName = "LargeImageServicePort", endpointInterface = "org.workin.ws.soap.service.LargeImageWebService", targetNamespace = WSConstants.NS)
public class LargeImageWebServiceImpl implements LargeImageWebService {

	@Override
	public LargeImageResult getImageWithPath(String imagePath) {
		Assert.hasText(imagePath,
				"Image Path can not be null! when excute LargeImageWebServiceImpl.getImage(String imagePath).");
		File file = null;
		try {
			URL url = FileUtils.findAsResource(imagePath);
			if (url != null)
				file = new File(FileUtils.findAsResource(imagePath).getFile());
		} catch (Exception e) {
			ThrowableHandle.handle("Hit Exception,when call getImage(String imagePath).", e, logger);
		}
		return getImage(file);
	}

	@Override
	public LargeImageResult getImage(File imageFile) {
		Assert.notNull(imageFile,
				"Image File can not be null! when excute LargeImageWebServiceImpl.getImage(File imageFile).");
		LargeImageResult result = new LargeImageResult();
		try {
			DataSource dataSource = new FileDataSource(imageFile);
			DataHandler dataHandler = new DataHandler(dataSource);
			result.setImageData(dataHandler);
		} catch (Exception e) {
			result.setResult(WSResult.IMAGE_ERROR, "Image reading error.");
			ThrowableHandle.handle("Hit Exception,when call getImage(File imageFile).", e, logger);
		}
		return result;
	}

	private static final transient Logger logger = LoggerFactory.getLogger(LargeImageWebServiceImpl.class);
}
