package n.play.calc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import n.entity.play.BetRecord;
import n.entity.play.PlayRule;
import n.entity.play.PlayType;
import n.entity.play.BetRecord.WinStatus;

public class Atlis {

	private static Logger log = LoggerFactory.getLogger(Atlis.class);

	/**
	 * 
	 * @param record
	 * @param arrow
	 *            和值
	 * @return
	 */
	public static BetRecord calc(BetRecord record, String arrow) {
		boolean win = false;
		try {
			PlayType pType = record.getPlayType();
			log.info("###begin calc.ID:{},{}", record.getBillNo(), arrow);
			if (pType.getPlayRule() == PlayRule.FIXED) {
				win = record.getBetNumber().equals(arrow);
			} else if (pType.getPlayRule() == PlayRule.SAMENUM) {
				String ln = record.getLn().getLotteryNumber();
				win = isSameNum(ln);
			} else {
				win = emit(pType.getTarget(), arrow);   //其它的类型
			}
		} catch (Exception e) {
			win = false;
		}

		if (win) {
			record.setWinStatus(WinStatus.WIN);
		} else {
			record.setWinStatus(WinStatus.FAIL);
		}
		return record;
	}

	public static boolean emit(String target, String arrow) {
		return target.indexOf(arrow) > -1;
	}

	public static boolean isSameNum(String ln) {
		String[] nums = ln.split(" ", -1);
		if (nums.length > 2) {
			return nums[0].equals(nums[1]) && nums[1].equals(nums[2]);
		}
		return false;
	}

	public static String spilt(String ln) {
		String[] nums = ln.split(" ", -1);
		int i = 0;
		for (String n : nums) {
			i += Integer.parseInt(n);
		}
		return "" + i;
	}

}
