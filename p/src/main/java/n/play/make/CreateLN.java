package n.play.make;

import java.util.List;

import n.entity.play.Game;
import n.entity.play.LN;

/**
 * 创建期号
 * @author onsou
 *
 */
public class CreateLN {

	/**
	 * 幸运5分 当天期数288
	 * @return
	 */
	public static List<LN> makePCOO(Game game) {
		// 06:04:30 当期开盘时间为98期
		int dayOfCount = 288;
		return MakeLN.makeFromMidnight(new int[] { 6, 4, 30 }, game, 74, dayOfCount, 300, 30);
	}

	
}
