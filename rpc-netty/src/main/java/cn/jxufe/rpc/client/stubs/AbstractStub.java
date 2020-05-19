package cn.jxufe.rpc.client.stubs;

import cn.jxufe.rpc.client.RequestIdSupport;
import cn.jxufe.rpc.client.ServiceStub;
import cn.jxufe.rpc.client.ServiceTypes;
import cn.jxufe.rpc.serialize.SerializeSupport;
import cn.jxufe.rpc.transport.Transport;
import cn.jxufe.rpc.transport.command.Code;
import cn.jxufe.rpc.transport.command.Command;
import cn.jxufe.rpc.transport.command.Header;
import cn.jxufe.rpc.transport.command.ResponseHeader;

import java.util.concurrent.ExecutionException;

/**
 * @author taojun
 * Data: 2020/4/24
 */
public abstract class AbstractStub implements ServiceStub {
    protected Transport transport;
    
    protected byte [] invokeRemote(RpcRequest request) {
        Header header = new Header(RequestIdSupport.next(), 1, ServiceTypes.TYPE_RPC_REQUEST);
        byte[] payload = SerializeSupport.serialize(request);
        Command requestCommand = new Command(header, payload);
        try {
            Command responseCommand = transport.send(requestCommand).get();
            ResponseHeader responseHeader = (ResponseHeader)responseCommand.getHeader();
            if(responseHeader.getCode() == Code.SUCCESS.getCode()) {
                return responseCommand.getPayload();
            } else {
                throw new Exception(responseHeader.getError());
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e.getCause());
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void setTransport(Transport transport) {
        this.transport = transport;
    }
}
