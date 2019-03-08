package com.legend.stock.dbservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.legend.stock.dbservice.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	User findByUsername(String username);
}
