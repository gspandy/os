package n.service.info;

import javax.annotation.Resource;

import org.junit.Test;

import n.web.test.BaseTest;

public class MessageRecordServiceTest extends BaseTest {

	@Resource
	private  IMessageRecordService messageRecordService;
	/**
	 * 来自用户的未读消息
	 * @throws Exception
	 */
	@Test
	public void findUnRead() throws Exception{
//		List<MessageRecord> records = messageRecordService.noReadMsg("jiang","jtterst1", 4l, 1);
//		for (MessageRecord record : records) {
//			System.err.println(record.getMsg()+" 发送者："+record.getFromUser());
//		}
	}
	/**
	 * 所有未读消息(发送人，消息数)
	 * @throws Exception
	 */
	@Test
	public void findAllUnRead() throws Exception{
//		List<UnReadMsgDTO> records = messageRecordService.allNoReadMsg("jtterst1",4l, 1);
//		for (UnReadMsgDTO record : records) {
//			System.err.println(" 发送者："+record.getFromUserName() +" size:"+record.getSize());
//		}
	}
	/**
	 * 标记为已读
	 * @throws Exception
	 */
	@Test
	public void  updateUnRead() throws Exception {
		messageRecordService.updateReadMsg("jiang","jtterst1", 4l, 1);
	}
}
