package n.table.ply;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import n.core.dto.support.GenericTable;
import n.entity.ply.Bank;



/**
 * 银行信息表
 * @author onsoul@qq.com
 * 2016年8月15日 下午3:54:02
 * @param <DTO>
 */
public class BankTable<DTO>  extends GenericTable<DTO, Bank>{
	
	private static final long serialVersionUID = 1L;

	@Override
	public List<Selection<?>> buildExpression(Root<Bank> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		return null;
	}

}
