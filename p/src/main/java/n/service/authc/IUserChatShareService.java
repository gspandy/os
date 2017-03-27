package n.service.authc;

import n.core.service.support.IGenericService;
import n.entity.authc.UserChatShare;

public interface IUserChatShareService extends IGenericService<UserChatShare, Long>{
	
	
	public int deleteUserShare(Long userId,Long shareUserId);

}
