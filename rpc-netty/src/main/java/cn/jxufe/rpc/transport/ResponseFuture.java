package cn.jxufe.rpc.transport;

import cn.jxufe.rpc.transport.command.Command;

import java.util.concurrent.CompletableFuture;

/**
 * @author taojun
 * Data: 2020/4/23
 */
public class ResponseFuture {
    private final int requestId;
    private final CompletableFuture<Command> future;
    private final long timestamp;
    
    public ResponseFuture(int requestId, CompletableFuture<Command> future) {
        this.requestId = requestId;
        this.future = future;
        timestamp = System.nanoTime();
    }

    public int getRequestId() {
        return requestId;
    }

    public CompletableFuture<Command> getFuture() {
        return future;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
