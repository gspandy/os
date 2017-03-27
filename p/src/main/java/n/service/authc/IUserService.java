package n.service.authc;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import n.core.dto.support.GenericDTO;
import n.core.service.support.IGenericService;
import n.entity.authc.Company;
import n.entity.authc.Layer;
import n.entity.authc.User;
import n.entity.authc.UserChatShare;
import n.entity.authc.User.AccountState;
import n.entity.authc.User.OnlineState;
import n.table.dto.authc.UserDTO;


/**
 * 会员服务
 * @author  jtwise
 * @date 2016年7月19日 上午11:08:46
 * @verion 1.0
 */
public interface IUserService extends IGenericService<User, Long> {

	/*注册*/
	public void register(GenericDTO<Long> userCrateDTO) throws Exception;
	
	/*认证*/
	public User authc(String uname,String tpwd);
	
	/*拿取基本信息*/
	public UserDTO findByID(Long id);
	/**
	 * 查询公司对应所有用户
	 * @param company
	 * @return
	 */
	public List<User> findByCompany(Company company);
	/**
	 * companyId查询公司对应所有用户
	 * @param companyId
	 * @return
	 */
	public List<User> findByCompanyId(Integer companyId);
	public Page<User> findByCompanyId(Integer companyId, Long uid,Pageable pageable);

	public User findByAccount(String token_uname);

	public boolean ping();
	
	/**
	 * change user online
	 * @param user
	 * @param state
	 * @return
	 */
	public User online(User user,OnlineState state)throws Exception;
	
	public User offline(Long uid)throws Exception;
	
	public OnlineState checkState(Long id);
	
	/**
	 * 启用/禁用
	 * @param id
	 * @param state
	 * @return
	 */
	public boolean locked(Long id, AccountState state);
	
	/**
	 * 客服自动分配
	 * @param id
	 * @param state
	 * @throws Exception
	 */
	public void allot(Long id, boolean state) throws Exception;
	/**
	 * 公司id查找分组
	 * @param companyId
	 * @return
	 */
	public List<Layer> findLayerByCompanyId(Integer companyId);
	
	public List<UserDTO> findUserListByCID(Integer companyId);
	
	public void updateUserLayer(Long uid, Integer layerid);
	/**
	 * 查询公司分组下所有用户
	 * @param layerId
	 * @return
	 */
	public List<User> findByLayerIdAndCompanyId(Integer layerId, Integer companyId);
	/**
	 * 分配客服
	 * @param companyId
	 * @param onlineState
	 * @return
	 */
	public User allotCustomer(Integer companyId, OnlineState onlineState);
	
	public User allotCustomerByLayer(Integer companyId, OnlineState onlineState,Integer layerId);
	
	/**
	 * 
	 * @param id
	 * @param num 减少服务客服数量
	 * @return
	 * @throws Exception
	 */
	public User changeReceive(Long id, Integer num) throws Exception;
	/**
	 * 
	 * @param id
	 * @param num 增加服务客服数量
	 * @return
	 * @throws Exception
	 */
	public User addReceive(Long id, Integer num) throws Exception;
	/**
	 * 清零当前分配数
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public void clearCurReceiveNum(Long id) throws Exception;
	/**
	 * 可移交的客服列表
	 * @param layerId
	 * @param companyId
	 * @param uid
	 * @return
	 */
	public List<UserDTO> findUserByLayerIdAndCompanyIdetc(Integer layerId, Integer companyId, Long uid);
	
	/**
	 * 查看用户同分组下的共享用户
	 * @param userId
	 * @return
	 */
	public List<UserChatShare> getUserChatShare(Long userId); 
	
	/**
	 * 查共享用户的记录
	 * @param userId
	 * @return
	 */
	public List<UserChatShare> getUserChatShareMsgRecord(Long userId);

	public User findByNickName(String nickName);

	public User findByNickNameAndNotUserId(String nickName, Long userId);
	
	public boolean updateLayer(Layer layer, Long uid) throws Exception;
}
