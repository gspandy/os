package n.service.sider;

import javax.annotation.Resource;

import org.junit.Test;

import n.entity.sider.Hero;
import n.web.test.BaseTest;

public class HeroServiceTest extends BaseTest{

	@Resource
	private IHeroService heroService;

	@Test
	public void saveTest() throws Exception {
		Hero hero=new Hero();
		hero.setName("WANGaa");
		heroService.save(hero);
	}

}
