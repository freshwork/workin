package org.workin.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
@SuppressWarnings( { "deprecation" })
public class FileUtils {

	private static final transient Logger logger = LoggerFactory.getLogger(FileUtils.class);

	private FileUtils() {
	}

	public static boolean deleteFile(String path, String fileName) {

		File file = new File(path + File.separator + fileName);

		return deleteDir(file);
	}

	private static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}

		return dir.delete();
	}

	public static final URL findAsResource(final String path) {
		URL url = null;

		// First, try to locate this resource through the current
		// context classloader.
		url = Thread.currentThread().getContextClassLoader().getResource(path);
		if (url != null) {
			return url;
		}

		// Next, try to locate this resource through this class's classloader
		url = FileUtils.class.getClassLoader().getResource(path);
		if (url != null)
			return url;

		// Next, try to locate this resource through the system classloader
		url = ClassLoader.getSystemClassLoader().getResource(path);
		if (url != null)
			return url;

		//Anywhere else
		if (url == null) {
			File file = new File(path);
			try {
				if (file.exists()) {
					url = file.toURL();
				}
			} catch (java.net.MalformedURLException e) {
				url = null;
			}
		}

		return url;
	}
	
	/**
	 * 
	 * @param target
	 * @return
	 * 
	 */
	public static final File findFileAsResource(final String target) {
		File file = null;
		URL url = findAsResource(target);
		
		if(url != null)
			file = new File(url.getFile());
		
		return file;
	}

	/**
	 * 
	 * @param file
	 * @param target
	 * @throws Exception
	 * 
	 */
	public static final void copyFile(final File file, File target) throws Exception {

		InputStream bis = null;
		OutputStream bos = null;

		try {

			bis = new BufferedInputStream(new FileInputStream(file));

			//write the file to the file specified
			bos = new BufferedOutputStream(new FileOutputStream(target));

			int bytesRead;
			byte[] buffer = new byte[BUFFER_SIZE];

			while ((bytesRead = bis.read(buffer, 0, BUFFER_SIZE)) != -1) {
				bos.write(buffer, 0, bytesRead);
				bos.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Hitting Exception, When copy file... : {}.", e.getMessage());
			throw e;
		} finally {
			try {
				if (bos != null)
					bos.close();
				if (bis != null)
					bis.close();
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error("Hitting Exception, When close stream... : {}.", ex.getMessage());
				throw ex;
			}
		}
	}

	// define file's buffer size
	public static final int BUFFER_SIZE = 1024;
}
