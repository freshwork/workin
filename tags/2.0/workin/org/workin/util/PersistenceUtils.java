package org.workin.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.workin.core.constant.Constants;
import org.workin.core.persistence.support.PropertyFilter;
import org.workin.core.persistence.support.PropertyFilter.LikeMatchPatten;
import org.workin.core.persistence.support.PropertyFilter.MatchType;
import org.workin.core.persistence.support.PropertyFilter.PropertyType;
import org.workin.exception.ThrowableHandler;


/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class PersistenceUtils {
	
	private PersistenceUtils() {
	}
	
	/**
	 * 
	 * @see #buildPropertyFilters(HttpServletRequest, String)
	 * 
	 */
	public static List<PropertyFilter> buildPropertyFilters(final HttpServletRequest request) {
		return buildPropertyFilters(request, Constants.PREFIX_PLACEHOLDER_FILTER);
	}
	
	/**
	 * 
	 * eg.
	 * filter_EQS_name
	 * filter_LIKES_name_OR_email
	 * 
	 * @param request
	 * @param filterPrefix
	 * @return
	 * 
	 */
	public static List<PropertyFilter> buildPropertyFilters(final HttpServletRequest request, final String filterPrefix) {
		
		List<PropertyFilter> filterList = new ArrayList<PropertyFilter>();
		Map<String, String> filterParamMap = WebUtils.getParametersStartingWith(request, filterPrefix);

		for (Map.Entry<String, String> entry : filterParamMap.entrySet()) {
			String filterName = entry.getKey();
			String value = entry.getValue();
			if (StringUtils.isNotBlank(value)) {
				PropertyFilter filter = new PropertyFilter(filterName, value);
				filterList.add(filter);
			}
		}
		return filterList;
	}
	

	/**
	 * 
	 * Build Query String(SELECT COUNT(*) OR SELECT * ) Using class type.
	 * 
	 * @param clazz
	 * @param isCount
	 * @return
	 * 
	 */
	public static StringBuilder buildQueryString(final Class<?> clazz, final boolean isCount) {
		StringBuilder queryBuilder = new StringBuilder();

		if (isCount)
			queryBuilder.append(Constants.HQL_SELECT_COUNT_FROM);
		else
			queryBuilder.append(Constants.HQL_SELECT_FROM);

		queryBuilder.append(clazz.getName());
		queryBuilder.append(Constants.HQL_ALIAS_OBJECT);

		return queryBuilder;
	}

	/**
	 * 
	 * Build Query String, Using class and values of parameters.
	 * 
	 * @param isCount
	 * @param clazz
	 * @param values
	 * @return
	 * 
	 */
	public static String buildQueryString(final boolean isCount, final Class<?> clazz, final String... values) {
		StringBuilder queryBuilder = buildQueryString(clazz, isCount);

		if (values != null && values.length > 0) {
			queryBuilder.append(Constants.HQL_KEYWORD_WHERE);
			for (String value : values) {
				queryBuilder.append(value).append(Constants.HQL_KEYWORD_AND_VALUE);
			}

			if (queryBuilder.lastIndexOf(Constants.HQL_KEYWORD_AND) == (queryBuilder.length() - 5)) {
				queryBuilder.delete(queryBuilder.length() - 5, queryBuilder.length());
			}
		}

		return queryBuilder.toString();
	}

	/**
	 * 
	 * Build Query String, Using class and values of parameters.
	 * 
	 * @param clazz
	 * @param params
	 * 
	 * @return string
	 * 
	 */
	public static String buildQueryStringWithNamedParams(final boolean isCount, final Class<?> clazz, final Map<String, ?> params) {
		StringBuilder queryBuilder = buildQueryString(clazz, isCount);

		if (!CollectionUtils.isEmpty(params)) {
			queryBuilder.append(Constants.HQL_KEYWORD_WHERE);

			for (Map.Entry<String, ?> entry : params.entrySet()) {
				queryBuilder.append(entry.getKey()).append(Constants.HQL_PLACEHOLDER_EQUALITY_COLON).append(entry.getKey()).append(Constants.HQL_KEYWORD_AND);
			}

			if (queryBuilder.lastIndexOf(Constants.HQL_KEYWORD_AND) == (queryBuilder.length() - 5)) {
				queryBuilder.delete(queryBuilder.length() - 5, queryBuilder.length());
			}
		}

		logger.info(" Build Query String With NamedParams: {}", queryBuilder.toString());
		return queryBuilder.toString();
	}
	
	/**
	 * 
	 * Build Query String, Using class and propertyFilters.
	 * 
	 * @param clazz
	 * @param filters
	 * @return
	 * 
	 */
	public static String buildQueryStringWithPropertyFilters(final boolean isCount, final Class<?> clazz, final List<PropertyFilter> filters) {
		
		StringBuilder queryBuilder = buildQueryString(clazz, isCount);
		
		if(!CollectionUtils.isEmpty(filters)) {
			queryBuilder.append(Constants.HQL_KEYWORD_WHERE);
			
			for(PropertyFilter filter : filters) {
				if(filter.isMultiProperty()) {
					queryBuilder.append(Constants.HQL_OPERATOR_BRACKET_BEFORE);
					for(String propertyName : filter.getPropertyNames()) {
						queryBuilder.append(propertyName);
						buildQueryStringWithPropertyFilter(filter, queryBuilder);
						queryBuilder.append(Constants.HQL_KEYWORD_OR);
					}
					if (queryBuilder.lastIndexOf(Constants.HQL_KEYWORD_OR) == (queryBuilder.length() - 4)) {
						queryBuilder.delete(queryBuilder.length() - 4, queryBuilder.length());
					}
					queryBuilder.append(Constants.HQL_OPERATOR_BRACKET_AFTER);
				} else {
					queryBuilder.append(filter.getPropertyName());
					buildQueryStringWithPropertyFilter(filter, queryBuilder);
				}
				queryBuilder.append(Constants.HQL_KEYWORD_AND);
			}
			
			if (queryBuilder.lastIndexOf(Constants.HQL_KEYWORD_AND) == (queryBuilder.length() - 5)) {
				queryBuilder.delete(queryBuilder.length() - 5, queryBuilder.length());
			}
			
			logger.info(" Build Query String With PropertyFilter: {}", queryBuilder.toString());
		}
		
		return queryBuilder.toString();
	}
	
	/**
	 * 
	 * Build Query String, Using propertyFilter and queryBuilder.
	 * 
	 * @param filter
	 * @param propertyName
	 * @param queryBuilder
	 * 
	 */
	private static void buildQueryStringWithPropertyFilter(final PropertyFilter filter, StringBuilder queryBuilder) {
		MatchType matchType = filter.getMatchType();
		LikeMatchPatten likeMatchPatten = filter.getLikeMatchPatten();
		Object propertyValue = filter.getPropertyValue();
		
		if (MatchType.EQ.equals(matchType)) {
			queryBuilder.append(Constants.HQL_PLACEHOLDER_EQUALITY);
		} else if (MatchType.LIKE.equals(matchType)) {
			queryBuilder.append(Constants.HQL_KEYWORD_LIKE);
			StringBuffer sbPatten = new StringBuffer();
			if(LikeMatchPatten.ALL.equals(likeMatchPatten)) {
				sbPatten.append(Constants.HQL_OPERATOR_PERCENT).append(propertyValue).append(Constants.HQL_OPERATOR_PERCENT);
			} else if(LikeMatchPatten.P.equals(likeMatchPatten)) {
				sbPatten.append(Constants.HQL_OPERATOR_PERCENT).append(propertyValue);
			} else if(LikeMatchPatten.S.equals(likeMatchPatten)) {
				sbPatten.append(propertyValue).append(Constants.HQL_OPERATOR_PERCENT);
			}
			propertyValue = sbPatten.toString();
		} else if (MatchType.LE.equals(matchType)) {
			queryBuilder.append(Constants.HQL_OPERATOR_LE);
		} else if (MatchType.LT.equals(matchType)) {
			queryBuilder.append(Constants.HQL_OPERATOR_LT);
		} else if (MatchType.GE.equals(matchType)) {
			queryBuilder.append(Constants.HQL_OPERATOR_GE);
		} else if (MatchType.GT.equals(matchType)) {
			queryBuilder.append(Constants.HQL_OPERATOR_GT);
		}
		
		if(PropertyType.S.getValue().equals(filter.getPropertyType())) {
			queryBuilder.append(Constants.HQL_PLACEHOLDER_APOSTROPHE);
			queryBuilder.append(propertyValue);
			queryBuilder.append(Constants.HQL_PLACEHOLDER_APOSTROPHE);
		} else {
			queryBuilder.append(propertyValue);
		}
	}
	
	/**
	 * 
	 * Build predicates, Using target class and propertyFilters.
	 * 
	 * @param targetClass
	 * @param criteriaBuilder
	 * @param criteriaQuery
	 * @param entity
	 * @param entityType
	 * @param isDistinct
	 * @param filters
	 * @return
	 * 
	 */
	public static Predicate[] buildPropertyFilterPredicates(final Class<?> targetClass,
			final CriteriaBuilder criteriaBuilder, final CriteriaQuery<?> criteriaQuery, final Root<?> entity,
			EntityType<?> entityType, final boolean isDistinct, final List<PropertyFilter> filters) {

		List<Predicate> predicateList = new ArrayList<Predicate>();
		
		for (PropertyFilter filter : filters) {
			if (!filter.isMultiProperty()) { 
				Predicate predicate = buildPropertyFilterPredicate(targetClass, criteriaBuilder, criteriaQuery, entity,
						entityType, isDistinct, filter.getPropertyName(), filter.getPropertyValue(), filter.getMatchType(), filter.getLikeMatchPatten());
				predicateList.add(criteriaBuilder.and(predicate));
			} else {
				List<Predicate> multiPropertiesPredicateList = new ArrayList<Predicate>();
				for (String param : filter.getPropertyNames()) {
					Predicate predicate = buildPropertyFilterPredicate(targetClass, criteriaBuilder, criteriaQuery, entity,
							entityType, isDistinct, param, filter.getPropertyValue(), filter.getMatchType(), filter.getLikeMatchPatten());
					multiPropertiesPredicateList.add(predicate);
				}
				predicateList.add(criteriaBuilder.or(multiPropertiesPredicateList.toArray(new Predicate[multiPropertiesPredicateList.size()])));
			}
		}

		return predicateList.toArray(new Predicate[predicateList.size()]);
	}

	/**
	 * 
	 * Build predicate, Using target class and propertyFilter's value.
	 * 
	 * @param targetClass
	 * @param criteriaBuilder
	 * @param criteriaQuery
	 * @param entity
	 * @param entityType
	 * @param isDistinct
	 * @param propertyName
	 * @param propertyValue
	 * @param matchType
	 * @param likeMatchPatten
	 * @return
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static Predicate buildPropertyFilterPredicate(final Class<?> targetClass, final CriteriaBuilder criteriaBuilder,
			final CriteriaQuery<?> criteriaQuery, final Root entity, EntityType<?> entityType,
			final boolean isDistinct, final String propertyName, final Object propertyValue, final MatchType matchType, 
			final LikeMatchPatten likeMatchPatten) {

		Assert.hasText(propertyName, "propertyName cannot be null!");
		
		Predicate predicate = null;
		Expression expression = (Expression) entity.get(entityType.getSingularAttribute(propertyName));
		
		try {
			if (MatchType.EQ.equals(matchType)) {
				predicate = criteriaBuilder.equal(expression, propertyValue);
			} else if (MatchType.LIKE.equals(matchType)) {
				StringBuffer sbPatten = new StringBuffer();
				if(LikeMatchPatten.ALL.equals(likeMatchPatten)) {
					sbPatten.append(Constants.HQL_OPERATOR_PERCENT).append(propertyValue).append(Constants.HQL_OPERATOR_PERCENT);
				} else if(LikeMatchPatten.P.equals(likeMatchPatten)) {
					sbPatten.append(Constants.HQL_OPERATOR_PERCENT).append(propertyValue);
				} else if(LikeMatchPatten.S.equals(likeMatchPatten)) {
					sbPatten.append(propertyValue).append(Constants.HQL_OPERATOR_PERCENT);
				}
				predicate = criteriaBuilder.like(expression, sbPatten.toString());
			} else if (MatchType.LE.equals(matchType)) {
				predicate = criteriaBuilder.le(expression, NumberUtils.createNumber(String.valueOf(propertyValue)));
			} else if (MatchType.LT.equals(matchType)) {
				predicate = criteriaBuilder.lt(expression, NumberUtils.createNumber(String.valueOf(propertyValue)));
			} else if (MatchType.GE.equals(matchType)) {
				predicate = criteriaBuilder.ge(expression, NumberUtils.createNumber(String.valueOf(propertyValue)));
			} else if (MatchType.GT.equals(matchType)) {
				predicate = criteriaBuilder.gt(expression, NumberUtils.createNumber(String.valueOf(propertyValue)));
			}
		} catch (Exception ex) {
			ThrowableHandler.handleThrow(ex, logger);
		}

		return predicate;
	}
	
	private static final transient Logger logger = LoggerFactory.getLogger(PersistenceUtils.class);
}
