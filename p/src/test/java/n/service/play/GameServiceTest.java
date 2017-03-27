package n.service.play;

import javax.annotation.Resource;

import org.junit.Test;

import n.entity.play.GMGroup;
import n.entity.play.Game;
import n.entity.play.Game.Enabled;
import n.entity.play.Game.Name;
import n.entity.play.Game.Type;
import n.service.play.IGameService;
import n.test.BaseTest;

public class GameServiceTest extends BaseTest {

	@Resource
	private IGameService gameService;

	@Test
	public void findTest() {
	}

	@Test
	public void addTest() throws Exception {
		Game game = new Game();
		game.setName(Name.PCOO);
		game.setGmGroup(new GMGroup());
		game.setEnabled(Enabled.ON);
		game.setIssuesDaily(248);
		game.setType(Type.OP);
		game.setDeep(1);
		game.setVersion(1);
		game.setCode("001");
		gameService.save(game);
	}

	@Test
	public void findRecord() {

	}

}
