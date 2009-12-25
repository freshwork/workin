package org.workin.web.struts2;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 * @param <T>
 */
@SuppressWarnings("serial")
public abstract class CRUDActionSupport<T> extends DefaultActionSupport implements
		ModelDriven<T>, Preparable {

	// redirect to action default screen
	public static final String RELOAD = "reload";
	
	// redirect to action default screen
	public static final String VIEW = "view";
	
	@Override
	public String execute() throws Exception {
		return list();
	}

	/**
	 * 
	 * Action function - show Entity list.
	 * 
	 * @return
	 * 
	 */
	public abstract String list() throws Exception;
	
	/**
	 * 
	 * Action function - save or update entity.
	 * 
	 * @return
	 */
	public abstract String save() throws Exception;

	/**
	 * 
	 * Action function - remove entity.
	 * 
	 * @return
	 */
	public abstract String remove() throws Exception;
	
	/**
	 * 
	 * Action function - view.
	 * 
	 * @return
	 */
	public String view() throws Exception {
		return VIEW;
	}
	
	/**
	 * 
	 * hidden this function.
	 * 
	 */
	public void prepare() throws Exception {
		
	}
	
	/**
	 * 
	 * Rebinding before save, need call this function by user.
	 * 
	 * @throws Exception
	 * 
	 */
	public void prepareSave() throws Exception {
		prepareModel();
	}
	
	/**
	 * 
	 * Rebinding before input, need call this function by user.
	 * 
	 * @throws Exception
	 */
	public void prepareInput() throws Exception {
		prepareModel();
	}
	
	/**
	 * 
	 * Rebinding before view, need call this function by user.
	 * 
	 * @throws Exception
	 */
	public void prepareView() throws Exception {
		prepareModel();
	}
	
	protected abstract void prepareModel() throws Exception;	

}
