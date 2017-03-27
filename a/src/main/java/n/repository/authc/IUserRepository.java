package n.repository.authc;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import n.core.repository.support.GenericRepository;
import n.entity.authc.Company;
import n.entity.authc.Layer;
import n.entity.authc.User;
import n.entity.authc.UserChatShare;
import n.entity.authc.User.AccountState;
import n.entity.authc.User.OnlineState;

/**
 * JPA 用户 核心
 * @author onsoul 2015-6-17 下午10:24:55
 */
public interface IUserRepository extends GenericRepository<User, Long> {
	
	/**
	 * 根据用户与密码(加密串)查找用户
	 * @param tname
	 * @param tpwd
	 * @return
	 */
	@Query("FROM User u WHERE u.account =:tname and u.password =:tpwd")
	public User findAccountAndPwd(@Param("tname") String tname,@Param("tpwd")String tpwd);
	/**
	 * 用户姓名
	 * @param userName
	 * @return
	 */
	public User findByUserName(String userName);
	/**
	 * 账户id
	 * @param account
	 * @return
	 */
	public User findByAccount(String account);
	
	public List<User> findByCompany(Company company);
	
	@Query(value = "FROM User u WHERE u.company.id = :companyId and u.accountState != 2")
	public List<User> findByCompanyId(@Param("companyId")Integer companyId);
	
	@Query(value = "FROM User u WHERE u.company.id = :companyId and u.id != :id and u.accountState != 2")
	public Page<User> findByCompanyId(@Param("companyId")Integer companyId, @Param("id")Long uid, Pageable pageable);
	
	@Query(value = "FROM User u WHERE u.company.id = :companyId and u.accountState != 2")
	public Page<User> findByCompanyId(@Param("companyId")Integer companyId, Pageable pageable);
	
	@Modifying
	@Query(value = "UPDATE User SET accountState=:state where id=:id")
	public int locked(@Param("id")Long id, @Param("state")AccountState state);
	
	@Modifying
	@Query(value = "UPDATE User SET autoAllot=:state where id=:id")
	public void allot(@Param("id")Long id, @Param("state")boolean state);
	
	@Query("SELECT distinct u.layer FROM User u WHERE u.company.id=:companyId")
	public List<Layer> findLayerByCompanyId(@Param("companyId")Integer companyId);
	
	@Modifying
	@Query(value = "UPDATE User SET layer.id=:layerid where id=:id")
	public void updateUserLayer(@Param("id")Long uid, @Param("layerid")Integer layerid);
	
	@Query(value = "FROM User WHERE layer.id=:layerid AND company.id=:companyId")
	public List<User> findUserByLayerIdAndCompanyId(@Param("layerid")Integer layerId, @Param("companyId")Integer companyId);
	
	@Query(value = "FROM User u WHERE u.layer.id=:layerid AND u.company.id=:companyId AND u.onlineState = 1 AND u.curReceiveNum < u.receiveNum and u.id != :uid")
	public List<User> findUserByLayerIdAndCompanyIdetc(@Param("layerid")Integer layerId, @Param("companyId")Integer companyId, @Param("uid") Long uid);
	
	@Query(value = "FROM User u WHERE u.company.id=:companyId AND u.onlineState = :onlineState and u.curReceiveNum < u.receiveNum ORDER BY u.curReceiveNum")
	public List<User> allotCustomer(@Param("companyId")Integer companyId, @Param("onlineState")OnlineState onlineState);
	
	@Query(value = "FROM User u WHERE u.company.id=:companyId AND u.onlineState = :onlineState and u.layer.id=:layerId and u.curReceiveNum < u.receiveNum ORDER BY u.curReceiveNum")
	public List<User> allotCustomerByLayerId(@Param("companyId")Integer companyId, @Param("onlineState")OnlineState onlineState, @Param("layerId")Integer layerId);
	
	@Modifying
	@Query(value = "update User  set  curReceiveNum =:num where id=:uid ")
	public void updateCurReceiveNum(@Param("num")Integer num, @Param("uid")Long uid);
	
	@Query(value = "FROM UserChatShare u WHERE u.userId=:userId")
	public List<UserChatShare> getUserChatShare(@Param("userId")Long userId) ;
	
	@Query(value = "FROM UserChatShare u WHERE u.shareUserId=:userId")
	public List<UserChatShare> getUserChatShareMsgRecord(@Param("userId")Long userId);
	
	public User findByNickName(String nickName);
	
	@Query(value = "FROM User u WHERE u.nickName=:nickName AND u.id !=:uid")
	public User findUser(@Param("nickName")String nickName,@Param("uid") Long uid);
	
	@Modifying
	@Query(value = "UPDATE User SET layer=:layer where id=:uid")
	public int updateLayer(@Param("layer")Layer layer,@Param("uid") Long uid);
}
