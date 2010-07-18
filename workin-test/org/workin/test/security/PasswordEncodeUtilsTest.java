package org.workin.test.security;

import org.junit.Test;
import org.workin.fortest.BaseTestCase;
import org.workin.util.PasswordEncodeUtils;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class PasswordEncodeUtilsTest extends BaseTestCase {
	
	@Test public void shaPasswordEncode() {
		String shaPasswordEncode = PasswordEncodeUtils.shaPasswordEncode(SOURCE_OF_PASSWORD);
		String shaPasswordEncodeWithSeed = PasswordEncodeUtils.shaPasswordEncode(SOURCE_OF_PASSWORD, SOURCE_OF_SEED);
		
		assertEquals(SHAPASSWORDENCODE, shaPasswordEncode);
		assertEquals(SHAPASSWORDENCODEWITHSEED, shaPasswordEncodeWithSeed);
	}
	
	@Test public void md5PasswordEncode() {
		String md5PasswordEncode = PasswordEncodeUtils.md5PasswordEncode(SOURCE_OF_PASSWORD);
		String md5PasswordEncodeWithSeed = PasswordEncodeUtils.md5PasswordEncode(SOURCE_OF_PASSWORD, SOURCE_OF_SEED);
		
		assertEquals(MD5PASSWORDENCODE, md5PasswordEncode);
		assertEquals(MD5PASSWORDENCODEWITHSEED, md5PasswordEncodeWithSeed);
	}
	
	private static final transient String SOURCE_OF_PASSWORD = "111111";
	private static final transient String SOURCE_OF_SEED = "workin";
	private static final transient String SHAPASSWORDENCODE = "570736fc1047e5ee61b5e64e40bc62102dc5c206";
	private static final transient String SHAPASSWORDENCODEWITHSEED = "610f54b0f105bb1d3c3279daf513d356994de80c";
	private static final transient String MD5PASSWORDENCODE = "cb0312b7fb0e1069ccc25723863505ef";
	private static final transient String MD5PASSWORDENCODEWITHSEED = "2a4bc3cce297dfebf021d34ec941b581";
	
}
