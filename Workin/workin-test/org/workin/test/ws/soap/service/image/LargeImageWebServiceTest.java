package org.workin.test.ws.soap.service.image;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataHandler;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.workin.fortest.BaseTestCase;
import org.workin.test.ws.soap.service.image.large.LargeImageResult;
import org.workin.test.ws.soap.service.image.large.LargeImageService;
import org.workin.ws.soap.client.SoapClient;
import org.workin.ws.soap.client.SoapClientImpl;
import org.workin.ws.soap.engine.SoapEngine;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class LargeImageWebServiceTest extends BaseTestCase {

	SoapClient<LargeImageService> client = null;
	LargeImageService largeImageService = null;

	public static void main(String[] args) throws Exception {
		new LargeImageWebServiceTest().startEngine();
		Runtime.getRuntime().exec("cmd /c start scripts\\ws\\wsdl2java-image-large.bat");
	}

	@Before
	public void startEngine() {
		SoapEngine.getInstance().start(org.workin.ws.soap.service.impl.LargeImageWebServiceImpl.class, URL_SOAP_SERVER);
		assertEquals(true, SoapEngine.getInstance().isServerStarted());
		client = new SoapClientImpl<LargeImageService>(LargeImageService.class);
		largeImageService = client.buildSoapServiceWithCxf("LargeImageService", URL_SOAP_SERVER_WSDL);
	}

	@Test
	public void getLargeImage() throws IOException {
		LargeImageResult result = largeImageService.getImageWithPath(FILE_PATH_CLIENT);
		DataHandler dataHandler = result.getImageData();
		String tempFilePath = System.getProperty("java.io.tmpdir") + "largePic.jpg";
		InputStream is = dataHandler.getInputStream();
		OutputStream os = new FileOutputStream(tempFilePath);

		IOUtils.copy(is, os);
		IOUtils.closeQuietly(is);
		IOUtils.closeQuietly(os);

		logger.info("largePic.jpg saved to: " + tempFilePath);

		File tempFile = new File(tempFilePath);
		assertTrue(tempFile.length() > 0);
	}

	private static final String URL_SOAP_SERVER = "http://localhost:8080/workin/soap/image/largeImageService";
	private static final String URL_SOAP_SERVER_WSDL = "http://localhost:8080/workin/soap/image/largeImageService?wsdl";

	private static final String FILE_PATH_CLIENT = "cxf.jpeg";

	private static final transient Logger logger = LoggerFactory.getLogger(LargeImageWebServiceTest.class);
}
