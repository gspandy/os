package com.hitler.service;

import javax.annotation.Resource;

import org.junit.Test;

import com.hitler.service.sider.IPreferenceService;
import com.hitler.web.test.BaseTest;
 

public class PreferenceServiceTest extends BaseTest {
	
	@Resource
	private IPreferenceService preferenceService;

	@Test
	public void delete() throws Exception{
		preferenceService.delete(1);
	}
	
}
