package cn.jxufe.rpc.transport.netty;

import cn.jxufe.rpc.transport.InFlightRequests;
import cn.jxufe.rpc.transport.ResponseFuture;
import cn.jxufe.rpc.transport.Transport;
import cn.jxufe.rpc.transport.command.Command;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;


import java.util.concurrent.CompletableFuture;

/**
 * @author taojun
 * Data: 2020/4/23
 */
public class NettyTransport implements Transport {
    
    private final InFlightRequests inFlightRequests;
    private final Channel channel;
    
    public NettyTransport(Channel channel, InFlightRequests inFlightRequests) {
        this.inFlightRequests = inFlightRequests;
        this.channel = channel;
    }

    @Override
    public CompletableFuture<Command> send(Command request) {
        // 创建返回值
        CompletableFuture<Command> completableFuture = new CompletableFuture<>();
        try {
            // 将还在途中的请求放在inFlightRequests中
            inFlightRequests.put(new ResponseFuture(request.getHeader().getRequestId(), completableFuture));
            // 发送命令
            channel.writeAndFlush(request).addListener((ChannelFutureListener) channelFuture -> {
               // 处理发送失败的情况 
                if(!channelFuture.isSuccess()) {
                    completableFuture.completeExceptionally(channelFuture.cause());
                    channel.close();
                }
            });
        } catch (Throwable e) {
            // 处理发送异常
            inFlightRequests.remove(request.getHeader().getRequestId());
            completableFuture.completeExceptionally(e);
            e.printStackTrace();
        } 
        return null;
    }
}
