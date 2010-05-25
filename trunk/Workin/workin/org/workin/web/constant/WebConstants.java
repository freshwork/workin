package org.workin.web.constant;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public interface WebConstants {

	// Define screen default page size.
	public static final int DEFAULT_PAGE_SIZE = 10;	
	
	// Define response header setting keys
	public static final String RES_HEADER_KEY_ETAG = "ETag";
	public static final String RES_HEADER_KEY_VARY = "Vary";
	public static final String RES_HEADER_KEY_PRAGMA = "Pragma";
	public static final String RES_HEADER_KEY_EXPIRES = "Expires";
	public static final String RES_HEADER_KEY_IFNONEMATCH = "If-None-Match";
	public static final String RES_HEADER_KEY_LASTMODIFIED= "Last-Modified";
	public static final String RES_HEADER_KEY_CACHECONTROL = "Cache-Control";
	public static final String RES_HEADER_KEY_IFMODIFIEDSINCE = "If-Modified-Since";
	public static final String RES_HEADER_KEY_CONTENTDISPOSITION = "Content-Disposition";
	public static final String RES_HEADER_KEY_CONTENT_ENCODING = "Content-Encoding";
	
	public static final String RES_HEADER_VARY = "Accept-Encoding";
	public static final String RES_HEADER_ENCODING_GZIP = "gzip";	
	public static final String RES_HEADER_MAXAGE = "private, max-age=";
	public static final String RES_HEADER_NOCACHE = "no-cache";
	
	// Encoding for default.
	public static final String CONTENT_ENCODING_UTF8 = "UTF-8";
	public static final String CONTENT_ENCODING_ISO88591 = "ISO8859-1";
	
	// Define text type of plain.
	public static final String CONTENT_TYPE_TEXT_PLAIN = "text/plain";
	// Define text type of html.
	public static final String CONTENT_TYPE_TEXT_HTML = "text/html";
	// Define text type of xml.
	public static final String CONTENT_TYPE_TEXT_XML = "text/xml";
	// Define text type of javascript.
	public static final String CONTENT_TYPE_TEXT_JS = "text/javascript";
	// Define text type of css.
	public static final String CONTENT_TYPE_TEXT_CSS = "text/css";
	
	// Define application type of json.
	public static final String CONTENT_TYPE_APPLICATION_JSON = "application/json";
	// Define application type of excel.
	public static final String CONTENT_TYPE_APPLICATION_EXCEL = "application/vnd.ms-excel";
	// Define application type of xhtml and xml.
	public static final String CONTENT_TYPE_APPLICATION_XHTML = "application/xhtml+xml";
	// Define application type of javascript.
	public static final String CONTENT_TYPE_APPLICATION_JS = "application/x-javascript";
	// Define application type of octet stream.
	public static final String CONTENT_TYPE_APPLICATION_STREAM = "application/octet-stream";
	
	// Define charset prefix.
	public static final String CHARSET_PREFIX = ";charset=";
	// Define encoding prefix.
	public static final String ENCODING_PREFIX = "encoding";
	// Define no cache.
	public static final String NOCACHE_PREFIX = "no-cache";
	// Define no cache default.
	public static final boolean NOCACHE_DEFAULT = true;
	
	
	// Define file upload folder path.
	public static final String UPLOAD_UPLOAD_PATH_DEFAULT = "upload";
	public static final String UPLOAD_UPLOAD_PATH_SUB_DEFAULT = "common";
	
	// Define request parameter callback method name.
	public static final String PARAM_REQUEST_CALLBACK = "callback";
	// Define parameter client response cache exprires sencond.
	public static final String PARAM_EXPIRES_SECOND = "expiresSeconds";
	
	// Define page number.
	public static final String PAGE_NUMBER = "pageNo";
	// Define pagination key
	public static final String PAGE_RESULT_SIZE = "resultSize";
}
