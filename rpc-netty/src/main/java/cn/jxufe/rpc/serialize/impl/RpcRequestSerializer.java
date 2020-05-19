package cn.jxufe.rpc.serialize.impl;

import cn.jxufe.rpc.client.stubs.RpcRequest;
import cn.jxufe.rpc.serialize.Serializer;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * @author taojun
 * Data: 2020/4/24
 */
public class RpcRequestSerializer implements Serializer<RpcRequest> {
    @Override
    public int size(RpcRequest request) {
        return Integer.BYTES + request.getInterfaceName().getBytes(StandardCharsets.UTF_8).length +
                Integer.BYTES + request.getMethodName().getBytes(StandardCharsets.UTF_8).length +
                Integer.BYTES + request.getSerializedArguments().length;
    }

    @Override
    public void serialize(RpcRequest request, byte[] bytes, int offset, int length) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes, offset, length);
        byte [] tmpBytes = request.getInterfaceName().getBytes(StandardCharsets.UTF_8);
        buffer.putInt(tmpBytes.length);
        buffer.put(tmpBytes);

        tmpBytes = request.getMethodName().getBytes(StandardCharsets.UTF_8);
        buffer.putInt(tmpBytes.length);
        buffer.put(tmpBytes);

        tmpBytes = request.getSerializedArguments();
        buffer.putInt(tmpBytes.length);
        buffer.put(tmpBytes);
    }

    @Override
    public RpcRequest parse(byte[] bytes, int offset, int length) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes, offset, length);
        int len = buffer.getInt();
        byte [] tmpBytes = new byte[len];
        buffer.get(tmpBytes);
        String interfaceName = new String(tmpBytes, StandardCharsets.UTF_8);

        len = buffer.getInt();
        tmpBytes = new byte[len];
        buffer.get(tmpBytes);
        String methodName = new String(tmpBytes, StandardCharsets.UTF_8);

        len = buffer.getInt();
        tmpBytes = new byte[len];
        buffer.get(tmpBytes);
        byte [] serializedArgs = tmpBytes;

        return new RpcRequest(interfaceName, methodName, serializedArgs);
    }

    @Override
    public byte type() {
        return Types.TYPE_METADATA;
    }

    @Override
    public Class<RpcRequest> getSerializeClass() {
        return RpcRequest.class;
    }
}
