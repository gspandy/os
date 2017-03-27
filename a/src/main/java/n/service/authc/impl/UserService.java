package n.service.authc.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import n.core.dto.support.GenericDTO;
import n.core.jutils.bean.BeanMapper;
import n.core.repository.support.GenericRepository;
import n.core.service.support.GenericService;
import n.entity.authc.Company;
import n.entity.authc.Layer;
import n.entity.authc.User;
import n.entity.authc.UserChatShare;
import n.entity.authc.User.AccountState;
import n.entity.authc.User.OnlineState;
import n.repository.authc.IUserRepository;
import n.service.authc.IUserService;
import n.table.dto.authc.UserDTO;

/**
 * 用户
 * 
 * @author
 * @version 1.0 2015-04-27
 */

@Service
public class UserService extends GenericService<User, Long> implements IUserService {

	public UserService() {
		super(User.class);
	}

	@Autowired
	public IUserRepository repository;

	@Override
	protected GenericRepository<User, Long> getRepository() {
		return repository;
	}

	@Override
	public User authc(String tname, String tpwd) {
		return repository.findAccountAndPwd(tname, tpwd);
	}

	@Override
	public UserDTO findByID(Long id) {
		User user = find(id);
		if (user != null) {
			UserDTO dto = BeanMapper.map(user, UserDTO.class);
			return dto;
		}
		return null;
	}

	@Override
	public void register(GenericDTO<Long> userCrateDTO) throws Exception {
		/**
		 * 1.先保存公司信息得到id 2.然后set进user，保存user 3.进行默认角色，权限设置 4.保存User Role，Role
		 * Permission关系
		 */
		this.save(userCrateDTO);
	}

	@Override
	public List<User> findByCompany(Company company) {
		return repository.findByCompany(company);
	}

	@Override
	public List<User> findByCompanyId(Integer companyId) {
		return repository.findByCompanyId(companyId);
	}

	@Override
	public User findByAccount(String token_uname) {
		return repository.findByAccount(token_uname);
	}

	@Override
	public boolean ping() {
		log.info("##服务成功ping通!");
		return true;
	}

	@Override
	@Transactional
	public User online(User user, OnlineState state) throws Exception {
		user.setOnlineState(state);
		return update(user);
	}

	@Override
	public User offline(Long uid)throws Exception {
		User user = find(uid);
		if (null != user) {
			online(user, OnlineState.off);
		}
		return null;
	}

	@Override
	public OnlineState checkState(Long id) {
		return this.find(id).getOnlineState();
	}

	@Override
	@Transactional
	public boolean locked(Long id, AccountState state) {
		if (repository.locked(id, state) > 0)
			return true;
		return false;
	}

	@Override
	public Page<User> findByCompanyId(Integer companyId, Long uid, Pageable pageable) {
		return repository.findByCompanyId(companyId, uid, pageable);
	}

	@Override
	@Transactional
	public void allot(Long id, boolean state) throws Exception {
		repository.allot(id, state);
	}

	@Override
	public List<Layer> findLayerByCompanyId(Integer companyId) {
		return repository.findLayerByCompanyId(companyId);
	}

	@Override
	@Transactional
	public void updateUserLayer(Long uid, Integer layerid) {
		repository.updateUserLayer(uid, layerid);
	}

	@Override
	public List<UserDTO> findUserListByCID(Integer companyId) {
		List<UserDTO> users = BeanMapper.map(repository.findByCompanyId(companyId), UserDTO.class);
		return users;
	}

	@Override
	public List<User> findByLayerIdAndCompanyId(Integer layerId, Integer companyId) {
		return repository.findUserByLayerIdAndCompanyId(layerId, companyId);
	}

	@Override
	@Transactional
	public User allotCustomer(Integer companyId, OnlineState onlineState) {
		List<User> users = repository.allotCustomer(companyId, onlineState);
		try {
			if (null != users && users.size() > 0) {
				User user = users.get(0);
				user.setCurReceiveNum(user.getCurReceiveNum() + 1);
				repository.updateCurReceiveNum(user.getCurReceiveNum(), user.getId());
				// update(user);
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	@Override
	@Transactional
	public User allotCustomerByLayer(Integer companyId, OnlineState onlineState, Integer layerId) { // 查找分组的客服
		List<User> users = repository.allotCustomerByLayerId(companyId, onlineState, layerId);
		try {
			if (null != users && users.size() > 0) {
				User user = users.get(0);
				user.setCurReceiveNum(user.getCurReceiveNum() + 1);
				// update(user);
				repository.updateCurReceiveNum(user.getCurReceiveNum(), user.getId());
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	@Override
	@Transactional
	public User changeReceive(Long id, Integer num) throws Exception {
		User user = find(id);
		Integer rec = user.getCurReceiveNum() - num;
		if (rec >= 0)
			user.setCurReceiveNum(rec);
		return update(user);
	}

	@Override
	@Transactional
	public void clearCurReceiveNum(Long id) throws Exception {
		repository.updateCurReceiveNum(0, id);
	}

	@Override
	public List<UserDTO> findUserByLayerIdAndCompanyIdetc(Integer layerId, Integer companyId, Long uid) {
		List<User> users = repository.findUserByLayerIdAndCompanyIdetc(layerId, companyId, uid);
		return BeanMapper.map(users, UserDTO.class);
	}

	@Override
	@Transactional
	public User addReceive(Long id, Integer num) throws Exception {
		User user = find(id);
		Integer rec = user.getCurReceiveNum() + num;
		if (rec >= 0 && rec <= user.getReceiveNum())
			user.setCurReceiveNum(rec);
		return update(user);
	}

	@Override
	public List<UserChatShare> getUserChatShare(Long userId) {
		return repository.getUserChatShare(userId);
	}
	
	@Override
	public List<UserChatShare> getUserChatShareMsgRecord(Long userId) {
		
		return repository.getUserChatShareMsgRecord(userId);
	}

	@Override
	public User findByNickName(String nickName) {
		return repository.findByNickName(nickName);
	}

	@Override
	public User findByNickNameAndNotUserId(String nickName, Long userId) {
		return repository.findUser(nickName, userId);
	}

	@Override
	@Transactional
	public boolean updateLayer(Layer layer, Long uid) throws Exception {
		return repository.updateLayer(layer, uid) > 0;
	}

}
