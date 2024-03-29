package com.role.implementation.service;

import com.role.implementation.model.Role;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.role.implementation.DTO.UserRegisteredDTO;
import com.role.implementation.model.User;

import java.util.List;


public interface DefaultUserService extends UserDetailsService{

	User save(UserRegisteredDTO userRegisteredDTO);
	List<User> getUsersByRole(int  role);
	User getUserByEmail(String email);
	void updateUser(User user); 
}
