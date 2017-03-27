package n.service.play.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import n.core.repository.support.GenericRepository;
import n.core.service.support.GenericService;
import n.entity.play.Game;
import n.entity.play.Game.Name;
import n.repository.play.IGameRepository;
import n.service.play.IGameService;

/**
 * 游戏
 * 
 * @author
 * @version 1.0 2015-04-27
 * 
 */

@Service
public class GameService extends GenericService<Game, Integer> implements IGameService {

	public GameService() {
		super(Game.class);
	}

	@Autowired
	public IGameRepository repository;

	@Override
	protected GenericRepository<Game, Integer> getRepository() {
		return repository;
	}

	@Override
	public List<Game> findByGroupID(Integer gid) {
		return repository.findByGroupID(gid);
	}

	@Override
	public Game findByName(Name gname) {
		return repository.findByName(gname);
	}

}
