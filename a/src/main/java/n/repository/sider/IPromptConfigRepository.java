package n.repository.sider;

import java.util.List;

import n.core.repository.support.GenericRepository;
import n.entity.sider.PromptConfig;
import n.entity.sider.PromptConfig.PromptType;
/**
 * 聊天提示配置仓库
 * @author jt_wangshuiping @date 2016年12月8日
 *
 */
public interface IPromptConfigRepository extends GenericRepository<PromptConfig, Integer> {

	public List<PromptConfig> findByType(PromptType type);
}
