package n.service.play.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import n.core.service.support.GenericService;
import n.entity.play.PlayType;
import n.entity.play.PlayTypeGroup;
import n.repository.play.IPlayTypeGroupRepository;
import n.repository.play.IPlayTypeRepository;
import n.service.play.IPlayTypeService;

/**
 * 玩法服务实现
 * 
 * @author jt_wangshuiping @date 2017年1月9日
 *
 */
@Service
public class PlayTypeService extends GenericService<PlayType, Integer> implements IPlayTypeService {

	@Resource
	private IPlayTypeRepository repository;

	@Autowired
	private IPlayTypeGroupRepository groupRepository;

	public IPlayTypeRepository getRepository() {
		return repository;
	}

	public PlayTypeService() {
		super(PlayType.class);
	}

	@Override
	public List<PlayTypeGroup> findAllGroup() { // 查找所有分组
		return groupRepository.findAll();
	}

	@Override
	public List<PlayType> findByGroup(Integer gmgid, Integer ptgid) {
		if (null != ptgid) {
			return repository.findByGroup(gmgid, ptgid); //查找游戏对应的玩法分类组 1、普通  2、任选  3、其它
		} else {
			return repository.findByGMGroup(gmgid);    //查找游戏对应的所有玩法分类
		}
	}

}
