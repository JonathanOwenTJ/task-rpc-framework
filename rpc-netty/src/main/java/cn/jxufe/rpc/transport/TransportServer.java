package cn.jxufe.rpc.transport;

/**
 * @author taojun
 * Data: 2020/4/23
 */
public interface TransportServer {
    void start(RequestHandlerRegistry requestHandlerRegistry, int port) throws Exception;
    void stop();
}
