package n.repository.sider;

import n.core.repository.support.GenericRepository;
import n.entity.sider.Preference;

/**
 * JPA 用户 核心
 * @author onsoul 2015-6-17 下午10:24:55
 */
public interface IPreferenceRepository extends GenericRepository<Preference, Integer> {
	 public Preference findByCode(String code);
}
