package n.service.authc.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import n.core.repository.support.GenericRepository;
import n.core.service.support.GenericService;
import n.entity.authc.UserChatShare;
import n.repository.authc.IUserChatShareRepository;
import n.service.authc.IUserChatShareService;
@Service
public class UserChatShareService extends GenericService<UserChatShare, Long> implements IUserChatShareService {
	
	
	public UserChatShareService() {
		super(UserChatShare.class);
	}

	@Autowired
	public IUserChatShareRepository repository;

	@Override
	protected GenericRepository<UserChatShare, Long> getRepository() {
		return repository;
	}

	@Override
	@Transactional
	public int deleteUserShare(Long userId, Long shareUserId) {
		// TODO Auto-generated method stub
		return repository.deleteUserShare(userId, shareUserId);
	}

}
