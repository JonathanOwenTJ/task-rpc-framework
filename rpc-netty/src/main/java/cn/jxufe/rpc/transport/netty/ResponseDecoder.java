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
public class ResponseDecoder extends CommandDecoder{

    @Override
    protected Header decodeHeader(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) {
        int type = byteBuf.readInt();
        int version = byteBuf.readInt();
        int requestId = byteBuf.readInt();
        int code = byteBuf.readInt();
        int errorLength = byteBuf.readInt();
        byte[] errorBytes = new byte[errorLength];
        byteBuf.readBytes(errorBytes);
        String error = new String(errorBytes, StandardCharsets.UTF_8);
        return new ResponseHeader(
                type, version, requestId, code, error
        );
    }
}
