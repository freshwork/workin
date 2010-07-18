package org.workin.web.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.workin.util.WebUtils;
import org.workin.web.constant.WebConstants;

/**
 * 
 * Follow this sample description to getting started.
 * 
 * eg.
 * 
 * Web.xml: 
 * 		<servlet>
 *			<servlet-name>cachedStaticContentDownloadServlet</servlet-name>
 *			<servlet-class>org.workin.web.servlet.CachedStaticContentDownloadServlet</servlet-class>
 *		</servlet>
 *		
 *		<servlet-mapping>
 *			<servlet-name>cachedStaticContentDownloadServlet</servlet-name>
 *			<url-pattern>/cache-static-content</url-pattern>
 *		</servlet-mapping>
 *	
 * Client call:
 *		${ctx}/cache-static-content?staticContentPath=js/?.js
 *	
 * EhcacheManager config:
 *		<bean id="ehcacheManager" class="org.springframework.cache.ehcache.EhCacheManagerFactoryBean">
 *			<property name="configLocation" value="classpath:/cache/ehcache.xml" />
 *		</bean>
 *			
 *		<defaultCache maxElementsInMemory="10000" memoryStoreEvictionPolicy="LRU" eternal="false"
 *			timeToIdleSeconds="300" timeToLiveSeconds="300" overflowToDisk="false" diskPersistent="false" />
 *
 *		<cache name="staticContentInfoCache" overflowToDisk="false" eternal="false" diskPersistent="false" 
 *			timeToLiveSeconds="3600" timeToIdleSeconds="3600" maxElementsInMemory="10000" memoryStoreEvictionPolicy="LRU"/>
 *	
 * Tomcat conf:
 * 		    <Connector port="8080" protocol="HTTP/1.1" redirectPort="8443" 
 *              executor="tomcatThreadPool"
 *              acceptCount="200"
 *              connectionTimeout="20000"
 *              keepAliveTimeout="5000"
 *              useBodyEncodingForURI="true"
 *              enableLookups="false"
 *              compression="on" 
 *              compressionMinSize="2048" 
 *              compressableMimeType="text/html,application/xhtml+xml,text/xml,text/css,text/javascript"/>
 *              
 * @see org.springside.examples.showcase.web.StaticContentServlet
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 * 
 */
public class CachedStaticContentDownloadServlet extends HttpServlet {

	private static final long serialVersionUID = -946357245332813617L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String staticContentPath = request.getParameter(PARAM_STATIC_CONTENT_PATH);
		StaticContentInfo staticContentInfo = loadContentInfoFromCache(staticContentPath);
		
		// If these headers are equal, the resonse content is not sent, but rather a 304 "Not Modified" status.
		if (!WebUtils.checkIfModifiedSince(request, response, staticContentInfo.lastModified)
				|| !WebUtils.checkIfNoneMatchEtag(request, response, staticContentInfo.etag)) {
			return;
		}

		// Setting Etag exprires time.
		WebUtils.setExpiresHeader(response, WebUtils.ONE_YEAR_SECONDS);
		WebUtils.setLastModifiedHeader(response, staticContentInfo.lastModified);
		WebUtils.setEtag(response, staticContentInfo.etag);

		// Setting mime type.
		response.setContentType(staticContentInfo.mimeType);

		// Setting pop-up window to download file requests Header
		if (request.getParameter(PARAM_DOWNLOAD) != null) {
			WebUtils.setFileDownloadHeader(response, staticContentInfo.fileName);
		}

		OutputStream output;
		if (checkAccetptGzip(request) && staticContentInfo.needGzip) {
			output = buildGzipOutputStream(response);
		} else {
			response.setContentLength(staticContentInfo.length);
			output = response.getOutputStream();
		}

