package n.repository.authc;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import n.core.repository.support.GenericRepository;
import n.entity.authc.Layer;
import n.entity.authc.Layer.LayerState;

/**
 * 分组
 * @author jt_wangshuiping
 * @date 2016-10-21
 * @version 3.0
 */
public interface ILayerRepository extends GenericRepository<Layer, Integer> {

	public Layer findById(Integer id);
	
	public Layer findByLayerName(String layerName);

	public Layer findByCode(String code);

	@Modifying
	@Query(value="UPDATE Layer SET state = :state WHERE id = :id ")
	public void forbid(@Param("id") Integer id,@Param("state") LayerState state);

	@Query(value="FROM Layer l WHERE l.company.id = :companyId ")
	public Page<Layer> findByCompanyId(@Param("companyId")Integer companyId, Pageable pageable);
	
	@Query(value="FROM Layer l WHERE l.company.id = :companyId and l.state != 'forbid'")
	public List<Layer> findByCID(@Param("companyId")Integer companyId);

	
	@Query(value="FROM Layer l WHERE l.layerName = :layerName and l.id !=:id and l.company.id =:companyId")
	public Layer findLayerNameNotId(@Param("layerName")String layerName, @Param("id") Integer id, @Param("companyId") Integer companyId);

}
