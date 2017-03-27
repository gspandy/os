package n.service.ply;

import java.util.List;

import n.core.service.support.IGenericService;
import n.entity.ply.ThirdPly;
import n.table.dto.ply.ThirdPlyDTO;

/**
 * 银行
 * @author onsoul
 *
 */
public interface IThirdPlyService extends IGenericService<ThirdPly, Integer> {
	
	List<ThirdPlyDTO> findToShow();
	
}
