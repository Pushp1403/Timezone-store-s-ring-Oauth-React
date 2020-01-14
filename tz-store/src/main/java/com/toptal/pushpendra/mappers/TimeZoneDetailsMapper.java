package com.toptal.pushpendra.mappers;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.toptal.pushpendra.entities.TimeZone;
import com.toptal.pushpendra.models.TimeZoneBO;
import com.toptal.pushpendra.models.TimeZoneFilterModel;

@Component
public class TimeZoneDetailsMapper {

	public TimeZone timeZoneBOtoTimeZoneEntity(TimeZoneBO tzEntry) {
		TimeZone tzEntity = new TimeZone();
		tzEntity.setCity(tzEntry.getCity());
		tzEntity.setName(tzEntry.getName());
		tzEntity.setDifferenceFromGMT(tzEntry.getDifferenceFromGMT());
		tzEntity.setTimeZoneRegion(tzEntry.getTimeZoneRegion());
		if (null == tzEntry.getTimeZoneId()) {
			tzEntity.setCreatedAt(new Timestamp(new Date().getTime()));
			tzEntity.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
			tzEntity.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		} else {
			tzEntity.setUpdatedAt(new Timestamp(new Date().getTime()));
			tzEntity.setUpdatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
			tzEntity.setTimeZoneId(tzEntry.getTimeZoneId());
			tzEntity.setUsername(tzEntry.getUsername());
			tzEntity.setCreatedAt(tzEntry.getCreatedAt());
			tzEntity.setCreatedBy(tzEntry.getCreatedBy());

		}
		return tzEntity;
	}

	public TimeZoneBO timeZoneEntityToTimeZoneBO(TimeZone timeZone) {
		TimeZoneBO bo = new TimeZoneBO();
		bo.setCity(timeZone.getCity());
		bo.setCreatedAt(timeZone.getCreatedAt());
		bo.setCreatedBy(timeZone.getCreatedBy());
		bo.setDifferenceFromGMT(timeZone.getDifferenceFromGMT());
		bo.setName(timeZone.getName());
		bo.setTimeZoneId(timeZone.getTimeZoneId());
		bo.setUpdatedAt(timeZone.getUpdatedAt());
		bo.setUpdatedBy(timeZone.getUpdatedBy());
		bo.setUsername(timeZone.getUsername());
		bo.setTimeZoneRegion(timeZone.getTimeZoneRegion());
		return bo;
	}

	public TimeZoneFilterModel createFilterModel(Map<String, String> requestParams) {
		TimeZoneFilterModel model = new TimeZoneFilterModel(requestParams);
		return model;
	}

	public void mergeWithExisting(TimeZoneBO tzEntry, TimeZone existingTimeZone) {
		tzEntry.setCreatedAt(existingTimeZone.getCreatedAt());
		tzEntry.setCreatedBy(existingTimeZone.getCreatedBy());
	}

}
