package com.toptal.pushpendra.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.toptal.pushpendra.entities.TimeZone;

@Repository
public interface ITimeZoneRepository extends JpaRepository<TimeZone, Integer>, JpaSpecificationExecutor<TimeZone> {

}
