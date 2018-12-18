package org.alibaba.dao.mapper;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;


public class User {
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date birthday;
	private String name;
	private Integer age;
	private Integer id;
	private String idNumber;
	
	
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	@Override
	public String toString() {
		return "User [birthday=" + birthday + ", name=" + name + ", age=" + age + ", id=" + id + ", idNumber="
				+ idNumber + "]";
	}
	
	
}
