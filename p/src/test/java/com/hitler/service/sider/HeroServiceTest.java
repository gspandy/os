package com.hitler.service.sider;

import javax.annotation.Resource;

import org.junit.Test;

import com.hitler.test.BaseTest;

import n.entity.sider.Hero;
import n.service.sider.IHeroService;

public class HeroServiceTest extends BaseTest{
	
	@Resource
	IHeroService heroService;
	
	@Test
	public void save() throws Exception{
		Hero hero=new Hero();
		hero.setName("ONSOUL_222232");
		heroService.save(hero);
	}
	
	public void setHeroService(IHeroService heroService) {
		this.heroService = heroService;
	}
	

}
