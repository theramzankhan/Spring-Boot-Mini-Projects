package com.projects.user_service.ValueObject;

import com.projects.user_service.entity.User;

public class ResponseTemplateVo {
	
	private User user;
	private Department department;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	

}
