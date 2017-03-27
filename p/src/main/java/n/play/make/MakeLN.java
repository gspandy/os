package n.play.make;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import n.core.jutils.base.StringUtils;
import n.core.jutils.math.RandomUtils;
import n.entity.play.Game;
import n.entity.play.LN;

/**
 * 生成期号开始投注时间、封盘时间、开奖时间等信息
 * @author caoyl    
 * @date 2016/07/19 12:02:00        
 * @version 1.0
 */
public class MakeLN {

	/**
	 * 产生当天的期号,用于生成全天运行的期号
	 * 
	 * @param startTime
	 *            开始生成的时间
	 * @param game
	 * @param beforeIssue
	 *            开始生成时的期号
	 * @param dayOfCount
	 *            一天的总期数
	 * @param cycle
	 *            (开盘-预定开奖时间 单位:秒)
	 * @param advanceEndTime
	 *            (封盘-预定开奖时间  单位:秒)
	 * @return
	 */
	public static List<LN> makeFromMidnight(int[] startTime,
			Game game, int beforeIssue, int dayOfCount, int cycle,
			int advanceEndTime) {
		List<LN> _List = new ArrayList<LN>();
		Calendar cal = Calendar.getInstance();
		Date open = null, end = null, sched = null;

		cal.set(Calendar.HOUR_OF_DAY, startTime[0]); // 时
		cal.set(Calendar.MINUTE, startTime[1]); // 分
		cal.set(Calendar.SECOND, startTime[2]); // 秒

		int closed_dist = cycle - advanceEndTime; // 封盘 封盘时间=（开盘 ~ 预定开奖）-（封盘 ~
													// 预定开奖）
		int sure_dist;
		int open_dist = -advanceEndTime;
		int lotterySize = dayOfCount<1000?3:((dayOfCount+"".trim()).length());//期号位数
		for (int i = beforeIssue; i <= dayOfCount; i++) { // 期数
			int _Year = cal.get(Calendar.YEAR);
			int _Morth = cal.get(Calendar.MONTH) + 1;
			int _Day = cal.get(Calendar.DAY_OF_MONTH);

			String issue = _Year + "" + RandomUtils.CompletionCount(_Morth, 2)
					+ "" + RandomUtils.CompletionCount(_Day, 2) + "-"
					+ RandomUtils.CompletionCount(i, lotterySize);//如果大于1000期则生成4位期号

			open = cal.getTime();

			sure_dist = (i > 1) ? closed_dist + advanceEndTime : closed_dist;

			cal.add(Calendar.SECOND, (sure_dist));
			end = cal.getTime();

			cal.add(Calendar.SECOND, advanceEndTime);
			cal.set(Calendar.MILLISECOND, 0);

			sched = cal.getTime();

			_List.add(MakeLN.cellLN(game, issue, open, end, sched));

			cal.add(Calendar.SECOND, open_dist);
		}
		List<LN> tomr = MakeLN.makeDay(new int[] { 1, 0, 0, 0 }, game,
				beforeIssue - 1, cycle, advanceEndTime,lotterySize); // 产生当天从零点开始的期号
		_List.addAll(tomr);
		return _List;

	}

