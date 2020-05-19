package cn.jxufe.rpc;

import cn.jxufe.rpc.spi.ServiceSupport;

import javax.xml.stream.events.Namespace;
import java.io.Closeable;
import java.net.URI;
import java.util.Collection;

/**
 * @author taojun
 * Data: 2020/4/23
 */
public interface RpcAccessPoint extends Closeable {
    /**
     * 客户端获取远程服务引用
     * @param uri 远程服务地址
     * @param serviceClass 服务的接口类Class
     * @param <T> 服务接口的类型
     * @return 远程服务引用
     */
    <T> T getRemoteService(URI uri, Class<T> serviceClass);

    /**
     * 服务端向rpc中注册服务
     * @param service 服务实例
     * @param serviceClass 服务接口类Class
     * @param <T>服务接口类型
     * @return 服务请求地址
     */
    <T> URI addServiceProvider(T service, Class<T> serviceClass);

    /**
     * 获取注册中心的引用
     * @param nameServiceUri 注册中心URI
     * @return 注册中心引用
     */
    default NameService getNameService(URI nameServiceUri) {
        Collection<NameService> nameServices = ServiceSupport.loadAll(NameService.class);
        for(NameService nameService : nameServices) {
            if(nameService.supportedSchemes().contains(nameServiceUri.getScheme())) {
                nameService.connect(nameServiceUri);
                return nameService;
            }
        }
        return null;
    }

    /**
     * 服务端启动RPC框架，监听接口，开始提供远程服务
     * @return 服务实例，用于程序停止时安全关闭服务
     * @throws Exception
     */
    Closeable startServer() throws Exception;
}
