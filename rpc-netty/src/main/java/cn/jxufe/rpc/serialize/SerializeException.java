package cn.jxufe.rpc.serialize;

/**
 * @author taojun
 * Data: 2020/4/23
 */
public class SerializeException extends RuntimeException {
    public SerializeException(String msg) {
        super(msg);
    }
    public SerializeException(Throwable throwable) {
        super(throwable);
    }
}
