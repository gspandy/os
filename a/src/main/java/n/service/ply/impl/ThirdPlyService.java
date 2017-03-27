package n.service.ply.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import n.core.jutils.bean.BeanMapper;
import n.core.repository.support.GenericRepository;
import n.core.service.support.GenericService;
import n.entity.ply.ThirdPly;
import n.repository.ply.IThirdPlyRepository;
import n.service.ply.IThirdPlyService;
import n.table.dto.ply.ThirdPlyDTO;

/**
 * 第三方支付信息服务实现
 * 
 * @author onsoul
 * @version 1.0 2015-04-27
 * 
 */

@Service
public class ThirdPlyService extends GenericService<ThirdPly, Integer> implements IThirdPlyService {

	public ThirdPlyService() {
		super(ThirdPly.class);
	}

	@Autowired
	public IThirdPlyRepository repository;

	@Override
	protected GenericRepository<ThirdPly, Integer> getRepository() {
		return repository;
	}

	@Override
	public List<ThirdPlyDTO> findToShow() {
		List<ThirdPly> result = findAll();
		if (!result.isEmpty()) {
			List<ThirdPlyDTO> shower = Arrays.asList();
			BeanMapper.map(findAll(), shower);
			return shower;
		}
		return null;
	}

}
