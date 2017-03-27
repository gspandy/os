package n.repository.play;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import n.core.repository.support.GenericRepository;
import n.entity.play.Game;
import n.entity.play.Game.Name;

/**
 * hero 此类用来检视系统是否正常
 * @author onsoul 2015-6-17 下午10:24:55
 */
public interface IGameRepository extends GenericRepository<Game, Integer> {

	/**
	 * 通过组ID查找游戏类型
	 * @param gid
	 * @return
	 */
	@Query("from Game a where a.gmGroup.id = :gid order by a.deep asc")
	public List<Game> findByGroupID(@Param("gid") Integer gid);
	
	public Game findByName(Name gname);

}
