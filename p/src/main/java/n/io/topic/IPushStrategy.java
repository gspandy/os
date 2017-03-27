package n.io.topic;

import java.util.Queue;


/**
 * 消息推送策略
 * @author onsoul
 * @param <Info>
 */
public interface IPushStrategy<I>  {

	String topic();    //推送所对应的Topic
	
	Queue<?> target(I info);     //推送目标的选择方法
	
}
