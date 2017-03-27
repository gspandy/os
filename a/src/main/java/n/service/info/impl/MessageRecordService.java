package n.service.info.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import n.core.repository.support.GenericRepository;
import n.core.service.support.GenericService;
import n.entity.info.MessageRecord;
import n.repository.info.IMessageRecordRepository;
import n.service.authc.ICompanyService;
import n.service.authc.IUserService;
import n.service.info.IMessageRecordService;
import n.table.dto.info.LeaveMsgDTO;
/**
 * 消息记录服务层
 * @author jt_wangshuiping
 * @date 2016-10-21
 * @version 3.0
 */
@Service
public class MessageRecordService extends GenericService<MessageRecord, Integer> implements IMessageRecordService {

	public MessageRecordService() {
		super(MessageRecord.class);
	}

	@Resource
	private IMessageRecordRepository repository;
	
	@Resource
	private ICompanyService companyService;
	
	@Resource
	private IUserService userService;

	@Override
	public MessageRecord findById(Integer id) {
		return repository.findById(id);
	}

	@Override
	protected GenericRepository<MessageRecord, Integer> getRepository() {
		return repository;
	}

	@Override
	public Page<MessageRecord> queryMessageRecord(String userName, Long userId, Integer companyId, Date sendTimeStart, Date sendTimeEnd, Pageable pageable) {
//		Map<String, Object> parameters=new HashMap<String,Object>();
//		parameters.put("userName", userName);
//		parameters.put("companyId", companyId);
//		parameters.put("customerId", userId);
//		String sql = "select entry_time,last_msg,visitor_id from tb_access_record where "
//				+ " userName=:userName and company_id=:companyId and customer_id=:customerId order by entry_time desc";
//		Query query= repository.getEntityManager().createNativeQuery(sql);
//		if(parameters != null) {
//			Iterator<Map.Entry<String,Object>> it = parameters.entrySet().iterator();
//			while(it.hasNext()) {
//				Entry<String,Object> entry = it.next();
//				query.setParameter(entry.getKey(), entry.getValue());
//			}
//		}
//		List<AccessRecord> arList=new ArrayList<AccessRecord>();
//		List<Object[]> list=query.getResultList();
//		if(CollectionHelper.isNotEmpty(list)){
//			for(Object[] obj:list){
//				AccessRecord ar=new AccessRecord();
//				ar.setEntryTime(DateUtils.str2Date( new SimpleDateFormat("yyyy-MM-dd hh:ss:dd").format(Timestamp.valueOf(obj[0]+""))));
//				ar.setVisitorId(obj[2]+"");
//				ar.setLastMsg(obj[1]+"");
//				arList.add(ar);
//			}
//		 	return arList;
//		}
//		return null;
		return repository.findMessageRecord(userName, companyId, userId, sendTimeStart,sendTimeEnd, pageable);
	}
	
	@Override
	@Transactional
	public int delMessageRecord(String userName, Long userId, Integer companyId){
		return repository.delMessageRecord(userName, companyId, userId);
	}

	@Override
	@Transactional
	public void updateReadMsg(String userName, String fromUser, Long userId, Integer companyId) throws Exception{
		repository.updateReadState(userName, fromUser, userId, companyId);
	}

	@Override
	public Page<MessageRecord> queryMessageRecordAll(String userName, Integer companyId, Date sendTimeStart,
			Date sendTimeEnd, Pageable pageable) {
		return repository.findMessageRecordByUserName(userName, companyId, sendTimeStart, sendTimeEnd, pageable);
	}

	@Override
	public List<MessageRecord> queryShareMsgRecord(Long userId) {
		// TODO Auto-generated method stub
		return repository.queryShareMsgRecord(userId);
	}

	@Override
	public Page<MessageRecord> findShareMessageRecord(String userName, Long userId, Integer companyId,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return repository.findShareMessageRecord(userName, userId, companyId, pageable);
	}
	@Override
	public void saveLeaveMsg(Integer companyId, Long id, String userName, List<LeaveMsgDTO> messages) throws Exception {
		MessageRecord messageRecord = new MessageRecord();
		messageRecord.setFromNickName(userName);
		messageRecord.setCompanyId(companyId);
		messageRecord.setFromUser(userName);
		messageRecord.setUserId(id);
		messageRecord.setStatus(true);//标记为已读
		for (LeaveMsgDTO dto : messages) {
			messageRecord.setSendTime(dto.getSendTime());
			messageRecord.setMsg(dto.getContent());
			save(messageRecord);
		}
		
	}

	@Override
	public Page<MessageRecord> servMessageRecord(String userName, Long userId, String curUserName, Long curId,
			Integer companyId, Date sendTimeStart, Date sendTimeEnd, Pageable pageable) {
		return repository.servFindMessageRecord(userName, userId, curUserName, curId, companyId, sendTimeStart, sendTimeEnd, pageable);
	}
}
