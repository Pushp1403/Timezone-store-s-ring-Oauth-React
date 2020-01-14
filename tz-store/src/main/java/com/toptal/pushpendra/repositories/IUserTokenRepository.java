package com.toptal.pushpendra.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.toptal.pushpendra.entities.UserToken;

@Repository
public interface IUserTokenRepository extends JpaRepository<UserToken, Integer> {

	void deleteByUsernameAndToken(String username, String token);

	boolean existsByUsernameAndToken(String username, String jwtToken);

	UserToken findByUsername(String username);

}
