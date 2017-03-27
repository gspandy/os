package n.service.authc.impl;

 
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import n.core.repository.support.GenericRepository;
import n.core.service.support.GenericService;
import n.entity.authc.Company;
import n.entity.authc.Company_;
import n.entity.authc.User.AccountState;
import n.repository.authc.ICompanyRepository;
import n.repository.authc.IUserRepository;
import n.service.authc.ICompanyService;
import n.table.dto.authc.CompanyDTO;
/**
 * 公司服务实现
 * @author jt_wangshuiping
 * @date 2016-10-21
 * @version 3.0
 */
@Service
public class CompanyService extends GenericService<Company, Integer> implements ICompanyService{

	public CompanyService() {
		super(Company.class);
	}
	
	@Resource
	private ICompanyRepository repository;
	@Resource
	private IUserRepository userRepository;

	@Override
	public Company findById(Integer id) {
		return repository.findById(id);
	}

	@Override
	protected GenericRepository<Company, Integer> getRepository() {
		return repository;
	}

	@Override
	public boolean exists(String companyName){
		return repository.findByCompanyName(companyName) == null ? false : true;
	}

	@Override
	public Company findByCompanyName(String companyName) {
		return repository.findByCompanyName(companyName);
	}

	@Override
	@Transactional
	public boolean isEnable(boolean enable, List<Integer> ids) {
		for (Integer id : ids) {
			Company c = this.find(id);
			AccountState t = AccountState.normal;
			if(!enable){
				t = AccountState.lock;
			}
			userRepository.locked(userRepository.findByAccount(c.getCompanyAccount()).getId(), t);
		}
		int i = repository.isEnable(enable,ids);
		if(i>0)
			return true;
		return false;
	}

	@Override
	@Transactional
	public void delCompany(Integer id) throws Exception {
		repository.delCompany(id);
	}

	@Override
	public Page<Company> findNotDel(CompanyDTO companyDto, Pageable pageable) {
		Specification<Company> spec = new Specification<Company>() {
			@Override
			public Predicate toPredicate(Root<Company> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.notEqual(root.get(Company_.cState),companyDto.getcState());
			}
		};
		return findAll(spec, pageable);
	}

}