	/**
	 * 生成期号开始投注时间、封盘时间、开奖时间等信息
	 * 
	 * @param startTime
	 *            开始时间 int[]数组，数组长度必须为3 , 各长度分别代表时、分、秒
	 * @param twoTime 第二期开始时间
	 * @param gt
	 *            gametype
	 * 
	 * @param dayOfCount
	 *            每天开奖期数
	 * @param cycle
	 *            开奖周期(两次开奖之间的时间间隔,单位:分钟)
	 * @param advanceEndTime
	 *            提前封盘时间(单位:分钟)
	 * @return List<LotteryNums> 对象
	 */
	public static List<LN> make(int[] startTime,int[] twoTime, Game game,
			int dayOfCount, int cycle, int advanceEndTime) {
		List<LN> _List = new ArrayList<LN>();

		Calendar _Calendar = Calendar.getInstance();
		Date _StartTime = null, _EndTime = null, _ScheduledLotteryTime = null;
		//生成002-最后期加第二天的001期
		_Calendar.set(Calendar.HOUR_OF_DAY, twoTime[0]); // 时
		_Calendar.set(Calendar.MINUTE, twoTime[1]); // 分
		_Calendar.set(Calendar.SECOND, twoTime[2]); // 秒

		int open_dist = -advanceEndTime;
		int count = dayOfCount+1;
		for (int i = 2; i <= count; i++) { // 期数
			_StartTime = _Calendar.getTime();
			//重新设置第一期的开盘时间,以便计算封盘时间和预定开奖时间
			if(i==dayOfCount+1){
				_Calendar.add(Calendar.DAY_OF_YEAR, 1);
				_Calendar.set(Calendar.HOUR_OF_DAY, startTime[0]); // 时
				_Calendar.set(Calendar.MINUTE, startTime[1]); // 分
				_Calendar.set(Calendar.SECOND, startTime[2]); // 秒
			}
			int _Year = _Calendar.get(Calendar.YEAR);
			int _Morth = _Calendar.get(Calendar.MONTH) + 1;
			int _Day = _Calendar.get(Calendar.DAY_OF_MONTH);
			String _ISSUENO = _Year + ""
					+ RandomUtils.CompletionCount(_Morth, 2) + ""
					+ RandomUtils.CompletionCount(_Day, 2) + "-"
					+ RandomUtils.CompletionCount(i==count?1:i, dayOfCount<1000?3:((dayOfCount+"".trim()).length()));//如果大于1000期则生成4位期号
			
			

			_Calendar.add(Calendar.MINUTE, (cycle));
			_EndTime = _Calendar.getTime();

			_Calendar.add(Calendar.MINUTE, advanceEndTime);
			_Calendar.set(Calendar.MILLISECOND, 0);
			_ScheduledLotteryTime = _Calendar.getTime();

			_List.add(cellLN(game, _ISSUENO, _StartTime, _EndTime,
					_ScheduledLotteryTime));
			_Calendar.add(Calendar.MINUTE, open_dist);
		}
		return _List;
	}

	/**
	 * 生成某一天的彩种期号
	 * 
	 * @param dayOfCount
	 *            当天期数
	 * @param cycle
	 *            多久一期(单位：秒)
	 * @param advanceEndTime
	 *            封盘时间(单位:秒)
	 * @parem lotterySize 期号位数(如100是3位数,1000是4位数)
	 */
	public static List<LN> makeDay(int[] startTime, Game game,
			int dayOfCount, int cycle, int advanceEndTime,int lotterySize) {

		List<LN> _List = new ArrayList<LN>();
		Calendar cal = Calendar.getInstance();

		Date open = null, end = null, sched = null;

		cal.add(Calendar.DAY_OF_YEAR, startTime[0]); // 天 ,当前时间增减
		cal.set(Calendar.HOUR_OF_DAY, startTime[1]); // 时
		cal.set(Calendar.MINUTE, startTime[2]); // 分
		cal.set(Calendar.SECOND, startTime[3]); // 秒

		int closed_dist = cycle - advanceEndTime; // 封盘 5 - 1 = 4

		int sure_dist;

		int open_dist = -advanceEndTime;

		for (int i = 1; i <= dayOfCount; i++) { // 期数

			int _Year = cal.get(Calendar.YEAR);
			int _Morth = cal.get(Calendar.MONTH) + 1;
			int _Day = cal.get(Calendar.DAY_OF_MONTH);
			String issue = _Year + "" + RandomUtils.CompletionCount(_Morth, 2)
					+ "" + RandomUtils.CompletionCount(_Day, 2) + "-"
					+ RandomUtils.CompletionCount(i, lotterySize);//如果大于1000期则生成4位期号

			open = cal.getTime();

			sure_dist = (i > 1) ? closed_dist + advanceEndTime : closed_dist;

			cal.add(Calendar.SECOND, (sure_dist));
			end = cal.getTime();

			cal.add(Calendar.SECOND, advanceEndTime);
			cal.set(Calendar.MILLISECOND, 0);

			sched = cal.getTime();

			_List.add(cellLN(game, issue, open, end, sched));

			cal.add(Calendar.SECOND, open_dist);
		}
		return _List;
	}

