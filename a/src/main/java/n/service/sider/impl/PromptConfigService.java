package n.service.sider.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import n.core.repository.support.GenericRepository;
import n.core.service.support.GenericService;
import n.entity.sider.PromptConfig;
import n.entity.sider.PromptConfig.PromptType;
import n.repository.sider.IPromptConfigRepository;
import n.service.sider.IPromptConfigService;
import n.table.dto.sider.PromptConfigCreateDTO;
/**
 * 聊天提示配置服务
 * @author jt_wangshuiping @date 2016年12月8日
 *
 */
@Service
public class PromptConfigService extends GenericService<PromptConfig, Integer> implements IPromptConfigService {
	
	@Resource
	private IPromptConfigRepository repository;

	public PromptConfigService() {
		super(PromptConfig.class);
	}

	@Override
	public PromptConfig findByType(PromptType type) {
		return repository.findByType(type).get(0);
	}

	@Override
	public List<PromptConfig> findListByType(PromptType type) {
		return repository.findByType(type);
	}

	@Override
	@Transactional
	public void add(PromptConfigCreateDTO dto) throws Exception {
		if (dto.getType().equals(PromptType.quick)) {
			super.save(dto);
			return;
		}
		List<PromptConfig> list = repository.findByType(dto.getType());
		if (dto.getType().equals(PromptType.timeout)) {
			if(null != list && list.size() >= 2){
				Iterable<PromptConfig> entities = list;
				repository.deleteInBatch(entities);
			}
			super.save(dto);
			return;
		}
		if (null != list && list.size() > 0) {
			Iterable<PromptConfig> entities = list;
			repository.deleteInBatch(entities);
		}
		super.save(dto);
	}

	@Override
	protected GenericRepository<PromptConfig, Integer> getRepository() {
		return repository;
	}



}
