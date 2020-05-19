package cn.jxufe.rpc.client;

import cn.jxufe.rpc.transport.netty.NettyClient;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author taojun
 * Data: 2020/4/24
 */
public class RequestIdSupport {
    private final static AtomicInteger nextRequestId = new AtomicInteger(0);
    public static int next() {
        return nextRequestId.getAndIncrement();
    }
}
