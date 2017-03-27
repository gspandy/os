package n.repository.play;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import n.core.repository.support.GenericRepository;
import n.entity.play.PlayType;

/**
 * 玩法核心
 * 
 * @author jt_wangshuiping @date 2017年1月9日
 *
 */
public interface IPlayTypeRepository extends GenericRepository<PlayType, Integer> {

	@Query("from PlayType a where a.gmGroup.id = :gid and a.playTypeGroup.id=:pid order by a.deep asc")
	List<PlayType> findByGroup(@Param("gid") Integer gid, @Param("pid") Integer pid);
	
	@Query("from PlayType a where a.gmGroup.id = :gid order by a.deep asc")
	List<PlayType> findByGMGroup(@Param("gid") Integer gid);
	
}
