package com.projects.user_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.projects.user_service.ValueObject.Department;
import com.projects.user_service.ValueObject.ResponseTemplateVo;
import com.projects.user_service.entity.User;
import com.projects.user_service.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private UserRepository userRepository;

	public User saveUser(User user) {
		return userRepository.save(user);
	}

	public ResponseTemplateVo getUserWithDepartment(Long userId) {
		ResponseTemplateVo vo = new ResponseTemplateVo();
		User user = userRepository.findByUserId(userId);
		
		Department department = restTemplate.getForObject("http://DEPARTMENT-SERVICE/departments/" + user.getDepartmentId(), Department.class); //using application name instead localhost due to service registry in use 
		vo.setUser(user);
		vo.setDepartment(department);
		
		return vo;
	}

}
