package n.repository.authc;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import n.core.repository.support.GenericRepository;
import n.entity.authc.Company;
/**
 * 公司
 * @author jt_wangshuiping
 * @date 2016-10-21
 * @version 3.0
 */
public interface ICompanyRepository extends GenericRepository<Company, Integer> {

	public Company findById(Integer id);
	
	public Company findByCompanyName(String companyName);

	@Modifying
	@Query(value="update Company set isEnable = :isenable where id in :ids ")
	public int isEnable(@Param("isenable")boolean enable, @Param("ids")List<Integer> ids);

	@Modifying
	@Query(value="update Company set cState = 'del' where id = :id ")
	public void delCompany(@Param("id")Integer id);
	
}
