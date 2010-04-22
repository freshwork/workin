package org.workin.web.struts2.util;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.workin.exception.ThrowableHandle;
import org.workin.util.Assert;
import org.workin.util.CollectionUtils;
import org.workin.util.WebUtils;
import org.workin.web.constant.WebConstants;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class Struts2Utils {

	/**
	 * 
	 * merge object by checked Ids.
	 * 
	 * @param <T>
	 * @param <ID>
	 * @param entityCollection
	 * @param allDateCollection
	 * @param checkedIds
	 * @param clazz
	 * 
	 */
	public static <T, ID> void mergeByCheckedIds(Collection<T> entityCollection, Collection<T> allDateCollection,
			Collection<ID> checkedIds, Class<T> clazz) {

		mergeByCheckedFields(entityCollection, allDateCollection, checkedIds, clazz, PROPERTY_NAME);

	}

	/**
	 * 
	 * merge object by checked Fields.
	 * 
	 * @param <T>
	 * @param <ID>
	 * @param entityCollection
	 * @param allDateCollection
	 * @param checkedIds
	 * @param clazz
	 * @param fieldName
	 * 
	 */
	public static <T, ID> void mergeByCheckedFields(Collection<T> entityCollection, Collection<T> allDateCollection,
			Collection<ID> checkedIds, Class<T> clazz, String fieldName) {

		Assert.notNull(entityCollection);
		Assert.notNull(allDateCollection);
		Assert.notNull(clazz);
		Assert.notNull(fieldName);

		try {
			entityCollection.clear();
			if (CollectionUtils.isEmpty(checkedIds)) {
				return;
			}

			for (T obj : allDateCollection) {
				for (ID id : checkedIds) {
					if (id.equals(PropertyUtils.getProperty(obj, fieldName))) {
						entityCollection.add(obj);
					}
				}
			}
		} catch (Exception e) {
			ThrowableHandle.handle(e, logger);
		}
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
	public static void render(final String contentType, final String content, final String... headers) {
		
		try {
			
			HttpServletResponse response = ServletActionContext.getResponse();
			WebUtils.render(contentType, content, response, headers);

		} catch (Exception e) {
			ThrowableHandle.handle(e);
		}
	}
	
	/**
	 * 
	 * Output text to client.
	 * 
	 * @param text
	 * @param headers
	 * @return
	 * 
	 */
	public static void renderText(final String text, final String... headers) {
		render(WebConstants.CONTENT_TYPE_TEXT_PLAIN, text, headers);
	}

	/**
	 * 
	 * Output html to client. 	
	 * 	
	 * @param html
	 * @param headers
	 * 
	 * @return
	 * 
	 */
	public static void renderHtml(final String html, final String... headers) {
		render(WebConstants.CONTENT_TYPE_TEXT_HTML, html, headers);
	}

	/**
	 *  Output xml to client. 
	 * 
	 * @param xml
	 * @param headers
	 * 
	 */
	public static void renderXml(final String xml, final String... headers) {
		render(WebConstants.CONTENT_TYPE_TEXT_XML, xml, headers);
	}

	/**
	 *  
	 *  Output JSON to client. 
	 * 
	 * @param string
	 * @param headers
	 */
	public static void renderJson(final String string, final String... headers) {
		render(WebConstants.CONTENT_TYPE_APPLICATION_JSON, string, headers);
	}

	/**
	 * 
	 * Output JSON to client. 
	 * 
	 * @param map
	 * @param headers
	 */
	@SuppressWarnings("unchecked")
	public static void renderJson(final Map map, final String... headers) {
		String jsonString = JSONObject.fromObject(map).toString();
		renderJson(jsonString, headers);
	}

	/**
	 * 
	 * Output JSON to client. 
	 * 
	 * @param object
	 * @param headers
	 */
	public static void renderJson(final Object object, final String... headers) {
		String jsonString = JSONObject.fromObject(object).toString();
		renderJson(jsonString, headers);
	}
	
	/**
	 * 
	 * Output JSON to client. 
	 * 
	 * @param collection
	 * @param headers
	 */
	public static void renderJson(final Collection<?> collection, final String... headers) {
		String jsonString = JSONArray.fromObject(collection).toString();
		render(WebConstants.CONTENT_TYPE_APPLICATION_JSON, jsonString, headers);
	}

	/**
	 * 
	 * Output JSON to client. 
	 * 
	 * @param array
	 * @param headers
	 */
	public static void renderJson(final Object[] array, final String... headers) {
		String jsonString = JSONArray.fromObject(array).toString();
		render(WebConstants.CONTENT_TYPE_APPLICATION_JSON, jsonString, headers);
	}

	/**
	 * 
	 * Output JSON to client. 
	 * 
	 * @param callbackName
	 * @param contentMap
	 * @param headers
	 */
	@SuppressWarnings("unchecked")
	public static void renderJsonp(final String callbackName, final Map contentMap, final String... headers) {
		String jsonParam = JSONObject.fromObject(contentMap).toString();
		StringBuilder result = new StringBuilder().append(callbackName).append("(").append(jsonParam).append(");");
		render(WebConstants.CONTENT_TYPE_APPLICATION_JSON, result.toString(), headers);
	}

	private static final String PROPERTY_NAME = "id";
	
	private static final transient Logger logger = LoggerFactory.getLogger(Struts2Utils.class);
}
