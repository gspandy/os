package com.hitler.service.info;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.hitler.test.BaseTest;

import n.entity.authc.User;
import n.entity.info.AccessRecord;
import n.service.info.IAccessRecordService;
import n.service.info.IMessageRecordService;
import n.table.dto.info.AccessRecordDTO;

public class AccessRecordServiceTest extends BaseTest{
	
	@Resource
	private IAccessRecordService accessRecordService;
	@Resource
	private IMessageRecordService messageRecordService;
	
	@Test
	public void findTest(){
		List<AccessRecord> list = accessRecordService.findAll();
		for (AccessRecord r : list) {
			System.err.println(r.getId());
		}
	}
	
	@Test
	public void addTest() throws Exception{
		AccessRecordDTO dto = new AccessRecordDTO();
		User visitor = new User();
		dto.setVisitorId(visitor);
		accessRecordService.save(dto);
	}
	
	@Test
	public void findRecord(){
		Pageable pa=new PageRequest(1, 10);
//		Page<MessageRecord> list = messageRecordService.queryMessageRecord("jiang", 3L, 1,pa);
//		for (MessageRecord r : list) {
//			System.err.println(r.getSendTime());
//		}
	}

}
