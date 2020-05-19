package cn.jxufe.rpc.transport.netty;

import cn.jxufe.rpc.transport.command.Header;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author taojun
 * Data: 2020/4/23
 */
public class RequestEncoder extends CommandEncoder{
    @Override
    protected void encoderHeader(ChannelHandlerContext channelHandlerContext, Header header, ByteBuf byteBuf) throws Exception {
        super.encoderHeader(channelHandlerContext, header, byteBuf);
    }
}
