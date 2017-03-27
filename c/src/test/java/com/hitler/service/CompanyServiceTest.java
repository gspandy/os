package com.hitler.service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.alibaba.fastjson.JSON;
import com.hitler.entity.authc.Company;
import com.hitler.entity.authc.Company_;
import com.hitler.service.authc.ICompanyService;
import com.hitler.web.test.BaseTest;

public class CompanyServiceTest extends BaseTest{

	@Resource
	private ICompanyService companyService;
	
	@Test
	public void exists(){
		boolean b = companyService.findByCompanyName("久泰") == null;
		assert b == true;
	}
	@Test
	public void findall(){
		Pageable p = new PageRequest(0, 10);
		Specification<Company> spec = new Specification<Company>() {
			@Override
			public Predicate toPredicate(Root<Company> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.notEqual(root.get(Company_.cState),"del");
			}
		};
		System.err.println(JSON.toJSONString(companyService.findAll(spec, p)));
	}

	
}
