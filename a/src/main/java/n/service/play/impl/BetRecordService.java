package n.service.play.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import n.core.repository.DynamicSpecifications;
import n.core.repository.OP;
import n.core.repository.SearchFilter;
import n.core.service.support.GenericService;
import n.entity.play.BetRecord;
import n.entity.play.BetRecord_;
import n.entity.play.LN;
import n.io.protocol.GmInfo;
import n.io.protocol.GmInfo.Type;
import n.io.topic.Topic;
import n.io.topic.Topic.TopicName;
import n.repository.play.IBetRecordRepository;
import n.service.play.IBetRecordService;
import n.table.dto.play.BetRecordDTO;

/**
 * 投注记录
 * 
 * @author jt_wangshuiping @date 2017年1月9日
 */
@Service
public class BetRecordService extends GenericService<BetRecord, Long> implements IBetRecordService {

	@Resource
	private IBetRecordRepository repository;

	@Resource
	private Topic topic;

	public IBetRecordRepository getRepository() {
		return repository;
	}

	public BetRecordService() {
		super(BetRecord.class);
	}

	@Override
	public List<BetRecord> findAllByLN(LN ln) {
		// Sort sort = new Sort(Sort.Direction.DESC,
		// BetRecord_.betTime.getName());
		Collection<SearchFilter> filters = Arrays.asList(new SearchFilter(BetRecord_.ln, OP.EQ, ln));
		Specification<BetRecord> spec = DynamicSpecifications.bySearchFilter(filters);
		return findAll(spec);
	}

	@Override
	public boolean bet(BetRecordDTO dto) {
		topic.production(makeBetInfo("用户A 投注了 1000元宝"), TopicName.LT_);
		return false;
	}

	private GmInfo makeBetInfo(String body) {
		GmInfo info = new GmInfo();
		info.setType(Type.bet);
		info.setBody(body);
		return info;
	}

}
