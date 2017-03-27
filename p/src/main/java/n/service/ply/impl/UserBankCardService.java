package n.service.ply.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import n.core.repository.support.GenericRepository;
import n.core.service.support.GenericService;
import n.entity.ply.UserBankCard;
import n.repository.ply.IUserBankCardRepository;
import n.service.ply.IUserBankCardService;

/**
 * 用户银行卡服务实现
 * @author onsoul
 * @version 1.0 2015-04-27
 * 
 */

@Service
public class UserBankCardService extends GenericService<UserBankCard, Long> implements IUserBankCardService {

	public UserBankCardService() {
		super(UserBankCard.class);
	}

	@Autowired
	public IUserBankCardRepository repository;

	@Override
	protected GenericRepository<UserBankCard, Long> getRepository() {
		return repository;
	}

}
