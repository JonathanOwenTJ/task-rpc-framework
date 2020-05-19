package cn.jxufe.rpc.transport;

import cn.jxufe.rpc.transport.command.Command;

/**
 * 请求处理器
 * @author taojun
 * Data: 2020/4/24
 */
public interface RequestHandler {
    /**
     * 处理请求
     * @param requestCommand 请求命令
     * @return 响应命令
     */
    Command handle(Command requestCommand);

    /**
     * 支持的请求类型
     * @return
     */
    int type();
}
