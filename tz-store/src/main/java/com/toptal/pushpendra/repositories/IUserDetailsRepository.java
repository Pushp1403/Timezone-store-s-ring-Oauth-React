package com.toptal.pushpendra.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.toptal.pushpendra.entities.UserDetail;

@Repository
public interface IUserDetailsRepository extends JpaRepository<UserDetail, String>, JpaSpecificationExecutor<UserDetail> {

}
