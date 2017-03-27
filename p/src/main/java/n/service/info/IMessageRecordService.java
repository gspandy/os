package n.service.info;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import n.core.service.support.IGenericService;
import n.entity.info.MessageRecord;
import n.table.dto.info.LeaveMsgDTO;

/**
 * 消息记录服务层
 * 
 * @author jt_wangshuiping
 * @date 2016-10-21
 * @version 3.0
 */
public interface IMessageRecordService extends IGenericService<MessageRecord, Integer> {

	public MessageRecord findById(Integer id);

	public Page<MessageRecord> queryMessageRecord(String userName, Long userId, Integer companyId, Date sendTimeStart, Date sendTimeEnd, Pageable pageable);
	
	public Page<MessageRecord> queryMessageRecordAll(String userName, Integer companyId, Date sendTimeStart, Date sendTimeEnd, Pageable pageable);

	public int delMessageRecord(String userName, Long userId, Integer companyId);
	
	/**
	 * 更新为已读
	 * @param userName
	 * @param fromUser 客服userName
	 * @param userId 客服id
	 * @param companyId
	 */
	public void updateReadMsg(String userName,String fromUser, Long userId, Integer companyId) throws Exception;

	/**
	 * 保存离线消息为已读
	 * @param companyId
	 * @param id
	 * @param userName
	 * @param messages
	 */
	public void saveLeaveMsg(Integer companyId, Long id, String userName, List<LeaveMsgDTO> messages) throws Exception;
	
	public List<MessageRecord> queryShareMsgRecord(Long userId);
	
	//查找共享聊天记录
	public Page<MessageRecord> findShareMessageRecord(String userName,Long userId,Integer companyId, Pageable pageable);
	/**
	 * 客服查询聊天记录接口
	 * @param userName
	 * @param userId
	 * @param curUserName
	 * @param curId
	 * @param companyId
	 * @param sendTimeStart
	 * @param sendTimeEnd
	 * @param pageable
	 * @return
	 */
	public Page<MessageRecord> servMessageRecord(String userName, Long userId, String curUserName, Long curId, Integer companyId, Date sendTimeStart, Date sendTimeEnd, Pageable pageable);
	
}
