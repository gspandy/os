package n.service.play;

import java.util.List;

import n.core.service.support.IGenericService;
import n.entity.play.BetRecord;
import n.entity.play.LN;
import n.table.dto.play.BetRecordDTO;

/**
 * 投注记录
 * @author jt_wangshuiping @date 2017年1月9日
 *
 */
public interface IBetRecordService extends IGenericService<BetRecord, Long> {
	
	/**
	 * 查找某一期所有单号
	 * @param ln
	 * @return
	 */
	List<BetRecord> findAllByLN(LN ln);
	
	boolean bet(BetRecordDTO dto);
	
}
