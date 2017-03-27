package n.service.authc.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import n.core.repository.support.GenericRepository;
import n.core.service.support.GenericService;
import n.entity.authc.Layer;
import n.entity.authc.Layer.LayerState;
import n.repository.authc.ILayerRepository;
import n.service.authc.ILayerService;

/**
 * 分组实现
 * 
 * @author jt_wangshuiping @date 2016年11月23日
 *
 */
@Service
public class LayerService extends GenericService<Layer, Integer> implements ILayerService {
	@Resource
	private ILayerRepository repository;

	public LayerService() {
		super(Layer.class);
	}

	@Override
	protected GenericRepository<Layer, Integer> getRepository() {
		return repository;
	}

	@Override
	public Layer findById(Integer id) {
		return repository.findById(id);
	}

	@Override
	public Layer findByLayerName(String layerName) {
		return repository.findByLayerName(layerName);
	}

	@Override
	public Layer findByCode(String code) {
		return repository.findByCode(code);
	}

	@Override
	@Transactional
	public void forbid(Integer id, LayerState state) throws Exception {
		repository.forbid(id, state);
	}

	@Override
	public Page<Layer> findByCompany(Integer companyId, Pageable pageable) {
		return repository.findByCompanyId(companyId, pageable);
	}

	@Override
	public List<Layer> findAllLayerByCID(Integer companyId) {
		return repository.findByCID(companyId);
	}

	@Override
	public Layer findByLayerName(String layerName, Integer id, Integer companyId) {
		return repository.findLayerNameNotId(layerName, id, companyId);
	}

}
