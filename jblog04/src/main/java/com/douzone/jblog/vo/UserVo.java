package com.douzone.jblog.vo;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

public class UserVo {
	
	@NotEmpty 
	@Length(min=2, max=8)
	private String id;
	@NotEmpty 
	@Length(min=2, max=8)
	private String name;
	@NotEmpty
//	@Pattern(regexp="[a-zA-Z0-1]{6,14}", flag="")
	@Length(min=4, max=16)
	private String password;
	private String role;
	private String joinDate;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}
	@Override
	public String toString() {
		return "UserVo [id=" + id + ", name=" + name + ", password=" + password + ", role=" + role + ", joinDate=" + joinDate + "]";
	}
	
	
	

}
