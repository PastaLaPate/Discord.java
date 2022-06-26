import java.net.http.WebSocket;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CountDownLatch;

public class WebSocketClient implements WebSocket.Listener {
    private final CountDownLatch latch;

    private WebSocket.Listener listener;
            
    public WebSocketClient(CountDownLatch latch) { this.latch = latch; }

    public WebSocketClient setListener(WebSocket.Listener listener) {
        this.listener = listener;
        return this;
    }
        
    @Override
    public void onOpen(WebSocket webSocket) {
        System.out.println("onOpen using subprotocol " + webSocket.getSubprotocol());
        WebSocket.Listener.super.onOpen(webSocket);
    }
        
    @Override
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
        System.out.println("onText received " + data);
        latch.countDown();
        return WebSocket.Listener.super.onText(webSocket, data, last);
    }
    
    @Override
    public void onError(WebSocket webSocket, Throwable error) {
        System.out.println("Bad day! " + webSocket.toString());
        WebSocket.Listener.super.onError(webSocket, error);
    }
}