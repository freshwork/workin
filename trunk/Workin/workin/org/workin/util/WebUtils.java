package org.workin.util;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.workin.exception.ThrowableHandle;
import org.workin.web.constant.WebConstants;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class WebUtils {
	
	public static final long ONE_YEAR_SECONDS = 60 * 60 * 24 * 365;
	
	public static final String AUTHENTICATION_HEADER = "Authorization";

	/**
	 * Set up client cache ecpires time.
	 * 
	 */
	public static void setExpiresHeader(HttpServletResponse response, long expiresSeconds) {
		//Http 1.0 header
		response.setDateHeader("Expires", System.currentTimeMillis() + expiresSeconds * 1000);
		//Http 1.1 header
		response.setHeader("Cache-Control", "private, max-age=" + expiresSeconds);
	}

	/**
	 * 
	 * Set up client no cache header.
	 * 
	 */
	public static void setNoCacheHeader(HttpServletResponse response) {
		//Http 1.0 header
		response.setDateHeader("Expires", 0);
		response.addHeader("Pragma", "no-cache");
		//Http 1.1 header
		response.setHeader("Cache-Control", "no-cache");
	}

	/**
	 * 
	 * Set up LastModified Header.
	 * 
	 */
	public static void setLastModifiedHeader(HttpServletResponse response, long lastModifiedDate) {
		response.setDateHeader("Last-Modified", lastModifiedDate);
	}

	/**
	 * 
	 * Set up Etag Header.
	 * 
	 */
	public static void setEtag(HttpServletResponse response, String etag) {
		response.setHeader("ETag", etag);
	}

	/**
	 * 
	 * Base on browser, If-Modified-Since Header, Determine whether the text has been modified
	 * 
	 * If have not any modified, checkIfModify return false ,Set 304 not modify status.
	 * 
	 * @param lastModified
	 * 
	 */
	public static boolean checkIfModifiedSince(HttpServletRequest request, HttpServletResponse response,
			long lastModified) {
		long ifModifiedSince = request.getDateHeader("If-Modified-Since");
		if ((ifModifiedSince != -1) && (lastModified < ifModifiedSince + 1000)) {
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			return false;
		}
		return true;
	}

	/**
	 * 
	 * Base on browser, If-None-Match Header, Determine whether the Etag has been modified.
	 * 
	 * 
	 * If Etag Effective, checkIfNoneMatch return false, Set 304 not modify status.
	 * 
	 * @param etag
	 * 
	 */
	public static boolean checkIfNoneMatchEtag(HttpServletRequest request, HttpServletResponse response, String etag) {
		String headerValue = request.getHeader("If-None-Match");
		if (headerValue != null) {
			boolean conditionSatisfied = false;
			if (!"*".equals(headerValue)) {
				StringTokenizer commaTokenizer = new StringTokenizer(headerValue, ",");

				while (!conditionSatisfied && commaTokenizer.hasMoreTokens()) {
					String currentToken = commaTokenizer.nextToken();
					if (currentToken.trim().equals(etag)) {
						conditionSatisfied = true;
					}
				}
			} else {
				conditionSatisfied = true;
			}

			if (conditionSatisfied) {
				response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
				response.setHeader("ETag", etag);
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * Set the browser pop-up download dialog Header.
	 * 
	 * @param fileName
	 */
	public static void setFileDownloadHeader(HttpServletResponse response, String fileName) {
		try {
			String encodedfileName = new String(fileName.getBytes(), "ISO8859-1");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedfileName + "\"");
		} catch (UnsupportedEncodingException e) {
			ThrowableHandle.handle(e);
		}
	}

	/**
	 * 
	 * Get the same prefix Request Parameters.
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static Map getParametersStartingWith(HttpServletRequest request, String prefix) {
		Enumeration paramNames = request.getParameterNames();
		Map params = new TreeMap();
		if (prefix == null) {
			prefix = "";
		}
		while (paramNames != null && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			if ("".equals(prefix) || paramName.startsWith(prefix)) {
				String unprefixed = paramName.substring(prefix.length());
				String[] values = request.getParameterValues(paramName);
				if (values == null || values.length == 0) {//NOSONAR
					// Do nothing, no values found at all.
				} else if (values.length > 1) {
					params.put(unprefixed, values);
				} else {
					params.put(unprefixed, values[0]);
				}
			}
		}
		return params;
	}

	/**
	 * 
	 * @param userName
	 * @param password
	 * @return
	 * 
	 */
	public static String encodeHttpBasic(String userName, String password) {
		String encode = userName + ":" + password;
		return "Basic " + EncodeUtils.base64Encode(encode.getBytes());
	}
	
	
	/**
	 * 
	 * Output text(contentType) to screen.
	 * 
	 * eg.
	 * render("text/plain", "hello", "encoding:GBK");
	 * render("text/plain", "hello", "no-cache:false");
	 * render("text/plain", "hello", "encoding:GBK", "no-cache:false");
	 * 
	 * 
	 * @param contentType
	 * @param content
	 * @param headers
	 */
	public static void render(final String contentType, final String content, final HttpServletResponse response, final String... headers) {
		
		try {
			
			String encoding = WebConstants.RENDER_CONTENT_ENCODING;
			boolean noCache = WebConstants.NOCACHE_DEFAULT;
			
			for (String header : headers) {
				String headerName = StringUtils.substringBefore(header, ":");
				String headerValue = StringUtils.substringAfter(header, ":");

				if (StringUtils.equalsIgnoreCase(headerName, WebConstants.ENCODING_PREFIX)) {
					encoding = headerValue;
				} else if (StringUtils.equalsIgnoreCase(headerName, WebConstants.NOCACHE_PREFIX)) {
					noCache = Boolean.parseBoolean(headerValue);
				} else {
					throw new IllegalArgumentException(headerName + " Invalid header type.");
				}
			}

			String fullContentType = new StringBuilder(contentType).append(WebConstants.CHARSET_PREFIX).append(encoding).toString();
			response.setContentType(fullContentType);
			
			// if not cache set up some default value to header
			if (noCache) {
				setNoCacheHeader(response);
			}

			response.getWriter().write(content);
			response.getWriter().flush();

		} catch (Exception e) {
			ThrowableHandle.handle(e);
		}
	}
}
