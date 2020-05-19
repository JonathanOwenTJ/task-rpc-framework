package cn.jxufe.rpc.transport;

import cn.jxufe.rpc.transport.command.Command;

import java.util.concurrent.CompletableFuture;

/**
 * @author taojun
 * Data: 2020/4/23
 */
public interface Transport {
    /**
     * 发送请求命令
     * @param request 请求命令
     * @return 返回值是一个Future
     */
    CompletableFuture<Command> send(Command request);
}
