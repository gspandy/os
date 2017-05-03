package com.wise.service.sider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wise.core.repository.support.GenericRepository;
import com.wise.core.service.support.GenericService;
import com.wise.entity.sider.Knight;
import com.wise.repository.sider.IKnightRepository;

@Service
public class KnightService extends GenericService<Knight, Integer> implements IKnightService {

	public KnightService() {
		super(Knight.class);
	}

	@Autowired
	private IKnightRepository repository;

	@Override
	protected GenericRepository<Knight, Integer> getRepository() {
		return repository;
	}

}
