package n.io.cache;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.websocket.Session;

public class Side {

	public static Queue<Session> CLI = new LinkedBlockingQueue<Session>();
	
}
