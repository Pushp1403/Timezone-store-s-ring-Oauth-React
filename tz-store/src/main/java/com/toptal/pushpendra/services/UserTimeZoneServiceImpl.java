package com.toptal.pushpendra.services;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.toptal.pushpendra.entities.TimeZone;
import com.toptal.pushpendra.exceptions.EntityNotFoundException;
import com.toptal.pushpendra.mappers.TimeZoneDetailsMapper;
import com.toptal.pushpendra.models.TimeZoneBO;
import com.toptal.pushpendra.models.TimeZoneFilterModel;
import com.toptal.pushpendra.repositories.ITimeZoneRepository;
import com.toptal.pushpendra.utilities.FilterProvider;

@Service
@Transactional
public class UserTimeZoneServiceImpl implements ITimeZoneService {

	@Autowired
	private ITimeZoneRepository timeZoneRepository;

	@Autowired
	private TimeZoneDetailsMapper mapper;

	@Override
	public TimeZoneBO createNewTimeZone(TimeZoneBO tzEntry) {
		TimeZone timeZone = mapper.timeZoneBOtoTimeZoneEntity(tzEntry);
		timeZone = timeZoneRepository.save(timeZone);
		return mapper.timeZoneEntityToTimeZoneBO(timeZone);
	}

	@Override
	public TimeZoneBO updateTimeZoneInfo(TimeZoneBO tzEntry) {
		TimeZone existingTimeZone = timeZoneRepository.getOne(tzEntry.getTimeZoneId());
		mapper.mergeWithExisting(tzEntry, existingTimeZone);
		return this.createNewTimeZone(tzEntry);
	}

	@Override
	public List<TimeZoneBO> getAllTimeZones(Map<String, String> requestParams) {
		TimeZoneFilterModel model = mapper.createFilterModel(requestParams);
		Specification<TimeZone> specification = FilterProvider.timeZoneSpecification(model);

		Pageable page = PageRequest.of(model.getNextPage(), model.getPageSize(),Sort.unsorted());

		Page<TimeZone> timezones = timeZoneRepository.findAll(specification, page);

		if (timezones.hasContent())
			return timezones.stream().map(tz -> {
				return mapper.timeZoneEntityToTimeZoneBO(tz);
			}).collect(Collectors.toList());

		return Collections.emptyList();
	}

	@Override
	public void deleteTimeZone(Integer timeZoneId) {
		timeZoneRepository.findById(timeZoneId)
				.orElseThrow(() -> new EntityNotFoundException(TimeZone.class, "timeZoneId", timeZoneId.toString()));
		timeZoneRepository.deleteById(timeZoneId);
	}

}
