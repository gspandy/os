package n.service.sider;

import java.util.List;

import n.core.service.support.IGenericService;
import n.entity.sider.PromptConfig;
import n.entity.sider.PromptConfig.PromptType;
import n.table.dto.sider.PromptConfigCreateDTO;
/**
 * 聊天提示配置服务接口
 * @author jt_wangshuiping @date 2016年12月8日
 *
 */
public interface IPromptConfigService extends IGenericService<PromptConfig, Integer> {

	public PromptConfig findByType(PromptType type);
	/**
	 * 用于查询快速输入、超时类型的列表数据
	 * @param type
	 * @return
	 */
	public List<PromptConfig> findListByType(PromptType type);
	/**
	 * 特殊保存方法
	 * @param dto
	 * @throws Exception
	 */
	public void add(PromptConfigCreateDTO dto) throws Exception;
}
