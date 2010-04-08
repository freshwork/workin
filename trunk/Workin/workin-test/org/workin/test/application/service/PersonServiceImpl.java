package org.workin.test.application.service;

import java.util.List;
import java.util.Map;

import org.workin.core.persistence.support.AbstractBeanService;
import org.workin.core.persistence.support.PropertyFilter;
import org.workin.test.application.entity.Person;


/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
@SuppressWarnings("unchecked")
public class PersonServiceImpl extends AbstractBeanService implements PersonService {
	
	@Override
	public List findPersonsBySqlMap(String sqlMapId, Person person) {
		return this.getPersistenceService().findListBySqlMap(sqlMapId, person);
	}
	
	@Override
	public List findPersonsByNamedQuery(String namedOfQuery, Map nameAndValue) {
		return this.getPersistenceService().findByNamedOfQuery(namedOfQuery, nameAndValue);
	}
	
	@Override
	public List findPersonsByProperty(String propertyName, Object value) {
		return this.getPersistenceService().findByProperty(Person.class, propertyName, value);
	}
	
	@Override
	public List findPersonsByPropertys(Map nameAndValue) {
		return this.getPersistenceService().findByPropertys(Person.class, nameAndValue);
	}

	@Override
	public int executeNamedOfQuery(String queryName, Object... values) {
		return this.getPersistenceService().executeNamedOfQuery(queryName, values);
	}

	@Override
	public int persistByNativeQuery(String queryString) {
		return this.getPersistenceService().persistByNativeQuery(queryString);
	}
	
	@Override
	public int persistByNativeQuery(final String queryString, final Object... values) {
		return this.getPersistenceService().persistByNativeQuery(queryString, values);
	}
	
	public List<Person> findByCriteriaQuery(List<PropertyFilter> filters) {
		return this.getPersistenceService().findByCriteriaQuery(Person.class, filters);
	}
}
