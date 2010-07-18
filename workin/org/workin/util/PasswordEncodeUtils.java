package org.workin.util;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;



/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class PasswordEncodeUtils {

	private static final PasswordEncoder shaPassWordEncoder = new ShaPasswordEncoder();
	
	private static final PasswordEncoder md5PassWordEncoder = new Md5PasswordEncoder();
	
	/**
	 * 
	 * Sha Password Encode with sourceOfPassWord.
	 * 
	 * @param sourceOfPassWord
	 * @return
	 * 
	 */
	public static final String shaPasswordEncode(String sourceOfPassWord) {
		return shaPasswordEncode(sourceOfPassWord, PasswordEncodeUtils.class.toString());
	}
	
	/**
	 * 
	 * Sha Password Encode with sourceOfPassWord and sourceOfSeed.
	 * 
	 * @param sourceOfPassWord
	 * @param sourceOfSeed
	 * @return
	 */
	public static final String shaPasswordEncode(String sourceOfPassWord, String sourceOfSeed) {
		Assert.notNull("Source of passWord can not be null!", sourceOfPassWord);
		return shaPassWordEncoder.encodePassword(sourceOfPassWord, sourceOfSeed);
	}
	
	/**
	 * 
	 * Md5 Password Encode with sourceOfPassWord.
	 * 
	 * @param sourceOfPassWord
	 * @return
	 * 
	 */
	public static final String md5PasswordEncode(String sourceOfPassWord) {
		return md5PasswordEncode(sourceOfPassWord, PasswordEncodeUtils.class.toString());
	}
	
	/**
	 * 
	 * Md5 Password Encode with sourceOfPassWord and sourceOfSeed.
	 * 
	 * @param sourceOfPassWord
	 * @param sourceOfSeed
	 * @return
	 * 
	 */
	public static final String md5PasswordEncode(String sourceOfPassWord, String sourceOfSeed) {
		Assert.notNull("Source of passWord can not be null!", sourceOfPassWord);
		return md5PassWordEncoder.encodePassword(sourceOfPassWord, sourceOfSeed);
	}
}
