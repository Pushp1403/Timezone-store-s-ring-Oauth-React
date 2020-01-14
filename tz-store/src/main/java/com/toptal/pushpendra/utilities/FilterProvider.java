package com.toptal.pushpendra.utilities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.toptal.pushpendra.entities.TimeZone;
import com.toptal.pushpendra.entities.UserDetail;
import com.toptal.pushpendra.models.TimeZoneFilterModel;
import com.toptal.pushpendra.models.UserFilterModel;

@Component
public class FilterProvider {

	public static Specification<UserDetail> userDetailsSpecification(UserFilterModel model) {
		return new Specification<UserDetail>() {

			private static final long serialVersionUID = -6513770624331930257L;

			@Override
			public Predicate toPredicate(Root<UserDetail> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicate = new ArrayList<>();

				if (null != model.getFirstName() && !model.getFirstName().trim().equals(Constants.BLANK_STRING))
					predicate.add(criteriaBuilder.like(root.get("firstName"), "%"+model.getFirstName()+"%"));

				if (null != model.getLastName() && !model.getLastName().trim().equals(Constants.BLANK_STRING))
					predicate.add(criteriaBuilder.like(root.get("lastName"), "%"+model.getLastName()+"%"));

				if (null != model.getEmailId() && !model.getEmailId().trim().equals(Constants.BLANK_STRING))
					predicate.add(criteriaBuilder.like(root.get("emailId"), "%"+model.getEmailId()+"%"));

				if (null != model.getUsername() && !model.getUsername().trim().equals(Constants.BLANK_STRING))
					predicate.add(criteriaBuilder.like(root.get("username"), "%"+model.getUsername()+"%"));

				return criteriaBuilder.and(predicate.toArray(new Predicate[] {}));
			}
		};
	}

	public static Specification<TimeZone> timeZoneSpecification(TimeZoneFilterModel model) {
		return new Specification<TimeZone>() {

			private static final long serialVersionUID = 3377424783613107762L;

			@Override
			public Predicate toPredicate(Root<TimeZone> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicate = new ArrayList<>();
				if(null != model.getCity() && !model.getCity().trim().equals(Constants.BLANK_STRING))
					predicate.add(criteriaBuilder.equal(root.get("city"), model.getCity()));
				
				if(null != model.getDifferenceFromGMT())
					predicate.add(criteriaBuilder.equal(root.get("differenceFromGMT"), model.getDifferenceFromGMT()));
				
				if(null != model.getTimeZoneId())
					predicate.add(criteriaBuilder.equal(root.get("timeZoneId"), model.getTimeZoneId()));
				
				if(null != model.getUsername() && ! model.getUsername().trim().equals(Constants.BLANK_STRING))
					predicate.add(criteriaBuilder.equal(root.get("username"), model.getUsername()));
				
				if(null != model.getName() && ! model.getName().trim().equals(Constants.BLANK_STRING))
					predicate.add(criteriaBuilder.like(root.get("name"), "%"+model.getName()+"%"));
				if(predicate.size()>0)
					return criteriaBuilder.and(predicate.toArray(new Predicate[] {}));
				return null;
			}
		};
	}

}
