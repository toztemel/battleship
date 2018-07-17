package app.game.service;

import app.game.conf.HTTPServerConf;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ProtocolService {

    private static final ProtocolService instance = new ProtocolService();

    private HTTPServerConf conf;
    private String ownProtocol;

    private ProtocolService() {
    }

    public static ProtocolService getInstance() {
        return instance;
    }

    public String getOwnProtocol() {
        if (ownProtocol == null) {
            try {
                String hostAddress = InetAddress.getLocalHost().getHostAddress();
                String port = Integer.toString(conf.httpServerPort());
                ownProtocol = hostAddress + ":" + port;
            } catch (UnknownHostException ignore) {
            }
        }
        return ownProtocol;
    }

    public ProtocolService setHttpConf(HTTPServerConf c) {
        conf = c;
        ownProtocol = null;
        return this;
    }
}
