package org.workin.web.constant;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public interface WebConstants {

	// define screen default page size
	public static final int DEFAULT_PAGE_SIZE = 10;	
	
	// encoding for default
	public static final String RENDER_CONTENT_ENCODING = "UTF-8";
	// text type defind string to client
	public static final String RENDER_CONTENT_TYPE_TEXT = "text/plain";
	// html type defind string to client
	public static final String RENDER_CONTENT_TYPE_HTML = "text/html";
	// xml type defind string to client
	public static final String RENDER_CONTENT_TYPE_XML = "text/xml";
	// json type defind string to client
	public static final String RENDER_CONTENT_TYPE_JSON = "application/json";
	// charset prefix
	public static final String CHARSET_PREFIX = ";charset=";
	// encoding prefix
	public static final String ENCODING_PREFIX = "encoding";
	// no cache
	public static final String NOCACHE_PREFIX = "no-cache";
	// no cache default
	public static final boolean NOCACHE_DEFAULT = true;
	
	
	// define upload folder
	public static final String FILE_UPLOAD_FOLDER = "resources";
}
