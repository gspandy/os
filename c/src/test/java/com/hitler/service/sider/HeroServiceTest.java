package com.hitler.service.sider;

import javax.annotation.Resource;

import org.junit.Test;

import com.hitler.entity.sider.Hero;
import com.hitler.web.test.BaseTest;

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
