package n.play.calc;

import n.entity.play.BetRecord;
import n.entity.play.LN;
import n.entity.play.PlayRule;
import n.entity.play.PlayType;

public class AtlisTest {

	public static void main(String[] args) {
		//bet();
	}

	public static void bet()
	{
		String ln = "2 2 2";
		String arrow = Atlis.spilt(ln);
		System.out.println(Atlis.isSameNum(ln));
		BetRecord record = cellRecord(ln, PlayRule.MAX);
		Atlis.calc(record, arrow);
	}

	public static BetRecord cellRecord(String lnnum, PlayRule rule) {
		BetRecord record = new BetRecord();
		LN ln = new LN();
		ln.setLotteryNumber(lnnum);

		record.setLn(ln);
		PlayType type = new PlayType();
		type.setPlayRule(rule);
		record.setPlayType(type);
		return record;
	}

}
