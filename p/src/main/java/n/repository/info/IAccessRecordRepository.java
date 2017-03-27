package n.repository.info;

import n.core.repository.support.GenericRepository;
import n.entity.info.AccessRecord;
/**
 * 访客记录
 * @author jt_wangshuiping
 * @date 2016-10-21
 * @version 3.0
 */
public interface IAccessRecordRepository extends GenericRepository<AccessRecord, Integer> {

	public AccessRecord findById(Integer id);
	
}
