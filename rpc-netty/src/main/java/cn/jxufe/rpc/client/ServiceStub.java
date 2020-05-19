package cn.jxufe.rpc.client;

import cn.jxufe.rpc.transport.Transport;

/**
 * @author taojun
 * Data: 2020/4/24
 */
public interface ServiceStub {
    void setTransport(Transport transport);
}
