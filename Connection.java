import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CountDownLatch;

public class Connection implements IConnection {

    private static String URL = "wss://gateway.discord.gg/?v=10&encoding=json";
    private static String token;
    private static boolean connected = false;

    private WebSocket socket;
    private WebSocketClient socketClient;

    public Connection(String token) {
        this.token = token;
    }
    
    public Connection(String url, String token) {
        this.URL = url;
        this.token = token;
    }

    @Override
    public Connection connect() throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(1);

        socketClient = new WebSocketClient(latch);
        socket = HttpClient
                .newHttpClient()
                .newWebSocketBuilder()
                .buildAsync(URI.create(this.URL), socketClient)
                .join();
        socket.sendText("Hello!", true);
        latch.await();
        
        return this;
    }

    public Connection setListener(WebSocket.Listener listener) {

        socketClient.setListener(listener);
        
        return this;
    }
    
    @Override
    public String getUrl() {
        return this.URL;
    }

    @Override
    public String getToken() {
        return this.token;
    }

    @Override
    public boolean Isconnected() {
        return this.connected;
    }
    
}