package n.service.play;

import java.util.List;

import n.core.service.support.IGenericService;
import n.entity.play.Game;
import n.entity.play.Game.Name;

/**
 * 游戏类型
 * 
 * @author onsoul
 *
 */
public interface IGameService extends IGenericService<Game, Integer> {
	
	List<Game> findByGroupID(Integer gid);

	Game findByName(Name gname);
}
