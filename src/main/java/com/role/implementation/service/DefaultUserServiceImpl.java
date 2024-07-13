package com.role.implementation.service;

import java.util.*;
import java.util.stream.Collectors;


import com.role.implementation.model.Report;
import com.role.implementation.repository.ReportRepository;
import org.apache.catalina.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.role.implementation.DTO.UserRegisteredDTO;
import com.role.implementation.model.Role;
import com.role.implementation.model.User;
import com.role.implementation.repository.RoleRepository;
import com.role.implementation.repository.UserRepository;

import javax.transaction.Transactional;

@Service
public class DefaultUserServiceImpl implements DefaultUserService{
   @Autowired
	private UserRepository userRepo;
   @Autowired
    private RoleRepository roleRepo;
   @Autowired
   private ReportRepository reportRepository;
   
	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


	public List<User> getFarmersByCollectorId(int collectorId) {
		return userRepo.findBySelectedMilkCollector(collectorId);
	}

	@Override
	public List<User> getUsersByRole(int  role) {
		return userRepo.findByRole(role);
	}

	@Override
	public User findUserById(int id) throws Exception{
		Optional<User> optionalUser=userRepo.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new Exception("user not found");
    }

	@Override
	public void removeMilkCollector(int milkCollectorId) {

	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	
		User user = userRepo.findByEmail(email);
		if(user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRole()));		
	}
	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles){
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList());
	}

	@Override
	public User save(User user) {
		Role role = new Role();
		if(user.getRole().equals("USER"))
			role = roleRepo.findByRole("USER");
		else if(user.getRole().equals("ADMIN"))
			role = roleRepo.findByRole("ADMIN");
		User user1 = new User();
		user1.setEmail(user.getEmail());
		user1.setName(user.getName());
		user1.setLocation(user.getLocation());
		user1.setMobileNo(user.getMobileNo());
		user1.setPassword(passwordEncoder.encode(user.getPassword()));
		user1.setRole(role);
		user1.setSelectedMilkCollector(user.getSelectedMilkCollector());
		user1.setMilkUnitsPerDay(user.getMilkUnitsPerDay());
		return userRepo.save(user);
	}

	@Override
	public User save(UserRegisteredDTO userRegisteredDTO) {
		Role role = new Role();
		if(userRegisteredDTO.getRole().equals("USER"))
		  role = roleRepo.findByRole("USER");
		else if(userRegisteredDTO.getRole().equals("ADMIN"))
		 role = roleRepo.findByRole("ADMIN");
		User user = new User();
		user.setEmail(userRegisteredDTO.getEmail_id());
		user.setName(userRegisteredDTO.getName());
		user.setLocation(userRegisteredDTO.getLocation());
		user.setMobileNo(userRegisteredDTO.getMobile_no());

		user.setPassword(passwordEncoder.encode(userRegisteredDTO.getPassword()));
		user.setRole(role);
		
		return userRepo.save(user);
	}

	@Transactional
	public void updateSelectedMilkCollector(int milkCollectorId) {
		// Assuming you have a method to get the current user
		User currentUser = getCurrentUser();
		if(currentUser.getSelectedMilkCollector()==0)
		{
			currentUser.setSelectedMilkCollector(milkCollectorId);
		}

		userRepo.save(currentUser);
	}

	public User getCurrentUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		return userRepo.findByEmail(username);
	}

	@Override
	@Transactional
	public void saveReport(String date, int milkUnits, int pricePerLiter, int amount) {
		Report report = new Report();
		report.setDate(date);
		report.setMilkUnits(milkUnits);
		report.setPricePerLiter(pricePerLiter);
		report.setAmount(amount);
		reportRepository.save(report);

	}

	@Transactional
	public void removeMilkCollector(Long milkCollectorId) {
		// Assuming you have a method to get the current user
		User currentUser = getCurrentUser();
		currentUser.setSelectedMilkCollector(0);
		userRepo.save(currentUser);
	}
}
