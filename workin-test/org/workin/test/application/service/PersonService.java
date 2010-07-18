package org.workin.test.application.service;


import java.util.List;
import java.util.Map;

import org.workin.core.persistence.support.CrudService;
import org.workin.core.persistence.support.PaginationSupport;
import org.workin.test.application.entity.Person;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
@SuppressWarnings("unchecked")
public interface PersonService extends CrudService {
	
	public List findPersonsBySqlMap(String sqlMapId, Person person);
	
	public PaginationSupport findPersonsBySqlMap(String sqlMapId, Person person, int offset, int maxRows);
	
	public List findPersonsByNamedQuery(String namedOfQuery, Map nameAndValue);
	
	public List findPersonsByProperty(String propertyName, Object value);
	
	public List findPersonsByPropertys(Map nameAndValue);
	
	public int executeNamedOfQuery(String queryName, Object... values);
	
	public int persistByNativeQuery(final String queryString);
	
	public int persistByNativeQuery(final String queryString, final Object... values);
	
	public List find(final String queryString, final Object... values);
	
	public PaginationSupport findPaginationSupport(final int start, final int maxRows, final String queryString, final Object... values);
}	
