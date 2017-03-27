package n.service.authc.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import n.core.repository.support.GenericRepository;
import n.core.service.support.GenericService;
import n.entity.authc.Permission;
import n.entity.authc.Permission_;
import n.repository.authc.IPermissionRepository;
import n.service.authc.IPermissionService;
import n.table.dto.authc.PermissionTreeDTO;

/**
 * 权限基础
 * @author
 * @version 1.0 2015-04-27
 * 
 */
@Service
public class PermissionService extends GenericService<Permission, Integer>
		implements IPermissionService {
	
	public PermissionService() {
		super(Permission.class);
	}

	@Autowired
	public IPermissionRepository repository;

	@Override
	protected GenericRepository<Permission, Integer> getRepository() {
		return repository;
	}

	@Override
	public Permission findByPath(String path) {
		return null;
	}

	@Override
	public List<Permission> findByRoleId(Integer roleId) throws Exception {
		return repository.findByRoleId(roleId);
	}

	@Override
	public List<Permission> findByUserId(Long userId) throws Exception {
		return repository.findByUserId(userId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public boolean insertRolePermission(Integer roleId, Integer permissionId) throws Exception {
		return repository.insertRolePermission(roleId, permissionId) == 0 ? false : true;
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public boolean deleteRolePermission(Integer roleId, Integer permissionId) throws Exception {
		return repository.deleteRolePermission(roleId, permissionId) == 0 ? false : true;
	}

	@Override
	public Set<String> findUserPermissionByUID(Long userId) {
		return repository.findUserPermissionByUID(userId);
	}

	@Override
	public Permission findByCode(String code) {
		return repository.findByCode(code);
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public boolean deleteByRoleId(Integer roleId) throws Exception {
		return repository.deleteRolePermissionByRoleId(roleId) == 0 ? false : true;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void permissionSave(Integer roleId, String permissions) throws Exception {
		repository.deleteRolePermissionByRoleId(roleId);
		String[] pIds =permissions.split(",");
		List<String> idStrList = Arrays.asList(pIds);
		
		List<Integer> idIntList = new ArrayList<Integer>(idStrList.size());
		CollectionUtils.collect(idStrList, new Transformer() {
			@Override
			public Object transform(Object input) {
				return new Integer((String)input);
			}
		}, idIntList);
		List<Permission> permissionList = repository.findAll(new Specification<Permission>() {
			
			@Override
			public Predicate toPredicate(Root<Permission> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return root.in(idIntList);
			}
		});
		if(permissionList.size() >0 && null !=permissionList){
			for(Integer pid: idIntList){
				repository.insertRolePermission(roleId, pid);
			}
		}

		return ;
	}
	
	/**
	 * 权限树
	 */
	public List<PermissionTreeDTO> permissionTree() {
		List<Permission> list = repository.findAll();
		List<PermissionTreeDTO> permissionTreeDTOList = new ArrayList<PermissionTreeDTO>();
		generatePermissionTree(list, permissionTreeDTOList,0);
		
		return permissionTreeDTOList;
	}
	
	private List<PermissionTreeDTO> generatePermissionTree(List<Permission> allList, List<PermissionTreeDTO> dtoList, Integer parentPermissionId){
		if(allList == null || allList.size() == 0)
			return dtoList;
		
		for (Permission p : allList) {
			if(p.getParentPermissionId() == parentPermissionId) {
				PermissionTreeDTO pt = new PermissionTreeDTO(); 
				pt.setId(p.getId());
				pt.setText(p.getPermissionName());
				pt.setDeep(p.getDeep());
				
				// 递归查找子权限
				List<PermissionTreeDTO> subTreeList = generatePermissionTree(allList, new ArrayList<PermissionTreeDTO>(), p.getId());
				pt.setChildren(subTreeList);
				
				dtoList.add(pt);
			}
		}
		
		return dtoList;
		
	}

	@Override
	public List<Permission> findByCodeIn(List<String> codes) {
		return repository.findByCodeIn(codes);
	}

	@Override
	public List<Permission> findBySpec(Integer id) {
		Specification<Permission> spec = new Specification<Permission>() {
			
			@Override
			public Predicate toPredicate(Root<Permission> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get(Permission_.parentPermissionId.getName()), id);
			}
		};
		return repository.findAll(spec);
	}
	
	
	
	
}
