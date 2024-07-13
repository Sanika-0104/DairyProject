package com.role.implementation.service;

import com.role.implementation.model.Role;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.role.implementation.DTO.UserRegisteredDTO;
import com.role.implementation.model.User;

import java.util.List;


public interface DefaultUserService extends UserDetailsService{

	User save(User user);
	User save(UserRegisteredDTO userRegisteredDTO);
	List<User> getUsersByRole(int  role);
	User findUserById(int id) throws Exception;
	void removeMilkCollector(int  milkCollectorId);
	void updateSelectedMilkCollector(int milkCollectorId);

	User getCurrentUser();


	List<User> getFarmersByCollectorId(int collectorId);
	void saveReport(String date,int milkUnits, int pricePerLiter,int amount);


}
