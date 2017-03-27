package n.table.authc;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import n.core.dto.support.GenericTable;
import n.entity.authc.User;



/**
 * 用户信息表
 * @author onsoul@qq.com
 * 2016年8月15日 下午3:54:02
 * @param <DTO>
 */
public class UserTable<DTO>  extends GenericTable<DTO, User>{
	
	private static final long serialVersionUID = 1L;

	@Override
	public List<Selection<?>> buildExpression(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		return null;
	}

}
