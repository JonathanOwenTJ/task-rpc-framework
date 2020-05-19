package cn.jxufe.rpc.server;

import cn.jxufe.rpc.hello.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author taojun
 * Data: 2020/4/24
 */
public class HelloServiceImpl implements HelloService {
    private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);
    @Override
    public String hello(String name) {
        logger.info("HelloServiceImpl收到: {}.", name);
        String ret = "Hello, " + name;
        logger.info("HelloServiceImpl返回: {}.", ret);
        return ret;
    }
}
