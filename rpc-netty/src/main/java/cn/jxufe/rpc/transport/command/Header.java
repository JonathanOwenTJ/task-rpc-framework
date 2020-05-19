package cn.jxufe.rpc.transport.command;

/**
 * @author taojun
 * Data: 2020/4/23
 */
public class Header {
    private int requestId;
    private int version;
    private int type;

    public Header() {
    }

    public Header(int requestId, int version, int type) {
        this.requestId = requestId;
        this.version = version;
        this.type = type;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    
    public int length() {
        return Integer.BYTES + Integer.BYTES + Integer.BYTES;
    }
}
