package org.workin.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.zip.GZIPOutputStream;

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

	/**
	 * 
	 * set browser download header.
	 * 
	 * @param fileName download fileName.
	 */
	public static void setDownloadableHeader(HttpServletResponse response, String fileName) {
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
	}
	
	
	/**
	 * 
	 * set LastModifiedHeader.
	 * 
	 * @param response
	 * @param lastModifiedDate
	 * 
	 */
	public static void setLastModifiedHeader(HttpServletResponse response, long lastModifiedDate) {
		response.setDateHeader("Last-Modified", lastModifiedDate);
	}

	/**
	 * 
	 * set Etag.
	 *  
	 * @param response
	 * @param etag
	 */
	public static void setEtag(HttpServletResponse response, String etag) {
		response.setHeader("ETag", etag);
	}

	/**
	 * set Expires time header.
	 * 
	 * @param response
	 * @param expiresSeconds
	 */
	public static void setExpiresHeader(HttpServletResponse response, long expiresSeconds) {
		//Http 1.0 header
		response.setDateHeader("Expires", System.currentTimeMillis() + expiresSeconds * 1000);
		//Http 1.1 header
		response.setHeader("Cache-Control", "max-age=" + expiresSeconds);
	}

	/**
	 * 
	 * set no cache header.
	 * 
	 * @param response
	 */
	public static void setNoCacheHeader(HttpServletResponse response) {
		response.setHeader("Pragma", "No-cache");
		
		//Http 1.0 header
		response.setDateHeader("Expires", 0);
		//Http 1.1 header
		response.setHeader("Cache-Control", "no-cache");
	}

	/**
	 * 
	 * check client holder gzip.
	 * 
	 * @param request
	 * @return
	 */
	public static boolean checkAccetptGzip(HttpServletRequest request) {
		//Http1.1 header
		String acceptEncoding = request.getHeader("Accept-Encoding");

		if (StringUtils.contains(acceptEncoding, "gzip")) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 
	 * set Gzip Header and return GZIPOutputStream.
	 * 
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public static OutputStream buildGzipOutputStream(HttpServletResponse response) throws IOException {
		response.setHeader("Content-Encoding", "gzip");
		return new GZIPOutputStream(response.getOutputStream());
	}

	/**
	 * 
	 * check if modified since.
	 * 
	 * @param request
	 * @param response
	 * @param lastModified
	 * @return
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
	 * check if none match Etag.
	 * 
	 * @param request
	 * @param response
	 * @param etag
	 * @return
	 * 
	 */
	public static boolean checkIfNoneMatchEtag(HttpServletRequest request, HttpServletResponse response, String etag) {
		String headerValue = request.getHeader("If-None-Match");
		if (headerValue != null) {
			boolean conditionSatisfied = false;
			if (!headerValue.equals("*")) {
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
	 * get the same prefix Request Parameters.
	 * 
	 * @param request
	 * @param prefix
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> getParametersStartingWith(HttpServletRequest request, String prefix) {
		return org.springframework.web.util.WebUtils.getParametersStartingWith(request, prefix);
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
