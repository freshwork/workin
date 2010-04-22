package org.workin.web.struts2;

import java.io.File;

import org.workin.exception.ThrowableHandle;
import org.workin.util.FileUtils;
import org.workin.util.StringUtils;
import org.workin.web.constant.WebConstants;

/**
 * 
 * eg.
 * 
 * 	<s:form action="genericFileUploadActionSupport" method="POST" enctype="multipart/form-data">
 *		<s:file label="File (1)" name="upload" />
 *		<s:file label="File (2)" name="upload" />
 *		<s:file label="FIle (3)" name="upload" />
 *		<s:submit />
 *	</s:form>
 *
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class GenericFileUploadActionSupport extends AbstractActionSupport {
    
	private static final long serialVersionUID = -7704932018887759061L;
	
	
	@Override
	public String execute() throws Exception {
		String fileWillUploadToPath = buildWillUploadFileToPath();
		for(int index = 0, uploadOfSize = uploads.length; index < uploadOfSize; index ++) {
        	FileUtils.copyFile(uploads[index], buidWillUploadFile(fileWillUploadToPath, uploadFileNames[index]));
        }
        return SUCCESS;  
	}
	
	/**
	 * 
	 * Build Will Upload File.
	 * 
	 * @param fileWillUploadToPath
	 * @param fileName
	 * @return
	 * 
	 * 
	 */
	private File buidWillUploadFile(String fileWillUploadToPath, String fileName) {
		File parent = new File(fileWillUploadToPath);
		
		if (!parent.exists()) {
			try {
				parent.mkdirs();
			} catch (Exception e) {
				ThrowableHandle.handle("Hit Exception, When mkdirs" + fileWillUploadToPath, e, logger);
			}
		}
		
		return new File(parent, fileName);
	}
	
	
	/**
	 * 
	 * Build will upload file to path.
	 * 
	 * @return
	 */
	private String buildWillUploadFileToPath() {
		StringBuilder fileWillUploadToThisPath;
		String requestRemoteUser = this.getRequest().getRemoteUser();
		StringBuilder asyncPath = new StringBuilder(File.separator);
		
		if (StringUtils.hasText(uploadFilePath)) {
			asyncPath.append(uploadFilePath);
		} else {
			asyncPath.append(WebConstants.UPLOAD_UPLOAD_PATH_DEFAULT);
		}
		
		fileWillUploadToThisPath = new StringBuilder(this.getServletContext().getRealPath(asyncPath.toString())); 
		
		fileWillUploadToThisPath.append(File.separator);
		requestRemoteUser = StringUtils.hasText(requestRemoteUser)? requestRemoteUser : WebConstants.UPLOAD_UPLOAD_PATH_SUB_DEFAULT;
		fileWillUploadToThisPath.append(requestRemoteUser);
		fileWillUploadToThisPath.append(File.separator);
		
		logger.debug(" File(s) will be upload to path: {}.", fileWillUploadToThisPath);
		return fileWillUploadToThisPath.toString();
	}
	
	
	// Define upload files array.
	private File[] uploads;
    
	// Define upload file names array.
	private String[] uploadFileNames;
    
	// Define upload content types array.
	private String[] uploadContentTypes;
	
	// Define upload file path.
	private String uploadFilePath;
	
	public File[] getUploads() {
		return uploads;
	}

	public void setUploads(File[] uploads) {
		this.uploads = uploads;
	}

	public String[] getUploadFileNames() {
		return uploadFileNames;
	}

	public void setUploadFileNames(String[] uploadFileNames) {
		this.uploadFileNames = uploadFileNames;
	}

	public String[] getUploadContentTypes() {
		return uploadContentTypes;
	}

	public void setUploadContentTypes(String[] uploadContentTypes) {
		this.uploadContentTypes = uploadContentTypes;
	}

	public String getUploadFilePath() {
		return uploadFilePath;
	}

	public void setUploadFilePath(String uploadFilePath) {
		this.uploadFilePath = uploadFilePath;
	}
}
