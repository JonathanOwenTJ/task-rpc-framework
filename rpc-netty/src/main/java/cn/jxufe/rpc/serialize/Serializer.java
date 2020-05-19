package cn.jxufe.rpc.serialize;

/**
 * @author taojun
 * Data: 2020/4/23
 */
public interface Serializer<T> {
    /**
     * 计算对象序列化之后的长度
     * @param entry 序列化对象
     * @return 对象序列化的长度
     */
    int size(T entry);

    /**
     * 序列化对象。将对象序列化成字节数组
     * @param entry 序列化对象
     * @param bytes 存序列化数据的字节数组
     * @param offset 数组的偏移量，从这个位置开始存储序列化数据
     * @param length 对象序列化的长度
     */
    void serialize(T entry, byte[] bytes, int offset, int length);

    /**
     * 反序列化对象
     * @param bytes 存放序列化数据的字节数组
     * @param offset 数组的偏移量
     * @param length 对象序列化的长度
     * @return 反序列化之后生成的对象
     */
    T parse(byte[] bytes, int offset, int length);

    /**
     * 用一个字节标识对象类型，每种数据类型应该具有不同的类型值
     * @return
     */
    byte type();

    /**
     * 反序列化对象类型的Class对象
     * @return
     */
    Class<T> getSerializeClass();
}
