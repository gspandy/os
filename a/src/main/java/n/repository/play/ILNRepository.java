package n.repository.play;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import n.core.repository.support.GenericRepository;
import n.entity.play.Game;
import n.entity.play.LN;

/**
 * LN
 * 
 * @author onsoul 2015-6-17 下午10:24:55
 */
public interface ILNRepository extends GenericRepository<LN, Long> {

	// 查找当前期
	@Query(nativeQuery = true, value = "SELECT * FROM TB_LN a WHERE a.GAME_ID=:gmid and a.CLOSE_TIME >= :now ORDER BY a.CLOSE_TIME ASC LIMIT 1")
	LN findCurrent(@Param("now") Date now, @Param("gmid") Integer gmid);

	// 查找上一期开奖成功的
	@Query(nativeQuery = true, value = "SELECT * FROM TB_LN a WHERE a.GAME_ID = :gmid  and a.SCHEDULED_TIME < :now and a.LOTTERY_NUMBER<>'' ORDER BY a.SCHEDULED_TIME DESC LIMIT 1")
	LN findLastLottery(@Param("gmid") Integer gmid, @Param("now") Date now);
	
	
	// 根据预定开奖时间
	@Query("from LN a where a.game = :game and a.scheduledTime > :scheduledTime order by a.openTime asc")
	List<LN> findByScheduledTime(@Param("game") Game game, @Param("scheduledTime") Date scheduledTime);

	// 根据开盘时间
	@Query("from LN a where a.game = :game and a.openTime >= :openTime order by a.openTime asc")
	List<LN> findByOpenTime(@Param("game") Game game, @Param("openTime") Date openTime);

	@Query("select count(*) from LN a where a.game = :game and a.openTime >= :openTime order by a.openTime asc")
	int countByopenTime(@Param("game") Game game, @Param("openTime") Date openTime);

}
