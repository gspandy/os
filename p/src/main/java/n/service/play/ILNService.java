package n.service.play;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import n.core.service.support.IGenericService;
import n.entity.play.Game;
import n.entity.play.LN;

/**
 * 游戏类型 
 * @author onsoul
 *
 */
public interface ILNService extends IGenericService<LN, Long> {
	
	LN findCurrent(Integer gmid); //当前期
	
	LN findLastLottery(Integer gmid);
	
	Page<LN> findHistory(Integer gmid,Integer index,Integer size); 
	
	//根据预定开奖时间
	List<LN> findByScheduledTime(Game game,Date scheduledTime);
	
	//根据开盘时间
	List<LN> findByOpenTime(Game game,Date openTime);
	
	int countDaliy(Game game,Date openTime);
}
