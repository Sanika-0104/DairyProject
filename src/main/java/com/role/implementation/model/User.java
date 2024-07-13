package com.role.implementation.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name;
	private String email;
	private String password;

	private String mobileNo;
	private String location;

	private Double milkUnitsPerDay=0.0;

	private Double ratePerLiter=0.0;
	private int selectedMilkCollector;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "users_role", joinColumns = @JoinColumn(name = "cust_id", referencedColumnName = "id"),
	inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id") )
	Set<Role> roles = new HashSet<Role>();


	public void setMilkUnitsPerDay(Double milkUnitsPerDay) {
		this.milkUnitsPerDay = milkUnitsPerDay;
	}

	public void setRatePerLiter(Double ratePerLiter) {
		this.ratePerLiter = ratePerLiter;
	}

	public int getSelectedMilkCollector() {
		return selectedMilkCollector;
	}

	public void setSelectedMilkCollector(int selectedMilkCollector) {
		this.selectedMilkCollector = selectedMilkCollector;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Set<Role> getRole() {
		return roles;
	}

	public void setRole(Role role) {
		this.roles.add(role);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public double getMilkUnitsPerDay() {
		return milkUnitsPerDay;
	}

	public void setMilkUnitsPerDay(double milkUnitsPerDay) {
		this.milkUnitsPerDay = milkUnitsPerDay;
	}

	public double getRatePerLiter() {
		return ratePerLiter;
	}

	public void setRatePerLiter(double ratePerLiter) {
		this.ratePerLiter = ratePerLiter;
	}



}
