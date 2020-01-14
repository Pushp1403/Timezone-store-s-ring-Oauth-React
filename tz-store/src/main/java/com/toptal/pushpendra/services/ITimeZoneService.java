package com.toptal.pushpendra.services;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.toptal.pushpendra.models.TimeZoneBO;

@Service
public interface ITimeZoneService {

	TimeZoneBO createNewTimeZone(TimeZoneBO tzEntry);

	TimeZoneBO updateTimeZoneInfo(TimeZoneBO tzEntry);

	List<TimeZoneBO> getAllTimeZones(Map<String, String> requestParams);

	void deleteTimeZone(Integer timeZoneId);
}
