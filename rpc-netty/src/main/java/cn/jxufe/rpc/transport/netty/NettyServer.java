package cn.jxufe.rpc.transport.netty;

import cn.jxufe.rpc.transport.RequestHandlerRegistry;
import cn.jxufe.rpc.transport.TransportServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author taojun
 * Data: 2020/4/23
 */
public class NettyServer implements TransportServer {
    
    private int port;
    private EventLoopGroup acceptEventGroup;
    private EventLoopGroup ioEventGroup;
    private Channel channel;
    private RequestHandlerRegistry requestHandlerRegistry;
    
    @Override
    public void start(RequestHandlerRegistry requestHandlerRegistry, int port) throws Exception {
        this.port = port;
        this.requestHandlerRegistry = requestHandlerRegistry;
        EventLoopGroup acceptEventGroup = newEventLoopGroup();
        EventLoopGroup ioEventGroup = newEventLoopGroup();
        ChannelHandler channelHandlerPipline = newChannelHandlerPipline();
        ServerBootstrap serverBootstrap = newBootStrap(channelHandlerPipline, acceptEventGroup, ioEventGroup);
        Channel channel = doBind(serverBootstrap);
        
        this.acceptEventGroup = acceptEventGroup;
        this.ioEventGroup = ioEventGroup;
        this.channel = channel;
    }

    private Channel doBind(ServerBootstrap serverBootstrap) throws Exception{
        return serverBootstrap.bind(port)
                .sync()
                .channel();
    }

    private ServerBootstrap newBootStrap(ChannelHandler channelHandler, EventLoopGroup acceptEventGroup, EventLoopGroup ioEventGroup) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.channel(Epoll.isAvailable() ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
                .group(acceptEventGroup, ioEventGroup)
                .childHandler(channelHandler)
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
        return serverBootstrap;
    }

    private ChannelHandler newChannelHandlerPipline() {
        return new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel channel) throws Exception {
                channel.pipeline()
                        .addLast(new RequestDecoder())
                        .addLast(new ResponseEncoder())
                        .addLast(new RequestInvocation(requestHandlerRegistry));
            }
        };
    }

    private EventLoopGroup newEventLoopGroup() {
        if(Epoll.isAvailable()) {
            return new EpollEventLoopGroup();
        } else {
            return new NioEventLoopGroup();
        }
    }

    @Override
    public void stop() {
        if(acceptEventGroup != null) {
            acceptEventGroup.shutdownGracefully();
        }
        if(ioEventGroup != null) {
            ioEventGroup.shutdownGracefully();
        }
        if(channel != null) {
            channel.close();
        }
    }
}
