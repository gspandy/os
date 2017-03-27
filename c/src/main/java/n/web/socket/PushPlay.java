package n.web.socket;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.web.socket.server.standard.SpringConfigurator;

import n.io.cache.Side;
import n.io.protocol.GmInfo;

@ServerEndpoint(value = "/push/play", configurator = SpringConfigurator.class)
public class PushPlay {

	@OnMessage
	public void onMessage(String message, Session session) throws IOException, InterruptedException {
		GmInfo info = new GmInfo();
		info.setBody(message);
	}

	@OnOpen
	public void onOpen(Session session) {
		Side.CLI.add(session);
		System.out.println("Client connected:" + session.getId());
	}
 
	@OnClose
	public void onClose(Session session) {
		System.out.println("Connection closed");
		Side.CLI.remove(session);
	}

	@OnError
	public void onError(Session session, Throwable t) {
		System.out.println("Connection error");
		Side.CLI.remove(session);
		t.printStackTrace();
	}

}
