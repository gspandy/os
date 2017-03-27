package n.repository.info;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import n.core.repository.support.GenericRepository;
import n.entity.info.MessageRecord;
/**
 * 消息记录
 * @author jt_wangshuiping
 * @date 2016-10-21
 * @version 3.0
 */
public interface IMessageRecordRepository extends GenericRepository<MessageRecord, Integer> {

	public MessageRecord findById(Integer id);
	
	@Query(value="FROM MessageRecord l WHERE  l.fromNickName=:userName and l.companyId = :companyId and l.userId=:userId and l.sendTime >= :sendTimeStart and l.sendTime <= :sendTimeEnd  order by sendTime desc")
	public Page<MessageRecord> findMessageRecord(@Param("userName")String userName,@Param("companyId")Integer companyId,@Param("userId")Long userId,@Param("sendTimeStart")Date sendTimeStart, @Param("sendTimeEnd")Date sendTimeEnd, Pageable pageable);
	
	
	@Query(value="FROM MessageRecord l WHERE  l.fromNickName=:userName and l.companyId = :companyId  and l.sendTime >= :sendTimeStart and l.sendTime <= :sendTimeEnd  order by sendTime desc")
	public Page<MessageRecord> findMessageRecordByUserName(@Param("userName")String userName,@Param("companyId")Integer companyId,@Param("sendTimeStart")Date sendTimeStart, @Param("sendTimeEnd")Date sendTimeEnd, Pageable pageable);

	/**
	 * 客服查询聊天记录接口
	 * @param userName
	 * @param curUserName
	 * @param companyId
	 * @param sendTimeStart
	 * @param sendTimeEnd
	 * @param pageable
	 * @return
	 * (m.USER_ID = 5 and m.FROM_NICK_NAME = 'jttest1') or (m.USER_ID = 4 and m.FROM_NICK_NAME = 'jttest2')
	 */
	@Query(value="FROM MessageRecord l WHERE  ((l.fromNickName=:userName and l.userId=:curId) or (l.fromNickName=:curUserName and l.userId =:userId)) and l.companyId = :companyId  and l.sendTime >= :sendTimeStart and l.sendTime <= :sendTimeEnd  order by sendTime desc")
	public Page<MessageRecord> servFindMessageRecord(@Param("userName")String userName,@Param("userId")Long userId,@Param("curUserName")String curUserName,@Param("curId")Long curId,@Param("companyId")Integer companyId,@Param("sendTimeStart")Date sendTimeStart, @Param("sendTimeEnd")Date sendTimeEnd, Pageable pageable);
	
	@Modifying
	@Query(value="Delete MessageRecord l WHERE  l.fromNickName=:userName and l.companyId = :companyId and l.userId=:userId ")
	public int delMessageRecord(@Param("userName")String userName,@Param("companyId")Integer companyId,@Param("userId")Long userId);

	/**
	 * 客服未读消息一对一
	 * @param userName
	 * @param fromUser --account
	 * @param userId
	 * @param companyId
	 * @return
	 */
	@Query(value="FROM MessageRecord l WHERE  l.fromNickName=:userName and l.companyId = :companyId and l.userId=:userId and l.status = 0 and l.fromUser != :fromUser order by sendTime desc")
	public List<MessageRecord> noReadMsg(@Param("userName") String userName,@Param("fromUser") String fromUser, @Param("userId") Long userId,@Param("companyId") Integer companyId);
	/**
	 * 客服所有的未读消息
	 * @param fromUser --account
	 * @param userId
	 * @param companyId
	 * @return
	 */
	@Query(nativeQuery = true, value="SELECT count(*) as size, l.FROM_NICK_NAME as fromUserName FROM TB_MESSAGE_RECORD l WHERE l.COMPANY_ID = :companyId and l.USER_ID=:userId and l.READ_STATE = 0 and l.FROM_USER != :fromUser group by FROM_NICK_NAME order by SEND_TIME desc")
	public List<Object[]> allNoReadMsg(@Param("fromUser")String fromUser, @Param("userId") Long userId,@Param("companyId") Integer companyId);

	@Modifying
	@Query(value="UPDATE MessageRecord SET status=1 WHERE userId = :userId AND companyId = :companyId AND fromNickName = :userName and fromUser != :fromUser")
	public void updateReadState(@Param("userName")String userName, @Param("fromUser")String fromUser, @Param("userId") Long userId,@Param("companyId") Integer companyId);
	
	@Query(value="from MessageRecord where userId=:userId and fromId=null group by userId,fromNickName")
	public List<MessageRecord> queryShareMsgRecord(@Param("userId")Long userId);
	
	@Query(value="FROM MessageRecord l WHERE  l.fromNickName=:userName and l.companyId = :companyId and l.userId=:userId   order by sendTime desc")
	public Page<MessageRecord> findShareMessageRecord(@Param("userName")String userName,@Param("userId")Long userId,@Param("companyId")Integer companyId, Pageable pageable);

}
