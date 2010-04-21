package org.workin.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringEscapeUtils;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class EncodeUtils {
	
	private static final String DEFAULT_URL_ENCODING = "UTF-8";
	
	private EncodeUtils() {}
	
	/**
	 * 
	 * hex encode.
	 * 
	 * @param input
	 * @return
	 */
	public static String hexEncode(byte[] input) {
		return Hex.encodeHexString(input);
	}
	
	/**
	 * 
	 * hex decode.
	 * 
	 * @param input
	 * @return
	 * 
	 */
	public static byte[] hexDecode(String input) {
		try {
			return Hex.decodeHex(input.toCharArray());
		} catch (DecoderException e) {
			throw new IllegalStateException("Hex Decoder exception", e);
		}
	}
	
	/**
	 * 
	 * base64 encode.
	 * 
	 * @param input
	 * @return
	 * 
	 */
	public static String base64Encode(byte[] input) {
		return Base64.encodeBase64String(input);
	}
	
	/**
	 * 
	 * base64 decode.
	 * 
	 * @param input
	 * @return
	 */
	public static byte[] base64Decode(String input) {
		return Base64.decodeBase64(input);
	}
	
	/**
	 * 
	 * base64 url encode.
	 * 
	 * @param input
	 * @return
	 */
	public static String base64UrlEncode(byte[] input) {
		return Base64.encodeBase64URLSafeString(input);
	}
	
	/**
	 * 
	 * url encode.
	 * 
	 * @param input
	 * @return
	 */
	public static String urlEncode(String input) {
		return urlEncode(input, DEFAULT_URL_ENCODING);
	}
	
	/**
	 * 
	 * url encode.
	 * 
	 * @param input
	 * @param encoding
	 * @return
	 */
	public static String urlEncode(String input, String encoding) {
		try {
			return URLEncoder.encode(input, encoding);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("Unsupported Encoding Exception", e);
		}
	}
	
	/**
	 * 
	 * url decode. 
	 * 
	 * @param input
	 * @return
	 */
	public static String urlDecode(String input) {
		return urlDecode(input, DEFAULT_URL_ENCODING);
	}
	
	/**
	 * 
	 * url decode. 
	 * 
	 * @param input
	 * @param encoding
	 * @return
	 */
	public static String urlDecode(String input, String encoding) {
		try {
			return URLDecoder.decode(input, encoding);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("Unsupported Encoding Exception", e);
		}
	}
	
	/**
	 * 
	 * html escape.
	 * 
	 * @param html
	 * @return
	 */
	public static String htmlEscape(String html) {
		return StringEscapeUtils.escapeHtml(html);
	}
	
	/**
	 * 
	 * html unescape.
	 * 
	 * @param htmlEscaped
	 * @return
	 * 
	 */
	public static String htmlUnescape(String htmlEscaped) {
		return StringEscapeUtils.unescapeHtml(htmlEscaped);
	}
	
	/**
	 * 
	 * xml escape.
	 * 
	 * @param xml
	 * @return
	 */
	public static String xmlEscape(String xml) {
		return StringEscapeUtils.escapeXml(xml);
	}
	
	/**
	 * 
	 * xtml unescape.
	 * 
	 * @param xmlEscaped
	 * @return
	 */
	public static String xmlUnescape(String xmlEscaped) {
		return StringEscapeUtils.unescapeXml(xmlEscaped);
	}
}
