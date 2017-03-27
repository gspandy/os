package n.service.info;

import n.core.service.support.IGenericService;
import n.entity.info.AccessRecord;
/**
 * 访客记录服务层
 * @author jt_wangshuiping
 * @date 2016-10-21
 * @version 3.0
 */
public interface IAccessRecordService extends IGenericService<AccessRecord, Integer> {

	public AccessRecord findById(Integer id);
	
	
}
