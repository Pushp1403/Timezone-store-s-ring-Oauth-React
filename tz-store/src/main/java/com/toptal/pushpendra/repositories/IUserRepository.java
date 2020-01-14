package com.toptal.pushpendra.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.toptal.pushpendra.entities.User;

@Repository
public interface IUserRepository extends JpaRepository<User, String>{

	Optional<User> findByEmailId(String email);

	boolean existsByEmailId(String email);

}
