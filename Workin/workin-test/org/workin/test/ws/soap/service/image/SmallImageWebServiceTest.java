package org.workin.test.ws.soap.service.image;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.workin.fortest.BaseTestCase;
import org.workin.test.ws.soap.service.image.small.SmallImageResult;
import org.workin.test.ws.soap.service.image.small.SmallImageService;
import org.workin.ws.soap.client.SoapClient;
import org.workin.ws.soap.client.SoapClientImpl;
import org.workin.ws.soap.engine.SoapEngine;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class SmallImageWebServiceTest extends BaseTestCase {

	SoapClient<SmallImageService> client = null;
	SmallImageService imageService = null;

	// start service emgine
	public static void main(String[] args) {
		new SmallImageWebServiceTest().startEngine();
	}

	@Before
	public void startEngine() {
		SoapEngine.getInstance().start(org.workin.ws.soap.service.impl.SmallImageWebServiceImpl.class, URL_SOAP_SERVER);
		assertEquals(true, SoapEngine.getInstance().isServerStarted());
		client = new SoapClientImpl<SmallImageService>(SmallImageService.class);
		imageService = client.buildSoapServiceWithCxf("SmallImageService", URL_SOAP_SERVER_WSDL);
	}

	@Test
	public void getSmallImage() throws IOException {
		SmallImageResult result = imageService.getImageWithPath(FILE_PATH_CLIENT);
		assertTrue(result.getImageData().length > 0);

		String tempFilePath = System.getProperty("java.io.tmpdir") + "smallPic.jpg";
		OutputStream os = new FileOutputStream(tempFilePath);
		IOUtils.write(result.getImageData(), os);
		IOUtils.closeQuietly(os);
		logger.info("smallPic.jpg saved to: " + tempFilePath);
	}

	private static final String URL_SOAP_SERVER = "http://localhost:8080/workin/soap/image/smallImageService";

	private static final String URL_SOAP_SERVER_WSDL = "http://localhost:8080/workin/soap/image/smallImageService?wsdl";

	private static final String FILE_PATH_CLIENT = "cxf.jpeg";

	private static final transient Logger logger = LoggerFactory.getLogger(SmallImageWebServiceTest.class);
}
