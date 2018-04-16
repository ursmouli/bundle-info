package app.ws;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/hello/{username}")
public class ChatEndPoint {

    private Session session;

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        this.session = session;
    }

    @OnMessage
    public void onMessage(Session session) {

    }

    @OnClose
    public void onClose(Session session) {

    }

    @OnError
    public void onError(Session session, Throwable throwable) {

    }
}
