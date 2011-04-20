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
@MappedSuperclass
public abstract class IdEntity implements Serializable, Idable {

	private static final long serialVersionUID = 8276131316287730389L;

	private Long id;

	@Transient
	private Serializable dynCompareField;

	private long version;

	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	@Transient
	public Serializable getDynCompareField() {
		return dynCompareField;
	}

	@Override
	public void setDynCompareField(Serializable dynCompareField) {
		this.dynCompareField = dynCompareField;
	}

	@Override
	@Version
	public long getVersion() {
		return version;
	}

	@Override
	public void setVersion(long version) {
		this.version = version;
	}

	@Override
	@Transient
	public void setDynCompareValue() {
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
