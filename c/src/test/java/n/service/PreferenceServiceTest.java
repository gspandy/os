package n.service;

import javax.annotation.Resource;

import org.junit.Test;

import n.service.sider.IPreferenceService;
import n.web.test.BaseTest;
 

public class PreferenceServiceTest extends BaseTest {
	
	@Resource
	private IPreferenceService preferenceService;

	@Test
	public void delete() throws Exception{
		preferenceService.delete(1);
	}
	
}
