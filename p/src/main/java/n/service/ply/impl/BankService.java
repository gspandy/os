package n.service.ply.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import n.core.repository.support.GenericRepository;
import n.core.service.support.GenericService;
import n.entity.ply.Bank;
import n.repository.ply.IBankRepository;
import n.service.ply.IBankService;

/**
 * 银行服务实现
 * @author onsoul
 * @version 1.0 2015-04-27
 * 
 */

@Service
public class BankService extends GenericService<Bank, Integer> implements IBankService {

	public BankService() {
		super(Bank.class);
	}

	@Autowired
	public IBankRepository repository;

	@Override
	protected GenericRepository<Bank, Integer> getRepository() {
		return repository;
	}

}
