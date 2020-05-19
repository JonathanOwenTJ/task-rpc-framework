package cn.jxufe.rpc.server;

/**
 * @author taojun
 * Data: 2020/4/24
 */
public interface ServiceProviderRegistry {
    <T> void addServiceProvider(Class<? extends T> serviceClass, T serviceProvider);
}
