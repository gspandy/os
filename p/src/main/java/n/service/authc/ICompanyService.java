package n.service.authc;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import n.core.service.support.IGenericService;
import n.entity.authc.Company;
import n.table.dto.authc.CompanyDTO;
/**
 * 公司服务层
 * @author jt_wangshuiping
 * @date 2016-10-21
 * @version 3.0
 */
public interface ICompanyService extends IGenericService<Company, Integer> {

	public Company findById(Integer id);
	
	public boolean exists(String companyName);
	
	public Company findByCompanyName(String companyName);
	
	public boolean isEnable(boolean enable, List<Integer> ids) ;
	
	public void delCompany(Integer id) throws Exception;
	
	public Page<Company> findNotDel(CompanyDTO companyDto,Pageable pageable);
}
