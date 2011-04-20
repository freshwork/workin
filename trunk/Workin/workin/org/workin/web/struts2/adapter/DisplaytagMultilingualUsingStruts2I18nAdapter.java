package org.workin.web.struts2.adapter;

import java.util.Iterator;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.apache.struts2.views.jsp.TagUtils;
import org.displaytag.Messages;
import org.displaytag.localization.I18nResourceProvider;
import org.displaytag.localization.LocaleResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.LocaleProvider;
import com.opensymphony.xwork2.TextProvider;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * 
 * 
 * @author <a href="mailto:angellin.yao@elegoninfotech.com">angellin.yao</a>
 *
 *
 */
public class DisplaytagMultilingualUsingStruts2I18nAdapter implements LocaleResolver, I18nResourceProvider {

	protected transient final Logger logger = LoggerFactory
			.getLogger(DisplaytagMultilingualUsingStruts2I18nAdapter.class);

	// prefix / suffix for missing entries.
	public static final String UNDEFINED_KEY = "Undefined";

	@Override
	public Locale resolveLocale(HttpServletRequest request) {

		Locale result = null;
		ValueStack stack = ActionContext.getContext().getValueStack();

		Iterator iterator = stack.getRoot().iterator();
		while (iterator.hasNext()) {
			Object object = iterator.next();

			if (object instanceof LocaleProvider) {
				LocaleProvider lp = (LocaleProvider) object;
				result = lp.getLocale();
				break;
			}
		}

		if (result == null) {
			logger.debug("Missing LocalProvider actions, init locale to default");
			result = Locale.getDefault();
		}

		return result;
	}

	/**
	 * 
	 * @see I18nResourceProvider#getResource(String, String, Tag, javax.servlet.jsp.PageContext)
	 * 
	 */
	@Override
	public String getResource(String resourceKey, String defaultValue, Tag tag, PageContext pageContext) {
		// if resourceKey isn't defined either, use defaultValue
		String key = (resourceKey != null) ? resourceKey : defaultValue;

		String message = null;
		ValueStack stack = TagUtils.getStack(pageContext);
		Iterator iterator = stack.getRoot().iterator();

		while (iterator.hasNext()) {
			Object object = iterator.next();

			if (object instanceof TextProvider) {
				TextProvider tp = (TextProvider) object;
				message = tp.getText(key, null, (String) null);

				break;
			}
		}
		// if user explicitly added a titleKey we guess this is an error
		if (message == null && resourceKey != null) {
			logger.debug(Messages.getString("Localization.missingkey", resourceKey));
			message = UNDEFINED_KEY + resourceKey + UNDEFINED_KEY;
		}
		return message;
	}
}