		FileInputStream input = new FileInputStream(staticContentInfo.file);
		try {
			IOUtils.copy(input, output);
			output.flush();
		} finally {
			IOUtils.closeQuietly(input);
			IOUtils.closeQuietly(output);
		}

	}

	@Override
	public void init() throws ServletException {
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		CacheManager ehcacheManager = (CacheManager) context.getBean(CONTENT_EHCACHE_MANAGER);
		staticContentInfoCache = ehcacheManager.getCache(CONTENT_STATICCONTENTINFO_CACHE);
	}

	/**
	 * 
	 * Check accetpt gzip file.
	 * 
	 * @param request
	 * @return
	 */
	private static boolean checkAccetptGzip(HttpServletRequest request) {
		//Http1.1 header
		String acceptEncoding = request.getHeader(WebConstants.RES_HEADER_KEY_CONTENT_ENCODING);

		if (StringUtils.contains(acceptEncoding, WebConstants.RES_HEADER_ENCODING_GZIP)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * Build gzip outputStream.
	 * 
	 * @param response
	 * @return
	 * @throws IOException
	 * 
	 */
	private OutputStream buildGzipOutputStream(HttpServletResponse response) throws IOException {
		response.setHeader(WebConstants.RES_HEADER_KEY_CONTENT_ENCODING, WebConstants.RES_HEADER_ENCODING_GZIP);
		response.setHeader(WebConstants.RES_HEADER_KEY_VARY, WebConstants.RES_HEADER_VARY);
		return new GZIPOutputStream(response.getOutputStream());
	}

	/**
	 * 
	 * Load static content info.
	 * 
	 * @param staticContentPath
	 * @return
	 * 
	 */
	private StaticContentInfo loadContentInfoFromCache(String staticContentPath) {
		Element element = staticContentInfoCache.get(staticContentPath);
		if (element == null) {
			StaticContentInfo content = buildStaticContentInfo(staticContentPath);
			element = new Element(content.staticContentPath, content);
			staticContentInfoCache.put(element);
		}
		return (StaticContentInfo) element.getObjectValue();
	}

	/**
	 * 
	 * Build static content info.
	 * 
	 * @param staticContentPath
	 * @return
	 * 
	 */
	private StaticContentInfo buildStaticContentInfo(String staticContentPath) {
		StaticContentInfo staticContentInfo = new StaticContentInfo();

		String realFilePath = getServletContext().getRealPath(staticContentPath);
		File file = new File(realFilePath);

		staticContentInfo.file = file;
		staticContentInfo.staticContentPath = staticContentPath;
		staticContentInfo.fileName = file.getName();
		staticContentInfo.length = (int) file.length();

		staticContentInfo.lastModified = file.lastModified();
		staticContentInfo.etag = "W/\"" + staticContentInfo.lastModified + "\"";

		String mimeType = mimetypesFileTypeMap.getContentType(staticContentInfo.fileName);
		if (mimeType == null) {
			mimeType = WebConstants.CONTENT_TYPE_APPLICATION_STREAM;
		}

		staticContentInfo.mimeType = mimeType;

		// check need GZIP or not
		if (staticContentInfo.length >= GZIP_MINI_LENGTH
				&& ArrayUtils.contains(GZIP_MIME_TYPES, staticContentInfo.mimeType)) {
			staticContentInfo.needGzip = true;
		} else {
			staticContentInfo.needGzip = false;
		}

		return staticContentInfo;
	}

	private MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();

	// Define cache for static content info.
	private Cache staticContentInfoCache;

	// Define Gzip compression needs to be the smallest file size.
	private static final int GZIP_MINI_LENGTH = 512;

	private static final String CONTENT_EHCACHE_MANAGER = "ehcacheManager"; // TODO

	private static final String CONTENT_STATICCONTENTINFO_CACHE = "staticContentInfoCache";

	private static final String PARAM_STATIC_CONTENT_PATH = "staticContentPath";

	private static final String PARAM_DOWNLOAD = "download";

	// Define need compression by GZIP.
	private static final String[] GZIP_MIME_TYPES = { WebConstants.CONTENT_TYPE_TEXT_HTML,
			WebConstants.CONTENT_TYPE_APPLICATION_XHTML, WebConstants.CONTENT_TYPE_TEXT_PLAIN,
			WebConstants.CONTENT_TYPE_TEXT_CSS, WebConstants.CONTENT_TYPE_TEXT_JS,
			WebConstants.CONTENT_TYPE_APPLICATION_JS };
	
	/**
	 * 
	 * @author
	 *
	 */
	static class StaticContentInfo {
		String staticContentPath;
		File file;
		String fileName;
		int length;
		String mimeType;
		long lastModified;
		String etag;
		boolean needGzip;
	}
}
