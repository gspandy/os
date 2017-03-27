package n.web.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:web-test-ctx.xml" })
public class BaseTest {
	protected Logger log = LoggerFactory.getLogger(BaseTest.class);
	 
	@Before
	public void setup() {
		System.setProperty("hitler", "DEV");
		log.info("##测试环境正常加载,开始执行测试...");
	}

	@Test
	public void SYSTEST() {
		log.info("##测试环境已正常.");
	}

}