	/**
	 * 生成期号开始投注时间、封盘时间、开奖时间等信息
	 * @param _StartTimes       开始时间 int[]数组，数组长度必须为3 , 各长度分别代表时、分、秒
	 * @param game           游戏ID
	 * @param _DayOfCount       每天开奖期数
	 * @param _Cycle            开奖周期(两次开奖之间的时间间隔 单位:分钟)
	 * @param _AdvanceEndTime   提前封盘时间 单位:分钟
	 * @param _StartISSUENO     开始生成的期号
	 * @param _StartIssuenoDate 开始生成的开盘时间
	 * @return List<LotteryNums> 对象
	 */
	public static List<LN> makeIncrement(int[] _StartTimes,
			Game game, int _DayOfCount, int _Cycle, int _AdvanceEndTime,
			String _StartISSUENO, Date _StartIssuenoDate) {
		Calendar cal = Calendar.getInstance();

		// 上一期结束时间为下一期开盘时间
		cal.setTime(_StartIssuenoDate);

		cal.add(Calendar.DAY_OF_YEAR, 1);
		cal.set(Calendar.HOUR_OF_DAY, _StartTimes[0]);
		cal.set(Calendar.MINUTE, _StartTimes[1]);
		cal.set(Calendar.SECOND, _StartTimes[2]);

		int _ISSUENO = StringUtils.StringToInt(_StartISSUENO);

		List<LN> _List = new ArrayList<LN>();
		Date _StartTime = null, _EndTime = null, _ScheduledLotteryTime = null;
		for (int i = 1; i <= _DayOfCount; i++) {
			_ISSUENO++;
			_StartTime = cal.getTime();
			cal.add(Calendar.MINUTE, (_Cycle - _AdvanceEndTime));
			_EndTime = cal.getTime();
			cal.add(Calendar.MINUTE, _AdvanceEndTime);
			cal.set(Calendar.MILLISECOND, 0);
			_ScheduledLotteryTime = cal.getTime();
			_List.add(cellLN(game, _ISSUENO + "", _StartTime, _EndTime,
					_ScheduledLotteryTime));
		}
		return _List;
	}
	
	/**
	 * 生成期号开始投注时间、封盘时间、开奖时间等信息
	 * @param startTimes        生成的第2天第一期时间 int[]数组，数组长度必须为3 , 各长度分别代表时、分、秒
	 * @param twoTimes          生成最新一期(定时执行的是第1天第二期时间) int[]数组，数组长度必须为3 , 各长度分别代表时、分、秒
	 * @param game           游戏ID
	 * @param _DayOfCount       生成期数(定时执行每天开奖期数)
	 * @param _Cycle            开奖周期(两次开奖之间的时间间隔 单位:秒)
	 * @param _AdvanceEndTime   提前封盘时间 单位:秒
	 * @param _StartISSUENO     开始生成的期号
	 * @param flag              生成的第二天第一期时间是否在第二天
	 * @param start             从多少期开始生成(与twoTimes对应)
	 * @return List<LotteryNums> 对象
	 */
	public static List<LN> makeKENO(int[] startTimes,int[] twoTimes,
			Game game, int _DayOfCount, int _Cycle, int _AdvanceEndTime,
			String _StartISSUENO,boolean flag,int start) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, twoTimes[0]);
		cal.set(Calendar.MINUTE, twoTimes[1]);
		cal.set(Calendar.SECOND, twoTimes[2]);
		int _ISSUENO = StringUtils.StringToInt(_StartISSUENO);

		List<LN> _List = new ArrayList<LN>();
		Date _StartTime = null, _EndTime = null, _ScheduledLotteryTime = null;
		int count = _DayOfCount+1;
		for (int i = start; i <= count; i++) {
			_ISSUENO++;
			_StartTime = cal.getTime();
			if(i==count){
				if(flag){
					cal.add(Calendar.DAY_OF_YEAR, 1);
				}
				cal.set(Calendar.HOUR_OF_DAY,startTimes[0]);
				cal.set(Calendar.MINUTE, startTimes[1]);
				cal.set(Calendar.SECOND,startTimes[2]);
			}
			cal.add(Calendar.SECOND, (_Cycle));
			_EndTime = cal.getTime();
			cal.add(Calendar.SECOND, _AdvanceEndTime);
			cal.set(Calendar.MILLISECOND, 0);
			_ScheduledLotteryTime = cal.getTime();
			_List.add(cellLN(game, _ISSUENO + "", _StartTime, _EndTime,_ScheduledLotteryTime));
			cal.add(Calendar.SECOND, -_AdvanceEndTime);
		}
		return _List;
	}


	/**
	 * 填充LN对象
	 */
	public static LN cellLN(Game game, String issue,
			Date open, Date close, Date _ScheduledTime) {
		LN ln = new LN();
		ln.setGame(game);
		ln.setIssue(issue);

		ln.setOpenTime(open);
		ln.setCloseTime(close);
		ln.setScheduledTime(_ScheduledTime);
		ln.setOperator("期号生成");
		return ln;
	}

}
