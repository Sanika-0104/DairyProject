package com.role.implementation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.role.implementation.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	User findByEmail(String emailId);

	@Query("SELECT u FROM User u " + "JOIN u.roles r " + "WHERE r.id = :roleId")
	List<User> findByRole(@Param("roleId") int roleId);

}
