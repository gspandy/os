package n.repository.authc;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import n.core.repository.support.GenericRepository;
import n.entity.authc.UserChatShare;

public interface IUserChatShareRepository extends GenericRepository<UserChatShare, Long> {
	
	@Modifying
	@Query("delete from UserChatShare where userId=:userId and shareUserId=:shareUserId ")
	public int deleteUserShare(@Param("userId")Long userId, @Param("shareUserId")Long shareUserId) ;

}
