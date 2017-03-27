package n.service.info.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import n.core.repository.support.GenericRepository;
import n.core.service.support.GenericService;
import n.entity.info.AccessRecord;
import n.repository.info.IAccessRecordRepository;
import n.service.authc.ICompanyService;
import n.service.authc.IUserService;
import n.service.info.IAccessRecordService;

/**
 * 访客记录服务层实现
 * 
 * @author jt_wangshuiping
 * @date 2016-10-24
 * @version 3.0
 */
@Service
public class AccessRecordService extends GenericService<AccessRecord, Integer> implements IAccessRecordService {

	private Logger log = LoggerFactory.getLogger(AccessRecordService.class);

	public AccessRecordService() {
		super(AccessRecord.class);
	}

	@Resource
	private IAccessRecordRepository repository;

	@Resource
	private ICompanyService companyService;

	@Resource
	private IUserService userService;

	@Override
	public AccessRecord findById(Integer id) {
		return repository.findById(id);
	}

	@Override
	protected GenericRepository<AccessRecord, Integer> getRepository() {
		return repository;
	}

	

}
