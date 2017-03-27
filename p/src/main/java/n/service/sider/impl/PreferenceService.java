package n.service.sider.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import n.core.repository.support.GenericRepository;
import n.core.service.support.GenericService;
import n.entity.sider.Preference;
import n.repository.sider.IPreferenceRepository;
import n.service.sider.IPreferenceService;
/**
 * 消息记录服务层
 * @author jt_wangshuiping
 * @date 2016-10-21
 * @version 3.0
 */
@Service
public class PreferenceService extends GenericService<Preference, Integer> implements IPreferenceService {

	public PreferenceService() {
		super(Preference.class);
	}

	@Resource
	private IPreferenceRepository repository;
	 

	@Override
	protected GenericRepository<Preference, Integer> getRepository() {
		return repository;
	}


	@Override
	public Preference findByCode(String code) {
		return repository.findByCode(code);
	}
}
