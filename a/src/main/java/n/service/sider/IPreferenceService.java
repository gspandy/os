package n.service.sider;

import n.core.service.support.IGenericService;
import n.entity.sider.Preference;
/**
 * 消息记录服务层
 * @author jt_wangshuiping
 * @date 2016-10-21
 * @version 3.0
 */
public interface IPreferenceService extends IGenericService<Preference, Integer> {
	 
	public Preference findByCode(String code);
}
