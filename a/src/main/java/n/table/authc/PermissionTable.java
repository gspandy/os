package n.table.authc;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import n.core.dto.support.GenericTable;
import n.entity.authc.Permission;



/**
 * 角色信息
 * @author jt_wangshuiping @date 2016年10月26日
 *
 * @param <DTO>
 */
public class PermissionTable<DTO>  extends GenericTable<DTO, Permission>{
	
	private static final long serialVersionUID = 1L;

	@Override
	public List<Selection<?>> buildExpression(Root<Permission> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		return null;
	}

}
