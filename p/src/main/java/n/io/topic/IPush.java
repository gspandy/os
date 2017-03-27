package n.io.topic;

import n.io.protocol.Info;

/**
 * push info
 * 
 * @author niesoul
 *
 */
public interface IPush {
	public void doPush(Info info, IPushStrategy<Info> strategy);
}
