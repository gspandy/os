package n.service.authc;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import n.core.service.support.IGenericService;
import n.entity.authc.Layer;
import n.entity.authc.Layer.LayerState;
/**
 * 分组服务
 * @author jt_wangshuiping @date 2016年11月23日
 *
 */
public interface ILayerService extends IGenericService<Layer, Integer>{

	public Layer findById(Integer id);
	
	public Layer findByLayerName(String layerName);
	
	public Layer findByLayerName(String layerName, Integer id, Integer companyId);
	
	public Layer findByCode(String code);
	
	public void forbid(Integer id, LayerState state) throws Exception;

	public Page<Layer> findByCompany(Integer companyId, Pageable pageable);
	/**
	 * 查询公司所有客服分组
	 * @param companyId
	 * @return
	 */
	public List<Layer> findAllLayerByCID(Integer companyId);
}
