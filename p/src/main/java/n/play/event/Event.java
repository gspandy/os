package n.play.event;

import n.entity.play.LN;

/**
 * 游戏事件
 * @author onsoul
 *
 */
public interface Event {

	public void open(LN ln) throws Exception;

	public void closed(LN ln) throws Exception;

	public void lottery(LN ln) throws Exception;

	public void crawler(LN ln) throws Exception;

	public void prize(LN ln) throws Exception;
}
