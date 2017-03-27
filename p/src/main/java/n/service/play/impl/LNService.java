package n.service.play.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import n.core.repository.DynamicSpecifications;
import n.core.repository.OP;
import n.core.repository.SearchFilter;
import n.core.repository.support.GenericRepository;
import n.core.service.support.GenericService;
import n.entity.play.Game;
import n.entity.play.LN;
import n.entity.play.LN_;
import n.repository.play.ILNRepository;
import n.service.play.ILNService;

/**
 * 游戏
 * 
 * @author
 * @version 1.0 2015-04-27
 * 
 */

@Service("lnService")
public class LNService extends GenericService<LN, Long> implements ILNService {

	public LNService() {
		super(LN.class);
	}

	@Autowired
	public ILNRepository repository;

	@Override
	protected GenericRepository<LN, Long> getRepository() {
		return repository;
	}

	@Override
	public List<LN> findByOpenTime(Game game, Date openTime) {
		return repository.findByOpenTime(game, openTime);
	}

	@Override
	public List<LN> findByScheduledTime(Game game, Date scheduledTime) {
		return repository.findByScheduledTime(game, scheduledTime);
	}

	@Override
	public int countDaliy(Game game, Date openTime) {
		return countDaliy(game, openTime);
	}

	@Override
	public LN findCurrent(Integer gmid) {
		return repository.findCurrent(new Date(), gmid);
	}

	@Override
	public LN findLastLottery(Integer gmid) {
		return repository.findLastLottery(gmid, new Date());
	}

	@Override
	public Page<LN> findHistory(Integer gmid, Integer index, Integer size) {
		Sort sort = new Sort(Sort.Direction.DESC, LN_.openTime.getName());
		PageRequest page = new PageRequest(index, size, sort);
		Collection<SearchFilter> filters = Arrays.asList(new SearchFilter(LN_.game, OP.EQ, gmid),
				new SearchFilter(LN_.lotteryNumber, OP.NE, ""));
		Specification<LN> spec = DynamicSpecifications.bySearchFilter(filters);
		return findAll(spec, page);
	}
}
