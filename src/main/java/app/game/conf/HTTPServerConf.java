package app.game.conf;

public final class HTTPServerConf {

    private static final int HTTP_SERVER_PORT = 7000;
    private int port;

    public int httpServerPort() {
        return port == 0 ? HTTP_SERVER_PORT : port;
    }

    public HTTPServerConf httpServerPort(int p) {
        port = p;
        return this;
    }
}
