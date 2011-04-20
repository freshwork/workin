package org.workin.test.application.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.workin.core.entity.IdEntity;
import org.workin.util.CollectionUtils;
import org.workin.util.DateUtils;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
@Entity
@Table(name = "person")
@NamedQueries({ @NamedQuery(name = "findAllPerson", query = "SELECT p FROM Person p"),
		@NamedQuery(name = "findPersonByName", query = "SELECT p FROM Person p WHERE p.name = :name"),
		@NamedQuery(name = "findPersonByNameExt", query = "SELECT p FROM Person p WHERE p.name = ?"),
		@NamedQuery(name = "update_Person", query = "UPDATE Person SET name = ? WHERE name = ?"),
		@NamedQuery(name = "del_Person", query = "DELETE FROM Person WHERE name = ?") })
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Person extends IdEntity {

	private static final long serialVersionUID = 6060042527393306255L;

	private String name;

	private String sex;

	private Date birthday;

	private Set<Address> address = new LinkedHashSet<Address>();

	private Double sortTest;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public Set<Address> getAddress() {
		return address;
	}

	public void setAddress(Set<Address> address) {
		this.address = address;
	}

	@Column(length = 30)
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Column(length = 20)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(length = 10)
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Transient
	public String getAddressPhone() {
		return CollectionUtils.fetchPropertyToString(address, "phone", ", ");
	}

	@Transient
	public Double getSortTest() {
		return sortTest;
	}

	public void setSortTest(Double sortTest) {
		this.sortTest = sortTest;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	@Transient
	public void setDynCompareValue() {
		this.setDynCompareField(this.sortTest);
	}

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		Person wanggang = new Person();
		wanggang.setId(101L);
		wanggang.setName("c");
		wanggang.setBirthday(DateUtils.getNow());
		wanggang.setSortTest(10.1);

		Person angellin = new Person();
		angellin.setId(78L);
		angellin.setName("b");
		angellin.setBirthday(new Date("2008/12/30"));
		angellin.setSortTest(10.7);

		Person goingmm = new Person();
		goingmm.setId(11L);
		goingmm.setName("a");
		goingmm.setBirthday(new Date("2009/12/30"));
		goingmm.setSortTest(10.3);

		List<Person> list = new ArrayList<Person>();
		list.add(wanggang);
		list.add(angellin);
		list.add(goingmm);

		Collections.sort(list, ascComparator);
		for (Person person : list) {
			System.out.println(" id: " + person.getId() + " name: " + person.getName() + " birthday: "
					+ person.getBirthday() + " sortTest: " + person.getSortTest());
		}
		System.out.println(" ------------------------ ");
		Collections.sort(list, descComparator);
		for (Person person : list) {
			System.out.println(" id: " + person.getId() + " name: " + person.getName() + " birthday: "
					+ person.getBirthday());
		}
	}
}
