package n.service.play;

import java.util.List;

import n.core.service.support.IGenericService;
import n.entity.play.PlayType;
import n.entity.play.PlayTypeGroup;

/**
 * 玩法服务接口
 * @author jt_wangshuiping @date 2017年1月9日
 *
 */
public interface IPlayTypeService extends IGenericService<PlayType, Integer> {

	List<PlayType> findByGroup(Integer gmgid,Integer ptgid);
	
	List<PlayTypeGroup> findAllGroup();

}
