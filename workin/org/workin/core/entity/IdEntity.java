package org.workin.core.entity;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.workin.core.entity.support.ASCComparator;
import org.workin.core.entity.support.DESCComparator;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
@SuppressWarnings("serial")
@MappedSuperclass
public abstract class IdEntity implements Serializable, Idable {

	private Long id;
	
	@Transient
	private Serializable dynCompareField;

	private long version;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Transient
	public Serializable getDynCompareField() {
		return dynCompareField;
	}
	
	public void setDynCompareField(Serializable dynCompareField) {
		this.dynCompareField = dynCompareField;
	}

	@Version
	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}
	
	@Transient
	public void setDynCompareValue(){
		this.setDynCompareField(id);
	}
	
	// For all subClass use same logic ascComparator
	public static final ASCComparator ascComparator = ASCComparator.getInstance();
	
	// For all subClass use same logic descComparator
	public static final DESCComparator descComparator = DESCComparator.getInstance();
	
//	@Override
//	public String toString() {
//		return ToStringBuilder.reflectionToString(this);
//	}
}
