package cn.jxufe.rpc.transport.netty;

import cn.jxufe.rpc.transport.command.Command;
import cn.jxufe.rpc.transport.command.Header;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author taojun
 * Data: 2020/4/23
 */
public abstract class CommandEncoder extends MessageToByteEncoder {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        if(!(o instanceof Command)) {
            throw new Exception(String.format("Unknown type: %s!", o.getClass().getCanonicalName()));
        }
        Command command = (Command) o;
        byteBuf.writeInt(Integer.BYTES+command.getHeader().length()+command.getPayload().length);
        encoderHeader(channelHandlerContext, command.getHeader(), byteBuf);
        byteBuf.writeBytes(command.getPayload());
    }

    protected void encoderHeader(ChannelHandlerContext channelHandlerContext, Header header, ByteBuf byteBuf) throws Exception{
        byteBuf.writeInt(header.getType());
        byteBuf.writeInt(header.getVersion());
        byteBuf.writeInt(header.getRequestId());
    }
}
