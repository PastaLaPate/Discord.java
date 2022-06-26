public interface IConnection {
    String getToken();
    String getUrl();

    Connection connect() throws InterruptedException;
    
    boolean Isconnected();
}