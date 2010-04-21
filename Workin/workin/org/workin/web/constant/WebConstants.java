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
	// text type define string to client
	public static final String RENDER_CONTENT_TYPE_TEXT = "text/plain";
	// html type define string to client
	public static final String RENDER_CONTENT_TYPE_HTML = "text/html";
	// xml type define string to client
	public static final String RENDER_CONTENT_TYPE_XML = "text/xml";
	// json type define string to client
	public static final String RENDER_CONTENT_TYPE_JSON = "application/json";
	// js type define string to client
	public static final String RENDER_CONTENT_TYPE_JS = "text/javascript";
	// excel type define string to client
	public static final String RENDER_CONTENT_TYPE_EXCEL = "application/vnd.ms-excel";

	// charset prefix define
	public static final String CHARSET_PREFIX = ";charset=";
	// encoding prefix define
	public static final String ENCODING_PREFIX = "encoding";
	// no cache define
	public static final String NOCACHE_PREFIX = "no-cache";
	// no cache default define
	public static final boolean NOCACHE_DEFAULT = true;
	
	
	// define upload folder.
	public static final String FILE_UPLOAD_FOLDER = "resources";
	
	// define request parameter callback method name.
	public static final String PARAM_REQUEST_CALLBACK = "callback";
	// define parameter client response cache exprires sencond.
	public static final String PARAM_EXPIRES_SECOND = "expiresSeconds";
}
