package cn.jxufe.rpc.transport.netty;

import cn.jxufe.rpc.transport.command.Header;
import cn.jxufe.rpc.transport.command.ResponseHeader;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.nio.charset.StandardCharsets;

/**
 * @author taojun
 * Data: 2020/4/23
 */
public class ResponseEncoder extends CommandEncoder {
    @Override
    protected void encoderHeader(ChannelHandlerContext channelHandlerContext, Header header, ByteBuf byteBuf) throws Exception{
        super.encoderHeader(channelHandlerContext, header, byteBuf);
        if(header instanceof ResponseHeader) {
            ResponseHeader responseHeader = (ResponseHeader) header;
            byteBuf.writeInt(responseHeader.getCode());
            int errorLength = header.length()-(Integer.BYTES + Integer.BYTES + Integer.BYTES + Integer.BYTES +
                    Integer.BYTES);
            byteBuf.writeInt(errorLength);
            byteBuf.writeBytes(responseHeader.getError() == null ? new byte[0] : responseHeader.getError().getBytes(StandardCharsets.UTF_8));
        } else {
            throw new Exception(String.format("Invalid header type: %s!", header.getClass().getCanonicalName()));
        }
    }
}
